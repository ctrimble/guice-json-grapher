package com.xiantrimble.guice.jsongrapher;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.output.StringBuilderWriter;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.grapher.GrapherModule;
import com.google.inject.grapher.InjectorGrapher;

/**
 * Tests the Guice JSON Grapher.
 */
public class JsonGrapherTest {
	@Test
	public void shouldGraphSimpleInjector() throws IOException {
		Injector injector = Guice.createInjector(new TestModule());
		injector.getInstance(A.class);
		InjectorGraph graph = new ObjectMapper().readValue(graph(injector), InjectorGraph.class);

		assertThat("there are 3 nodes", graph.getNodes().size(), equalTo(3));
		assertThat("there are 3 edges", graph.getEdges().size(), equalTo(3));
	}

	private static String graph(Injector demoInjector) throws IOException {

		StringBuilderWriter out = new StringBuilderWriter();

		Injector injector = Guice.createInjector(new GrapherModule(),
				new JsonGrapherModule());
		injector.getInstance(JsonGrapher.class).withWriter(out);

		injector.getInstance(InjectorGrapher.class).of(demoInjector).graph();

		return out.toString();
	}

	public static class TestModule extends AbstractModule {
		@Override
		protected void configure() {
			this.bind(A.class).asEagerSingleton();
		}
	}
	
	public static class A {
		@Inject B b;
		@Inject C c;
	}
	
	public static class B {
		@Inject C c;
	}
	
	public static class C { }
}
