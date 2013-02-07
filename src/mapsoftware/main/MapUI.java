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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import mapsoftware.wms.WMServiceFactory;
import mapsoftware.wms.WMServiceFactory.WMServiceProvider;
import mapsoftware.wms.WMServiceStrategy;
import mapsoftware.wms.domain.LayerInformation;
import mapsoftware.wms.domain.LocationInformation;
import mapsoftware.wms.domain.ServiceCapabilitiesInformation;

/**
 * <p>
 * WMSMapServiceUI made with Java's Swing library
 * </p>
 * 
 * @author Ville Ahti
 * 
 */
public class MapUI extends JFrame {

    private final WMServiceStrategy wmServiceStrategy;
    private LocationInformation Area;

    private final JLabel imageLabel = new JLabel();
    private final JLayeredPane leftPanel = new JLayeredPane();
    private JPanel pnlCheckBox;

    private final JButton btnRefresh = new JButton("Refresh");
    private final JButton btnLeft = new JButton("<");
    private final JButton btnRight = new JButton(">");
    private final JButton btnUp = new JButton("^");
    private final JButton btnDown = new JButton("v");
    private final JButton btnZoomIn = new JButton("+");
    private final JButton btnZoomOut = new JButton("-");

    public static void main(String[] args) {
        new MapUI(
                WMServiceFactory.getWMService(WMServiceProvider.LOUNAISPAIKKA));
    }

    public MapUI(WMServiceStrategy wmServiceStrategy) {
        super();
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
        // TODO: Edit layout: improve panel organization and whole appearance
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        ServiceCapabilitiesInformation serviceInfo = wmServiceStrategy
                .getCapabilities();
        List<LayerInformation> layers = serviceInfo.getLayerInformations();
        Area = serviceInfo.getLocationInformation();

        attachButtonListeners();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        leftPanel.setMaximumSize(new Dimension(100, 600));

        pnlCheckBox = new JPanel();
        pnlCheckBox.setLayout(new BoxLayout(pnlCheckBox, BoxLayout.Y_AXIS));
        leftPanel.add(pnlCheckBox);

        for (LayerInformation layerInfo : layers) {
            pnlCheckBox.add(new LayerCheckBox(layerInfo, false));
        }

        leftPanel.add(btnRefresh);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnLeft);
        leftPanel.add(btnRight);
        leftPanel.add(btnUp);
        leftPanel.add(btnDown);
        leftPanel.add(btnZoomIn);
        leftPanel.add(btnZoomOut);

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
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnRefresh) {
                updateImage();
            }
            if (e.getSource() == btnLeft) {
                Area.move("L");
                updateImage();
            }
            if (e.getSource() == btnRight) {
                Area.move("R");
                updateImage();
            }
            if (e.getSource() == btnUp) {
                Area.move("U");
                updateImage();
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
     * <p>
     * CheckBox that keeps information about WMS layers
     * </p>
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
            @Override
            public void run() {
                SwingUtilities.invokeLater(new MapUpdater());
            }
        }.start();
    }

    /**
     * @return request parameter from chosen LayerCheckbox components
     */
    // TODO: Check that at least one is checked to prevent no layers checked
    // errors with WMS
    private String formatCapabilities() {
        String s = "";
        for (Component comLayer : pnlCheckBox.getComponents()) {
            if (((LayerCheckBox) comLayer).isSelected()) {
                s = s
                        + ((LayerCheckBox) comLayer).getLayerInformation()
                                .getName() + ",";
            }
        }
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }

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
        @Override
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
