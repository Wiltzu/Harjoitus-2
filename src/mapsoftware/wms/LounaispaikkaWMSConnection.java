package mapsoftware.wms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

public class LounaispaikkaWMSConnection implements WMSConnectionStrategy {
	
	private static final String GET_CAP_REQ_ADDRESS = "/wms/maakuntakaava?version=1.1.1&service=WMS&request=GetCapabilities";
	private static final int HTTP_PORT = 80;
	private static final String HOST = "kartat.lounaispaikka.fi";
	private Socket s;
	private final WMSCapabilitiesParser parser;
	
	
	public LounaispaikkaWMSConnection(WMSCapabilitiesParser parser) {
		this.parser = parser;
	}
	

	@Override
	public List<LayerInformation> getCapabilities() {
		PrintWriter pw = null;
		InputStream is = null;
		try {
			s = new Socket(InetAddress.getByName(HOST), HTTP_PORT);
			pw = new PrintWriter(s.getOutputStream());
			is = s.getInputStream();
		} catch (UnknownHostException e) {
			//problem with constant address
			e.printStackTrace();
		} catch (IOException e) {
			//socket problem
			e.printStackTrace();
		}
		
		pw.println("GET " + GET_CAP_REQ_ADDRESS + " HTTP/1.1");
		pw.println("Host: " + HOST);
		pw.println("");
		pw.flush();
		
		return parser.parseDocument(is);
	}

	@Override
	public URL getMap(List<LayerInformation> layers, LocationArea area) {
		try {
			return new URL("http://kartat.lounaispaikka.fi/wms/maakuntakaava?version=1.1.1&service=WMS&request=GetMap&" + 
					"layers=" + "mk_aluevaraus" + "&srs=EPSG:4326&bbox=" + "22.1,60.4,22.3,60.5" +
							"&width=700&height=500&format=image/png&styles=");
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
