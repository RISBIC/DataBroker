package org.risbic.databroker.openshift.v2.test; /**
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

import org.junit.Test;
import org.risbic.databroker.openshift.v2.DrupalApplication;
import org.risbic.databroker.openshift.v2.OpenShiftException;
import org.risbic.databroker.openshift.v2.PHPApplication;
import org.risbic.databroker.openshift.v2.WordPressApplication;

import java.util.UUID;

/**
 * @author mtaylor
 */

public class TestDeployDrupals extends OpenShiftTestBase
{
   @Test
   public void testDeployWordPress() throws OpenShiftException
   {
      PHPApplication drupals = new DrupalApplication(username, password, domain, generateAppName());
      try
      {
         drupals.deploy();
         drupals.start();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         drupals.destroy();
      }
   }
}
