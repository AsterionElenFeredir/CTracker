package gui;

import javax.swing.JTabbedPane;

public class EditableTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EditableTabbedPane() {
		EdiatbleTabbedPaneListener l = new EdiatbleTabbedPaneListener(this);
	        addChangeListener(l);
	        addMouseListener(l);
	}
	
}
