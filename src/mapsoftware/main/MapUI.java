// Kartankatseluohjelman graafinen kÃ¤yttÃ¶liittymÃ¤
package mapsoftware.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingUtilities;

import mapsoftware.wms.LayerInformation;
import mapsoftware.wms.LocationInformation;
import mapsoftware.wms.GenericWMSCapabilitiesParser;
import mapsoftware.wms.LounaispaikkaWMSConnection;
import mapsoftware.wms.WMServiceFactory;
import mapsoftware.wms.WMServiceStrategy;

public class MapUI extends JFrame {

	private WMServiceStrategy wmServiceStrategy;
	private LocationInformation Area;

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

	public MapUI(WMServiceStrategy wmServiceStrategy) {
		this.wmServiceStrategy = wmServiceStrategy;
		init();
	}
	
	
	/**
	 * <p>Inits UI</p>
	 * 
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		// ALLA OLEVAN TESTIRIVIN VOI KORVATA JOLLAKIN MUULLA ERI
		// ALOITUSNÃ„KYMÃ„N
		// LATAAVALLA RIVILLÄ

		// NS. Default position
		Area = new LocationInformation(22.1, 60.4, 22.3, 60.5);
		List<LayerInformation> layers = wmServiceStrategy.getCapabilities();
		// imageLabel
		// .setIcon(new ImageIcon(
		// ConStra.getMap(null, null)));
		// add(imageLabel, BorderLayout.EAST);

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

		for (LayerInformation layerInfo: layers) {
				leftPanel.add(new LayerCheckBox(layerInfo, true));
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

		imageLabel.setIcon(new ImageIcon(wmServiceStrategy.getMap(formatCapabilities(),
				this.Area.getArea())));
		System.out.println(wmServiceStrategy.getMap(null, null));
		add(imageLabel, BorderLayout.EAST);

		add(leftPanel, BorderLayout.WEST);

		pack();
		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args) throws Exception {
		new MapUI(WMServiceFactory.getLounaispaikkaWMService());
	}

	// Kontrollinappien kuuntelija
	// KAIKKIEN NAPPIEN YHTEYDESSÄ VOINEE HYÖDYNTÄÄ updateImage()-METODIA
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refreshB) {
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == leftB) {
				// VASEMMALLE SIIRTYMINEN KARTALLA
				Area.move("L");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == rightB) {
				// OIKEALLE SIIRTYMINEN KARTALLA
				Area.move("R");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == upB) {
				// YLÃ–SPÃ„IN SIIRTYMINEN KARTALLA
				Area.move("U");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == downB) {
				// ALASPÃ„IN SIIRTYMINEN KARTALLA
				Area.move("D");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == zoomInB) {
				// ZOOM IN -TOIMINTO
				Area.move("I");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == zoomOutB) {
				// ZOOM OUT -TOIMINTO
				Area.move("O");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// Valintalaatikko, joka muistaa karttakerroksen nimen
	private class LayerCheckBox extends JCheckBox {
		private final LayerInformation layerInformation;

		public LayerCheckBox(LayerInformation layerInfo, boolean selected) {
			super(layerInfo.getTitle(), null, selected);
			this.layerInformation = layerInfo;
		}

		public LayerInformation getLayerInformation() {
			return layerInformation;
		}

	}


	/**
	 * <p>Updates map image</p>
	 */
	public void updateImage() {
		new Thread() {
			public void run() {
				SwingUtilities.invokeLater(new MapUpdater());
			}
		}.start();
	}

	/**
	 * @return request parameter from chosen LayerCheckbox components
	 */
	public String formatCapabilities() {
		String s = "";
		for (Component com : this.Components) {
			if (com instanceof LayerCheckBox)
				if (((LayerCheckBox) com).isSelected())
					s = s + ((LayerCheckBox) com).getLayerInformation().getName() + ",";
		}
		if (s.endsWith(","))
			s = s.substring(0, s.length() - 1);

		return s;
	}

	
	/**
	 * <p>Runnable class which updates map</p>
	 * 
	 * @author Aleksi Haapsaari
	 * @author Ville Ahti
	 * @author Johannes Miettinen
	 */
	private class MapUpdater implements Runnable {
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			updateMap();
		}
		
		/**
		 * <p>updates map UIs image</p>
		 */
		private void updateMap() {
			String s = formatCapabilities();
			System.out.println(wmServiceStrategy.getMap(s, Area.getArea()));
			imageLabel
					.setIcon(new ImageIcon(wmServiceStrategy.getMap(s, Area.getArea())));
		}

	}

} // MapDialog
