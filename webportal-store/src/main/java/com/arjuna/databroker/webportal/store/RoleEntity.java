/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="DBWP_Roles")
public class RoleEntity implements Serializable
{
    private static final long serialVersionUID = 7647165834710624621L;

    public RoleEntity()
    {
    }

    public RoleEntity(String userName, String roleName, String roleGroup)
    {
        _userName  = userName;
        _roleName  = roleName;
        _roleGroup = roleGroup;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getUsername()
    {
        return _userName;
    }

    public void setUserame(String userName)
    {
        _userName = userName;
    }

    public String getRoleName()
    {
        return _roleName;
    }

    public void setRoleName(String roleName)
    {
        _roleName = roleName;
    }

    public String getRoleGroup()
    {
        return _roleGroup;
    }

    public void setRoleGroup(String roleGroup)
    {
        _roleGroup = roleGroup;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    protected String _id;

    @Column(name = "userName", length = 64)
    private String _userName;

    @Column(name = "roleName", length = 32)
    private String _roleName;

    @Column(name = "roleGroup", length = 32)
    private String _roleGroup;
}
