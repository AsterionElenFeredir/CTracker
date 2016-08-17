package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EdiatbleTabbedPaneListener extends MouseAdapter implements ChangeListener, DocumentListener {
	private final JTextField editor = new JTextField();
	private final JTabbedPane tabbedPane;
	private int editingIdx = -1;
	private int len = -1;
	private Dimension dim;
	private Component tabComponent;
	
	private final Action startEditing = new AbstractAction() {
		@Override public void actionPerformed(ActionEvent e) {
			editingIdx = tabbedPane.getSelectedIndex();
			tabComponent = tabbedPane.getTabComponentAt(editingIdx);
			tabbedPane.setTabComponentAt(editingIdx, editor);
			editor.setVisible(true);
			editor.setText(tabbedPane.getTitleAt(editingIdx));
			editor.selectAll();
			editor.requestFocusInWindow();
			len = editor.getText().length();
			dim = editor.getPreferredSize();
			editor.setMinimumSize(dim);
		}
	};

	private final Action renameTabTitle = new AbstractAction() {
		@Override public void actionPerformed(ActionEvent e) {
			String title = editor.getText().trim();
			if (editingIdx >= 0 && !title.isEmpty()) {
				tabbedPane.setTitleAt(editingIdx, title);
				EncounterPanel encounterPanel = (EncounterPanel) tabbedPane.getComponentAt(editingIdx);
				encounterPanel.encounter.encounterName = title;
			}
			cancelEditing.actionPerformed(null);
		}
	};
	private final Action cancelEditing = new AbstractAction() {
		@Override public void actionPerformed(ActionEvent e) {
			if (editingIdx >= 0) {
				tabbedPane.setTabComponentAt(editingIdx, tabComponent);
				editor.setVisible(false);
				editingIdx = -1;
				len = -1;
				tabComponent = null;
				editor.setPreferredSize(null);
				tabbedPane.requestFocusInWindow();
			}
		}
	};
	public EdiatbleTabbedPaneListener(JTabbedPane tabbedPane) {
		super();
		this.tabbedPane = tabbedPane;
		editor.setBorder(BorderFactory.createEmptyBorder());
		editor.addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				renameTabTitle.actionPerformed(null);
			}
		});
		InputMap im = editor.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = editor.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel-editing");
		am.put("cancel-editing", cancelEditing);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "rename-tab-title");
		am.put("rename-tab-title", renameTabTitle);
		editor.getDocument().addDocumentListener(this);
		//         editor.addKeyListener(new KeyAdapter() {
			//             @Override public void keyPressed(KeyEvent e) {
				//                 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		//                     renameTabTitle();
		//                 } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		//                     cancelEditing();
		//                 } else {
		//                     editor.setPreferredSize(editor.getText().length() > len ? null : dim);
		//                     tabbedPane.revalidate();
		//                 }
		//             }
		//         });
		tabbedPane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "start-editing");
		tabbedPane.getActionMap().put("start-editing", startEditing);
	}
	
	@Override public void stateChanged(ChangeEvent e) {
		renameTabTitle.actionPerformed(null);
	}
	
	@Override public void insertUpdate(DocumentEvent e) {
		updateTabSize();
	}
	
	@Override public void removeUpdate(DocumentEvent e) {
		updateTabSize();
	}
	
	@Override public void changedUpdate(DocumentEvent e) { /* not needed */ }
	
	@Override public void mouseClicked(MouseEvent e) {
		Rectangle r = tabbedPane.getUI().getTabBounds(tabbedPane, tabbedPane.getSelectedIndex());
		if (r.contains(e.getPoint()) && e.getClickCount() == 2) {
			startEditing.actionPerformed(null);
		} else {
			renameTabTitle.actionPerformed(null);
		}
	}
	
	private void updateTabSize() {
		editor.setPreferredSize(editor.getText().length() > len ? null : dim);
		tabbedPane.revalidate();
	}
}