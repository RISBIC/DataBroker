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
    public void example01Validate()
        throws IOException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example01.xml");
        assertNotNull("Problem loading Example01", xmlConfig.loadDataFlow(inputStream, problems, null, null));
        inputStream.close();
        assertEquals("Unexpected problems reported with Example01" , 0, problems.size());
    }

    @Test
    public void example02Validate()
        throws IOException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example02.xml");
        assertNotNull("Problem loading Example02", xmlConfig.loadDataFlow(inputStream, problems, null, null));
        inputStream.close();
        assertEquals("Unexpected problems reported with Example02" , 0, problems.size());
    }

    @Test
    public void example03Validate()
        throws IOException, InterruptedException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example03.xml");
        assertNotNull("Problem loading Example03", xmlConfig.loadDataFlow(inputStream, problems, null, null));
        inputStream.close();
        assertEquals("Unexpected problems reported with Example03" , 0, problems.size());
    }

    @Test
    public void example04Validate()
        throws IOException, InterruptedException
    {
        XMLConfig     xmlConfig   = new XMLConfig();
        List<Problem> problems    = new LinkedList<Problem>();
        InputStream   inputStream = ExamplesTest.class.getResourceAsStream("example04.xml");
        assertNotNull("Problem loading Example04", xmlConfig.loadDataFlow(inputStream, problems, null, null));
        inputStream.close();
        assertEquals("Unexpected problems reported with Example04" , 0, problems.size());
    }
}
