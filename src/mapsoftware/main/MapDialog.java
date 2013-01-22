// Kartankatseluohjelman graafinen käyttöliittymä
package mapsoftware.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mapsoftware.wms.LayerInformation;
import mapsoftware.wms.LocationArea;
import mapsoftware.wms.LounaispaikkaCapParser;
import mapsoftware.wms.LounaispaikkaWMSConnection;
import mapsoftware.wms.WMSConnectionStrategy;

public class MapDialog extends JFrame {

	// Käyttöliittymän komponentit
	private WMSConnectionStrategy ConStra;
	private LocationArea Area;

	private Component[] Components;
	private JLabel imageLabel = new JLabel();
	private JPanel leftPanel = new JPanel();

	private JButton refreshB = new JButton("Refresh");
	private JButton leftB = new JButton("<");
	private JButton rightB = new JButton(">");
	private JButton upB = new JButton("^");
	private JButton downB = new JButton("v");
	private JButton zoomInB = new JButton("+");
	private JButton zoomOutB = new JButton("-");

	public MapDialog() throws Exception {
		init();
	}

	private void init() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		// ALLA OLEVAN TESTIRIVIN VOI KORVATA JOLLAKIN MUULLA ERI ALOITUSNÄKYMÄN
		// LATAAVALLA RIVILL�
		
		// NS. Default position
		Area = new LocationArea(22.1, 60.4, 22.3, 60.5);
		ConStra = new LounaispaikkaWMSConnection(new LounaispaikkaCapParser());
		List<LayerInformation> layers = ConStra.getCapabilities();
//		imageLabel
//				.setIcon(new ImageIcon(
//						ConStra.getMap(null, null)));
//		add(imageLabel, BorderLayout.EAST);

		ButtonListener bl = new ButtonListener();
		refreshB.addActionListener(bl);
		leftB.addActionListener(bl);
		rightB.addActionListener(bl);
		upB.addActionListener(bl);
		downB.addActionListener(bl);
		zoomInB.addActionListener(bl);
		zoomOutB.addActionListener(bl);

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		leftPanel.setMaximumSize(new Dimension(100, 600));

		for(int i=0; i<layers.size(); i++) {
			if(i==1) {
				leftPanel.add(new LayerCheckBox(layers.get(i).getName(), layers.get(i).getTitle(), true));
			}
			else {
				leftPanel.add(new LayerCheckBox(layers.get(i).getName(), layers.get(i).getTitle(), false));
			}
			
		}


		leftPanel.add(refreshB);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(leftB);
		leftPanel.add(rightB);
		leftPanel.add(upB);
		leftPanel.add(downB);
		leftPanel.add(zoomInB);
		leftPanel.add(zoomOutB);
		this.Components = leftPanel.getComponents();

		imageLabel
				.setIcon(new ImageIcon(
						ConStra.getMap(formatCapabilities(), this.Area.getArea())));
		System.out.println(ConStra.getMap(null, null));
		add(imageLabel, BorderLayout.EAST);

		add(leftPanel, BorderLayout.WEST);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		new MapDialog();
	}
	

	// Kontrollinappien kuuntelija
	// KAIKKIEN NAPPIEN YHTEYDESSÄ VOINEE HYÖDYNTÄÄ updateImage()-METODIA
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refreshB) {
				try { updateImage(); } catch(Exception ex) {
				ex.printStackTrace(); }
			}
			if (e.getSource() == leftB) {
				// TODO:
				// VASEMMALLE SIIRTYMINEN KARTALLA
				// MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PÄIVITÄ
				// KUVA
			}
			if (e.getSource() == rightB) {
				// TODO:
				// OIKEALLE SIIRTYMINEN KARTALLA
				// MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PÄIVITÄ
				// KUVA
			}
			if (e.getSource() == upB) {
				// TODO:
				// YLÖSPÄIN SIIRTYMINEN KARTALLA
				// MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PÄIVITÄ
				// KUVA
			}
			if (e.getSource() == downB) {
				// TODO:
				// ALASPÄIN SIIRTYMINEN KARTALLA
				// MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PÄIVITÄ
				// KUVA
			}
			if (e.getSource() == zoomInB) {
				// TODO:
				// ZOOM IN -TOIMINTO
				// MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PÄIVITÄ
				// KUVA
			}
			if (e.getSource() == zoomOutB) {
				// TODO:
				// ZOOM OUT -TOIMINTO
				// MUUTA KOORDINAATTEJA, HAE KARTTAKUVA PALVELIMELTA JA PÄIVITÄ
				// KUVA
			}
		}
	}

	// Valintalaatikko, joka muistaa karttakerroksen nimen
	private class LayerCheckBox extends JCheckBox {
		private String name = "";

		public LayerCheckBox(String name, String title, boolean selected) {
			super(title, null, selected);
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
	}

	// Tarkastetaan mitkä karttakerrokset on valittu,
	// tehdään uudesta karttakuvasta pyyntö palvelimelle ja päivitetään kuva
	public void updateImage() throws Exception {
		vittuMitaPaskaa vmp = new vittuMitaPaskaa();
		vmp.run();
		
		
		// imageLabel.setIcon(new ImageIcon(url));
	}
	
	public String formatCapabilities() {
		String s = "";
		for (Component com : this.Components) {
			if (com instanceof LayerCheckBox)
				if (((LayerCheckBox) com).isSelected())
					s = s + com.getName() + ",";
		}
		if (s.endsWith(","))
			s = s.substring(0, s.length() - 1);
		
		return s;
	}

//Toteutettu threadilla, koska teht�v�n anto.
	private class vittuMitaPaskaa extends Thread {
		
		private void newMap() {
			String s = formatCapabilities();
			System.out.println(ConStra.getMap(s, Area.getArea()));
			imageLabel.setIcon(new ImageIcon(ConStra.getMap(s, Area.getArea())));
		}
		
		public void run() {
			newMap();
		}
	}
	

} // MapDialog
