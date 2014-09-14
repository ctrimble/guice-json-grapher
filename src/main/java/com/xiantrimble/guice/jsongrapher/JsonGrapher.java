package com.xiantrimble.guice.jsongrapher;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.grapher.NodeAliasFactory;
import com.google.inject.grapher.Renderer;

/**
 *
 */
public class JsonGrapher
  implements Renderer, NodeAliasFactory<String>
{
	protected Map<String, String> aliases = Maps.newHashMap();
	protected List<InjectorNode> nodes = Lists.newArrayList();
	protected List<InjectorEdge> edges = Lists.newArrayList();
	
	protected Writer out;
	protected ObjectMapper mapper = new ObjectMapper();
	
	public JsonGrapher withWriter( Writer out ) {
		this.out = out;
		return this;
	}
	
	public JsonGrapher withObjectMapper( ObjectMapper mapper ) {
		this.mapper = mapper;
		return this;
	}
	
	public void newAlias(String fromId, String toId) {
		aliases.put(fromId, toId);
	}
	
	protected String resolveAlias(String id) {
	  while (aliases.containsKey(id)) {
	    id = aliases.get(id);
	  }
		    
	  return id;
    }

	public void render() throws IOException {
	  InjectorGraph graph = new InjectorGraph();
	  
	  for(  InjectorNode node : nodes ) {
	    if( !aliases.containsKey(node.getId()) ) {
	    	graph.getNodes().add(node);
	    }
	  }
	  
	  for( InjectorEdge edge : edges ) {
		edge
		  .withFromId(resolveAlias(edge.getFromId()))
		  .withToId(resolveAlias(edge.getToId()));
		
		graph.getEdges().add(edge);
	  }
	  
	  mapper.writeValue(out, graph);
	  out.flush();
	}

	public void addNode(InjectorNode node) {
		nodes.add(node);
	}
	
	public void addEdge(InjectorEdge edge) {
		edges.add(edge);
	}
}
