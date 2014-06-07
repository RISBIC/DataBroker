/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.core.nano.config;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
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

import com.arjuna.databroker.control.core.nano.GlobalDataFlowFactory;
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

public class LoadXMLConfig
{
    private static final Logger logger = Logger.getLogger(LoadXMLConfig.class.getName());

    public boolean load(InputStream inputStream)
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder        documentBuilder        = documentBuilderFactory.newDocumentBuilder();
            Document               document               = documentBuilder.parse(inputStream);

            return parseDocument(document);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unexpected problem while parsing OSM XML", throwable);

            return false;
        }
    }

    private boolean parseDocument(Document document)
    {
        Element element = document.getDocumentElement();

        return parseDataFlow(element);
    }

    private boolean parseDataFlow(Element element)
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
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
        }

        Map<String, String> metaProperties = new HashMap<String, String>();
        Map<String, String> properties     = new HashMap<String, String>();

        DataFlow dataFlow = null;

        NodeList childNodes = element.getChildNodes();
        int childNodePhaseStart = childNodes.getLength();
        for (int childNodePhase1Index = 0; (dataFlow == null) && (childNodePhase1Index < childNodes.getLength()); childNodePhase1Index++)
        {
            Node childNode = childNodes.item(childNodePhase1Index);

            if ((childNode.getNodeType() == Node.COMMENT_NODE))
                continue;
            else if ((childNode.getNodeType() == Node.TEXT_NODE) && isWhiteSpace(childNode.getNodeValue()))
                continue;
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("metaProperties"))
                valid &= parseMetaProperties((Element) childNode, metaProperties);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("properties"))
                valid &= parseProperties((Element) childNode, properties);
            else
            {
                dataFlow = createDataFlow(name, metaProperties, properties);
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
                valid &= parseDataFlowNodeFactory((Element) childNode, dataFlow);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("dataFlowNode"))
                valid &= parseDataFlowNode((Element) childNode, dataFlow);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("dataFlowLink"))
                valid &= parseDataFlowLink((Element) childNode, dataFlow);
            else
            {
                processUnexpectedNode(childNode);
                valid = false;
            }
        }
        
        return valid;
    }

    @SuppressWarnings("unchecked")
    private boolean parseDataFlowNodeFactory(Element element, DataFlow dataFlow)
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
                valid &= parseMetaProperties((Element) childNode, metaProperties);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("properties"))
                valid &= parseProperties((Element) childNode, properties);
            else
            {
                processUnexpectedNode(childNode);
                valid = false;
            }
        }

        if (valid)
        {
            try
            {
                return deployDataFlowNodeFactory(dataFlow, (Class<? extends DataFlowNodeFactory>) Class.forName(className), name, properties);
            }
            catch (Throwable throwable)
            {
                logger.log(Level.WARNING, "Unable to create data flow", throwable);

                return false;
            }
        }
        else
            return false;
    }

    private boolean parseDataFlowNode(Element element, DataFlow dataFlow)
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
                valid &= parseMetaProperties((Element) childNode, metaProperties);
            else if ((childNode.getNodeType() == Node.ELEMENT_NODE) && childNode.getNodeName().equals("properties"))
                valid &= parseProperties((Element) childNode, properties);
            else
            {
                processUnexpectedNode(childNode);
                valid = false;
            }
        }

        if (valid)
        {
            if ("datasource".equals(type))
                return deployDataFlowNode(dataFlow, factoryName, DataSource.class, name, metaProperties, properties);
            else if ("dataprocessor".equals(type))
                return deployDataFlowNode(dataFlow, factoryName, DataProcessor.class, name, metaProperties, properties);
            else if ("dataservice".equals(type))
                return deployDataFlowNode(dataFlow, factoryName, DataService.class, name, metaProperties, properties);
            else if ("datastore".equals(type))
                return deployDataFlowNode(dataFlow, factoryName, DataStore.class, name, metaProperties, properties);
            else if ("datasink".equals(type))
                return deployDataFlowNode(dataFlow, factoryName, DataSink.class, name, metaProperties, properties);
            else
            {
                logger.log(Level.WARNING, "Unknown data flow node type \"" + type + "\"");
                return false;
            }
        }
        else
            return false;
    }

    private boolean parseDataFlowLink(Element element, DataFlow dataFlow)
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
                valid = false;
            }
        }
        
        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            processUnexpectedNode(childNode);
            valid = false;
        }

        if (valid)
        {
            if ((sourceDataFlowNodeName != null) && (sinkDataFlowNodeName != null))
                return deployDataFlowLink(dataFlow, sourceDataFlowNodeName, sinkDataFlowNodeName);
            else
            {
                logger.log(Level.WARNING, "Unknown source/sink of data flow link \"" + sourceDataFlowNodeName + "\" -> \"" + sourceDataFlowNodeName + "\"");
                return false;
            }
        }
        else
            return false;
    }

    private boolean parseMetaProperties(Element element, Map<String, String> metaProperties)
    {
        boolean valid = true;

        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
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
                valid &= parseProperty((Element) childNode, metaProperties);
            else 
            {
                processUnexpectedNode(childNode);
                valid = false;
            }
        }

        return valid;
    }

    private boolean parseProperties(Element element, Map<String, String> properties)
    {
        boolean valid = true;

        NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++)
        {
            Node attribute = attributes.item(attributeIndex);

            logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
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
                valid &= parseProperty((Element) childNode, properties);
            else 
            {
                processUnexpectedNode(childNode);
                valid = false;
            }
        }

        return valid;
    }

    private boolean parseProperty(Element element, Map<String, String> properties)
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
                value = attribute.getNodeValue();
            else
            {
                logger.log(Level.WARNING, "Unexpected attribute \"" + attribute.getNodeName() + "\" with value \"" + attribute.getNodeValue() + "\"");
                valid = false;
            }
        }

        NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++)
        {
            Node childNode = childNodes.item(childNodeIndex);

            processUnexpectedNode(childNode);
            valid = false;
        }

        if ((name != null) && (value != null))
            properties.put(name, value);
        else
        {
            logger.log(Level.WARNING, "Expected both 'name' and 'value'");
            valid = false;
        }

        return valid;
    }

    private void processUnexpectedNode(Node node)
    {
        if (node.getNodeType() == Node.TEXT_NODE)
        {
            String unexpectedText = node.getNodeValue().trim();
            if (unexpectedText.length() > 16)
                logger.log(Level.WARNING, "Unexpected text \"" + unexpectedText.substring(0, 16) + "...\"");
            else
                logger.log(Level.WARNING, "Unexpected text \"" + unexpectedText + "\"");
        }
        else
            logger.log(Level.WARNING, "Unexpected node \"" + node.getNodeName() + "\" with value \"" + node.getNodeValue() + "\"" + "\" of type \"" + node.getNodeType() + "\"");
    }

    public boolean isWhiteSpace(String text)
    {
        for (char ch: text.toCharArray())
            if (! Character.isWhitespace(ch))
                return false;
        
        return true;
    }

    private DataFlow createDataFlow(String name, Map<String, String> metaProperties, Map<String, String> properties)
    {
        try
        {
            DataFlowFactory dataFlowFactory = GlobalDataFlowFactory.getInstance();

            return dataFlowFactory.createDataFlow(name, metaProperties, properties);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create data flow", throwable);

            return null;
        }
    }

    private <T extends DataFlowNodeFactory> boolean deployDataFlowNodeFactory(DataFlow dataFlow, Class<T> dataFlowNodeFactoryClass, String name, Map<String, String> properties)
    {
        try
        {
            T dataFlowNodeFactory = dataFlowNodeFactoryClass.getDeclaredConstructor(String.class, Map.class).newInstance(name, properties);
            dataFlow.getDataFlowNodeFactoryInventory().addDataFlowNodeFactory(dataFlowNodeFactory);
            
            return true;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create data flow factory", throwable);

            return false;
        }
    }

    private <T extends DataFlowNode> boolean deployDataFlowNode(DataFlow dataFlow, String dataFlowNodeFactoryName, Class<T> dataFlowNodeClass, String dataFlowNodeName, Map<String, String> dataFlowNodeMetaProperties, Map<String, String> dataFlowNodeProperties)
    {
        try
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

                return false;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to create data flow node", throwable);

            return false;
        }
    }

    private <T> boolean deployDataFlowLink(DataFlow dataFlow, String sourceDataFlowNodeName, String sinkDataFlowNodeName)
    {
        try
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
                    dataProvider.addDataConsumer(dataConsumer);
                    return true;
                }
                else
                {
                    logger.log(Level.WARNING, "Unable to find links provider or consumer");
                    return false;
                }
            }
            else
            {
                logger.log(Level.WARNING, "Unable to find source or sink node");
                return false;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem while creating data flow link", throwable);

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
}
