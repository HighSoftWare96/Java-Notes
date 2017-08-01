package it.gb.main;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import it.gb.gui.NoteColors;
import it.gb.gui.OneNoteThread;
import it.gb.gui.listeners.WindowListener;

public class Main {

	private static ServerSocket socketOffline;
	private static JFrame mainInvisibleFrame = new JFrame("JNotes");
	private static final String notePath = System.getenv("APPDATA") + "\\JNotes\\notes.jnotes";
	private static final File noteFile = new File(notePath);
	private static HashSet<OneNoteThread> threads = new HashSet<>();
	private static OneNoteThread lastThreadCreated = null;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			socketOffline = new ServerSocket(8765);
		} catch (IOException e) {
			System.out.println("Another instance is probably running...");
			System.exit(0);
		}

		buildGUI();
		findNotes();
		mainInvisibleFrame.setVisible(true);
	}

	private static void buildGUI() {
		// inizializzo i temi
		NoteColors.initilize();
		mainInvisibleFrame.addWindowListener(new WindowListener());
		mainInvisibleFrame.setIconImage(new ImageIcon(Main.class.getResource("/resources/images/icon.png")).getImage());
		mainInvisibleFrame.setSize(0, 0);
		mainInvisibleFrame.setUndecorated(true);
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
			JOptionPane.showMessageDialog(null, "Catalogo IO: inizializzazione dati da file", "Errore",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

		for (NoteData item : notes) {
			newNote(item);
		}

		// nessuna nota recuperata
		if (notes.size() == 0)
			newNote();
	}

	private static void saveNotes() {
		HashSet<NoteData> notes = new HashSet<>();

		for (OneNoteThread item : threads) {
			NoteData temp = item.getData();
			if (!temp.getText().equals(""))
				notes.add(item.getData());
		}

		if (!noteFile.exists()) { // se il file non esiste lo creo
			try {
				noteFile.getParentFile().mkdirs();
				noteFile.createNewFile();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Catalogo IO: creazione file di salvataggio", "Errore",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(-1);
			}
		}

		try {
			FileOutputStream outToFile;
			ObjectOutputStream byteStreamToSave;

			// creo un file di output (stesso di prima)
			outToFile = new FileOutputStream(noteFile);

			// creo uno stream di output che punta al file
			byteStreamToSave = new ObjectOutputStream(outToFile);

			// scrivo l'oggetto nel file in modo da poterlo recuperare la
			// prossima volta
			byteStreamToSave.writeObject((Object) notes);
			byteStreamToSave.close();
			outToFile.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Catalogo IO: salvataggio dati su file", "Errore",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

	}

	public static void newNote() {
		if (lastThreadCreated == null || lastThreadCreated.isWithText()) {
			// se l'ultimo ha del testo allora ne creo una nuova
			OneNoteThread thread = new OneNoteThread(mainInvisibleFrame);
			threads.add(thread);
			thread.start();
			// è la prima nota creata
			if (lastThreadCreated == null)
				thread.setLocation(null);
			// di fianco all'ultima creata
			else {
				Point location = lastThreadCreated.getLocation();
				location.setLocation(new Point(
						(int) ((location.getX() + 310)
								% (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 200)),
						(int) location.getY()));
				thread.setLocation(location);
			}
			lastThreadCreated = thread;
		}
	}

	public static void newNote(NoteData data) {
		// location già prefissata
		OneNoteThread thread = new OneNoteThread(mainInvisibleFrame, data);
		threads.add(thread);
		thread.start();
		lastThreadCreated = thread;
	}

	public static void exit(int code, OneNoteThread thread) {
		saveAndClose(code);
	}

	public static void saveAndClose(int code) {
		saveNotes();
		try {
			socketOffline.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(code);
	}

	public static void removeNote(OneNoteThread thread) {
		if (JOptionPane.showConfirmDialog(thread.frame, "Sicuro?", "Elimina nota", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				new ImageIcon(Main.class.getResource("/resources/images/minus_big.png"))) == 0) {
			thread.dispose();
			threads.remove(thread);
			if (threads.size() == 0)
				saveAndClose(0);
		}
	}
}
