package io.pedrohos.event.provider.spi;

import java.util.Map;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public class SampleEventListenerProvider implements EventListenerProvider {

    Logger log = Logger.getLogger(SampleEventListenerProvider.class);
    
    KeycloakSession session;
    RealmModel realm;
    
    /**
     * @param session
     */
    public SampleEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.realm = session.getContext().getRealm();
    }
    
    @Deprecated
    public SampleEventListenerProvider() { }

    @Override
    public void close() { }

    @Override
    public void onEvent(Event event) {
        log.info("Event Occurred:" + toString(event));
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        log.info("Admin Event Occurred:" + toString(event));
    }

    private String toString(Event event) {

        StringBuilder sb = new StringBuilder();

        sb.append("type=");

        sb.append(event.getType());

        sb.append(", realmId=");

        sb.append(event.getRealmId());

        sb.append(", clientId=");

        sb.append(event.getClientId());

        sb.append(", userId=");

        sb.append(event.getUserId());
        
        sb.append(", userName=");
        
        sb.append(getUserName(event.getUserId()));

        sb.append(", ipAddress=");

        sb.append(event.getIpAddress());

        if (event.getError() != null) {

            sb.append(", error=");

            sb.append(event.getError());

        }

        if (event.getDetails() != null) {

            for (Map.Entry<String, String> e : event.getDetails().entrySet()) {

                sb.append(", ");

                sb.append(e.getKey());

                if (e.getValue() == null || e.getValue().indexOf(' ') == -1) {

                    sb.append("=");

                    sb.append(e.getValue());

                } else {

                    sb.append("='");

                    sb.append(e.getValue());

                    sb.append("'");

                }

            }

        }

        return sb.toString();

    }

    /**
     * @param userId
     * @return
     */
    private String getUserName(String userId) {
        UserModel user = session.users().getUserById(userId, realm);
        return user.getUsername();
    }

    private String toString(AdminEvent adminEvent) {

        StringBuilder sb = new StringBuilder();

        sb.append("operationType=");

        sb.append(adminEvent.getOperationType());

        sb.append(", realmId=");

        sb.append(adminEvent.getAuthDetails().getRealmId());

        sb.append(", clientId=");

        sb.append(adminEvent.getAuthDetails().getClientId());

        sb.append(", userId=");

        sb.append(adminEvent.getAuthDetails().getUserId());

        sb.append(", ipAddress=");

        sb.append(adminEvent.getAuthDetails().getIpAddress());

        sb.append(", resourcePath=");

        sb.append(adminEvent.getResourcePath());

        if (adminEvent.getError() != null) {

            sb.append(", error=");

            sb.append(adminEvent.getError());

        }

        return sb.toString();

    }

}
