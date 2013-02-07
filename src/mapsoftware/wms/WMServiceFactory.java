package mapsoftware.wms;

public class WMServiceFactory {

    private final static WMSCapabilitiesParser parser = new GenericWMSCapabilitiesParser();

    public static enum WMServiceProvider {
        LOUNAISPAIKKA, WORLD_MAP
    }

    public static WMServiceStrategy getWMService(
            WMServiceProvider serviceProvider) {
        return new LounaispaikkaWMSConnection(parser);
    }
}
