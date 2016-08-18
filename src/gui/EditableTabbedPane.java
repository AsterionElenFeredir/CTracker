package gui;

import javax.swing.JTabbedPane;

public class EditableTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EditableTabbedPane() {
		EditableTabbedPaneListener l = new EditableTabbedPaneListener(this);
	        addChangeListener(l);
	        addMouseListener(l);
	}
	
}
