package mapsoftware.wms;

import java.net.URL;

public interface WMSConnectionStrategy {
	 String getCapabilities();
	 URL getMap(String[] layers, LocationArea area);
}
