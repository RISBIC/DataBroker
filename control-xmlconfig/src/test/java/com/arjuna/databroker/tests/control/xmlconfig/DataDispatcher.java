/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.databroker.tests.control.xmlconfig;

import com.arjuna.databroker.data.DataFlowNode;

public interface DataDispatcher<T> extends DataFlowNode
{
    public void dispatch(T data);
}