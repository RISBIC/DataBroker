package org.risbic.databroker.openshift.v2; /**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.UUID;

import com.openshift.client.IApplication;
import com.openshift.client.IDomain;
import com.openshift.client.IOpenShiftConnection;
import com.openshift.client.IUser;
import com.openshift.client.OpenShiftConnectionFactory;

/**
 * @author mtaylor
 */

public abstract class BaseOpenShiftApplicationImpl implements OpenShiftApplication
{
   protected String username;

   protected String password;

   protected String domainName;

   protected String applicationName;

   protected IDomain domain;

   protected String clientId;

   private IOpenShiftConnection connection;

   private IApplication app;

   public BaseOpenShiftApplicationImpl(String username, String password, String domain, String appName)
   {
      this.username = username;
      this.password = password;
      this.domainName = domain;
      this.applicationName = appName;
      clientId = UUID.randomUUID().toString();
   }

   protected void checkApplicationExists() throws OpenShiftException
   {
      IApplication app = getDomain().getApplicationByName(getApplicationName());
      if (app != null)
         throw new OpenShiftException("An application with name: " + getApplicationName() + " already exists for this domain");
   }

   protected IOpenShiftConnection getConnection()
   {
      if (connection == null)
      {
         connection = new OpenShiftConnectionFactory().getConnection(getClientId(), username, password);
      }
      return connection;
   }

   protected IDomain getDomain()
   {
      if (domain == null)
      {
         domain = getUser().getDomain(domainName);
      }
      return domain;
   }

   public String getApplicationName()
   {
      return applicationName;
   }

   public String getClientId()
   {
      return clientId;
   }

   protected IUser getUser()
   {
      return getConnection().getUser();
   }

   public IApplication getApplication()
   {
      return app;
   }

   public abstract IApplication createApplication();

   public void start()  throws OpenShiftException
   {
      app.start();
   }

   public void destroy() throws OpenShiftException
   {
      getApplication().destroy();
   }

   public void deploy() throws OpenShiftException
   {
      app = createApplication();
   }

   public String getUrl() throws OpenShiftException
   {
      if (app != null)
      {
         return app.getApplicationUrl();
      }
      throw new OpenShiftException("Application has not been initialized");
   }
}
