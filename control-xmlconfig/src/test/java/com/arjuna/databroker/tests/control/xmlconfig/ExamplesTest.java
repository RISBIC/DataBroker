/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.LinkedList;
import org.junit.Test;
import com.arjuna.databroker.control.xmlconfig.Problem;
import com.arjuna.databroker.control.xmlconfig.XMLConfig;
import static org.junit.Assert.*;

public class ExamplesTest
{
    @Test
    public void example01()
        throws IOException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example01.xml");
        assertTrue("Problem in Example01", xmlConfig.load(inputStream, problems, null));
        inputStream.close();
    }

    @Test
    public void example02()
        throws IOException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example02.xml");
        assertTrue("Problem in Example02", xmlConfig.load(inputStream, problems, null));
        inputStream.close();
    }

    @Test
    public void example03()
        throws IOException, InterruptedException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example03.xml");
        assertTrue("Problem in Example03", xmlConfig.load(inputStream, problems, null));
        inputStream.close();
        
        Thread.sleep(10000);
    }

    @Test
    public void example04()
        throws IOException, InterruptedException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example04.xml");
        assertTrue("Problem in Example04", xmlConfig.load(inputStream, problems, null));
        inputStream.close();
        
        Thread.sleep(10000);
    }
}
