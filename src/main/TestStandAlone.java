package main;

import gui.ButtonTabComponent;
import gui.DraggableTabbedPane;
import gui.EncounterPanel;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import model.Encounter;

public class TestStandAlone {

	public static void main2(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				
				EncounterPanel contentPane = new EncounterPanel("SISI");

				frame.setContentPane(contentPane);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);

				for (int i = 0; i < 50; i++) {
//					contentPane.addActor();
				}
			}
		});
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				JTabbedPane tabbedPane = new JTabbedPane();
				UIDefaults def = UIManager.getLookAndFeelDefaults();
				def.put( "TabbedPane.foreground", Color.RED );
				def.put( "TabbedPane.textIconGap", new Integer(26) );
				def.put( "TabbedPane.background", Color.BLUE );
				//    def.put( "TabbedPane.tabsOverlapBorder", true);
				def.put( "TabbedPane.contentBorderInsets", new Insets(0,0,0,0) );
				frame.setUndecorated(true);

				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				tabbedPane = new DraggableTabbedPane();

				tabbedPane.setBackground(Color.BLACK);
				//	tabbedPane.setForeground(Color.red);
				frame.getContentPane().add(tabbedPane);

				frame.setTitle("CTracker");
				frame.setIconImage(new ImageIcon("images/dd_sigle.png").getImage());

				// Plus de barre de menu : le menu contextuel suffit et permet de mettre une image en premier plan.
				// On verra ensuite si je la remet au cas ou il y aurai des fonctionnalités supplémentaires fournies (comme une MAP fullscreen avec des outils)
				//	buildMenuBar();

				frame.pack();
				JFrame.setDefaultLookAndFeelDecorated(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setVisible(true);

				EncounterPanel encounterPanel = new EncounterPanel(new Encounter("toto"));
				tabbedPane.addTab(encounterPanel.encounter.encounterName, encounterPanel);
				tabbedPane.setSelectedComponent(encounterPanel);
				int index = tabbedPane.getSelectedIndex();
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
//				encounterPanel.build();
				
				for (int i = 0; i < 50; i++) {
//					encounterPanel.addActor();
				}
			}
		});
	}
}