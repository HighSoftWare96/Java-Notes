package it.gb.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import it.gb.gui.listeners.ColorSelectionListener;
import it.gb.gui.listeners.NoteThreadMouseListener;
import it.gb.gui.listeners.TitleChangeListener;
import it.gb.gui.listeners.WindowListener;
import it.gb.gui.themes.ColorComponent;
import it.gb.gui.themes.NoteColors;
import it.gb.lib.ComponentMover;
import it.gb.lib.ComponentResizer;
import it.gb.main.Main;
import it.gb.main.NoteData;

public class OneNoteThread extends Thread {

	private String title;
	private String body;

	private ColorComponent theme;

	private JDialog frame;

	private JPanel titlePanel = new JPanel();
	private JPanel colorsPanel = new JPanel(new FlowLayout());
	private JPanel northPanel = new JPanel(new BorderLayout());
	private JPanel buttonsMenu = new JPanel(new FlowLayout());
	private JPanel centerPanel = new JPanel(new FlowLayout());

	private JButton closeBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/x.png")));
	private JLabel titleLabel = new JLabel();

	private JTextField titleField = new JTextField();
	private JButton customizeBtn = new JButton(
			new ImageIcon(this.getClass().getResource("/images/customize.png")));
	private JButton titleBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/ok.png")));
	private JButton addBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/plus.png")));
	private JButton titleChangeBtn = new JButton(
			new ImageIcon(this.getClass().getResource("/images/change.png")));
	private JButton removeBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/minus.png")));
	private JTextPane noteArea = new JTextPane();
	private JScrollPane noteAreaContainer = new JScrollPane(noteArea);

	// empty Note
	public OneNoteThread(JFrame parent) {
		this.title = Main.rsBundle.getString("s_default_title");
		this.body = "";

		frame = new JDialog(parent);
		frame.setLocation(new Point(10, 10));

		this.setNewTheme(NoteColors.getDefaultTheme());

		showTitlePanel(true);
	}

