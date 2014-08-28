/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsersUtils
{
    private static final Logger logger = Logger.getLogger(UsersUtils.class.getName());

    public List<UserEntity> listUsers()
    {
        logger.log(Level.FINE, "UsersUtils.listUsers");

        try
        {
            TypedQuery<UserEntity> query = _entityManager.createQuery("SELECT u FROM UserEntity AS u ORDER BY u._userName ASC", UserEntity.class);

            return query.getResultList();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Failed to obtain Users list", throwable);
            return Collections.emptyList();
        }
    }

    public void createUser(String username, String password)
    {
        logger.log(Level.FINE, "UsersUtils.createUser: " + username + ", " + password);

        UserEntity user = new UserEntity(username, password);

        logger.log(Level.FINE, "UsersUtils.createUser: " + user.getUserName() + ", " + user.getPassword());

        _entityManager.persist(user);
    }

    public UserEntity retrieveUser(String userName)
    {
        logger.log(Level.FINE, "UsersUtils.retrieveUser: " + userName);

        return _entityManager.find(UserEntity.class, userName);
    }

    public void replaceUser(String userName, String password)
    {
        logger.log(Level.FINE, "UsersUtils.replaceUser: " + userName + ", " + password);

        UserEntity user = _entityManager.find(UserEntity.class, userName);
        user.setUserName(userName);
        user.setPassword(password);

        _entityManager.merge(user);
    }

    public void removeUser(String userName)
    {
        logger.log(Level.FINE, "UsersUtils.removeUser: " + userName);

        UserEntity user = _entityManager.find(UserEntity.class, userName);

        _entityManager.remove(user);
    }

    @PersistenceContext(unitName="WebPortal")
    private EntityManager _entityManager;
}