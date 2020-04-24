package it.gb.gui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.gb.gui.OneNoteThread;

public class TitleChangeListener extends KeyAdapter {

	private final OneNoteThread instance;

	public TitleChangeListener(OneNoteThread instance) {
		this.instance = instance;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		instance.setNewTitle();
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			instance.confirmTitle();
		}
	}
}
