package com.daghan.iot.utils.internal;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;
import org.osgi.util.tracker.BundleTracker;

/**
 * Provides a service that tracks the bundles which has Bundle-Category heading includes "IoT"
 * 
 * @author daghan
 *
 */
@Component(service = DeviceTracker.class)
public class DeviceTracker {
	@Reference
	private MetaTypeService metatypeService;
	//
	private BundleTrackerImpl deviceTracker;
	final private String CATEGORY_HEADER = "Bundle-Category";
	final private String IOT_CATEGORY_NAME = "IoT";
	private Map<Long, String[]> bundleMap = new ConcurrentHashMap<>();

	@Activate
	public void start(BundleContext bundleContext) {
		deviceTracker = new BundleTrackerImpl(bundleContext);
		deviceTracker.open();

	};

	@Deactivate
	public void stop() {
		deviceTracker.close();
	}

	public Map<Long, String[]> getDeviceSummary() {
		return bundleMap;
	}

	// Internal tracker implementation
	private class BundleTrackerImpl extends BundleTracker<Long> {
		public BundleTrackerImpl(BundleContext context) {
			super(context, Bundle.ACTIVE, null);
		}

		@Override
		public Long addingBundle(Bundle bundle, BundleEvent event) {
			Long returnVal = null;
			String categoryNames = bundle.getHeaders().get(CATEGORY_HEADER);
			if ((categoryNames != null) && categoryNames.length() > 0
					&& Arrays.binarySearch(categoryNames.split(","), IOT_CATEGORY_NAME) >= 0) {
				MetaTypeInformation info = metatypeService.getMetaTypeInformation(bundle);
				returnVal = bundle.getBundleId();
				bundleMap.put(returnVal, info.getFactoryPids());
			}
			// retunVal == null notifies to the framework that we are not
			// tracking
			// this bundle
			return returnVal;
		}

		@Override
		public void removedBundle(Bundle bundle, BundleEvent event, Long object) {
			bundleMap.remove(object);
			super.removedBundle(bundle, event, object);
		}

	}
}