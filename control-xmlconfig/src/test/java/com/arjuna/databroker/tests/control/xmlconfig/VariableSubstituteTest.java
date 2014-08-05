/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.arjuna.databroker.control.xmlconfig.XMLConfig;
import com.arjuna.databroker.control.xmlconfig.Variable;
import static org.junit.Assert.*;

public class VariableSubstituteTest
{
    @Test
    public void variableSubstitute01()
        throws IOException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "test";
        String                precessedText   = "test";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, xmlConfig.variableSubstitute(rawText, variableMapping));
    }

    @Test
    public void variableSubstitute02()
        throws IOException
    {
        XMLConfig             xmlConfig       = new XMLConfig();
        String                rawText         = "${test}";
        String                precessedText   = "${test}";
        Map<String, Variable> variableMapping = new HashMap<String, Variable>();

        assertEquals(precessedText, xmlConfig.variableSubstitute(rawText, variableMapping));
    }
}
