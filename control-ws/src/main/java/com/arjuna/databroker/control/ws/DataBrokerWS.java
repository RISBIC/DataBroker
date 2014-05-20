/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.control.ws;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import com.arjuna.databroker.control.comms.DataBrokerDTO;
import com.arjuna.databroker.control.comms.DataFlowNodeFactoryDTO;
import com.arjuna.databroker.data.DataFlow;
import com.arjuna.databroker.data.DataFlowInventory;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataFlowNodeFactory;
import com.arjuna.databroker.data.DataFlowNodeFactoryInventory;
import com.arjuna.databroker.data.DataProcessor;
import com.arjuna.databroker.data.DataService;
import com.arjuna.databroker.data.DataSink;
import com.arjuna.databroker.data.DataSource;
import com.arjuna.databroker.data.DataStore;

@Path("/databroker")
@Stateless
public class DataBrokerWS
{
    private static final Logger logger = Logger.getLogger(DataBrokerWS.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataBrokerDTO getDataBrokerSummariesJSON()
    {
        logger.log(Level.FINE, "DataBrokerWS.getDataBrokerSummariesJSON");

        if ((_dataFlowInventory != null) && (_dataFlowNodeFactoryInventory != null))
        {
            DataBrokerDTO dataBrokerDTO = new DataBrokerDTO();

            List<String> dataFlowNames = new LinkedList<String>();
            for (DataFlow dataFlow: _dataFlowInventory.getDataFlows())
                dataFlowNames.add(dataFlow.getName());
            dataBrokerDTO.setDataFlowNames(dataFlowNames);

            List<DataFlowNodeFactoryDTO> dataFlowNodeFactories = new LinkedList<DataFlowNodeFactoryDTO>();
            for (DataFlowNodeFactory dataFlowNodeFactory: _dataFlowNodeFactoryInventory.getDataFlowNodeFactorys())
            {
                List<Class<? extends DataFlowNode>> dataFlowNodeFactoryClasses = dataFlowNodeFactory.getClasses();

                Boolean dataSourceFactory    = dataFlowNodeFactoryClasses.contains(DataSource.class);
                Boolean dataSinkFactory      = dataFlowNodeFactoryClasses.contains(DataSink.class);;
                Boolean dataProcessorFactory = dataFlowNodeFactoryClasses.contains(DataProcessor.class);;
                Boolean dataServiceFactory   = dataFlowNodeFactoryClasses.contains(DataService.class);;
                Boolean dataStoreFactory     = dataFlowNodeFactoryClasses.contains(DataStore.class);;

                dataFlowNodeFactories.add(new DataFlowNodeFactoryDTO(dataFlowNodeFactory.getName(), dataSourceFactory, dataSinkFactory, dataProcessorFactory, dataServiceFactory, dataStoreFactory));
            }
            dataBrokerDTO.setDataFlowNodeFactories(dataFlowNodeFactories);

            return dataBrokerDTO;
        }
        else
            throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @EJB(name="DataFlowInventory")
    private DataFlowInventory _dataFlowInventory;

    @EJB(name="DataFlowNodeFactoryInventory")
    private DataFlowNodeFactoryInventory _dataFlowNodeFactoryInventory;
}