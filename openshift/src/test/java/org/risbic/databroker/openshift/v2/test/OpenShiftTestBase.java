/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.risbic.databroker.openshift.v2.test;

import org.junit.Before;

import java.io.FileReader;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OpenShiftTestBase
{
   private static final String OPENSHIFT_TEST_PROPERTIES = "/openshift.properties";

   private static final String OPENSHIFT_USERNAME_KEY = "username";

   private static final String OPENSHIFT_PASSWORD_KEY = "password";

   private static final String OPENSHIFT_DOMAIN_KEY = "domain";

   String username;

   String password;

   String domain;

   private static final Logger logger = Logger.getLogger(OpenShiftTestBase.class.getName());

   @Before
   public void setup()
   {
      try
      {
         String file = getClass().getResource(OPENSHIFT_TEST_PROPERTIES).getFile();
         try(FileReader fr = new FileReader(file))
         {
            Properties properties = new Properties();
            properties.load(fr);
            username = properties.getProperty(OPENSHIFT_USERNAME_KEY);
            password = properties.getProperty(OPENSHIFT_PASSWORD_KEY);
            domain = properties.getProperty(OPENSHIFT_DOMAIN_KEY);
         }
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING, "Unable to parse OpenShift properties file. + '" + OPENSHIFT_TEST_PROPERTIES + "'");
      }
   }

   protected String generateAppName()
   {
      return UUID.randomUUID().toString().replace("-", "");
   }
}
