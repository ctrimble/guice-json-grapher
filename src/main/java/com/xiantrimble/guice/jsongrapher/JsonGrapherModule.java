package com.xiantrimble.guice.jsongrapher;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.grapher.BindingEdge;
import com.google.inject.grapher.DependencyEdge;
import com.google.inject.grapher.ImplementationNode;
import com.google.inject.grapher.InterfaceNode;
import com.google.inject.grapher.NameFactory;
import com.google.inject.grapher.NodeAliasFactory;
import com.google.inject.grapher.NodeIdFactory;
import com.google.inject.grapher.Renderer;
import com.google.inject.grapher.ShortNameFactory;
import com.google.inject.grapher.StringNodeIdFactory;

public class JsonGrapherModule extends AbstractModule {

	@Override
	protected void configure() {
	    bind(Renderer.class).to(JsonGrapher.class);
	    bind(new TypeLiteral<NodeAliasFactory<String>>() {}).to(JsonGrapher.class);
	    bind(JsonGrapher.class).in(Singleton.class);

	    bind(NameFactory.class).to(ShortNameFactory.class);
	    bind(new TypeLiteral<NodeIdFactory<String>>() {}).to(StringNodeIdFactory.class);

	    bind(new TypeLiteral<BindingEdge.Factory<String, BindingEdge<String>>>() {})
	        .to(BindingEdgeFactory.class);
	    bind(new TypeLiteral<DependencyEdge.Factory<String, DependencyEdge<String>>>() {})
	        .to(DependencyEdgeFactory.class);
	    bind(new TypeLiteral<InterfaceNode.Factory<String, InterfaceNode<String>>>() {})
	        .to(InterfaceNodeFactory.class);
	    bind(new TypeLiteral<ImplementationNode.Factory<String, ImplementationNode<String>>>() {})
	        .to(ImplementationNodeFactory.class);
	}

}
