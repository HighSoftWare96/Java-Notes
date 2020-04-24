package it.gb.gui.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import it.gb.main.Main;

public class WindowListener extends WindowAdapter {
	@Override
	public void windowClosing(WindowEvent e) {
		Main.saveAndClose(0);
	}
}