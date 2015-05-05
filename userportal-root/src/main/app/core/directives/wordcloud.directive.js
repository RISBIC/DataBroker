// global unescape

'use strict';

angular.module('core').directive('wordCloud',  function ($timeout, $log, $window) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {

                $log.info(element);

            $timeout(function() {

                var d3 = $window.d3;

                var fill = d3.scale.category20c();

                var w = 500,
                    h = 250;

                var words = [],
                    max,
                    scale = 1,
                    complete = 0,
                    tags,
                    fontSize,
                    maxLength = 30,
                    statusText = d3.select('#status');

                function progress(d) {
                    statusText.text(++complete + '/' + max);
                  //console.log(d);
                }

                function draw(data, bounds) {
                    statusText.style('display', 'none');
                    scale = bounds ? Math.min(
                            w / Math.abs(bounds[1].x - w / 2),
                            w / Math.abs(bounds[0].x - w / 2),
                            h / Math.abs(bounds[1].y - h / 2),
                            h / Math.abs(bounds[0].y - h / 2)) / 2 : 1;
                    words = data;
                    var text = vis.selectAll('text')
                        .data(words, function(d) { return d.text.toLowerCase(); });
                    text.transition()
                        .duration(1000)
                        .attr('transform', function(d) { return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')'; })
                        .style('font-size', function(d) { return d.size + 'px'; });
                    text.enter().append('text')
                        .attr('text-anchor', 'middle')
                        .attr('transform', function(d) { return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')'; })
                        .style('font-size', function(d) { return d.size + 'px'; })
                        .on('click', function(d) {
                            scope.navToSearch(d.text);
                        })
                        .style('opacity', 1e-6)
                        .transition()
                        .duration(1000)
                        .style('opacity', 1);
                    text.style('font-family', function(d) { return d.font; })
                        .style('fill', function(d) { return fill(d.text.toLowerCase()); })
                        .text(function(d) { return d.text; }).style('opacity', 1);
                    var exitGroup = background.append('g')
                        .attr('transform', vis.attr('transform'));
                    var exitGroupNode = exitGroup.node();
                    text.exit().each(function() {
                        exitGroupNode.appendChild(this);
                    });
                    exitGroup.transition()
                        .duration(1000)
                        .style('opacity', 1e-6)
                        .remove();
                    vis.transition()
                        .delay(1000)
                        .duration(750)
                        .attr('transform', 'translate(' + [w >> 1, h >> 1] + ')scale(' + scale + ')');
                }

                var layout = d3.layout.cloud()
                    .rotate(function(d) { return 0; })
                    .timeInterval(10)
                    .size([w, h])
                    .fontSize(function(d) { return fontSize(+d.value); })
                    .text(function(d) { return d.key; })
                    .on('word', progress)
                    .on('end', draw);

                var svg = d3.select(element[0]).append('svg')
                    .attr('width', w)
                    .attr('height', h);

                var background = svg.append('g'),
                    vis = svg.append('g')
                        .attr('transform', 'translate(' + [w >> 1, h >> 1] + ')');



                // Converts a given word cloud to image/png.
                scope.downloadPNG = function($event) {
                    var canvas = document.createElement('canvas'),
                        c = canvas.getContext('2d');
                    canvas.width = w;
                    canvas.height = h;
                    c.translate(w >> 1, h >> 1);
                    c.scale(scale, scale);
                    words.forEach(function(word, i) {
                        c.save();
                        c.translate(word.x, word.y);
                        c.rotate(word.rotate * Math.PI / 180);
                        c.textAlign = 'center';
                        c.fillStyle = fill(word.text.toLowerCase());
                        c.font = word.size + 'px ' + word.font;
                        c.fillText(word.text, 0, 0);
                        c.restore();
                    });
                    d3.select($event.currentTarget).attr('href', canvas.toDataURL('image/png'));
                };

                scope.downloadSVG = function($event) {
                    d3.select($event.currentTarget).attr('href', 'data:image/svg+xml;charset=utf-8;base64,' + btoa(encodeURIComponent(
                        svg.attr('version', '1.1')
                            .attr('xmlns', 'http://www.w3.org/2000/svg')
                            .node().parentNode.innerHTML)));
                };


                // From Jonathan Feinberg's cue.language, see lib/cue.language/license.txt.
                var stopWords = /^(i|me|my|myself|we|us|our|ours|ourselves|you|your|yours|yourself|yourselves|he|him|his|himself|she|her|hers|herself|it|its|itself|they|them|their|theirs|themselves|what|which|who|whom|whose|this|that|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|doing|will|would|should|can|could|ought|i'm|you're|he's|she's|it's|we're|they're|i've|you've|we've|they've|i'd|you'd|he'd|she'd|we'd|they'd|i'll|you'll|he'll|she'll|we'll|they'll|isn't|aren't|wasn't|weren't|hasn't|haven't|hadn't|doesn't|don't|didn't|won't|wouldn't|shan't|shouldn't|can't|cannot|couldn't|mustn't|let's|that's|who's|what's|here's|there's|when's|where's|why's|how's|a|an|the|and|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|before|after|above|below|to|from|up|upon|down|in|out|on|off|over|under|again|further|then|once|here|there|when|where|why|how|all|any|both|each|few|more|most|other|some|such|no|nor|not|only|own|same|so|than|too|very|say|says|said|shall|data|set|dataset)$/,
                //punctuation = new RegExp('[' + unicodePunctuationRe + ']', 'g'),
                //wordSeparators = /[\s\u3031-\u3035\u309b\u309c\u30a0\u30fc\uff70]+/g,
                    discard = /^(@|https?:|\/\/)/;
                    //htmlTags = /(<[^>]*?>|<script.*?<\/script>|<style.*?<\/style>|<head.*?><\/head>)/g;


                /*function parseHTML(d) {
                    parseText(d.replace(htmlTags, ' ').replace(/&#(x?)([\dA-Fa-f]{1,4});/g, function(d, hex, m) {
                        return String.fromCharCode(+((hex ? '0x' : '') + m));
                    }).replace(/&\w+;/g, ' '));
                }

                function flatten(o, k) {
                    if (typeof o === 'string') return o;
                    var text = [];
                    for (k in o) {
                        var v = flatten(o[k], k);
                        if (v) text.push(v);
                    }
                    return text.join(' ');
                }*/

                function parseText(text) {
                    tags = {};
                    var cases = {};

                    if (scope.tags) {
                      scope.tags.forEach(function(trend) {


                        var expandedWords = [];

                        expandedWords = [trend];

                        expandedWords.forEach(function(word){
                          if (discard.test(word)) return;
                          //word = word.replace(punctuation, '');
                          if (stopWords.test(word.toLowerCase())) return;
                          word = word.substr(0, maxLength);
                          cases[word.toLowerCase()] = word;
                          tags[word = word.toLowerCase()] = (tags[word] || 0) + 1;
                        });


                      });
                      tags = d3.entries(tags).sort(function(a, b) { return b.value - a.value; });
                      tags.forEach(function(d) { d.key = cases[d.key]; });
                      generate();
                    }
                }

                function generate() {
                    layout
                        .font('Impact')
                        .spiral('archimedean');
                    fontSize = d3.scale.log().range([10, 50]);
                    if (tags.length) fontSize.domain([+tags[tags.length - 1].value || 1, +tags[0].value]);
                    complete = 0;
                    statusText.style('display', null);
                    words = [];
                  //console.log(tags);
                    layout.stop().words(tags.slice(0, max = Math.min(tags.length, 1000))).start();
                }

                function load(f) {
                    parseText(f);
                }

                scope.$watch('tags', function(value) {
                    load('text');
                });

            },10);

            $log.info('Running wordcloud');
            $log.info(scope.tags);
        }
    };
});
