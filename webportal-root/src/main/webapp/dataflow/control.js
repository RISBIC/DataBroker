/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

function DataFlowControl(diagramId)
{
    this.diagram = document.getElementById(diagramId);
    this.context = this.diagram.getContext('2d');

    this.defaultDiagramHeight = this.diagram.parentNode.clientHeight;

    this.dataFlow      = null;
    this.item          = null;
    this.eventListener = null;
}

DataFlowControl.prototype.init = function(dataFlow)
{
    var parent          = this.diagram.parentNode;
    this.diagram.width  = parent.clientWidth;
    this.diagram.height = parent.clientHeight;

    this.dataFlow = dataFlow;
}

DataFlowControl.prototype.doResize = function()
{
    var parent          = this.diagram.parentNode;
    this.diagram.width  = parent.clientWidth;
    this.diagram.height = parent.clientHeight;

    this.redraw();
}

DataFlowControl.prototype.doEvent = function(event)
{
//    var type = event.type;
    var x    = event.pageX - event.currentTarget.offsetLeft;
    var y    = event.pageY - event.currentTarget.offsetTop;

    this.doUnselect(this.item);
    this.item = this.dataFlow.itemAt(x, y);
    this.doSelect(this.item);
}

DataFlowControl.prototype.doUnselect = function(item)
{
    if ((item != null) && (this.eventListener != null))
        this.eventListener.doUnselect(item);
}

DataFlowControl.prototype.doSelect = function(item)
{
    if ((item != null) && (this.eventListener != null))
        this.eventListener.doSelect(item);
}

DataFlowControl.prototype.redraw = function()
{
    var newHeight = Math.max(this.dataFlow.minHeight(), this.diagram.height);
    if (newHeight != this.diagram.height)
        this.diagram.height = newHeight;

    if (this.dataFlow != undefined)
    {
        this.dataFlow.layout(this.context, 5.0, 5.0, this.diagram.width - 10.0, this.diagram.height - 10.0);
        this.clear();
        this.dataFlow.draw(this.context);
    }
    else
        this.clear();
}

DataFlowControl.prototype.clear = function()
{
    this.context.save();
    this.context.fillStyle = "white";
    this.context.beginPath();
    this.context.rect(0.0, 0.0, this.diagram.width, this.diagram.height);
    this.context.closePath();
    this.context.fill();
    this.context.restore();
}