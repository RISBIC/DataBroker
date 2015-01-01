/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.metadata.rdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils
{
    public static String loadInputStream(InputStream inputStream)
        throws IOException
    {
        StringBuffer   result = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = reader.readLine();
        while (line != null)
        {
            result.append(line).append('\n');
            line = reader.readLine();
        }

        inputStream.close();

        return result.toString();
    }
}
