package com.xiantrimble.guice.jsongrapher;

import javax.inject.Inject;

import com.google.inject.grapher.DependencyEdge;
import com.google.inject.spi.InjectionPoint;

public class DependencyEdgeFactory implements DependencyEdge.Factory<String, DependencyEdge<String>> {

	@Inject
	JsonGrapher grapher;
	
	public DependencyEdge<String> newDependencyEdge(String fromId,
			InjectionPoint fromPoint, String toId) {
		InjectorEdge edge = new InjectorEdge()
		  .withFromId(fromId)
		  .withToId(toId)
		  .withType(InjectorEdge.Type.DEPENDENCY);
		
		grapher.addEdge(edge);
		
		return adapt(edge);
	}
	
	DependencyEdge<String> adapt( final InjectorEdge edge ) {
		return new DependencyEdge<String>() {};
	}

}
