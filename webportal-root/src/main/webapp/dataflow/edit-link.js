/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

function DataFlowLinkEdit(dataFlowControl)
{
    this.dataFlowControl = dataFlowControl;
    this.producer        = null;
    this.consumer        = null;
}

DataFlowLinkEdit.SELECTED_PRODUCER_BODYSTYLE   = ProducerGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_PRODUCER_BORDERSTYLE = "#EE7711";
DataFlowLinkEdit.SELECTED_CONSUMER_BODYSTYLE   = ConsumerGlyph.DEFAULT_BODYSTYLE;
DataFlowLinkEdit.SELECTED_CONSUMER_BORDERSTYLE = "#EE7711";

DataFlowLinkEdit.prototype.doUnselect = function(item)
{
}

DataFlowLinkEdit.prototype.doSelect = function(item)
{
    if (item instanceof ProducerGlyph)
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
            selectSourceDataFlowNode(item.parent.name);
        }
        else
       	{
            this.producer    = null;
            item.bodyStyle   = ProducerGlyph.DEFAULT_BODYSTYLE;
            item.borderStyle = ProducerGlyph.DEFAULT_BORDERSTYLE;
            selectSourceDataFlowNode(null);
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
            selectSinkDataFlowNode(item.parent.name);
        }
        else
       	{
            this.consumer    = null;
            item.bodyStyle   = ConsumerGlyph.DEFAULT_BODYSTYLE;
            item.borderStyle = ConsumerGlyph.DEFAULT_BORDERSTYLE;
            selectSinkDataFlowNode(null);
        }
    }
    this.dataFlowControl.redraw();
}
