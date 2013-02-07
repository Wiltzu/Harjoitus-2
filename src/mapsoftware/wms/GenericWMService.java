package mapsoftware.wms;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import mapsoftware.wms.domain.LayerInformation;
import mapsoftware.wms.servicetype.WMServiceType;

//TODO: make this class as generic as possible
public class GenericWMService implements WMServiceStrategy {

    private final WMSCapabilitiesParser parser;
    private final WMServiceType serviceType;

    /**
     * @param parser
     *            for getCapabilities xml data
     */
    public GenericWMService(WMSCapabilitiesParser parser,
            WMServiceType serviceType) {
        this.parser = parser;
        this.serviceType = serviceType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapsoftware.wms.WMSConnectionStrategy#getCapabilities()
     */
    @Override
    public List<LayerInformation> getCapabilities() {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(serviceType.getCapabilitiesStaticURL());
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

    /*
     * (non-Javadoc)
     * 
     * @see mapsoftware.wms.WMSConnectionStrategy#getMap(java.lang.String,
     * java.lang.String)
     */
    @Override
    public URL getMap(String layers, String area) {
        try {
            // dynamic URL generation here
            return new URL(serviceType.getMapStaticURL() + "layers=" + layers
                    + "&" + "bbox=" + area);

        } catch (MalformedURLException e) {
            return null;
        }
    }

}
