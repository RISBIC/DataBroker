/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.store;

import java.math.BigInteger;
import java.security.MessageDigest;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsersUtils
{
    private static final Logger logger = Logger.getLogger(UsersUtils.class.getName());

    private static final String ADMIN_ROLENAME   = "admin";
    private static final String USER_ROLENAME    = "user";
    private static final String GUEST_ROLENAME   = "guest";
    private static final String ROLES_ROLEGROUPS = "Roles";

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

    public void createUser(String userName, String password, Boolean adminRole, Boolean userRole, Boolean guestRole)
        throws IllegalArgumentException
    {
        logger.log(Level.FINE, "UsersUtils.createUser: " + userName);

        if (_entityManager.find(UserEntity.class, userName) != null)
            throw new IllegalArgumentException("User aleady exists");

        if (password != null)
        {
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                messageDigest.update(password.getBytes("UTF-8"));
                password = String.format("%040x", new BigInteger(1, messageDigest.digest()));
            }
            catch (Throwable throwable)
            {
                logger.log(Level.WARNING, "Problem encoding password", throwable);
                throw new IllegalArgumentException("Problem encoding password");
            }
        }
                
        UserEntity userEntity = new UserEntity(userName, password);
        _entityManager.persist(userEntity);

        if (adminRole)
        {
            RoleEntity adminRoleEntity = new RoleEntity(userName, ADMIN_ROLENAME, ROLES_ROLEGROUPS);
            _entityManager.persist(adminRoleEntity);
        }

        if (userRole)
        {
            RoleEntity userRoleEntity = new RoleEntity(userName, USER_ROLENAME, ROLES_ROLEGROUPS);
            _entityManager.persist(userRoleEntity);
        }

        if (guestRole)
        {
            RoleEntity guestRoleEntity = new RoleEntity(userName, GUEST_ROLENAME, ROLES_ROLEGROUPS);
            _entityManager.persist(guestRoleEntity);
        }
    }

    public UserEntity retrieveUser(String userName)
    {
        logger.log(Level.FINE, "UsersUtils.retrieveUser: " + userName);

        return _entityManager.find(UserEntity.class, userName);
    }

    public void changeUser(String userName, String password, Boolean adminRole, Boolean userRole, Boolean guestRole)
        throws IllegalArgumentException
    {
        logger.log(Level.FINE, "UsersUtils.replaceUser: " + userName);

        if (_entityManager.find(UserEntity.class, userName) == null)
            throw new IllegalArgumentException("User does not exist");

        if (password != null)
        {
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                messageDigest.update(password.getBytes("UTF-8"));
                password = String.format("%040x", new BigInteger(1, messageDigest.digest()));
            }
            catch (Throwable throwable)
            {
                logger.log(Level.WARNING, "Problem encoding password", throwable);
                throw new IllegalArgumentException("Problem encoding password");
            }
        }

        UserEntity userEntity = _entityManager.find(UserEntity.class, userName);
        userEntity.setPassword(password);
        _entityManager.merge(userEntity);
        
        Query rolesDelete = _entityManager.createQuery("DELETE FROM RoleEntity WHERE _userName = :userName");
        rolesDelete.setParameter("userName", userName);
        rolesDelete.executeUpdate();

        if (adminRole)
        {
            RoleEntity adminRoleEntity = new RoleEntity(userName, ADMIN_ROLENAME, ROLES_ROLEGROUPS);
            _entityManager.persist(adminRoleEntity);
        }

        if (userRole)
        {
            RoleEntity userRoleEntity = new RoleEntity(userName, USER_ROLENAME, ROLES_ROLEGROUPS);
            _entityManager.persist(userRoleEntity);
        }

        if (guestRole)
        {
            RoleEntity guestRoleEntity = new RoleEntity(userName, GUEST_ROLENAME, ROLES_ROLEGROUPS);
            _entityManager.persist(guestRoleEntity);
        }
    }

    public void removeUser(String userName)
    {
        logger.log(Level.FINE, "UsersUtils.removeUser: " + userName);

        UserEntity userEntity = _entityManager.find(UserEntity.class, userName);

        Query rolesDelete = _entityManager.createQuery("DELETE FROM RoleEntity WHERE _userName = :userName");
        rolesDelete.setParameter("userName", userName);
        rolesDelete.executeUpdate();

        _entityManager.remove(userEntity);
    }

    @PersistenceContext(unitName="WebPortal")
    private EntityManager _entityManager;
}