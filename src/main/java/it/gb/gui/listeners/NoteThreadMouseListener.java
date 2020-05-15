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
		case ActionCommands.CLOSE_COMMAND:
			Controller.exit(0);
			break;
		case ActionCommands.TITLE_OK_COMMAND:
			this.instance.showTitlePanel(false);
			break;
		case ActionCommands.TITLE_CHANGE_COMMAND:
			this.instance.showColorsPanel(false);
			this.instance.showTitlePanel(true);
			break;
		case ActionCommands.NEW_NOTE:
			Controller.newNote();
			break;
		case ActionCommands.REMOVE_NOTE:
			Controller.removeNote(this.instance);
			break;
		case ActionCommands.CUSTOMIZE_COMMAND:
			this.instance.showTitlePanel(false);
			this.instance.showColorsPanel(true);
			break;
		case ActionCommands.CUSTOMIZE_OK_COMMAND:
			this.instance.showColorsPanel(false);
			break;
		}

	}
}
