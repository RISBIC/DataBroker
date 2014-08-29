/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.arjuna.databroker.webportal.store.UserEntity;
import com.arjuna.databroker.webportal.store.UsersUtils;

@SessionScoped
@ManagedBean(name="users")
public class UsersMO implements Serializable
{
    private static final long serialVersionUID = 6786191553605314899L;

    public UsersMO()
    {
        _users = new LinkedList<UserVO>();
    }

    public List<UserVO> getUsers()
    {
        return _users;
    }

    public String doLoad()
    {
        load();

        return "/users/users?faces-redirect=true";
    }

    public String doRemove(String userName)
    {
        _usersUtils.removeUser(userName);

        load();

        return "/users/users?faces-redirect=true";
    }

    public boolean load()
    {
        try
        {
            synchronized (_users)
            {
                List<UserEntity> users = _usersUtils.listUsers();

                _users.clear();
                for (UserEntity user: users)
                    _users.add(new UserVO(user.getUserName(), user.getPassword() != null, Collections.<String>emptyList()));
            }

            return true;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            _users.clear();

            return false;
        }
    }

    private List<UserVO> _users;

    @EJB
    private UsersUtils _usersUtils;
}
