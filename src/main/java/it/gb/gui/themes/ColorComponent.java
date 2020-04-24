package it.gb.gui.themes;

import java.awt.Color;
import java.io.Serializable;

public class ColorComponent implements Serializable {

	private static final long serialVersionUID = -5239977092938421570L;
	private final Color mainColor;
	private final Color backColor;
	private final Color secColor;
	private final String command;

	public ColorComponent(Color main, Color back, Color sec, String command) {
		this.mainColor = main;
		this.secColor = sec;
		this.backColor = back;
		this.command = command;
	}

	public Color[] getColors() {
		return new Color[] { this.mainColor, this.backColor, this.secColor };
	}

	public String getCommand() {
		return this.command;
	}
}
