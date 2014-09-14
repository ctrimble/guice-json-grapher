package com.xiantrimble.guice.jsongrapher;

import javax.inject.Inject;

import com.google.inject.grapher.ImplementationNode;
import com.google.inject.grapher.NameFactory;

public class ImplementationNodeFactory implements ImplementationNode.Factory<String, ImplementationNode<String>> {
	
	@Inject
	JsonGrapher grapher;

	@Inject
	NameFactory nameFactory;

	public ImplementationNode<String> newImplementationNode(String nodeId) {
		InjectorNode node = new InjectorNode()
		  .withId(nodeId)
		  .withType(InjectorNode.Type.IMPLEMENTATION);
		
		grapher.addNode(node);
		
		return adapt(node);
	}
	
	ImplementationNode<String> adapt(final InjectorNode node) {
		return new ImplementationNode<String>() {

			public void addMember(java.lang.reflect.Member member) {
				node.getMembers().add(new Member()
				  .withName(nameFactory.getMemberName(member)));
			}

			public void setClassKey(com.google.inject.Key<?> classKey) {
				node.withClassName(nameFactory.getClassName(classKey));
			}

			public void setInstance(Object instance) {
				node.withInstanceName(nameFactory.getInstanceName(instance));
			}

			public void setSource(Object source) {
				node.withSourceName(nameFactory.getSourceName(source));
			}
		};
	}
}
