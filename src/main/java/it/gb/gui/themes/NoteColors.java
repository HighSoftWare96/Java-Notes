package it.gb.gui.themes;

import java.awt.Color;
import java.util.ArrayList;

public class NoteColors {
	private static ArrayList<ColorComponent> colors = new ArrayList<>();
	private static NoteColors instance = null;

	// TODO: remove this suppression once the theme names are either internationalized, or we don't need to care about it anymore
	@SuppressWarnings("SpellCheckingInspection")
	private NoteColors() {
		// VIOLA
		colors.add(new ColorComponent(Color.decode("#d98cb3"), Color.decode("#993366"), Color.decode("#cc6699"),
				"purple theme"));
		// VERDE
		colors.add(new ColorComponent(Color.decode("#5cd65c"), Color.decode("#009933"), Color.decode("#88cc00"),
				"green theme"));
		// BLU (DEFAULT)
		colors.add(new ColorComponent(Color.decode("#66ccff"), Color.decode("#3399ff"), Color.decode("#99ccff"),
				"blue theme"));
		// ARANCIO
		colors.add(new ColorComponent(Color.decode("#ffa64d"), Color.decode("#e67300"), Color.decode("#ffa366"),
				"orange theme"));
		// BIANCO
		colors.add(new ColorComponent(Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, "white theme"));
	}

	public static NoteColors getInstance() {
		if (instance == null)
			instance = new NoteColors();
		return instance;
	}

	public static void initialize() {
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
