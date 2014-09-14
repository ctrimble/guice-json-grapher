package com.xiantrimble.guice.jsongrapher;

import javax.inject.Inject;

import com.google.inject.grapher.InterfaceNode;
import com.google.inject.grapher.NameFactory;

public class InterfaceNodeFactory implements InterfaceNode.Factory<String, InterfaceNode<String>> {
	
	@Inject
	JsonGrapher grapher;
	
	@Inject
	NameFactory nameFactory;

	public InterfaceNode<String> newInterfaceNode(String nodeId) {
		InjectorNode node = new InjectorNode()
		  .withId(nodeId)
		  .withType(InjectorNode.Type.INTERFACE);
		
		grapher.addNode(node);
		
		return adapt(node);
	}
	
	InterfaceNode<String> adapt(final InjectorNode node) {
		return new InterfaceNode<String>() {

			public void setKey(com.google.inject.Key<?> key) {
				node
				  .withClassName(nameFactory.getClassName(key))
				  .withAnnotationName(nameFactory.getAnnotationName(key));
			}

			public void setSource(Object source) {
				node.withSourceName(nameFactory.getSourceName(source));
			}

		};
	}

}
