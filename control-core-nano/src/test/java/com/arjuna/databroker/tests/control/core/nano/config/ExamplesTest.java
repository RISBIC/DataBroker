/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.core.nano.config;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import com.arjuna.databroker.control.core.nano.config.XMLConfigParse;
import static org.junit.Assert.*;

public class ExamplesTest
{
    @Test
    public void example01()
        throws IOException
    {
        XMLConfigParse xmlConfigParse = new XMLConfigParse();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example01.xml");
        assertTrue("Problem in Example01", xmlConfigParse.parse(inputStream));
        inputStream.close();
    }

    @Test
    public void example02()
        throws IOException
    {
        XMLConfigParse xmlConfigParse = new XMLConfigParse();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example02.xml");
        assertTrue("Problem in Example02", xmlConfigParse.parse(inputStream));
        inputStream.close();
    }

    @Test
    public void example03()
        throws IOException
    {
        XMLConfigParse xmlConfigParse = new XMLConfigParse();
        InputStream inputStream = ExamplesTest.class.getResourceAsStream("example03.xml");
        assertTrue("Problem in Example03", xmlConfigParse.parse(inputStream));
        inputStream.close();
    }
}
