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
	
	private Socket s;
	private WMSCapabilitiesParser parser;
	
	public LounaispaikkaWMSConnection() {
		
	}
	
	public LounaispaikkaWMSConnection(WMSCapabilitiesParser parser) {
		this.parser = parser;
	}
	

	@Override
	public List<LayerInformation> getCapabilities() {
		PrintWriter pw = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		InputStream is = null;
		try {
			s = new Socket(InetAddress.getByName("kartat.lounaispaikka.fi"), 80);
			pw = new PrintWriter(s.getOutputStream());
			isr = new InputStreamReader(s.getInputStream());
			br = new BufferedReader(isr);
			is = s.getInputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pw.println("GET /wms/maakuntakaava?version=1.1.1&service=WMS&request=GetCapabilities HTTP/1.1");
		pw.println("Host: kartat.lounaispaikka.fi");
		pw.println("");
		pw.flush();
		
		try {
			while(br.read() != -1) 
				System.out.println(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<LayerInformation> result = null;
		if(parser != null) {
			return parser.parseDocument(is);
		}
		return result;
		
	}

	@Override
	public URL getMap(String[] layers, LocationArea area) {
		try {
			return new URL("http://kartat.lounaispaikka.fi/wms/maakuntakaava?version=1.1.1&service=WMS&request=GetMap&" + 
					"layers=mk_aluevaraus&srs=EPSG:4326&bbox=22.1,60.4,22.3,60.5&width=700&height=500&format=image/png&styles=");
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
