/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.core.nano.config;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import com.arjuna.databroker.control.core.nano.config.LoadXMLConfig;
import static org.junit.Assert.*;

public class ExamplesTest
{
    @Test
    public void example01()
        throws IOException
    {
        LoadXMLConfig xmlConfigParse = new LoadXMLConfig();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example01.xml");
        assertTrue("Problem in Example01", xmlConfigParse.load(inputStream));
        inputStream.close();
    }

    @Test
    public void example02()
        throws IOException
    {
        LoadXMLConfig xmlConfigParse = new LoadXMLConfig();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example02.xml");
        assertTrue("Problem in Example02", xmlConfigParse.load(inputStream));
        inputStream.close();
    }

    @Test
    public void example03()
        throws IOException, InterruptedException
    {
        LoadXMLConfig xmlConfigParse = new LoadXMLConfig();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example03.xml");
        assertTrue("Problem in Example03", xmlConfigParse.load(inputStream));
        inputStream.close();
        
        Thread.sleep(10000);
    }

    @Test
    public void example04()
        throws IOException, InterruptedException
    {
        LoadXMLConfig xmlConfigParse = new LoadXMLConfig();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example04.xml");
        assertTrue("Problem in Example04", xmlConfigParse.load(inputStream));
        inputStream.close();
        
        Thread.sleep(10000);
    }
}
