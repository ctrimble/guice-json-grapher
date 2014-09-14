package com.xiantrimble.guice.jsongrapher;

import javax.inject.Inject;

import com.google.inject.grapher.BindingEdge;

public class BindingEdgeFactory implements BindingEdge.Factory<String, BindingEdge<String>> {

	@Inject
	JsonGrapher grapher;
	
	public BindingEdge<String> newBindingEdge(String fromId, String toId) {
		InjectorEdge edge = new InjectorEdge()
		  .withFromId(fromId)
		  .withToId(toId)
		  .withType(InjectorEdge.Type.BINDING);
		
		grapher.addEdge(edge);
		
		return adapt(edge);
	}
	
	BindingEdge<String> adapt( final InjectorEdge edge ) {
		return new BindingEdge<String>() {
			public void setType(com.google.inject.grapher.BindingEdge.Type bindingType) {
				edge.setBindingType(bindingType.name());
			}
		};
	}
}
