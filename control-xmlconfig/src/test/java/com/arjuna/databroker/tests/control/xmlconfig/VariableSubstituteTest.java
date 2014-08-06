/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.arjuna.databroker.control.xmlconfig.XMLConfig;
import com.arjuna.databroker.control.xmlconfig.Variable;
import static org.junit.Assert.*;

public class VariableSubstituteTest
{
    @Test
    public void variableSubstitute00()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "";
        String                precessedText   = "";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute01()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "test";
        String                precessedText   = "test";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute02()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "${test}";
        String                precessedText   = "${test}";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute03()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "${test}${test}";
        String                precessedText   = "${test}${test}";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute04()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "X${test}Y${test}Z";
        String                precessedText   = "X${test}Y${test}Z";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute05()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "${test}";
        String                precessedText   = "abc";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        variableMapping.put("test", new Variable("test", "test label", "abc"));

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute06()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "${test}${test}";
        String                precessedText   = "abcabc";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        variableMapping.put("test", new Variable("test", "test label", "abc"));

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute07()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "X${test}Y${test}Z";
        String                precessedText   = "XabcYabcZ";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        variableMapping.put("test", new Variable("test", "test label", "abc"));

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute08()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "${test1}${test2}";
        String                precessedText   = "abcdef";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        variableMapping.put("test1", new Variable("test1", "test1 label", "abc"));
        variableMapping.put("test2", new Variable("test2", "test2 label", "def"));

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    @Test
    public void variableSubstitute09()
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "X${test1}Y${test2}Z";
        String                precessedText   = "XabcYdefZ";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();
        variableMapping.put("test1", new Variable("test1", "test1 label", "abc"));
        variableMapping.put("test2", new Variable("test2", "test2 label", "def"));

        assertEquals(precessedText, variableSubstitute(xmlConfig, rawText, variableMapping));
    }

    private String variableSubstitute(XMLConfig xmlConfig, String text, Map<String, Variable> variableMapping)
    {
        try
        {
            Method method = XMLConfig.class.getDeclaredMethod("variableSubstitute", new Class[] { String.class, Map.class });
            method.setAccessible(true);
            return (String) method.invoke(xmlConfig, new Object[] { text, variableMapping });
        }
        catch (Throwable throwable)
        {
            fail("Unable to invoke 'variableSubstitute' method");
            return null;
        }
    }
}
