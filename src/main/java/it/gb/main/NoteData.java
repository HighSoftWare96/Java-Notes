package it.gb.main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

import it.gb.gui.themes.ColorComponent;

public class NoteData implements Serializable {

	private static final long serialVersionUID = -41657801163597106L;
	private String noteText;
	private String noteTitle;
	private Point locationOnScreen;
	private ColorComponent theme;
	private Dimension size;

	public NoteData(String title, String text, Point location, ColorComponent theme, Dimension size) {
		this.noteText = text;
		this.noteTitle = title;
		this.locationOnScreen = location;
		this.theme = theme;
		this.size = size;
	}

	public Dimension getSize() {
		return this.size;
	}

	public String getTitle() {
		return this.noteTitle;
	}

	public String getText() {
		return this.noteText;
	}

	public Point getLocation() {
		return this.locationOnScreen;
	}

	public ColorComponent getTheme() {
		return this.theme;
	}

}
