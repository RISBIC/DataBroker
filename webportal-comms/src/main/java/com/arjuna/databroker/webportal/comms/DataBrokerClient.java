/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.webportal.comms;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.jboss.resteasy.util.HttpResponseCodes;
import com.arjuna.databroker.control.comms.DataBrokerDTO;
import com.arjuna.databroker.control.comms.DataFlowNodeFactoryDTO;

@Stateless
public class DataBrokerClient
{
    private static final Logger logger = Logger.getLogger(DataBrokerClient.class.getName());

    public DataBrokerSummary getDataBrokerSummaries(String serviceRootURL)
        throws RequestProblemException
    {
        logger.log(Level.FINE, "DataBrokerClient.getDataBrokerSummaries: " + serviceRootURL);

        try
        {
            ClientRequest request = new ClientRequest(serviceRootURL + "/control/ws/databroker");
            request.accept(MediaType.APPLICATION_JSON);

            ClientResponse<DataBrokerDTO> response = request.get(new GenericType<DataBrokerDTO>() {});

            if (response.getStatus() == HttpResponseCodes.SC_OK)
            {
                DataBrokerDTO dataBrokerDTO = response.getEntity();

                List<DataFlowSummary> dataFlowSummaries = new LinkedList<DataFlowSummary>();
                for (String name: dataBrokerDTO.getDataFlowNames())
                    dataFlowSummaries.add(new DataFlowSummary(name));

                List<DataFlowNodeFactorySummary> dataFlowNodeFactorySummaries = new LinkedList<DataFlowNodeFactorySummary>();
                for (DataFlowNodeFactoryDTO dataFlowNodeFactory: dataBrokerDTO.getDataFlowNodeFactories())
                    dataFlowNodeFactorySummaries.add(new DataFlowNodeFactorySummary(dataFlowNodeFactory.getAttribute(), dataFlowNodeFactory.getProperties(), dataFlowNodeFactory.isDataSourceFactory(), dataFlowNodeFactory.isDataSinkFactory(), dataFlowNodeFactory.isDataProcessorFactory(), dataFlowNodeFactory.isDataServiceFactory(), dataFlowNodeFactory.isDataStoreFactory()));

                return new DataBrokerSummary(dataFlowSummaries, dataFlowNodeFactorySummaries);
            }
            else if (response.getStatus() == HttpResponseCodes.SC_BAD_REQUEST)
                throw new RequestProblemException(response.getEntity(String.class));
            else
            {
                logger.log(Level.WARNING, "DataBrokerClient.getDataBrokerSummaries: status = " + response.getStatus());

                throw new RequestProblemException("Problem during request of data broker summary");
            }
        }
        catch (RequestProblemException requestProblemException)
        {
            throw requestProblemException;
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Problem in 'DataBrokerClient.getDataBrokerSummaries'", throwable);

            throw new RequestProblemException("Problem during request of data broker summary");
        }
    }
}
