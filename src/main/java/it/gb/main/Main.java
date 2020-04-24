package it.gb.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import it.gb.gui.OneNoteThread;
import it.gb.gui.listeners.WindowListener;
import it.gb.gui.themes.NoteColors;

public class Main {
	
	// locale e insieme di risorse linguistiche
	private static Locale locale;
	public static ResourceBundle rsBundle;
	
	private static ServerSocket socketOffline;
	private static JFrame mainInvisibleFrame;
	private static String notePath;
	public static File noteFile;

	public static void main(String[] args) {

		// TODO: make this a command line parameter instead, but just use the current directory for now
//		notePath = System.getenv("APPDATA") + "\\JNotes\\notes.jnotes";
		notePath = "notes.jnotes";
		noteFile = new File(notePath);
		
		// Impostazione della lingua del programma
		locale = Locale.getDefault();

		try {
			// imposto la lingua della risorsa
			rsBundle = ResourceBundle.getBundle("it.gb.lang.Res", locale);
		} catch (MissingResourceException e) {
			// se non la trova metto inglese come predefinito
			System.out.println("MissingResourceException: setting English language as default");
			locale = new Locale("en", "US");
			rsBundle = ResourceBundle.getBundle("it.gb.lang.Res", locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		// creazione di una socket fittizia per evitare altre istanze del
		// programma nel sistema
		try {
			socketOffline = new ServerSocket(8765);
		} catch (IOException e) {
			System.out.println("Another instance is probably running...");
			System.exit(0);
		}
		
		// impostazione della grafica simile al sistema di supporto
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// inizializzo i temi
				NoteColors.initilize();
				buildGUI();
				findNotes();
				mainInvisibleFrame.setVisible(true);
			}
		});
		
		// creazione di un thread che salva ogni 20 secondi
		new SaverThread().start();
	}

	private static void buildGUI() {
		mainInvisibleFrame = new JFrame("JNotes");
		mainInvisibleFrame.addWindowListener(new WindowListener());
		mainInvisibleFrame.setIconImage(new ImageIcon(Main.class.getResource("/images/icon.png")).getImage());
		mainInvisibleFrame.setSize(0, 0);
		mainInvisibleFrame.setUndecorated(true);
	}
	
	public static JFrame getFrame() {
		return mainInvisibleFrame;
	}

	private static void findNotes() {
		HashSet<NoteData> notes = new HashSet<>();

		try {
			if (noteFile.exists()) {
				// recupero i dati
				// file di input
				FileInputStream inputFile = new FileInputStream(noteFile);
				// creazione dello stream di byte da ricevere
				ObjectInputStream streamInput = new ObjectInputStream(inputFile);
				// recupero l'array list dal file e lo salvo nell'arraylist
				// attuale del programma
				notes = (HashSet<NoteData>) streamInput.readObject();
				streamInput.close();
			} else // creo il catalogo da zero
				notes = new HashSet<>();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errore while initializing data from file", "Critical error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

		for (NoteData item : notes) {
			Controller.newNote(item);
		}

		// nessuna nota recuperata
		if (notes.size() == 0)
			Controller.newNote();
	}

	public static void saveAndClose(int code) {
		SaverThread.saveAll();
		try {
			socketOffline.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(code);
	}
}
