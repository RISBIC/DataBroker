/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import org.junit.Test;
import com.arjuna.databroker.control.xmlconfig.Problem;
import com.arjuna.databroker.control.xmlconfig.XMLConfig;
import com.arjuna.databroker.control.xmlconfig.Variable;
import static org.junit.Assert.*;

public class ExamplesTest
{
    @Test
    public void example01Validate()
        throws IOException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example01.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertNotNull("Problem loading Example01", valid);
        assertEquals("Unexpected variables with Example01" , 0, variableMapping.size());
        assertEquals("Unexpected problems reported with Example01" , 0, problems.size());
    }

    @Test
    public void example02Validate()
        throws IOException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example02.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertNotNull("Problem loading Example02", valid);
        assertEquals("Unexpected variables with Example02" , 0, variableMapping.size());
        assertEquals("Unexpected problems reported with Example02" , 0, problems.size());
    }

    @Test
    public void example03Validate()
        throws IOException, InterruptedException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example03.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertNotNull("Problem loading Example03", valid);
        assertEquals("Unexpected variables with Example03" , 0, variableMapping.size());
        assertEquals("Unexpected problems reported with Example03" , 0, problems.size());
    }

    @Test
    public void example04Validate()
        throws IOException, InterruptedException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example04.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertNotNull("Problem loading Example04", valid);
        assertEquals("Unexpected variables with Example04" , 0, variableMapping.size());
        assertEquals("Unexpected problems reported with Example04" , 0, problems.size());
    }

    @Test
    public void example05Validate()
        throws IOException, InterruptedException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example05.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertNotNull("Problem loading Example05", valid);
        assertEquals("Unexpected variables with Example05" , 0, variableMapping.size());
        assertEquals("Unexpected problems reported with Example05" , 0, problems.size());
    }

    @Test
    public void example06Validate()
        throws IOException, InterruptedException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example06.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertTrue("Problem loading Example06", valid);
        assertEquals("Unexpected variables with Example06" , 2, variableMapping.size());
        Variable name1Variable = variableMapping.get("name1");
        assertNotNull("Expected variable of 'name1", name1Variable);
        assertEquals("Unexpected name for variable 'name1", "name1", name1Variable.getName());
        assertEquals("Unexpected label for variable 'name1", "label1", name1Variable.getLabel());
        assertEquals("Unexpected value for variable 'name1", "defaultvalue1", name1Variable.getValue());
        Variable name2Variable = variableMapping.get("name2");
        assertNotNull("Expected variable of 'name2", name2Variable);
        assertEquals("Unexpected name for variable 'name2", "name2", name2Variable.getName());
        assertEquals("Unexpected label for variable 'name2", "label2", name2Variable.getLabel());
        assertEquals("Unexpected value for variable 'name2", "defaultvalue2", name2Variable.getValue());
        assertEquals("Unexpected problems reported with Example06" , 0, problems.size());
    }

    @Test
    public void example07Validate()
        throws IOException, InterruptedException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        List<Problem>         problems        = new LinkedList<Problem>();
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        InputStream           inputStream     = ExamplesTest.class.getResourceAsStream("example07.xml");
        boolean               valid           = xmlConfig.loadDataFlow(inputStream, problems, variableMapping, null);
        inputStream.close();

        assertTrue("Problem loading Example07", valid);
        assertEquals("Unexpected variables with Example07" , 1, variableMapping.size());
        Variable nameVariable = variableMapping.get("name");
        assertNotNull("Expected variable of 'name1", nameVariable);
        assertEquals("Unexpected name for variable 'name", "name", nameVariable.getName());
        assertEquals("Unexpected label for variable 'name", "label", nameVariable.getLabel());
        assertEquals("Unexpected value for variable 'name", "defaultvalue", nameVariable.getValue());
        assertEquals("Unexpected problems reported with Example07" , 0, problems.size());
    }
}
