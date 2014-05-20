/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.Map;
import java.util.List;

/**
 * DataFlowFactory is an interface through which data flow instances can be created.
 *
 * <p>The normally sequence of operations to create a data flow instances, is:
 * <ul>
 * <li>call {@code getMetaPropertyNames};</li>
 * <li>values are provided to form metaProperties;</li>
 * <li>call {@code getPropertyNames};</li>
 * <li>values are provided to form properties;</li>
 * <li>desired name is obtained;</li>
 * <li>call {@code createDataFlow}.</li>
 * </ul>
 * <p>It is also possible to retain the values of metaProperties and properties, to form a type of template, from which 
 * multiple data flow instances could be created, using {@code createDataFlow}.
 * 
 * <p>It is possible, if not probable, that meta property names and property names could be empty, so no values
 * need be specified.
 */
public interface DataFlowFactory
{
    /**
     * Returns the name of the data flow factory.
     *
     * @return the name of the data flow factory
     */
    public String getName();

    /**
     * Returns the properties of the data flow factory.
     *
     * @return the properties of the data flow factory
     */
    public Map<String, String> getProperties();

    /**
     * Returns the list of meta property names required.
     *
     * @return the list of meta property names required
     */
    public List<String> getMetaPropertyNames();

    /**
     * Returns the list of property names required.
     * 
     * @param metaProperties the meta properties required to obtain the property names
     * @return the list of property names required
     * @throws InvalidMetaPropertyException thrown if a meta property is invalid
     * @throws MissingMetaPropertyException thrown if a meta property is missing
     * @throws InvalidPropertyException thrown if a property is invalid
     * @throws MissingPropertyException thrown if a property is missing
     */
    public List<String> getPropertyNames(Map<String, String> metaProperties)
        throws InvalidMetaPropertyException, MissingMetaPropertyException;

    /**
     * Creates a data flow instance.
     * 
     * @param name the intended name of the data flow
     * @param metaProperties the meta properties required to create the data flow
     * @param properties the properties required to create the data flow
     * @return the created data flow instance
     * @throws InvalidNameException thrown if the value of name is invalid, e.g. already used
     * @throws InvalidMetaPropertyException thrown if a meta property is invalid
     * @throws MissingMetaPropertyException thrown if a meta property is missing
     * @throws InvalidPropertyException thrown if a property is invalid
     * @throws MissingPropertyException thrown if a property is missing
     */
    public DataFlow createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException;
}
