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

import com.openshift.client.IApplication;
import com.openshift.client.cartridge.IStandaloneCartridge;
import com.openshift.client.cartridge.query.LatestStandaloneCartridge;
import com.openshift.client.cartridge.query.LatestVersionOf;

/**
 * @author mtaylor
 */

public abstract class PHPApplication extends BaseOpenShiftApplicationImpl
{
   public PHPApplication(String username, String password, String domain, String app)
   {
      super(username, password, domain, app);
   }

   @Override
   public IApplication createApplication()
   {
      IStandaloneCartridge phpCartridge = new LatestStandaloneCartridge(IStandaloneCartridge.NAME_PHP).get(getUser());

      IApplication app = getDomain().createApplication(applicationName, phpCartridge, null, null,getGitURL(), Integer.MAX_VALUE, LatestVersionOf.mySQL().get(getUser()));
      return app;
   }

   public abstract String getGitURL();
}
