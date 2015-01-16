/*
 * Copyright (c) 2013-2015, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

function DataFlowLinkEdit(dataFlowControl)
{
    this.dataFlowControl = dataFlowControl;
    this.producer        = null;
    this.consumer        = null;
}

DataFlowLinkEdit.SELECTED_PROCESSOR_BODYSTYLE   = ProcessorGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_PROCESSOR_BORDERSTYLE = "#EE7711";
DataFlowLinkEdit.SELECTED_SOURCE_BODYSTYLE      = SourceGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_SOURCE_BORDERSTYLE    = "#EE7711";
DataFlowLinkEdit.SELECTED_SINK_BODYSTYLE        = SinkGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_SINK_BORDERSTYLE      = "#EE7711";
DataFlowLinkEdit.SELECTED_SERVICE_BODYSTYLE     = ServiceGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_SERVICE_BORDERSTYLE   = "#EE7711";
DataFlowLinkEdit.SELECTED_STORE_BODYSTYLE       = StoreGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_STORE_BORDERSTYLE     = "#EE7711";
DataFlowLinkEdit.SELECTED_PRODUCER_BODYSTYLE    = ProducerGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_PRODUCER_BORDERSTYLE  = "#EE7711";
DataFlowLinkEdit.SELECTED_CONSUMER_BODYSTYLE    = ConsumerGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_CONSUMER_BORDERSTYLE  = "#EE7711";
DataFlowLinkEdit.SELECTED_LINK_LINESTYLE        = "#EE7711";

DataFlowLinkEdit.prototype.doUnselect = function(item)
{
    if (item instanceof ProcessorGlyph)
    {
        item.bodyStyle   = ProcessorGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = ProcessorGlyph.DEFAULT_BORDERSTYLE;
        selectProcessorDataFlowNode("");
    }
    else if (item instanceof SourceGlyph)
    {
        item.bodyStyle   = SourceGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = SourceGlyph.DEFAULT_BORDERSTYLE;
        selectSourceDataFlowNode("");
    }
    else if (item instanceof SinkGlyph)
    {
        item.bodyStyle   = SinkGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = SinkGlyph.DEFAULT_BORDERSTYLE;
        selectSinkDataFlowNode("");
    }
    else if (item instanceof ServiceGlyph)
    {
        item.bodyStyle   = ServiceGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = ServiceGlyph.DEFAULT_BORDERSTYLE;
        selectServiceDataFlowNode("");
    }
    else if (item instanceof StoreGlyph)
    {
        item.bodyStyle   = StoreGlyph.DEFAULT_BODYSTYLE;
        item.borderStyle = StoreGlyph.DEFAULT_BORDERSTYLE;
        selectStoreDataFlowNode("");
    }
    else if (item instanceof LinkGlyph)
    {
        item.lineStyle = LinkGlyph.DEFAULT_LINESTYLE;
        selectLinkSourceDataFlowNode("");
        selectLinkSinkDataFlowNode("");
    }
    this.dataFlowControl.redraw();
}

DataFlowLinkEdit.prototype.doSelect = function(item)
{
    if (item instanceof ProcessorGlyph)
    {
        item.bodyStyle   = DataFlowLinkEdit.SELECTED_PROCESSOR_BODYSTYLE;
        item.borderStyle = DataFlowLinkEdit.SELECTED_PROCESSOR_BORDERSTYLE;
        selectProcessorDataFlowNode(item.name);
    }
    else if (item instanceof SourceGlyph)
    {
        item.bodyStyle   = DataFlowLinkEdit.SELECTED_SOURCE_BODYSTYLE
        item.borderStyle = DataFlowLinkEdit.SELECTED_SOURCE_BORDERSTYLE;
        selectSourceDataFlowNode(item.name);
    }
    else if (item instanceof SinkGlyph)
    {
        item.bodyStyle   = DataFlowLinkEdit.SELECTED_SINK_BODYSTYLE;
        item.borderStyle = DataFlowLinkEdit.SELECTED_SINK_BORDERSTYLE;
        selectSinkDataFlowNode(item.name);
    }
    else if (item instanceof ServiceGlyph)
    {
        item.bodyStyle   = DataFlowLinkEdit.SELECTED_SERVICE_BODYSTYLE;
        item.borderStyle = DataFlowLinkEdit.SELECTED_SERVICE_BORDERSTYLE;
        selectServiceDataFlowNode(item.name);
    }
    else if (item instanceof StoreGlyph)
    {
        item.bodyStyle   = DataFlowLinkEdit.SELECTED_STORE_BODYSTYLE;
        item.borderStyle = DataFlowLinkEdit.SELECTED_STORE_BORDERSTYLE;
        selectStoreDataFlowNode(item.name);
    }
    else if (item instanceof ProducerGlyph)
    {
        if (item != this.producer)
        {
            if (this.producer != null)
            {
                this.producer.bodyStyle   = ProducerGlyph.DEFAULT_BODYSTYLE;
                this.producer.borderStyle = ProducerGlyph.DEFAULT_BORDERSTYLE;
            }
            this.producer    = item;
            item.bodyStyle   = DataFlowLinkEdit.SELECTED_PRODUCER_BODYSTYLE;
            item.borderStyle = DataFlowLinkEdit.SELECTED_PRODUCER_BORDERSTYLE;
            selectLinkSourceDataFlowNode(item.parent.name);

            linkedDataFlowNodes(this.areLinkDataFlowNodes());
        }
        else
        {
            this.producer    = null;
            item.bodyStyle   = ProducerGlyph.DEFAULT_BODYSTYLE;
            item.borderStyle = ProducerGlyph.DEFAULT_BORDERSTYLE;
            selectLinkSourceDataFlowNode("");
            linkedDataFlowNodes(false);
        }
    }
    else if (item instanceof ConsumerGlyph)
    {
        if (item != this.consumer)
        {
            if (this.consumer != null)
            {
                this.consumer.bodyStyle   = ConsumerGlyph.DEFAULT_BODYSTYLE;
                this.consumer.borderStyle = ConsumerGlyph.DEFAULT_BORDERSTYLE;
            }
            this.consumer    = item;
            item.bodyStyle   = DataFlowLinkEdit.SELECTED_CONSUMER_BODYSTYLE;
            item.borderStyle = DataFlowLinkEdit.SELECTED_CONSUMER_BORDERSTYLE;
            selectLinkSinkDataFlowNode(item.parent.name);

            linkedDataFlowNodes(this.areLinkDataFlowNodes());
        }
        else
        {
            this.consumer    = null;
            item.bodyStyle   = ConsumerGlyph.DEFAULT_BODYSTYLE;
            item.borderStyle = ConsumerGlyph.DEFAULT_BORDERSTYLE;
            selectLinkSinkDataFlowNode("");
            linkedDataFlowNodes(false);
        }
    }
    else if (item instanceof LinkGlyph)
    {
        item.lineStyle = DataFlowLinkEdit.SELECTED_LINK_LINESTYLE;
        selectLinkSourceDataFlowNode(item.producer.parent.name);
        selectLinkSinkDataFlowNode(item.consumer.parent.name);
        linkedDataFlowNodes(true);
    }
    this.dataFlowControl.redraw();
}

DataFlowLinkEdit.prototype.areLinkDataFlowNodes = function()
{
    if ((this.producer != null) && (this.consumer != null))
    {
        var links = this.dataFlowControl.dataFlow.links;

        for (var linkIndex = 0; linkIndex < links.length; linkIndex++)
            if ((this.producer == links.producer) && (this.consumer == links.consumer))
                return true;
    }

    return false;
}
