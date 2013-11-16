package impl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import test.ServiceA;
import test.ServiceB;

public class Activator implements BundleActivator {
	private final List<ServiceTracker> trackers = new ArrayList<ServiceTracker>();

	public void start(BundleContext context) throws Exception {
		trackers.add(newTypedTracker(context, ServiceA.class));
		trackers.add(newTypedTracker(context, ServiceB.class));
		for (ServiceTracker tracker : trackers) {
			tracker.open(true);
		}
	}

	public void stop(BundleContext context) throws Exception {
		for (final ServiceTracker tracker : trackers) {
			tracker.close();
		}
	}

	private ServiceTracker newTypedTracker(final BundleContext context, Class<?> type) {
		final String name = type.getSimpleName() + "Tracker";
		return new ServiceTracker(context, type.getName(), new ServiceTrackerCustomizer() {
			@Override
			public void removedService(ServiceReference reference, Object service) {
				System.out.println(name + ".removedService(" + reference + "," + service + ")");
			}

			@Override
			public void modifiedService(ServiceReference reference, Object service) {
				System.out.println(name + ".modifiedService(" + reference + "," + service + ")");
			}

			@Override
			public Object addingService(ServiceReference reference) {
				System.out.println(name + ".addingService(" + reference + ")");
				return context.getService(reference);
			}
		});
	}
}
