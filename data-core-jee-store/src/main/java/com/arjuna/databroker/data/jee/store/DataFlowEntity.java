/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.data.jee.store;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Type;

@Entity
public class DataFlowEntity implements Serializable
{
    private static final long serialVersionUID = -7243768175433344968L;

    public DataFlowEntity()
    {
    }

    public DataFlowEntity(String id, String name, Map<String, String> properties, Set<DataFlowNodeEntity> dataFlowNodes, Set<DataFlowNodeLinkEntity> dataFlowNodeLinks, String className, List<String> dataFlowNodeFactoryClassNames)
    {
        _id                            = id;
        _name                          = name;
        _properties                    = properties;
        _className                     = className;
        _dataFlowNodes                 = dataFlowNodes;
        _dataFlowNodeLinks             = dataFlowNodeLinks;
        _dataFlowNodeFactoryClassNames = dataFlowNodeFactoryClassNames;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        _properties = properties;
    }

    public String getClassName()
    {
        return _className;
    }

    public void setClassName(String className)
    {
        _className = className;
    }

    public Set<DataFlowNodeEntity> getDataFlowNodes()
    {
        return _dataFlowNodes;
    }

    public void setDataFlowNodes(Set<DataFlowNodeEntity> dataFlowNodes)
    {
        _dataFlowNodes = dataFlowNodes;
    }

    public Set<DataFlowNodeLinkEntity> getDataFlowNodeLinks()
    {
        return _dataFlowNodeLinks;
    }

    public void setDataFlowNodeLinks(Set<DataFlowNodeLinkEntity> dataFlowNodeLinks)
    {
        _dataFlowNodeLinks = dataFlowNodeLinks;
    }

    public List<String> getDataFlowNodeFactoryClassNames()
    {
        return _dataFlowNodeFactoryClassNames;
    }

    public void setDataFlowNodeFactoryClassNames(List<String> dataFlowNodeFactoryClassNames)
    {
        _dataFlowNodeFactoryClassNames = dataFlowNodeFactoryClassNames;
    }

    @Id
    @Column(name = "id")
    protected String _id;

    @Column(name = "name")
    protected String _name;

    @Type(type = "serializable")
    @Column(name = "properties")
    protected Map<String, String> _properties;

    @Column(name = "className")
    protected String _className;

    @Column(name = "dataFlowNodes")
    @OneToMany(mappedBy = "_dataFlow", cascade = CascadeType.ALL)
    protected Set<DataFlowNodeEntity> _dataFlowNodes;

    @Column(name = "dataFlowNodeLinks")
    @OneToMany(mappedBy = "_dataFlow", cascade = CascadeType.ALL)
    protected Set<DataFlowNodeLinkEntity> _dataFlowNodeLinks;

    @Type(type = "serializable")
    @Column(name = "dataFlowNodeFactoryClassNames")
    protected List<String> _dataFlowNodeFactoryClassNames;
}
