package it.gb.gui.themes;

import java.awt.*;
import java.util.List;

public class NoteColors {
	// TODO: remove this suppression once the theme names are either internationalized, or we don't need to care about it anymore
	@SuppressWarnings("SpellCheckingInspection")
	private static final List<ColorComponent> COLORS = List.of(
			// VIOLA
			new ColorComponent(Color.decode("#d98cb3"), Color.decode("#993366"), Color.decode("#cc6699"), "purple theme"),
			// VERDE
			new ColorComponent(Color.decode("#5cd65c"), Color.decode("#009933"), Color.decode("#88cc00"), "green theme"),
			// BLU (DEFAULT)
			new ColorComponent(Color.decode("#66ccff"), Color.decode("#3399ff"), Color.decode("#99ccff"), "blue theme"),
			// ARANCIO
			new ColorComponent(Color.decode("#ffa64d"), Color.decode("#e67300"), Color.decode("#ffa366"), "orange theme"),
			// BIANCO
			new ColorComponent(Color.WHITE, Color.LIGHT_GRAY, Color.WHITE, "white theme")
	);

	private static NoteColors instance = null;


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
		for (ColorComponent item : COLORS) {
			if (item.getCommand().equals(command))
				return item;
		}
		return null;
	}

	public List<ColorComponent> getColors() {
		return COLORS;
	}

	public static ColorComponent getTheme(int index) {
		return (index < COLORS.size()) ? COLORS.get(index) : null;
	}

	public static ColorComponent getDefaultTheme() {
		return COLORS.get(2);
	}

}
