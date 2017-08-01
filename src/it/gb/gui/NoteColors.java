package it.gb.gui;

import java.awt.Color;
import java.util.ArrayList;

public class NoteColors {
	// color[0] = maincolor // color[1] = backColor // color[2] = secColor
	private static ArrayList<ColorComponent> colors = new ArrayList<>();
	private static NoteColors instance = null;

	private NoteColors() {
		// TEMA VIOLA
		colors.add(new ColorComponent(Color.decode("#d98cb3"), Color.decode("#993366"), Color.decode("#cc6699"),
				"tema viola"));
		// TEMA VERDE
		colors.add(new ColorComponent(Color.decode("#5cd65c"), Color.decode("#009933"), Color.decode("#88cc00"),
				"tema verde"));
		// TEMA BLU (DEFAULT)
		colors.add(new ColorComponent(Color.decode("#66ccff"), Color.decode("#3399ff"), Color.decode("#99ccff"),
				"tema blue"));
		// TEMA ARANCIO
		colors.add(new ColorComponent(Color.decode("#ffa64d"), Color.decode("#e67300"), Color.decode("#ffa366"),
				"tema arancio"));
		// TEMA BIANCO
		colors.add(new ColorComponent(Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, "tema bianco"));
	}

	public static NoteColors getInstance() {
		if (instance == null)
			instance = new NoteColors();
		return instance;
	}

	public static void initilize() {
		if (instance == null)
			instance = new NoteColors();
	}

	public static ColorComponent searchThemeFromCommand(String command) {
		for (ColorComponent item : colors) {
			if (item.getCommand().equals(command))
				return item;
		}
		return null;
	}

	public ArrayList<ColorComponent> getColors() {
		return colors;
	}

	public static ColorComponent getTheme(int index) {
		return (index < colors.size()) ? colors.get(index) : null;
	}

	public static ColorComponent getDefaultTheme() {
		return colors.get(2);
	}

}
