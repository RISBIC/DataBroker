/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

function DataFlowEdit(operationsId, dataFlowControl)
{
    this.operations      = document.getElementById(operationsId);
    this.dataFlowControl = dataFlowControl;
    this.item            = null;

    this.linkStart = undefined;
}

DataFlowEdit.prototype.doUnselect = function(item)
{
    if (item instanceof DataFlowGlyph)
    {
        this.operations.innerHTML = "";
        item.bodyStyle   = DataFlowGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = DataFlowGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof ProcessorGlyph)
    {
        item.bodyStyle   = ProcessorGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = ProcessorGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof SourceGlyph)
    {
        item.bodyStyle   = SourceGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = SourceGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof SinkGlyph)
    {
        item.bodyStyle   = SinkGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = SinkGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof ServiceGlyph)
    {
        item.bodyStyle   = ServiceGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = ServiceGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof StoreGlyph)
    {
        item.bodyStyle   = StoreGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = StoreGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof ProducerGlyph)
    {
        item.bodyStyle   = ProducerGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = ProducerGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof ConsumerGlyph)
    {
        item.bodyStyle   = ConsumerGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = ConsumerGlyph.DEFAULT_BORDERSTYLE;
    }
    else if (item instanceof LinkGlyph)
    {
        item.bodyStyle   = LinkGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = LinkGlyph.DEFAULT_BORDERSTYLE;
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.doSelect = function(item)
{
    this.item = item;
    
    var linkStart = this.linkStart;

    if ((this.linkStart != undefined) && (this.linkStart instanceof ProducerGlyph))
    {
        this.linkStart.bodyStyle   = ProducerGlyph.DEFAULT_BODYSTYLE;
        this.linkStart.borderStyle = ProducerGlyph.DEFAULT_BORDERSTYLE;
        this.linkStart             = undefined;
    }

    if (item instanceof DataFlowGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Flow</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Add Data Source\" onclick=\"dataFlowEdit.addSourceToDataFlow()\">" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Add Data Processor\" onclick=\"dataFlowEdit.addProcessorToDataFlow()\">" + 
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Add Data Sink\" onclick=\"dataFlowEdit.addSinkToDataFlow()\">" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Add Data Service\" onclick=\"dataFlowEdit.addServiceToDataFlow()\">" + 
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Add Data Store\" onclick=\"dataFlowEdit.addStoreToDataFlow()\">"; 
        item.borderStyle = "#cc7733";
        selectDataFlowNode("DataFlow: " + null);
    }
    else if (item instanceof ProcessorGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Processor</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Remove\" onclick=\"dataFlowEdit.removeProcessorFromDataFlow()\">";
        item.borderStyle = "#cc7733";
        selectDataFlowNode("Processor: " + item.id);
    }
    else if (item instanceof SourceGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Source</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Remove\" onclick=\"dataFlowEdit.removeSourceFromDataFlow()\">";
        item.borderStyle = "#cc7733";
        selectDataFlowNode("Source: " + item.id);
    }
    else if (item instanceof SinkGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Sink</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Remove\" onclick=\"dataFlowEdit.removeSinkFromDataFlow()\">";
        item.borderStyle = "#cc7733";
        selectDataFlowNode("Sink: " + item.id);
    }
    else if (item instanceof ServiceGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Service</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Remove\" onclick=\"dataFlowEdit.removeServiceFromDataFlow()\">";
        item.borderStyle = "#cc7733";
        selectDataFlowNode("Service: " + item.id);
    }
    else if (item instanceof StoreGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Store</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Remove\" onclick=\"dataFlowEdit.removeStoreFromDataFlow()\">";
        item.borderStyle = "#cc7733";
        selectDataFlowNode("Store: " + item.id);
    }
    else if (item instanceof ProducerGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Producer</span>" +
                                    "<input class=\"dataflow-operation\" type=\"button\" value=\"Create Data Link\" onclick=\"dataFlowEdit.createLink()\">";
        item.borderStyle = "#cc7733";
    }
    else if (item instanceof ConsumerGlyph)
    {
        if (linkStart == undefined)
        {
            this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Consumer</span>" +
                                        "<input class=\"dataflow-operation\" type=\"button\" value=\"Remove Data Links\" onclick=\"dataFlowEdit.removeLinks()\">";
            item.borderStyle = "#cc7733";
        }
        else
        {
            this.operations.innerHTML = "";
            var link = new LinkGlyph(linkStart, this.item);
            this.dataFlowControl.dataFlow.links.push(link);
        }
    }
    else if (item instanceof LinkGlyph)
    {
        this.operations.innerHTML = "<span class=\"dataflow-operations-label\">Data Link</span>";
        item.lineStyle = "#cc7733";
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.addProcessorToDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof DataFlowGlyph))
    {
        var process = new ProcessorGlyph(Math.random(), this.item);

        this.item.processors.push(process);
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.removeProcessorFromDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof ProcessorGlyph))
    {
        var processorIndex = this.item.parent.processors.indexOf(this.item);
        if (processorIndex != -1)
        {
            DataFlowEdit.removeLinkFromProducer(this.item.parent, this.item.producer);
            DataFlowEdit.removeLinkToConsumer(this.item.parent, this.item.consumer);
            this.item.parent.processors.splice(processorIndex, 1);
        }
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.addSourceToDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof DataFlowGlyph))
    {
        var source = new SourceGlyph(Math.random(), this.item);

       this.item.sources.push(source);
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.removeSourceFromDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof SourceGlyph))
    {
        var sourceIndex = this.item.parent.sources.indexOf(this.item);
        if (sourceIndex != -1)
        {
            DataFlowEdit.removeLinkFromProducer(this.item.parent, this.item.producer);
            this.item.parent.sources.splice(sourceIndex, 1);
        }
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.addSinkToDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof DataFlowGlyph))
    {
        var sink = new SinkGlyph(Math.random(), this.item);

        this.item.sinks.push(sink);
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.removeSinkFromDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof SinkGlyph))
    {
        var sinkIndex = this.item.parent.sinks.indexOf(this.item);
        if (sinkIndex != -1)
        {
            DataFlowEdit.removeLinkToConsumer(this.item.parent, this.item.consumer);
            this.item.parent.sinks.splice(sinkIndex, 1);
        }
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.addServiceToDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof DataFlowGlyph))
    {
        var service = new ServiceGlyph(Math.random(), this.item);

        this.item.services.push(service);
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.removeServiceFromDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof ServiceGlyph))
    {
        var serviceIndex = this.item.parent.services.indexOf(this.item);
        if (serviceIndex != -1)
        {
            DataFlowEdit.removeLinkToConsumer(this.item.parent, this.item.consumer);
            this.item.parent.services.splice(serviceIndex, 1);
        }
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.addStoreToDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof DataFlowGlyph))
    {
        var store = new StoreGlyph(Math.random(), this.item);

        this.item.stores.push(store);
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.removeStoreFromDataFlow = function()
{
    if ((this.item != null) && (this.item instanceof StoreGlyph))
    {
        var storeIndex = this.item.parent.stores.indexOf(this.item);
        if (storeIndex != -1)
        {
            DataFlowEdit.removeLinkFromProducer(this.item.parent, this.item.producer);
            DataFlowEdit.removeLinkToConsumer(this.item.parent, this.item.consumer);
            this.item.parent.stores.splice(storeIndex, 1);
        }
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.createLink = function()
{
    if ((this.item != null) && (this.item instanceof ProducerGlyph))
    {
        this.linkStart             = this.item;
        this.linkStart.bodyStyle   = "#cc7733";
        this.linkStart.borderStyle = "#ee7711";
    }
    this.dataFlowControl.redraw();
}

DataFlowEdit.prototype.removeLinks = function()
{
    if ((this.item != null) && (this.item instanceof ConsumerGlyph))
        DataFlowEdit.removeLinkToConsumer(this.item.parent.parent, this.item);
    this.dataFlowControl.redraw();
}

DataFlowEdit.removeLinkFromProducer = function(dataFlow, producer)
{
    for (var linkIndex = dataFlow.links.length - 1; 0 <= linkIndex; linkIndex--)
        if (dataFlow.links[linkIndex].producer == producer)
            dataFlow.links.splice(linkIndex, 1);
}

DataFlowEdit.removeLinkToConsumer = function(dataFlow, consumer)
{
    for (var linkIndex = dataFlow.links.length - 1; 0 <= linkIndex; linkIndex--)
        if (dataFlow.links[linkIndex].consumer == consumer)
            dataFlow.links.splice(linkIndex, 1);
}
