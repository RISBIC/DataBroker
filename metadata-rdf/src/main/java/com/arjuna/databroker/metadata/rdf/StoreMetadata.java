/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.metadata.rdf;

import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.metadata.Metadata;
import com.arjuna.databroker.metadata.MetadataContentStore;
import com.arjuna.databroker.metadata.MutableMetadata;
import com.arjuna.databroker.metadata.selectors.MetadataContentsSelector;
import com.arjuna.databroker.metadata.selectors.MetadataSelector;
import com.arjuna.databroker.metadata.selectors.MetadatasSelector;
import com.arjuna.databroker.metadata.rdf.selectors.RDFMetadataContentsSelector;
import com.arjuna.databroker.metadata.rdf.selectors.StoreMetadataSelector;
import com.arjuna.databroker.metadata.rdf.selectors.StoreMetadatasSelector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class StoreMetadata implements Metadata
{
    private static final Logger logger = Logger.getLogger(StoreMetadata.class.getName());

    public StoreMetadata(MetadataContentStore metadataContentStore, String id)
    {
        _metadataContentStore = metadataContentStore;
        _id                   = id;
    }

    @Override
    public String getId()
    {
        return _id;
    }

    @Override
    public MetadataSelector parent()
    {
        return new StoreMetadataSelector(_metadataContentStore, _metadataContentStore.getParentId(_id));
    }

    @Override
    public MetadatasSelector children()
    {
        return new StoreMetadatasSelector(_metadataContentStore, _metadataContentStore.getChildrenIds(_id));
    }

    @Override
    public MetadataSelector description()
    {
        return new StoreMetadataSelector(_metadataContentStore, _metadataContentStore.getDescriptionId(_id));
    }

    @Override
    public MetadataContentsSelector contents()
    {
        try
        {
            String content = _metadataContentStore.getContent(_id);

            Model model = ModelFactory.createDefaultModel();
            Reader reader = new StringReader(content);
            model.read(reader, null);
            reader.close();
    
            return new RDFMetadataContentsSelector(model);
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Unable to process RDF", throwable);
            return new RDFMetadataContentsSelector(null);
        }
    }

    public String getRawRDF()
    {
        return _metadataContentStore.getContent(_id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MutableMetadata> M mutableClone(Class<M> c)
    {
        if (c.isAssignableFrom(getClass()))
            return (M) this;
        else if (c.isAssignableFrom(StoreMutableMetadata.class))
            return (M) new StoreMutableMetadata(_metadataContentStore, _id);
        else
            return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends MetadataSelector> S selector(Class<S> c)
        throws IllegalArgumentException
    {
        if (c.isAssignableFrom(StoreMetadataSelector.class))
            return (S) new StoreMetadataSelector(_metadataContentStore, _id);
        else
            return null;
    }

    protected MetadataContentStore _metadataContentStore;
    protected String               _id;
}