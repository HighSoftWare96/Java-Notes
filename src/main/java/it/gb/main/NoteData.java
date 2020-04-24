package it.gb.main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

import it.gb.gui.themes.ColorComponent;

public class NoteData implements Serializable {

	private static final long serialVersionUID = -41657801163597106L;
	private final String noteText;
	private final String noteTitle;
	private final Point locationOnScreen;
	private final ColorComponent theme;
	private final Dimension size;

	public NoteData(String title, String text, Point location, ColorComponent theme, Dimension size) {
		this.noteText = text;
		this.noteTitle = title;
		this.locationOnScreen = location;
		this.theme = theme;
		this.size = size;
	}

	public Dimension getSize() {
		return size;
	}

	public String getTitle() {
		return noteTitle;
	}

	public String getText() {
		return noteText;
	}

	public Point getLocation() {
		return locationOnScreen;
	}

	public ColorComponent getTheme() {
		return theme;
	}

}
