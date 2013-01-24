package mapsoftware.wms;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WorldMapWMSConnection implements WMSConnectionStrategy {

	private static final String GET_MAP_STATIC_PART = "http://www2.demis.nl/WMS/wms.asp?WMS=WorldMap&WMTVER=1.0.0&request=getmap&srs=EPSG:4326&&width=700&height=500&format=image/png&styles=&";
	private static final String GET_CAP_ADDRESS = "http://www2.demis.nl/WMS/wms.asp?WMS=WorldMap&WMTVER=1.0.0&request=capabilities";
	WMSCapabilitiesParser parser;

	/**
	 * @param parser for getCapabilities xml data 
	 */
	public WorldMapWMSConnection(WMSCapabilitiesParser parser) {
		this.parser = parser;
	}

	/* (non-Javadoc)
	 * @see mapsoftware.wms.WMSConnectionStrategy#getCapabilities()
	 */
	@Override
	public List<LayerInformation> getCapabilities() {
		URL url = null;
		InputStream is = null;
		try {
			url = new URL(GET_CAP_ADDRESS);
			is = url.openStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parser.parseDocument(is);
	}

	/* (non-Javadoc)
	 * @see mapsoftware.wms.WMSConnectionStrategy#getMap(java.lang.String, java.lang.String)
	 */
	@Override
	public URL getMap(String layers, String area) {
		try {
			//dynamic URL generation here
			return new URL(GET_MAP_STATIC_PART + "layers=" + layers + "&" + "bbox=" + area);
			
			
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
