// Kartankatseluohjelman graafinen käyttöliittymä
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
import mapsoftware.wms.WMServiceFactory;
import mapsoftware.wms.WMServiceStrategy;

public class MapUI extends JFrame {

	private WMServiceStrategy wmServiceStrategy;
	private LocationInformation Area;

	private Component[] Components;
	private JLabel imageLabel = new JLabel();
	private JPanel leftPanel = new JPanel();

	private JButton btnRefresh = new JButton("Refresh");
	private JButton btnLeft = new JButton("<");
	private JButton btnRight = new JButton(">");
	private JButton btnUp = new JButton("^");
	private JButton btnDown = new JButton("v");
	private JButton btnZoomIn = new JButton("+");
	private JButton btnZoomOut = new JButton("-");

	public static void main(String[] args) {
		new MapUI(WMServiceFactory.getLounaispaikkaWMService());
	}

	public MapUI(WMServiceStrategy wmServiceStrategy) {
		this.wmServiceStrategy = wmServiceStrategy;
		init();
	}

	/**
	 * <p>
	 * Inits UI
	 * </p>
	 * 
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		Area = new LocationInformation(22.1, 60.4, 22.3, 60.5);
		List<LayerInformation> layers = wmServiceStrategy.getCapabilities();

		attachButtonListeners();

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		leftPanel.setMaximumSize(new Dimension(100, 600));

		for (LayerInformation layerInfo : layers) {
			leftPanel.add(new LayerCheckBox(layerInfo, true));
		}

		leftPanel.add(btnRefresh);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(btnLeft);
		leftPanel.add(btnRight);
		leftPanel.add(btnUp);
		leftPanel.add(btnDown);
		leftPanel.add(btnZoomIn);
		leftPanel.add(btnZoomOut);
		this.Components = leftPanel.getComponents();

		imageLabel.setIcon(new ImageIcon(wmServiceStrategy.getMap(
				formatCapabilities(), this.Area.getArea())));

		add(imageLabel, BorderLayout.EAST);
		add(leftPanel, BorderLayout.WEST);

		pack();
		setVisible(true);
		setResizable(false);
	}

	private void attachButtonListeners() {
		ButtonListener bl = new ButtonListener();
		btnRefresh.addActionListener(bl);
		btnLeft.addActionListener(bl);
		btnRight.addActionListener(bl);
		btnUp.addActionListener(bl);
		btnDown.addActionListener(bl);
		btnZoomIn.addActionListener(bl);
		btnZoomOut.addActionListener(bl);
	}

	/**
	 * <p>
	 * ButtonListener for all UI components
	 * </p>
	 * 
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnRefresh) {
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == btnLeft) {
				Area.move("L");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == btnRight) {
				Area.move("R");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == btnUp) {
				Area.move("U");
				try {
					updateImage();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (e.getSource() == btnDown) {
				Area.move("D");
				updateImage();
			}
			if (e.getSource() == btnZoomIn) {
				Area.move("I");
				updateImage();
			}
			if (e.getSource() == btnZoomOut) {
				Area.move("O");
				updateImage();
			}
		}
	}

	
	/**
	 * <p>CheckBox that keeps information about WMS layers</p>
	 * 
	 * @author Ville Ahti
	 *
	 */
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
	 * <p>
	 * Updates map image
	 * </p>
	 */
	private void updateImage() {
		new Thread() {
			public void run() {
				SwingUtilities.invokeLater(new MapUpdater());
			}
		}.start();
	}

	/**
	 * @return request parameter from chosen LayerCheckbox components
	 */
	private String formatCapabilities() {
		String s = "";
		for (Component com : this.Components) {
			if (com instanceof LayerCheckBox)
				if (((LayerCheckBox) com).isSelected())
					s = s
							+ ((LayerCheckBox) com).getLayerInformation()
									.getName() + ",";
		}
		if (s.endsWith(","))
			s = s.substring(0, s.length() - 1);

		return s;
	}

	/**
	 * <p>
	 * Runnable class which updates map
	 * </p>
	 * 
	 * @author Ville Ahti
	 */
	private class MapUpdater implements Runnable {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			updateMap();
		}

		/**
		 * <p>
		 * updates map UIs image
		 * </p>
		 */
		private void updateMap() {
			String s = formatCapabilities();
			System.out.println(wmServiceStrategy.getMap(s, Area.getArea()));
			imageLabel.setIcon(new ImageIcon(wmServiceStrategy.getMap(s,
					Area.getArea())));
		}

	}

}
