/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.xmlconfig;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowFactory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataService;
import com.arjuna.databroker.data.DataSink;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.DataStore;
import com.arjuna.databroker.data.connector.ObservableDataProvider;
import com.arjuna.databroker.data.connector.ObserverDataConsumer;

public class XMLConfig
{
    private static final Logger logger = Logger.getLogger(XMLConfig.class.getName());

    public boolean loadVariables(InputStream inputStream, List<Problem> problems, Map<String, Variable> variableMapping)
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder        documentBuilder        = documentBuilderFactory.newDocumentBuilder();
            Document               document               = documentBuilder.parse(inputStream);

            return parseDocument(document, problems, variableMapping, null);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unexpected problem while parsing DataFlow XML", throwable);
            problems.add(new Problem("Unexpected problem while parsing DataFlow XML : " + throwable));

            return false;
        }
    }

    public boolean loadDataFlow(InputStream inputStream, List<Problem> problems, Map<String, Variable> variableMapping, DataFlowFactory dataFlowFactory)
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder        documentBuilder        = documentBuilderFactory.newDocumentBuilder();
            Document               document               = documentBuilder.parse(inputStream);

            return parseDocument(document, problems, variableMapping, dataFlowFactory);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unexpected problem while parsing DataFlow XML", throwable);
            problems.add(new Problem("Unexpected problem while parsing DataFlow XML : " + throwable));

            return false;
        }
    }

    private boolean parseDocument(Document document, List<Problem> problems, Map<String, Variable> variableMapping, DataFlowFactory dataFlowFactory)
    {
        Element element = document.getDocumentElement();

        return parseDataFlow(element, problems, variableMapping, dataFlowFactory);
    }

    private boolean parseDataFlow(Element element, List<Problem> problems, Map<String, Variable> variableMapping, DataFlowFactory dataFlowFactory)
    {
        boolean valid = true;

        String name = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            if (attribute.getNodeName().equals("name"))
                name = attribute.getNodeValue();
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
            }
        }

        Map<String, String> metaProperties = new HashMap<String, String>();
        Map<String, String> properties     = new HashMap<String, String>();

        DataFlow dataFlow = null;

        boolean  donePhase1 = false;
        NodeList childNodes = element.getChildNodes();
        int childNodePhaseStart = childNodes.getLength();
        for (int childNodePhase1Index = 0; (! donePhase1) && (childNodePhase1Index < childNodes.getLength()); childNodePhase1Index++)
        {
            Node childNode = childNodes.item(childNodePhase1Index);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("variables"))
                valid &= parseVariables((Element) childNode, problems, variableMapping, dataFlowFactory == null);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("metaProperties"))
                valid &= parseMetaProperties((Element) childNode, problems, variableMapping, metaProperties);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("properties"))
                valid &= parseProperties((Element) childNode, problems, variableMapping, properties);
            else
            {
                name = variableSubstitute(name, variableMapping);

                dataFlow   = createDataFlow(name, metaProperties, properties, problems, dataFlowFactory);
                donePhase1 = true;
                childNodePhaseStart = childNodePhase1Index;
            }
        }

        for (int childNodePhase2Index = childNodePhaseStart; childNodePhase2Index < childNodes.getLength(); childNodePhase2Index++)
        {
            Node childNode = childNodes.item(childNodePhase2Index);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("dataFlowNodeFactory"))
                valid &= parseDataFlowNodeFactory((Element) childNode, problems, variableMapping, dataFlow);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("dataFlowNode"))
                valid &= parseDataFlowNode((Element) childNode, problems, variableMapping, dataFlow);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("dataFlowLink"))
                valid &= parseDataFlowLink((Element) childNode, problems, dataFlow);
            else
            {
                processUnexpectedNode(childNode, problems);
                valid = false;
            }
        }

        return valid;
    }

    @SuppressWarnings("unchecked")
    private boolean parseDataFlowNodeFactory(Element element, List<Problem> problems, Map<String, Variable> variableMapping, DataFlow dataFlow)
    {
        boolean valid = true;

        String name      = null;
        String className = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            if (attribute.getNodeName().equals("name"))
                name = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("class"))
                className = attribute.getNodeValue();
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
                valid = false;
            }
        }

        Map<String, String> metaProperties = new HashMap<String, String>();
        Map<String, String> properties     = new HashMap<String, String>();

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("metaProperties"))
                valid &= parseMetaProperties((Element) childNode, problems, variableMapping, metaProperties);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("properties"))
                valid &= parseProperties((Element) childNode, problems, variableMapping, properties);
            else
            {
                processUnexpectedNode(childNode, problems);
                valid = false;
            }
        }

        if (valid)
        {
            try
            {
                return deployDataFlowNodeFactory(dataFlow, problems, (Class<? extends DataFlowNodeFactory>) Class.forName(className), name, properties);
            }
            catch (Throwable throwable)
            {
                logger.log(Level.WARNING, "Unable to create data flow", throwable);
                problems.add(new Problem("Unable to create data flow: " + throwable));

                return false;
            }
        }
        else
            return false;
    }

    private boolean parseDataFlowNode(Element element, List<Problem> problems, Map<String, Variable> variableMapping, DataFlow dataFlow)
    {
        boolean valid = true;

        String name        = null;
        String type        = null;
        String factoryName = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            if (attribute.getNodeName().equals("name"))
                name = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("type"))
                type = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("factoryName"))
                factoryName = attribute.getNodeValue();
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
                valid = false;
            }
        }

        Map<String, String> metaProperties = new HashMap<String, String>();
        Map<String, String> properties     = new HashMap<String, String>();

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("metaProperties"))
                valid &= parseMetaProperties((Element) childNode, problems, variableMapping, metaProperties);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("properties"))
                valid &= parseProperties((Element) childNode, problems, variableMapping, properties);
            else
            {
                processUnexpectedNode(childNode, problems);
                valid = false;
            }
        }

        if (valid)
        {
            if ("datasource".equals(type))
                return deployDataFlowNode(dataFlow, problems, factoryName, DataSource.class, name, metaProperties, properties);
            else if ("dataprocessor".equals(type))
                return deployDataFlowNode(dataFlow, problems, factoryName, DataProcessor.class, name, metaProperties, properties);
            else if ("dataservice".equals(type))
                return deployDataFlowNode(dataFlow, problems, factoryName, DataService.class, name, metaProperties, properties);
            else if ("datastore".equals(type))
                return deployDataFlowNode(dataFlow, problems, factoryName, DataStore.class, name, metaProperties, properties);
            else if ("datasink".equals(type))
                return deployDataFlowNode(dataFlow, problems, factoryName, DataSink.class, name, metaProperties, properties);
            else
            {
                logger.log(Level.WARNING, "Unknown data flow node type \"" + type + "\"");
                problems.add(new Problem("Unknown data flow node type \"" + type + "\""));
                return false;
            }
        }
        else
            return false;
    }

    private boolean parseDataFlowLink(Element element, List<Problem> problems, DataFlow dataFlow)
    {
        boolean valid = true;

        String sourceDataFlowNodeName = null;
        String sinkDataFlowNodeName   = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            if (attribute.getNodeName().equals("sourceDataFlowNode"))
                sourceDataFlowNodeName = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("sinkDataFlowNode"))
                sinkDataFlowNodeName = attribute.getNodeValue();
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
                valid = false;
            }
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            processUnexpectedNode(childNode, problems);
            valid = false;
        }

        if (valid)
        {
            if ((sourceDataFlowNodeName != null) && (sinkDataFlowNodeName != null))
                return deployDataFlowLink(dataFlow, problems, sourceDataFlowNodeName, sinkDataFlowNodeName);
            else
            {
                logger.log(Level.WARNING, "Unknown source/sink of data flow link \"" + sourceDataFlowNodeName + "\" -> \"" + sourceDataFlowNodeName + "\"");
                problems.add(new Problem("Unknown source/sink of data flow link \"" + sourceDataFlowNodeName + "\" -> \"" + sourceDataFlowNodeName + "\""));
                return false;
            }
        }
        else
            return false;
    }

    private boolean parseVariables(Element element, List<Problem> problems, Map<String, Variable> variableMapping, boolean update)
    {
        boolean valid = true;

        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
            problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
            valid = false;
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("variable"))
                valid &= parseVariable((Element) childNode, problems, variableMapping, update);
            else 
            {
                processUnexpectedNode(childNode, problems);
                valid = false;
            }
        }

        return valid;
    }

    private boolean parseMetaProperties(Element element, List<Problem> problems, Map<String, Variable> variableMapping, Map<String, String> metaProperties)
    {
        boolean valid = true;

        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
            problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
            valid = false;
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("metaProperty"))
                valid &= parseProperty((Element) childNode, problems, variableMapping, metaProperties);
            else 
            {
                processUnexpectedNode(childNode, problems);
                valid = false;
            }
        }

        return valid;
    }

    private boolean parseProperties(Element element, List<Problem> problems, Map<String, Variable> variableMapping, Map<String, String> properties)
    {
        boolean valid = true;

        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
            problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
            valid = false;
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("property"))
                valid &= parseProperty((Element) childNode, problems, variableMapping, properties);
            else 
            {
                processUnexpectedNode(childNode, problems);
                valid = false;
            }
        }

        return valid;
    }

    private boolean parseVariable(Element element, List<Problem> problems, Map<String, Variable> variableMapping, boolean update)
    {
        boolean valid = true;

        String name         = null;
        String label        = null;
        String defaultValue = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            if (attribute.getNodeName().equals("name"))
                name = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("label"))
                label = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("defaultValue"))
                defaultValue = attribute.getNodeValue();
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
                valid = false;
            }
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            processUnexpectedNode(childNode, problems);
            valid = false;
        }

        if ((name != null) && (label != null) && (defaultValue != null))
            variableMapping.put(name, new Variable(name, label, defaultValue));
        else
        {
            logger.log(Level.WARNING, "Expected both 'name', 'label' and 'defaultValue'");
            problems.add(new Problem("Expected both 'name', 'label' and 'defaultValue'"));
            valid = false;
        }

        return valid;
    }

    private boolean parseProperty(Element element, List<Problem> problems, Map<String, Variable> variableMapping, Map<String, String> properties)
    {
        boolean valid = true;

        String name  = null;
        String value = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            if (attribute.getNodeName().equals("name"))
                name = attribute.getNodeValue();
            else if (attribute.getNodeName().equals("value"))
                value = variableSubstitute(attribute.getNodeValue(), variableMapping);
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                problems.add(new Problem("Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\""));
                valid = false;
            }
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            processUnexpectedNode(childNode, problems);
            valid = false;
        }

        if ((name != null) && (value != null))
            properties.put(name, value);
        else
        {
            logger.log(Level.WARNING, "Expected both 'name' and 'value'");
            problems.add(new Problem("Expected both 'name' and 'value'"));
            valid = false;
        }

        return valid;
    }

    private void processUnexpectedNode(Node node, List<Problem> problems)
    {
        if (node.getNodeType() == Node.TEXT_NODE)
        {
            String unexpectedText = node.getNodeValue().trim();
            if (unexpectedText.length() > 16)
            {
                logger.log(Level.WARNING, "Unexpected text \"" + unexpectedText.substring(0, 16) + "...\"");
                problems.add(new Problem("Unexpected text \"" + unexpectedText.substring(0, 16) + "...\""));
            }
            else
            {
                logger.log(Level.WARNING, "Unexpected text \"" + unexpectedText + "\"");
                problems.add(new Problem("Unexpected text \"" + unexpectedText + "\""));
            }
        }
        else
        {
            logger.log(Level.WARNING, "Unexpected node \"" + node.getNodeName() + "\" with value \"" + node.getNodeValue() + "\"" + "\" of type \"" + node.getNodeType() + "\"");
            problems.add(new Problem("Unexpected node \"" + node.getNodeName() + "\" with value \"" + node.getNodeValue() + "\"" + "\" of type \"" + node.getNodeType() + "\""));
        }
    }

    public boolean isWhiteSpace(String text)
    {
        for (char ch: text.toCharArray())
            if (! Character.isWhitespace(ch))
                return false;

        return true;
    }

    private DataFlow createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties, List<Problem> problems, DataFlowFactory dataFlowFactory)
    {
        try
        {
            if (dataFlowFactory != null)
                return dataFlowFactory.createDataFlow(name, metaProperties, properties);
            else
                return null;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create data flow", throwable);
            problems.add(new Problem("Unable to create data flow: " + throwable));

            return null;
        }
    }

    private <T extends DataFlowNodeFactory> boolean deployDataFlowNodeFactory(DataFlow dataFlow, List<Problem> problems, Class<T> dataFlowNodeFactoryClass, String name, Map<String, String> properties)
    {
        try
        {
            if (dataFlow != null)
            {
                T dataFlowNodeFactory = dataFlowNodeFactoryClass.getDeclaredConstructor(String.class, Map.class).newInstance(name, properties);
                dataFlow.getDataFlowNodeFactoryInventory().addDataFlowNodeFactory(dataFlowNodeFactory);
            }

            return true;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create data flow factory", throwable);
            problems.add(new Problem("Unable to create data flow factory: " + throwable));

            return false;
        }
    }

    private <T extends DataFlowNode> boolean deployDataFlowNode(DataFlow dataFlow, List<Problem> problems, String dataFlowNodeFactoryName, Class<T> dataFlowNodeClass, String dataFlowNodeName, Map<String, String> dataFlowNodeMetaProperties, Map<String, String> dataFlowNodeProperties)
    {
        try
        {
            if (dataFlow != null)
            {
                DataFlowNodeFactory dataFlowNodeFactory = dataFlow.getDataFlowNodeFactoryInventory().getDataFlowNodeFactory(dataFlowNodeFactoryName);

                T dataFlowNode = dataFlowNodeFactory.createDataFlowNode(dataFlowNodeName, dataFlowNodeClass, dataFlowNodeMetaProperties, dataFlowNodeProperties);
                if (dataFlowNode != null)
                {
                     dataFlow.getDataFlowNodeInventory().addDataFlowNode(dataFlowNode);
                    return true;
                }
                else
                {
                    logger.log(Level.WARNING, "Unable to create data flow node");
                    problems.add(new Problem("Unable to create data flow node"));

                    return false;
                }
            }
            else
                return true;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create data flow node", throwable);
            problems.add(new Problem("Unable to create data flow node:" + throwable));

            return false;
        }
    }

    private <T> boolean deployDataFlowLink(DataFlow dataFlow, List<Problem> problems, String sourceDataFlowNodeName, String sinkDataFlowNodeName)
    {
        try
        {
            if (dataFlow != null)
            {
                DataFlowNode sourceDataFlowNode = dataFlow.getDataFlowNodeInventory().getDataFlowNode(sourceDataFlowNodeName);
                DataFlowNode sinkDataFlowNode   = dataFlow.getDataFlowNodeInventory().getDataFlowNode(sinkDataFlowNodeName);

                if ((sourceDataFlowNode != null) && (sinkDataFlowNode != null))
                {
                    @SuppressWarnings("unchecked")
                    Class<T> linkClass = (Class<T>) getLinkClass(sourceDataFlowNode, sinkDataFlowNode);

                    DataProvider<T> dataProvider = getSourceProvider(sourceDataFlowNode, linkClass);
                    DataConsumer<T> dataConsumer = getSinkConsumer(sinkDataFlowNode, linkClass);

                    if ((dataProvider != null) && (dataConsumer != null))
                    {
                        if ((dataProvider instanceof ObservableDataProvider) && (dataConsumer instanceof ObserverDataConsumer))
                        {
                            ObservableDataProvider<T> observableDataProvider = (ObservableDataProvider<T>) dataProvider;
                            ObserverDataConsumer<T>   observerDataConsumer   = (ObserverDataConsumer<T>) dataConsumer;

                            observableDataProvider.addDataConsumer(observerDataConsumer);
                            return true;
                        }
                        else
                            return true;
                    }
                    else
                    {
                        logger.log(Level.WARNING, "Unable to find links provider or consumer");
                        problems.add(new Problem("Unable to find links provider or consumer"));
                        return false;
                    }
                }
                else
                {
                    logger.log(Level.WARNING, "Unable to find source or sink node");
                    problems.add(new Problem("Unable to find source or sink node"));
                    return false;
                }
            }
            else
                return true;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem while creating data flow link", throwable);
            problems.add(new Problem("Problem while creating data flow link: " + throwable));

            return false;
        }
    }

    private Class<?> getLinkClass(DataFlowNode sourceDataFlowNode, DataFlowNode sinkDataFlowNode)
    {
        Collection<Class<?>> sourceDataClasses = getSourceProviderClasses(sourceDataFlowNode);
        Collection<Class<?>> sinkDataClasses   = getSinkConsumerClasses(sinkDataFlowNode);

        if ((sourceDataClasses != null) && (sinkDataClasses != null))
        {
            for (Class<?> sourceDataClass: sourceDataClasses)
                for (Class<?> sinkDataClass: sinkDataClasses)
                    if (sourceDataClass.equals(sinkDataClass))
                        return sourceDataClass;

            return null;
        }
        else
            return null;
    }

    private Collection<Class<?>> getSourceProviderClasses(DataFlowNode sourceDataFlowNode)
    {
        if (sourceDataFlowNode instanceof DataSource)
            return ((DataSource) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataService)
            return ((DataService) sourceDataFlowNode).getDataProviderDataClasses();
        else if (sourceDataFlowNode instanceof DataStore)
            return ((DataStore) sourceDataFlowNode).getDataProviderDataClasses();
        else
            return Collections.emptySet();
    }

    private Collection<Class<?>> getSinkConsumerClasses(DataFlowNode sinkDataFlowNode)
    {
        if (sinkDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataService)
            return ((DataService) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataStore)
            return ((DataStore) sinkDataFlowNode).getDataConsumerDataClasses();
        else if (sinkDataFlowNode instanceof DataSink)
            return ((DataSink) sinkDataFlowNode).getDataConsumerDataClasses();
        else
            return Collections.emptySet();
    }

    private <T> DataProvider<T> getSourceProvider(DataFlowNode sourceDataFlowNode, Class<T> dataClass)
    {
        if (sourceDataFlowNode instanceof DataSource)
            return ((DataSource) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataService)
            return ((DataService) sourceDataFlowNode).getDataProvider(dataClass);
        else if (sourceDataFlowNode instanceof DataStore)
            return ((DataStore) sourceDataFlowNode).getDataProvider(dataClass);
        else
            return null;
    }

    private <T> DataConsumer<T> getSinkConsumer(DataFlowNode sinkDataFlowNode, Class<T> dataClass)
    {
        if (sinkDataFlowNode instanceof DataProcessor)
            return ((DataProcessor) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataService)
            return ((DataService) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataStore)
            return ((DataStore) sinkDataFlowNode).getDataConsumer(dataClass);
        else if (sinkDataFlowNode instanceof DataSink)
            return ((DataSink) sinkDataFlowNode).getDataConsumer(dataClass);
        else
            return null;
    }

    private String variableSubstitute(String text, Map<String, Variable> variableMapping)
    {
        StringBuffer result = new StringBuffer();

        int substituteStart = text.indexOf("${");
        int substituteEnd   = 0;
        while ((substituteStart != -1) && (substituteEnd != -1))
        {
            result.append(text.substring(substituteEnd, substituteStart));

            substituteEnd = text.indexOf("}", substituteStart);
            if (substituteEnd != -1)
            {
                String   variableName = text.substring(substituteStart + 2 , substituteEnd);
                Variable variable     = variableMapping.get(variableName);
                if (variable != null)
                {
                    result.append(variable.getValue());
                    substituteEnd++;
                }
                else
                    result.append(text.substring(substituteStart, substituteEnd));

                substituteStart = text.indexOf("${", substituteEnd);
            }
        }
        if (substituteEnd != -1)
            result.append(text.substring(substituteEnd, text.length()));
        else
            result.append(text.substring(substituteStart, text.length()));

        return result.toString();
    }
}
