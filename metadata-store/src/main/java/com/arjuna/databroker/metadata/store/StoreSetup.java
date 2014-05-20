/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StoreSetup implements Serializable
{
    private static final long serialVersionUID = 2568567962494253287L;

    public StoreSetup()
    {
    }

    @PostConstruct
    public void setup()
    {
        MetadataEntity      descriptionSchemaMetadata           = new MetadataEntity(null, loadRDFResource("com/arjuna/databroker/metadata/store/DescriptionSchema.rdf"));
        MetadataEntity      dataSourceSchemaMetadata            = new MetadataEntity(null, loadRDFResource("com/arjuna/databroker/metadata/store/DataSourceSchema.rdf"));
        MetadataEntity      speedManagementNetworkMetadata      = new MetadataEntity(null, loadRDFResource("com/arjuna/databroker/metadata/store/SpeedManagementNetwork.rdf"));
        AccessControlEntity descriptionSchemaAccessControl      = new AccessControlEntity(descriptionSchemaMetadata, null, null, false, true, false, false, false, false);
        AccessControlEntity dataSourceSchemaAccessControl       = new AccessControlEntity(dataSourceSchemaMetadata, null, null, false, true, false, false, false, false);
        AccessControlEntity speedManagementNetworkAccessControl = new AccessControlEntity(speedManagementNetworkMetadata, null, null, true, true, false, false, false, false);

        _entityManager.persist(descriptionSchemaMetadata);
        _entityManager.persist(dataSourceSchemaMetadata);
        _entityManager.persist(speedManagementNetworkMetadata);
        _entityManager.persist(descriptionSchemaAccessControl);
        _entityManager.persist(dataSourceSchemaAccessControl);
        _entityManager.persist(speedManagementNetworkAccessControl);
    }

    private String loadRDFResource(String rdfResourceName)
    {
        try
        {
            StringBuffer rdfText        = new StringBuffer();
            InputStream  rdfInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(rdfResourceName);

            byte[] buffer     = new byte[4096];
            int    readLength = rdfInputStream.read(buffer);
            while (readLength >= 0)
            {
                rdfText.append(new String(buffer, 0, readLength, "UTF-8"));
                readLength = rdfInputStream.read(buffer);
            }

            return rdfText.toString();
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
            
            return null;
        }
    }

    @PersistenceContext(unitName="Metadata")
    private EntityManager _entityManager;
}
