package impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import test.ServiceA;
import test.ServiceB;

public class Activator implements BundleActivator {
	public void start(BundleContext context) throws Exception {
		registerService(context, ServiceA.class, new ServiceAImpl());
		registerService(context, ServiceB.class, new ServiceBImpl());
	}

	public void stop(BundleContext context) throws Exception {}

	private <T> void registerService(BundleContext context, Class<T> serviceClass, T service) {
		final Dictionary<Object,Object> properties = new Hashtable<Object,Object>();
		properties.put("foo", "bar");
		context.registerService(serviceClass.getName(), service, properties);
	}
}
