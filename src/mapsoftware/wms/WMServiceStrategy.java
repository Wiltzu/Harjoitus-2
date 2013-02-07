package mapsoftware.wms;

import java.net.URL;

import mapsoftware.wms.domain.ServiceCapabilitiesInformation;


/**
 * <p>WMS protocol interface for doing basic WMS requests</p>
 * 
 * @author Ville Ahti
 *
 */
public interface WMServiceStrategy {
	
	 /**
	  * <p>Protocol method for getting layers and other information</p>
	  * 
	 * @return list of services map layers. 
	 */
	ServiceCapabilitiesInformation getCapabilities();
	 
	
	 /**
	  * <p>Returns a URL to requested map.</p>
	  * 
	 * @param layers gotten from getCapabilities
	 * @param area location parameters
	 * @return URL which has the requested map 
	 */
	//TODO: improve interface
	URL getMap(String layers, String area);
}
