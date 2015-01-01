/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DBWP_Users")
public class UserEntity implements Serializable
{
    private static final long serialVersionUID = 8031089526548541183L;

    public UserEntity()
    {
    }

    public UserEntity(String userName, String password)
    {
        _userName = userName;
        _password = password;
    }

    public String getUserName()
    {
        return _userName;
    }

    public void setUserName(String userName)
    {
        _userName = userName;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String password)
    {
        _password = password;
    }

    @Id
    @Column(name = "userName", length = 64)
    private String _userName;

    @Column(name = "password", length = 128)
    private String _password;
}
