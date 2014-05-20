/*
 * Copyright (c) 2013-2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

// Data Flow Glyph

function DataFlowGlyph()
{
    this.borderStyle = DataFlowGlyph.DEFAULT_BORDERSTYLE;
    this.bodyStyle   = DataFlowGlyph.DEFAULT_BODYSTYLE;

    this.bodyX      = undefined;
    this.bodyY      = undefined;
    this.bodyWidth  = undefined;
    this.bodyHeight = undefined;

    this.sources    = new Array(0);
    this.sinks      = new Array(0);
    this.processors = new Array(0);
    this.services   = new Array(0);
    this.stores     = new Array(0);
    this.links      = new Array(0);
}

DataFlowGlyph.DEFAULT_BORDERSTYLE = "#111111";
DataFlowGlyph.DEFAULT_BODYSTYLE   = "#eeeeee";
DataFlowGlyph.sourceGap           = 20.0;
DataFlowGlyph.sinkGap             = 20.0;
DataFlowGlyph.processorGapWidth   = 40.0;
DataFlowGlyph.processorGapHeight  = 20.0;
DataFlowGlyph.serviceGap          = 40.0;
DataFlowGlyph.storeGap            = 40.0;
DataFlowGlyph.borderWidth         = 2.0;

DataFlowGlyph.prototype.layout = function(context, x, y, width, height)
{
    this.bodyX      = x + (SourceGlyph.bodyWidth / 2.0);
    this.bodyY      = y + (ServiceGlyph.bodyHeight / 2.0);
    this.bodyWidth  = width - ((SourceGlyph.bodyWidth + SinkGlyph.bodyWidth) / 2.0);
    this.bodyHeight = height - ((ServiceGlyph.bodyHeight + StoreGlyph.bodyHeight) / 2.0);

    var totalSourceHeight = (SourceGlyph.bodyHeight * this.sources.length) + (DataFlowGlyph.sourceGap * Math.max(0, this.sources.length - 1));
    var currentSourceX = x + (SourceGlyph.bodyWidth / 2.0);
    var currentSourceY = y + ((height - totalSourceHeight) / 2.0);
    for (var index = 0; index < this.sources.length; index++)
    {
        currentSourceY = currentSourceY + (SourceGlyph.bodyHeight / 2.0);
        this.sources[index].place(context, currentSourceX, currentSourceY);
        currentSourceY = currentSourceY + (SourceGlyph.bodyHeight / 2.0) + DataFlowGlyph.sourceGap;
    }

    var totalSinkHeight   = (SinkGlyph.bodyHeight * this.sinks.length) + (DataFlowGlyph.sinkGap * Math.max(0, this.sinks.length - 1));
    var currentSinkX = x + width - (SinkGlyph.bodyWidth / 2.0);
    var currentSinkY = y + ((height - totalSinkHeight) / 2.0);
    for (var index = 0; index < this.sinks.length; index++)
    {
        currentSinkY = currentSinkY + (SinkGlyph.bodyWidth / 2.0);
        this.sinks[index].place(context, currentSinkX, currentSinkY);
        currentSinkY = currentSinkY + (SinkGlyph.bodyWidth / 2.0) + DataFlowGlyph.sinkGap;
    }

    var processorLevels = this.assignProcessorLevels();

    var processorsWidth       = DataFlowGlyph.processorGapWidth * Math.max(0, processorLevels.length - 1);
    var processorsHeight      = 0.0;
    var processorLevelWidths  = new Array(0);
    var processorLevelHeights = new Array(0);
    for (var processorLevel = 0; processorLevel < processorLevels.length; processorLevel++)
    {
        processorLevelWidths[processorLevel]  = 0.0;
        processorLevelHeights[processorLevel] = DataFlowGlyph.processorGapHeight * Math.max(0, processorLevels[processorLevel].length - 1);
        for (var index = 0; index < processorLevels[processorLevel].length; index++)
        {
            processorLevelWidths[processorLevel]  = Math.max(processorLevelWidths[processorLevel], processorLevels[processorLevel][index].width());
            processorLevelHeights[processorLevel] = processorLevelHeights[processorLevel] + processorLevels[processorLevel][index].height();
        }
        processorsWidth  = processorsWidth + processorLevelWidths[processorLevel];
        processorsHeight = Math.max(processorsHeight, processorLevelHeights[processorLevel]);
    }

    var currentProcessorX = x + ((width - processorsWidth) / 2.0);
    for (var processorLevel = 0; processorLevel < processorLevels.length; processorLevel++)
    {
        currentProcessorX = currentProcessorX + (processorLevelWidths[processorLevel] / 2.0);
        var currentProcessorY = y + ((height - processorLevelHeights[processorLevel]) / 2.0);
        for (var index = 0; index < processorLevels[processorLevel].length; index++)
        {
            currentProcessorY = currentProcessorY + (processorLevels[processorLevel][index].height() / 2.0);
            processorLevels[processorLevel][index].place(context, currentProcessorX, currentProcessorY);
            currentProcessorY = currentProcessorY + (processorLevels[processorLevel][index].height() / 2.0) + DataFlowGlyph.processorGapHeight;
        }
        currentProcessorX = currentProcessorX + (processorLevelWidths[processorLevel] / 2.0) + DataFlowGlyph.processorGapWidth;
    }
    
    var totalServiceWidth = (ServiceGlyph.bodyWidth * this.services.length) + (DataFlowGlyph.serviceGap * Math.max(0, this.services.length - 1));
    var currentServiceX = x + ((width - totalServiceWidth) / 2.0);
    var currentServiceY = y + (ServiceGlyph.bodyHeight / 2.0);
    for (var index = 0; index < this.services.length; index++)
    {
        currentServiceX = currentServiceX + (ServiceGlyph.bodyWidth / 2.0);
        this.services[index].place(context, currentServiceX, currentServiceY);
        currentServiceX = currentServiceX + (ServiceGlyph.bodyWidth / 2.0) + DataFlowGlyph.serviceGap;
    }

    var totalStoreWidth = (StoreGlyph.bodyWidth * this.stores.length) + (DataFlowGlyph.storeGap * Math.max(0, this.stores.length - 1));
    var currentStoreX = x + ((width - totalStoreWidth) / 2.0);
    var currentStoreY = y + height - (StoreGlyph.bodyHeight / 2.0);
    for (var index = 0; index < this.stores.length; index++)
    {
        currentStoreX = currentStoreX + (StoreGlyph.bodyWidth / 2.0);
        this.stores[index].place(context, currentStoreX, currentStoreY);
        currentStoreX = currentStoreX + (StoreGlyph.bodyWidth / 2.0) + DataFlowGlyph.storeGap;
    }
}

DataFlowGlyph.prototype.minHeight = function()
{
    var totalSourceHeight = (SourceGlyph.bodyHeight * this.sources.length) + (DataFlowGlyph.sourceGap * Math.max(0, this.sources.length - 1));
    var totalSinkHeight   = (SinkGlyph.bodyHeight * this.sinks.length) + (DataFlowGlyph.sinkGap * Math.max(0, this.sinks.length - 1));

    return Math.max(totalSourceHeight, totalSinkHeight);
}

DataFlowGlyph.prototype.assignProcessorLevels = function()
{
    var processorLevelNumbers = new Array(0);
    for (var processorIndex = 0; processorIndex < this.processors.length; processorIndex++)
        processorLevelNumbers[processorIndex] = 0;

    var count      = 0;
    var changeSeen = true;
    while (changeSeen && (count < this.processors.length))
    {
        changeSeen = false;

        for (var linkIndex = 0; linkIndex < this.links.length; linkIndex++)
        {
            var startGlyph          = this.links[linkIndex].producer.parent;
            var endGlyph            = this.links[linkIndex].consumer.parent;
            var startProcessorIndex = this.processors.indexOf(startGlyph);
            var endProcessorIndex   = this.processors.indexOf(endGlyph);

            if ((startProcessorIndex != -1) && (endProcessorIndex != -1) && (processorLevelNumbers[endProcessorIndex] <= processorLevelNumbers[startProcessorIndex]))
            {
                processorLevelNumbers[endProcessorIndex] = Math.max(processorLevelNumbers[endProcessorIndex], processorLevelNumbers[startProcessorIndex] + 1);
                changeSeen = true;
            }
        }

        count++;
    }

    var processorLevels = new Array(0);
    for (var processorIndex = 0; processorIndex < this.processors.length; processorIndex++)
    {
        if (processorLevels[processorLevelNumbers[processorIndex]] == undefined)
            processorLevels[processorLevelNumbers[processorIndex]] = new Array(0);
        processorLevels[processorLevelNumbers[processorIndex]].push(this.processors[processorIndex]);
    }

    return processorLevels;
}

DataFlowGlyph.prototype.draw = function(context)
{
    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, this.bodyWidth, this.bodyHeight);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.lineWidth   = DataFlowGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, this.bodyWidth, this.bodyHeight);
    context.closePath();
    context.stroke();
    context.restore();

    for (var index = 0; index < this.sources.length; index++)
        this.sources[index].draw(context);

    for (var index = 0; index < this.sinks.length; index++)
        this.sinks[index].draw(context);

    for (var index = 0; index < this.processors.length; index++)
        this.processors[index].draw(context);

    for (var index = 0; index < this.services.length; index++)
        this.services[index].draw(context);

    for (var index = 0; index < this.stores.length; index++)
        this.stores[index].draw(context);

    for (var index = 0; index < this.links.length; index++)
        this.links[index].draw(context);
}

DataFlowGlyph.prototype.itemAt = function(x, y)
{
    var item = null;

    for (var index = 0; (item == null) && (index < this.links.length); index++)
        item = this.links[index].itemAt(x, y);

    for (var index = 0; (item == null) && (index < this.stores.length); index++)
        item = this.stores[index].itemAt(x, y);

    for (var index = 0; (item == null) && (index < this.services.length); index++)
        item = this.services[index].itemAt(x, y);

    for (var index = 0; (item == null) && (index < this.processors.length); index++)
        item = this.processors[index].itemAt(x, y);

    for (var index = 0; (item == null) && (index < this.sources.length); index++)
        item = this.sources[index].itemAt(x, y);

    for (var index = 0; (item == null) && (index < this.sinks.length); index++)
        item = this.sinks[index].itemAt(x, y);

    if ((item == null) && (x > this.bodyX) && (x < (this.bodyX + this.bodyWidth)) && (y > this.bodyY) && (y < (this.bodyY + this.bodyHeight)))
        item = this;

    return item;
}

DataFlowGlyph.load = function(dataFlow)
{
    var dataFlowGlyph = new DataFlowGlyph();

    for (var index = 0; index < dataFlow.dataFlowNodes.length; index++)
    {
        var dataFlowNode = dataFlow.dataFlowNodes[index];
        var name         = dataFlowNode.attributes.Name;
        var type         = dataFlowNode.attributes.Type;

        if (type === "DataSource")
            dataFlowGlyph.sources.push(new SourceGlyph(name, dataFlowGlyph));
        else if (type === "DataProcessor")
            dataFlowGlyph.processors.push(new ProcessorGlyph(name, dataFlowGlyph));
        else if (type === "DataSink")
            dataFlowGlyph.sinks.push(new SinkGlyph(name, dataFlowGlyph));
        else if (type === "DataService")
            dataFlowGlyph.services.push(new ServiceGlyph(name, dataFlowGlyph));
        else if (type === "DataStore")
            dataFlowGlyph.stores.push(new StoreGlyph(name, dataFlowGlyph));
        else
            alert("Problem: type = " + type + ", name = " + name);
    }

    return dataFlowGlyph;
}

// Source Glyph

function SourceGlyph(name, parent)
{
    this.name   = name;
    this.parent = parent;

    this.borderStyle = SourceGlyph.DEFAULT_BORDERSTYLE;
    this.labelStyle  = SourceGlyph.DEFAULT_LABELSTYLE
    this.bodyStyle   = SourceGlyph.DEFAULT_BODYSTYLE;

    this.bodyX  = undefined;
    this.bodyY  = undefined;
    this.labels = undefined;

    this.producer = new ProducerGlyph(this, 0.5 * Math.PI);
}

SourceGlyph.DEFAULT_BORDERSTYLE = "#111111";
SourceGlyph.DEFAULT_LABELSTYLE  = "#111111";
SourceGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
SourceGlyph.bodyWidth           = 60.0;
SourceGlyph.bodyHeight          = 60.0;
SourceGlyph.labelFont           = "12px sans-serif";
SourceGlyph.labelTextAlign      ="center"; 
SourceGlyph.labelTextBaseline   = "middle";
SourceGlyph.lineSeparation      = 16.0;
SourceGlyph.borderWidth         = 2.0;

SourceGlyph.prototype.place = function(context, x, y)
{
    this.bodyX  = x - (SourceGlyph.bodyWidth / 2.0);
    this.bodyY  = y - (SourceGlyph.bodyHeight / 2.0);
    this.labels = LabelUtil.generateLabel(context, this.name, x, y, SourceGlyph.bodyWidth - 12.0, SourceGlyph.bodyHeight - 8.0, SourceGlyph.lineSeparation);

    this.producer.place(context, this.bodyX + SourceGlyph.bodyWidth, y);
}

SourceGlyph.prototype.draw = function(context)
{
    this.producer.draw(context);

    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, SourceGlyph.bodyWidth, SourceGlyph.bodyHeight);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.font         = SourceGlyph.labelFont;
    context.textAlign    = SourceGlyph.labelTextAlign;
    context.textBaseline = SourceGlyph.labelTextBaseline;
    context.fillStyle    = this.labelStyle;
    for (var index = 0; index < this.labels.length; index++)
        context.fillText(this.labels[index].text, this.labels[index].x, this.labels[index].y);
    context.restore();

    context.save();
    context.lineWidth   = SourceGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, SourceGlyph.bodyWidth, SourceGlyph.bodyHeight);
    context.closePath();
    context.stroke();
    context.restore();
}

SourceGlyph.prototype.itemAt = function(x, y)
{
    var item = null;

    if ((item == null) && (x > this.bodyX) && (x < (this.bodyX + SourceGlyph.bodyWidth)) && (y > this.bodyY) && (y < (this.bodyY + SourceGlyph.bodyHeight)))
        item = this;

    if (item == null)
        item = this.producer.itemAt(x, y);

    return item;
}

//Processor Glyph

function ProcessorGlyph(name, parent)
{
    this.name   = name;
    this.parent = parent;

    this.borderStyle = ProcessorGlyph.DEFAULT_BORDERSTYLE;
    this.labelStyle  = ProcessorGlyph.DEFAULT_LABELSTYLE
    this.bodyStyle   = ProcessorGlyph.DEFAULT_BODYSTYLE;

    this.bodyX  = undefined;
    this.bodyY  = undefined;
    this.labels = undefined;

    this.consumer = new ConsumerGlyph(this, 1.5 * Math.PI);
    this.producer = new ProducerGlyph(this, 0.5 * Math.PI);
}

ProcessorGlyph.DEFAULT_BORDERSTYLE = "#111111";
ProcessorGlyph.DEFAULT_LABELSTYLE  = "#111111";
ProcessorGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
ProcessorGlyph.bodyWidth           = 60.0;
ProcessorGlyph.bodyHeight          = 60.0;
ProcessorGlyph.labelFont           = "12px sans-serif";
ProcessorGlyph.labelTextAlign      ="center"; 
ProcessorGlyph.labelTextBaseline   = "middle";
ProcessorGlyph.lineSeparation      = 16.0;
ProcessorGlyph.borderWidth         = 2.0;

ProcessorGlyph.prototype.place = function(context, x, y)
{
    this.bodyX  = x - (ProcessorGlyph.bodyWidth / 2.0);
    this.bodyY  = y - (ProcessorGlyph.bodyHeight / 2.0);
    this.labels = LabelUtil.generateLabel(context, this.name, x, y, ProcessorGlyph.bodyWidth - 12.0, ProcessorGlyph.bodyHeight - 8.0, ProcessorGlyph.lineSeparation);

    this.consumer.place(context, x - (ProcessorGlyph.bodyWidth / 2.0), y);
    this.producer.place(context, x + (ProcessorGlyph.bodyWidth / 2.0), y);
}

ProcessorGlyph.prototype.width = function()
{
    return ProcessorGlyph.bodyWidth + ConsumerGlyph.radius + ProducerGlyph.radius;
}

ProcessorGlyph.prototype.height = function()
{
    return Math.max(ProcessorGlyph.bodyHeight, Math.max(ConsumerGlyph.radius, ProducerGlyph.radius));
}

ProcessorGlyph.prototype.draw = function(context)
{
    this.consumer.draw(context);
    this.producer.draw(context);

    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, ProcessorGlyph.bodyWidth, ProcessorGlyph.bodyHeight);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.font         = ProcessorGlyph.labelFont;
    context.textAlign    = ProcessorGlyph.labelTextAlign;
    context.textBaseline = ProcessorGlyph.labelTextBaseline;
    context.fillStyle    = this.labelStyle;
    for (var index = 0; index < this.labels.length; index++)
        context.fillText(this.labels[index].text, this.labels[index].x, this.labels[index].y);
    context.restore();

    context.save();
    context.lineWidth   = ProcessorGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, ProcessorGlyph.bodyWidth, ProcessorGlyph.bodyHeight);
    context.closePath();
    context.stroke();
    context.restore();
}

ProcessorGlyph.prototype.itemAt = function(x, y)
{
    var item = null;

    if ((item == null) && (x > this.bodyX) && (x < (this.bodyX + ProcessorGlyph.bodyWidth)) && (y > this.bodyY) && (y < (this.bodyY + ProcessorGlyph.bodyHeight)))
        item = this;

    if (item == null)
        item = this.consumer.itemAt(x, y);

    if (item == null)
        item = this.producer.itemAt(x, y);

    return item;
}

// Sink Glyph

function SinkGlyph(name, parent)
{
    this.name   = name;
    this.parent = parent;

    this.borderStyle = SinkGlyph.DEFAULT_BORDERSTYLE;
    this.labelStyle  = SinkGlyph.DEFAULT_LABELSTYLE
    this.bodyStyle   = SinkGlyph.DEFAULT_BODYSTYLE;

    this.bodyX  = undefined;
    this.bodyY  = undefined;
    this.labels = undefined;

    this.consumer = new ConsumerGlyph(this, 1.5 * Math.PI);
}

SinkGlyph.DEFAULT_BORDERSTYLE = "#111111";
SinkGlyph.DEFAULT_LABELSTYLE  = "#111111";
SinkGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
SinkGlyph.bodyWidth           = 60.0;
SinkGlyph.bodyHeight          = 60.0;
SinkGlyph.labelFont           = "12px sans-serif";
SinkGlyph.labelTextAlign      ="center"; 
SinkGlyph.labelTextBaseline   = "middle";
SinkGlyph.lineSeparation      = 16.0;
SinkGlyph.borderWidth         = 2.0;

SinkGlyph.prototype.place = function(context, x, y)
{
    this.bodyX  = x - (SinkGlyph.bodyWidth / 2.0);
    this.bodyY  = y - (SinkGlyph.bodyHeight / 2.0);
    this.labels = LabelUtil.generateLabel(context, this.name, x, y, SinkGlyph.bodyWidth - 12.0, SinkGlyph.bodyHeight - 8.0, SinkGlyph.lineSeparation);

    this.consumer.place(context, this.bodyX, y);
}

SinkGlyph.prototype.draw = function(context)
{
    this.consumer.draw(context);

    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, SinkGlyph.bodyWidth, SinkGlyph.bodyHeight);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.font         = SinkGlyph.labelFont;
    context.textAlign    = SinkGlyph.labelTextAlign;
    context.textBaseline = SinkGlyph.labelTextBaseline;
    context.fillStyle    = this.labelStyle;
    for (var index = 0; index < this.labels.length; index++)
        context.fillText(this.labels[index].text, this.labels[index].x, this.labels[index].y);
    context.restore();

    context.save();
    context.lineWidth   = SinkGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, SinkGlyph.bodyWidth, SinkGlyph.bodyHeight);
    context.closePath();
    context.stroke();
    context.restore();
}

SinkGlyph.prototype.itemAt = function(x, y)
{
    var item = null;

    if ((item == null) && (x > this.bodyX) && (x < (this.bodyX + SinkGlyph.bodyWidth)) && (y > this.bodyY) && (y < (this.bodyY + SinkGlyph.bodyHeight)))
        item = this;

    if (item == null)
        item = this.consumer.itemAt(x, y);

    return item;
}

// Service Glyph

function ServiceGlyph(name, parent)
{
    this.name   = name;
    this.parent = parent;

    this.borderStyle = ServiceGlyph.DEFAULT_BORDERSTYLE;
    this.labelStyle  = ServiceGlyph.DEFAULT_LABELSTYLE
    this.bodyStyle   = ServiceGlyph.DEFAULT_BODYSTYLE;

    this.bodyX  = undefined;
    this.bodyY  = undefined;
    this.labels = undefined;

    this.consumer = new ConsumerGlyph(this, 0.0);
}

ServiceGlyph.DEFAULT_BORDERSTYLE = "#111111";
ServiceGlyph.DEFAULT_LABELSTYLE  = "#111111";
ServiceGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
ServiceGlyph.bodyWidth           = 60.0;
ServiceGlyph.bodyHeight          = 60.0;
ServiceGlyph.labelFont           = "12px sans-serif";
ServiceGlyph.labelTextAlign      ="center"; 
ServiceGlyph.labelTextBaseline   = "middle";
ServiceGlyph.lineSeparation      = 16.0;
ServiceGlyph.borderWidth         = 2.0;

ServiceGlyph.prototype.place = function(context, x, y)
{
    this.bodyX  = x - (ServiceGlyph.bodyWidth / 2.0);
    this.bodyY  = y - (ServiceGlyph.bodyHeight / 2.0);
    this.labels = LabelUtil.generateLabel(context, this.name, x, y, ServiceGlyph.bodyWidth - 12.0, ServiceGlyph.bodyHeight - 8.0, ServiceGlyph.lineSeparation);

    this.consumer.place(context, x, y + (ServiceGlyph.bodyWidth / 2.0));
}

ServiceGlyph.prototype.draw = function(context)
{
    this.consumer.draw(context);

    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, ServiceGlyph.bodyWidth, ServiceGlyph.bodyHeight);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.font         = ServiceGlyph.labelFont;
    context.textAlign    = ServiceGlyph.labelTextAlign;
    context.textBaseline = ServiceGlyph.labelTextBaseline;
    context.fillStyle    = this.labelStyle;
    for (var index = 0; index < this.labels.length; index++)
        context.fillText(this.labels[index].text, this.labels[index].x, this.labels[index].y);
    context.restore();

    context.save();
    context.lineWidth   = ServiceGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, ServiceGlyph.bodyWidth, ServiceGlyph.bodyHeight);
    context.closePath();
    context.stroke();
    context.restore();
}

ServiceGlyph.prototype.itemAt = function(x, y)
{
    var item = null;

    if ((item == null) && (x > this.bodyX) && (x < (this.bodyX + ServiceGlyph.bodyWidth)) && (y > this.bodyY) && (y < (this.bodyY + ServiceGlyph.bodyHeight)))
        item = this;

    if (item == null)
        item = this.consumer.itemAt(x, y);

    return item;
}

// Store Glyph

function StoreGlyph(name, parent)
{
    this.name   = name;
    this.parent = parent;

    this.borderStyle = SourceGlyph.DEFAULT_BORDERSTYLE;
    this.labelStyle  = SourceGlyph.DEFAULT_LABELSTYLE
    this.bodyStyle   = SourceGlyph.DEFAULT_BODYSTYLE;

    this.bodyX  = undefined;
    this.bodyY  = undefined;
    this.labels = undefined;

    this.consumer = new ConsumerGlyph(this, Math.PI);
    this.producer = new ProducerGlyph(this, Math.PI);
}

StoreGlyph.DEFAULT_BORDERSTYLE = "#111111";
StoreGlyph.DEFAULT_LABELSTYLE  = "#111111";
StoreGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
StoreGlyph.bodyWidth           = 120.0;
StoreGlyph.bodyHeight          = 60.0;
StoreGlyph.portGap             = 60.0;
StoreGlyph.labelFont           = "12px sans-serif";
StoreGlyph.labelTextAlign      ="center"; 
StoreGlyph.labelTextBaseline   = "middle";
StoreGlyph.lineSeparation      = 16.0;
StoreGlyph.borderWidth         = 2.0;

StoreGlyph.prototype.place = function(context, x, y)
{
    this.bodyX  = x - (StoreGlyph.bodyWidth / 2.0);
    this.bodyY  = y - (StoreGlyph.bodyHeight / 2.0);
    this.labels = LabelUtil.generateLabel(context, this.name, x, y, StoreGlyph.bodyWidth - 12.0, StoreGlyph.bodyHeight - 8.0, StoreGlyph.lineSeparation);

    this.consumer.place(context, x - (StoreGlyph.portGap / 2.0), y - (StoreGlyph.bodyHeight / 2.0));
    this.producer.place(context, x + (StoreGlyph.portGap / 2.0), y - (StoreGlyph.bodyHeight / 2.0));
}

StoreGlyph.prototype.width = function()
{
    return StoreGlyph.bodyWidth + ConsumerGlyph.radius + ProducerGlyph.radius;
}

StoreGlyph.prototype.height = function()
{
    return Math.max(StoreGlyph.bodyHeight, Math.max(ConsumerGlyph.radius, ProducerGlyph.radius));
}

StoreGlyph.prototype.draw = function(context)
{
    this.consumer.draw(context);
    this.producer.draw(context);

    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, StoreGlyph.bodyWidth, StoreGlyph.bodyHeight);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.font         = StoreGlyph.labelFont;
    context.textAlign    = StoreGlyph.labelTextAlign;
    context.textBaseline = StoreGlyph.labelTextBaseline;
    context.fillStyle    = this.labelStyle;
    for (var index = 0; index < this.labels.length; index++)
        context.fillText(this.labels[index].text, this.labels[index].x, this.labels[index].y);
    context.restore();

    context.save();
    context.lineWidth   = StoreGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.rect(this.bodyX, this.bodyY, StoreGlyph.bodyWidth, StoreGlyph.bodyHeight);
    context.closePath();
    context.stroke();
    context.restore();
}

StoreGlyph.prototype.itemAt = function(x, y)
{
    var item = null;

    if ((item == null) && (x > this.bodyX) && (x < (this.bodyX + StoreGlyph.bodyWidth)) && (y > this.bodyY) && (y < (this.bodyY + StoreGlyph.bodyHeight)))
        item = this;

    if (item == null)
        item = this.consumer.itemAt(x, y);

    if (item == null)
        item = this.producer.itemAt(x, y);

    return item;
}

// Producer Glyph

function ProducerGlyph(parent, pointAngle)
{
    this.parent     = parent;
    this.pointAngle = pointAngle;

    this.bodyStyle   = ProducerGlyph.DEFAULT_BODYSTYLE;
    this.borderStyle = ProducerGlyph.DEFAULT_BORDERSTYLE;

    this.x      = undefined;
    this.y      = undefined;
    this.pointX = undefined;
    this.pointY = undefined;
}

ProducerGlyph.DEFAULT_BORDERSTYLE = "#111111";
ProducerGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
ProducerGlyph.borderWidth         = 2.0;
ProducerGlyph.radius              = 15.0;

ProducerGlyph.prototype.place = function(context, x, y)
{
    this.x      = x;
    this.y      = y;
    this.pointX = x + (Math.sin(this.pointAngle) * ProducerGlyph.radius);
    this.pointY = y + (Math.cos(this.pointAngle) * ProducerGlyph.radius);
}

ProducerGlyph.prototype.draw = function(context)
{
    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.arc(this.x, this.y, ProducerGlyph.radius, 0.0, 2.0 * Math.PI, false);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.lineWidth   = ProducerGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.arc(this.x, this.y, ProducerGlyph.radius, 0.0, 2.0 * Math.PI, false);
    context.closePath();
    context.stroke();
    context.restore();
}

ProducerGlyph.prototype.itemAt = function(x, y)
{
    if ((x > (this.x - ProducerGlyph.radius)) && (x < (this.x + ProducerGlyph.radius)) &&  (y > (this.y - ProducerGlyph.radius)) && (y < (this.y + ProducerGlyph.radius)))
        return this;
    else
        return null;
}

// Consumer Glyph

function ConsumerGlyph(parent, pointAngle)
{
    this.parent     = parent;
    this.pointAngle = pointAngle;

    this.bodyStyle   = ConsumerGlyph.DEFAULT_BODYSTYLE;
    this.borderStyle = ConsumerGlyph.DEFAULT_BORDERSTYLE;

    this.x      = undefined;
    this.y      = undefined;
    this.pointX = undefined;
    this.pointY = undefined;
}

ConsumerGlyph.DEFAULT_BORDERSTYLE = "#111111";
ConsumerGlyph.DEFAULT_BODYSTYLE   = "#dddddd";
ConsumerGlyph.borderWidth         = 2.0;
ConsumerGlyph.radius              = 15.0;

ConsumerGlyph.prototype.place = function(context, x, y)
{
    this.x      = x;
    this.y      = y;
    this.pointX = x + (Math.sin(this.pointAngle) * ConsumerGlyph.radius);
    this.pointY = y + (Math.cos(this.pointAngle) * ConsumerGlyph.radius);
}

ConsumerGlyph.prototype.draw = function(context)
{
    context.save();
    context.fillStyle = this.bodyStyle;
    context.beginPath();
    context.arc(this.x, this.y, ConsumerGlyph.radius, 0.0, 2.0 * Math.PI, false);
    context.closePath();
    context.fill();
    context.restore();

    context.save();
    context.lineWidth   = ConsumerGlyph.borderWidth;
    context.strokeStyle = this.borderStyle;
    context.beginPath();
    context.arc(this.x, this.y, ConsumerGlyph.radius, 0.0, 2.0 * Math.PI, false);
    context.closePath();
    context.stroke();
    context.restore();
}

ConsumerGlyph.prototype.itemAt = function(x, y)
{
    if ((x > (this.x - ConsumerGlyph.radius)) && (x < (this.x + ConsumerGlyph.radius)) &&  (y > (this.y - ConsumerGlyph.radius)) && (y < (this.y + ConsumerGlyph.radius)))
        return this;
    else
        return null;
}

// Link Glyph

function LinkGlyph(producer, consumer)
{
    this.producer  = producer;
    this.consumer  = consumer;

    this.lineStyle = LinkGlyph.DEFAULT_LINESTYLE;
}

LinkGlyph.DEFAULT_LINESTYLE = "#111111";
LinkGlyph.lineWidth         = 2.0;

LinkGlyph.prototype.draw = function(context)
{
    context.save();
    context.lineWidth   = LinkGlyph.lineWidth;
    context.strokeStyle = this.lineStyle;
    context.beginPath();
    context.moveTo(this.producer.pointX, this.producer.pointY);
    context.lineTo(this.consumer.pointX, this.consumer.pointY);
    context.closePath();
    context.stroke();
    context.restore();
}

LinkGlyph.prototype.itemAt = function(x, y)
{
    return null;
}

// Label Util

function LabelUtil()
{
}

LabelUtil.generateLabel = function(context, text, x, y, width, height, lineSeparation)
{
    var labels    = new Array(0);
    var maxLabels = Math.floor(height / lineSeparation);

    var currentText = text.replace(/\s+/g, ' ').trim();
    while ((currentText !== "") && (labels.length < maxLabels))
    {
    	var bestLabel      = currentText.substring(0, 1);
    	var testLabel      = currentText.substring(0, 2);
    	var testLabelWidth = context.measureText(testLabel).width;
    	while ((bestLabel !== currentText) && (testLabelWidth < width))
        {
    		bestLabel      = testLabel;
    		testLabel      = currentText.substring(0, testLabel.length + 1);
    		testLabelWidth = context.measureText(testLabel).width;
        }

    	if ((bestLabel.length >= 3) && (bestLabel[bestLabel.length - 2] === ' '))
    		bestLabel = bestLabel.substring(0, bestLabel.length - 1);

        currentText = currentText.substring(bestLabel.length, currentText.length).trim();

    	var label = new Object();
        label.text = bestLabel.trim();

        labels.push(label);
    }

    if ((labels.length == maxLabels) && (currentText !== ""))
    {
        var lastLabelText = labels[labels.length - 1].text;
    	labels[labels.length - 1].text = lastLabelText.substring(0, lastLabelText.length - 3).concat("...");
    }

    var currentLabelY = y - ((lineSeparation * (labels.length - 1)) / 2.0);
    for (var index = 0; index < labels.length; index++)
    {
        labels[index].x = x;
        labels[index].y = currentLabelY;

        currentLabelY = currentLabelY + lineSeparation;
    }

    return labels;
}
