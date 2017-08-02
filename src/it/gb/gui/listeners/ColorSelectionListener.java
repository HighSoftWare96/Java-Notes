package it.gb.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.gb.gui.OneNoteThread;
import it.gb.gui.themes.NoteColors;

public class ColorSelectionListener implements ActionListener {
	
	private OneNoteThread instance;
	
	public ColorSelectionListener(OneNoteThread instance) {
		this.instance = instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		instance.setNewTheme(NoteColors.searchThemeFromCommand(arg0.getActionCommand()));
	}

}