	// already-filled Note
	public OneNoteThread(JFrame parent, NoteData data) {
		this.title = data.getTitle();
		this.body = data.getText();

		frame = new JDialog(parent);
		frame.setLocation(data.getLocation());
		frame.setSize(data.getSize());

		this.setNewTheme(data.getTheme());

		// TODO: set focus on the note text
		this.noteArea.setFocusable(true);

		showTitlePanel(false);
	}

	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				OneNoteThread.this.buildGUI();
			}
		});
	}

	public void buildGUI() {
		frame.setTitle(this.title);
		frame.setUndecorated(true);
		frame.addWindowListener(new WindowListener());
		frame.getRootPane().setBorder(new LineBorder(Color.WHITE));

		titleLabel.setText(this.title);
		titleLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
		titleLabel.setPreferredSize(new Dimension(115, 40));
		noteArea.setText(this.body);
		titleField.setText(this.title);
		this.noteArea.setText(this.body);

		// START OF TOP PANEL
		titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		NoteThreadMouseListener listenerMouse = new NoteThreadMouseListener(this);

		// START OF TOP-BUTTON PANEL
		// change title button
		titleChangeBtn.setPreferredSize(new Dimension(30, 30));
		titleChangeBtn.setBackground(Color.WHITE);
		titleChangeBtn.setContentAreaFilled(false);
		titleChangeBtn.setOpaque(true);
		titleChangeBtn.addActionListener(listenerMouse);
		titleChangeBtn.setActionCommand(ActionCommands.titleChangeCommand);
		titleChangeBtn.setToolTipText(Main.rsBundle.getString("t_title"));

		// new note button
		addBtn.addActionListener(listenerMouse);
		addBtn.setBackground(Color.WHITE);
		addBtn.setContentAreaFilled(false);
		addBtn.setOpaque(true);
		addBtn.setPreferredSize(new Dimension(30, 30));
		addBtn.setActionCommand(ActionCommands.newNote);
		addBtn.setFocusable(false);
		addBtn.setToolTipText(Main.rsBundle.getString("t_add"));

		// close button
		closeBtn.addActionListener(listenerMouse);
		closeBtn.setBackground(Color.WHITE);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setOpaque(true);
		closeBtn.setPreferredSize(new Dimension(30, 30));
		closeBtn.setActionCommand(ActionCommands.closeCommand);
		closeBtn.setFocusable(false);
		closeBtn.setToolTipText(Main.rsBundle.getString("t_close"));

		// delete note button
		removeBtn.addActionListener(listenerMouse);
		removeBtn.setBackground(Color.WHITE);
		removeBtn.setContentAreaFilled(false);
		removeBtn.setOpaque(true);
		removeBtn.setPreferredSize(new Dimension(30, 30));
		removeBtn.setActionCommand(ActionCommands.remove);
		removeBtn.setFocusable(false);
		removeBtn.setToolTipText(Main.rsBundle.getString("t_delete"));

		// change color button
		customizeBtn.addActionListener(listenerMouse);
		customizeBtn.setPreferredSize(new Dimension(30, 30));
		customizeBtn.setBackground(Color.WHITE);
		customizeBtn.setContentAreaFilled(false);
		customizeBtn.setOpaque(true);
		customizeBtn.setActionCommand(ActionCommands.customizeCommand);
		customizeBtn.addActionListener(listenerMouse);
		customizeBtn.setFocusable(false);
		customizeBtn.setToolTipText(Main.rsBundle.getString("t_customize"));

		// button panel
		buttonsMenu.add(customizeBtn);
		buttonsMenu.add(titleChangeBtn);
		buttonsMenu.add(removeBtn);
		buttonsMenu.add(addBtn);
		buttonsMenu.add(closeBtn);
		// END OF TOP-BUTTON PANEL

		// GUI FUNCTIONS
		// dragging
		ComponentMover cm = new ComponentMover(JDialog.class, northPanel);
		cm.setChangeCursor(false);
		// resizing
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(frame);
		cr.setSnapSize(new Dimension(10, 10));
		cr.setMinimumSize(new Dimension(300, 300));
		cr.setMaximumSize(new Dimension(300, 1000));

		// add them to the top panel
		northPanel.add(titleLabel, BorderLayout.WEST);
		northPanel.add(buttonsMenu, BorderLayout.EAST);
		// END OF TOP PANEL

		// START OF CENTRAL PANEL
		// Color panel
		colorsPanel.setPreferredSize(new Dimension(300, 50));
		colorsPanel.setVisible(false);

		// get themes
		NoteColors colorLib = NoteColors.getInstance();

		ColorSelectionListener themeListener = new ColorSelectionListener(this);

		// loop over themes
		for (ColorComponent item : colorLib.getColors()) {
			JButton tempBtn = new JButton();
			tempBtn.setPreferredSize(new Dimension(30, 30));
			tempBtn.setBorder(new LineBorder(Color.BLACK));
			tempBtn.setBackground(item.getColors()[0]);
			tempBtn.setContentAreaFilled(false);
			tempBtn.setOpaque(true);

			tempBtn.addActionListener(themeListener);
			tempBtn.setActionCommand(item.getCommand());

			colorsPanel.add(tempBtn);
		}

		// confirm button
		JButton colorsOkBtn = new JButton(new ImageIcon(this.getClass().getResource("/images/ok.png")));
		colorsOkBtn.setPreferredSize(new Dimension(30, 30));
		colorsOkBtn.setActionCommand(ActionCommands.customizeOkCommand);
		colorsOkBtn.setBackground(Color.WHITE);
		colorsOkBtn.setContentAreaFilled(false);
		colorsOkBtn.setOpaque(true);
		colorsOkBtn.addActionListener(listenerMouse);
		colorsPanel.add(colorsOkBtn);

		// Change Title panel
		titlePanel.setPreferredSize(new Dimension(300, 50));

		// text field
		titleField.setPreferredSize(new Dimension(220, 25));
		titleField.addKeyListener(new TitleChangeListener(this));

		// Title button
		titleBtn.setPreferredSize(new Dimension(30, 30));
		titleBtn.setActionCommand(ActionCommands.titleOkCommand);
		titleBtn.addActionListener(listenerMouse);
		titleBtn.setBackground(Color.WHITE);
		titleBtn.setContentAreaFilled(false);
		titleBtn.setOpaque(true);

		// add them to the Change Title panel
		titlePanel.add(this.titleField);
		titlePanel.add(this.titleBtn);

		// text field for a note
		noteArea.setFont(new Font("Segoe Print", Font.PLAIN, 20));
		noteAreaContainer.setPreferredSize(new Dimension(290, 250));
		noteAreaContainer.setBorder(new EmptyBorder(0, 0, 0, 0));

		// add panels to the central panel
		centerPanel.add(titlePanel);
		centerPanel.add(colorsPanel);
		centerPanel.add(this.noteAreaContainer);
		// END OF CENTRAL PANEL

		// GUI things
		frame.setResizable(true);
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public Component getFrame() {
		return this.frame;
	}

	public NoteData getData() {
		return new NoteData(this.title, noteArea.getText(), this.frame.getLocationOnScreen(), this.theme,
				this.frame.getSize());
	}

	public Point getLocation() {
		return this.frame.getLocationOnScreen();
	}

	public void setLocation(Point point) {
		if (point == null)
			this.frame.setLocationRelativeTo(null);
		else
			this.frame.setLocation(point);
	}

	public void setNewTheme(ColorComponent colorComp) {
		Color[] colors = colorComp.getColors();
		this.theme = colorComp;
		Color mainColor = colors[0];
		Color backColor = colors[1];
		Color secColor = colors[2];
		frame.setBackground(mainColor);
		northPanel.setBackground(backColor);
		buttonsMenu.setBackground(backColor);
		centerPanel.setBackground(mainColor);
		colorsPanel.setBackground(secColor);
		titlePanel.setBackground(secColor);
		noteArea.setBackground(mainColor);
		noteAreaContainer.setBackground(mainColor);
	}

	public void setNewTitle() {
		this.title = this.titleField.getText();
		frame.setTitle(title);
		titleLabel.setText(title);
	}

	public boolean isWithText() {
		return !this.noteArea.getText().equals("");
	}

	public void dispose() {
		frame.dispose();
	}

	public void showTitlePanel(boolean b) {
		if (b) {
			this.titleChangeBtn.setVisible(false);
			this.titlePanel.setVisible(true);
			this.frame.setSize(new Dimension(300, frame.getHeight() < 350 ? 350 : frame.getHeight()));
		} else {
			titlePanel.setVisible(false);
			titleChangeBtn.setVisible(true);
			this.frame.setSize(new Dimension(300, frame.getHeight() == 350 ? 300 : frame.getHeight()));
		}
	}

	public void showColorsPanel(boolean b) {
		if (b) {
			this.customizeBtn.setVisible(false);
			this.colorsPanel.setVisible(true);
			this.frame.setSize(new Dimension(300, 350));
		} else {
			this.colorsPanel.setVisible(false);
			this.customizeBtn.setVisible(true);
			this.frame.setSize(new Dimension(300, 300));
		}
	}

	public void confirmTitle() {
		this.titleBtn.doClick();
	}

}
