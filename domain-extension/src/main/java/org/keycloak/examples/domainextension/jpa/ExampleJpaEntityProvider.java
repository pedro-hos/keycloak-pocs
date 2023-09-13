/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.examples.domainextension.jpa;

import java.util.Collections;
import java.util.List;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

/**
 * @author <a href="mailto:erik.mulder@docdatapayments.com">Erik Mulder</a>
 * 
 *         Example JpaEntityProvider.
 */
public class ExampleJpaEntityProvider implements JpaEntityProvider {

	public void close() {
		// TODO Auto-generated method stub

	}

	public List<Class<?>> getEntities() {
		return Collections.<Class<?>>singletonList(Company.class);
	}

	public String getChangelogLocation() {
		return "META-INF/example-changelog.xml";
	}

	public String getFactoryId() {
		return ExampleJpaEntityProviderFactory.ID;
	}
}
