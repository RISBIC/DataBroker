/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data;

import java.util.List;
import java.util.Map;

/**
 * DataFlowNodeFactory is an interface through which data flow node instances can be created.
 *
 * <p>The normally sequence of operations to create a data flow instances, is:
 * <ul>
 * <li>call {@code getClasses};</li>
 * <li>select a data flow node class;</li>
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
public interface DataFlowNodeFactory
{
    /**
     * Returns the name of the data flow node factory.
     *
     * @return the name of the data flow node factory
     */
    public String getName();

    /**
     * Returns the properties of the data flow node factory.
     *
     * @return the properties of the data flow node factory
     */
    public Map<String, String> getProperties();

    /**
     * Returns the classes of the data flow node available.
     *
     * @return the classes of the data flow node available
     */
    public List<Class<? extends DataFlowNode>> getClasses();

    /**
     * Returns the list of meta property names required.
     * 
     * @param dataFlowNodeClass the class of the data flow node
     * @return the list of meta property names required
     * @throws InvalidClassException thrown if the value of data flow node class is invalid
     */
    public <T extends DataFlowNode> List<String> getMetaPropertyNames(Class<T> dataFlowNodeClass)
        throws InvalidClassException;

    /**
     * Returns the list of property names required.
     * 
     * @param dataFlowNodeClass the class of the data flow node
     * @param metaProperties the meta properties required to obtain the property names
     * @return the list of property names required
     * @throws InvalidClassException thrown if the value of data flow node class is invalid
     * @throws InvalidMetaPropertyException thrown if a meta property is invalid
     * @throws MissingMetaPropertyException thrown if a meta property is missing
     */
    public <T extends DataFlowNode> List<String> getPropertyNames(Class<T> dataFlowNodeClass, Map<String, String> metaProperties)
        throws InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException;

    /**
     * 
     * @param name the intended name of the data flow node
     * @param dataFlowNodeClass the class of the data flow node
     * @param metaProperties the meta properties required to create the data flow node
     * @param properties the properties required to create the data flow node
     * @return the created data flow node instance
     * @throws InvalidNameException thrown if the value of name is invalid, e.g. already used
     * @throws InvalidClassException thrown if the value of data flow node class is invalid
     * @throws InvalidMetaPropertyException thrown if a meta property is invalid
     * @throws MissingMetaPropertyException thrown if a meta property is missing
     * @throws InvalidPropertyException thrown if a property is invalid
     * @throws MissingPropertyException thrown if a property is missing
     */
    public <T extends DataFlowNode> T createDataFlowNode(String name, Class<T> dataFlowNodeClass, Map<String, String> metaProperties, Map<String, String> properties)
        throws InvalidNameException, InvalidClassException, InvalidMetaPropertyException, MissingMetaPropertyException, InvalidPropertyException, MissingPropertyException;
}
