package it.gb.gui.listeners;

import it.gb.gui.ActionCommands;
import it.gb.gui.OneNoteThread;
import it.gb.main.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteThreadMouseListener implements ActionListener {

	private final OneNoteThread instance;

	public NoteThreadMouseListener(OneNoteThread instance) {
		this.instance = instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ActionCommands.closeCommand:
			Controller.exit(0);
			break;
		case ActionCommands.titleOkCommand:
			this.instance.showTitlePanel(false);
			break;
		case ActionCommands.titleChangeCommand:
			this.instance.showColorsPanel(false);
			this.instance.showTitlePanel(true);
			break;
		case ActionCommands.newNote:
			Controller.newNote();
			break;
		case ActionCommands.remove:
			Controller.removeNote(this.instance);
			break;
		case ActionCommands.customizeCommand:
			this.instance.showTitlePanel(false);
			this.instance.showColorsPanel(true);
			break;
		case ActionCommands.customizeOkCommand:
			this.instance.showColorsPanel(false);
			break;
		}

	}
}
