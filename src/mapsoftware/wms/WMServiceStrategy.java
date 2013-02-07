package mapsoftware.wms;

import java.net.URL;
import java.util.List;

import mapsoftware.wms.domain.LayerInformation;


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
	List<LayerInformation> getCapabilities();
	 
	
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
