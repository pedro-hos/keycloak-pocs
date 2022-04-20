package io.pedrohos.user.provider.spi;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import io.pedrohos.user.provider.model.UserAdapter;
import io.pedrohos.user.provider.model.UserEntity;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
@Stateful
@Local(EjbExampleUserStorageProvider.class)
public class EjbExampleUserStorageProvider implements UserStorageProvider, 
													  UserLookupProvider, 
													  UserRegistrationProvider,
													  UserQueryProvider, 
													  CredentialInputUpdater, 
													  CredentialInputValidator, 
													  OnUserCache {

	private static final Logger logger = Logger.getLogger(EjbExampleUserStorageProvider.class);
	public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";

	@PersistenceContext
	protected EntityManager em;
	protected ComponentModel model;
	protected KeycloakSession session;

	public void setModel(ComponentModel model) {
		this.model = model;
	}

	public void setSession(KeycloakSession session) {
		this.session = session;
	}

	public String getPassword(UserModel user) {

		String password = null;

		if (user instanceof CachedUserModel) {
			logger.info("Getting password from cached user model");
			password = (String) ((CachedUserModel) user).getCachedWith().get(PASSWORD_CACHE_KEY);
		} else if (user instanceof UserAdapter) {
			logger.info("Getting password from DB (UserAdapter)");
			password = ((UserAdapter) user).getPassword();
		}

		return password;
	}

	public UserAdapter getUserAdapter(UserModel user) {
		UserAdapter adapter = null;
		
		if (user instanceof CachedUserModel) {
			logger.info("Getting user from cached user model");
			adapter = (UserAdapter) ((CachedUserModel) user).getDelegateForUpdate();
		} else {
			logger.info("Getting user from DB (UserAdapter)");
			adapter = (UserAdapter) user;
		}
		
		return adapter;
	}

	@Remove
	@Override
	public void close() {
		logger.info("<<<<< Calling close()");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onCache(RealmModel realm, CachedUserModel user, UserModel delegate) {
		
		String password = ((UserAdapter) delegate).getPassword();

		if (password != null) {
			user.getCachedWith().put(PASSWORD_CACHE_KEY, password);
		}
	}

	@Override
	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		return supportsCredentialType(credentialType) && getPassword(user) != null;
	}

	@Override
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		
		logger.info("Starting password validation.");
		
		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}

		UserCredentialModel cred = (UserCredentialModel) input;
		String password = getPassword(user);

		return password != null && password.equals(cred.getValue());
	}

	@Override
	public boolean supportsCredentialType(String credentialType) {
		return CredentialModel.PASSWORD.equals(credentialType);
	}

	@Override
	public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {

		logger.info("Updating Credentials...");
		
		if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
			return false;
		}

		UserCredentialModel cred = (UserCredentialModel) input;
		UserAdapter adapter = getUserAdapter(user);
		adapter.setPassword(cred.getValue());

		return true;
	}

	@Override
	public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {

		if (!supportsCredentialType(credentialType)) {
			return;
		}

		getUserAdapter(user).setPassword(null);

	}

	@Override
	public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {

		if (getUserAdapter(user).getPassword() != null) {
			Set<String> set = new HashSet<>();
			set.add(CredentialModel.PASSWORD);
			return set;
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public int getUsersCount(RealmModel realm) {
		Object count = em.createNamedQuery("getUserCount").getSingleResult();
		return ((Number) count).intValue();
	}

	@Override
	public List<UserModel> getUsers(RealmModel realm) {
		return getUsers(realm, -1, -1);
	}

	@Override
	public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
		TypedQuery<UserEntity> query = em.createNamedQuery("getAllUsers", UserEntity.class);

		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}

		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}

		List<UserEntity> results = query.getResultList();
		List<UserModel> users = new LinkedList<>();

		for (UserEntity entity : results) {
			users.add(new UserAdapter(session, realm, model, entity));
		}

		return users;
	}

	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm) {
		return searchForUser(search, realm, -1, -1);
	}

	@Override
	public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
		TypedQuery<UserEntity> query = em.createNamedQuery("searchForUser", UserEntity.class);
		query.setParameter("search", "%" + search.toLowerCase() + "%");

		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}

		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}

		List<UserEntity> results = query.getResultList();
		List<UserModel> users = new LinkedList<>();

		for (UserEntity entity : results) {
			users.add(new UserAdapter(session, realm, model, entity));
		}

		return users;
	}

	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult,
			int maxResults) {
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
		return Collections.emptyList();
	}

	@Override
	public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
		return Collections.emptyList();
	}

	@Override
	public UserModel addUser(RealmModel realm, String username) {
		UserEntity entity = new UserEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setUsername(username);
		em.persist(entity);
		logger.info("added user: " + username);
		return new UserAdapter(session, realm, model, entity);
	}

	@Override
	public boolean removeUser(RealmModel realm, UserModel user) {
		String persistenceId = StorageId.externalId(user.getId());
		UserEntity entity = em.find(UserEntity.class, persistenceId);
		if (entity == null)
			return false;
		em.remove(entity);
		return true;
	}

	@Override
	public UserModel getUserById(String id, RealmModel realm) {
		logger.info("getUserById: " + id);
		String persistenceId = StorageId.externalId(id);
		UserEntity entity = em.find(UserEntity.class, persistenceId);
		if (entity == null) {
			logger.info("could not find user by id: " + id);
			return null;
		}
		return new UserAdapter(session, realm, model, entity);
	}

	@Override
	public UserModel getUserByUsername(String username, RealmModel realm) {
		logger.info("getUserByUsername: " + username);
		TypedQuery<UserEntity> query = em.createNamedQuery("getUserByUsername", UserEntity.class);
		query.setParameter("username", username);
		List<UserEntity> result = query.getResultList();
		if (result.isEmpty()) {
			logger.info("could not find username: " + username);
			return null;
		}

		return new UserAdapter(session, realm, model, result.get(0));
	}

	@Override
	public UserModel getUserByEmail(String email, RealmModel realm) {
		TypedQuery<UserEntity> query = em.createNamedQuery("getUserByEmail", UserEntity.class);
		query.setParameter("email", email);
		List<UserEntity> result = query.getResultList();
		if (result.isEmpty())
			return null;
		return new UserAdapter(session, realm, model, result.get(0));
	}
}
