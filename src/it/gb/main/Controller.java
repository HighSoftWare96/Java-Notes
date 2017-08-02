package it.gb.main;

import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import it.gb.gui.OneNoteThread;

public class Controller {

	private static OneNoteThread lastThreadCreated = null;
	private static HashSet<OneNoteThread> threads = new HashSet<>();

	
	public static HashSet<OneNoteThread> getThreads() {
		return threads;
	}
	
	public static void newNote() {
		if (lastThreadCreated == null || lastThreadCreated.isWithText()) {
			// se l'ultimo ha del testo allora ne creo una nuova
			OneNoteThread thread = new OneNoteThread(Main.getFrame());
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
		OneNoteThread thread = new OneNoteThread(Main.getFrame(), data);
		threads.add(thread);
		thread.start();
		lastThreadCreated = thread;
	}

	public static void removeNote(OneNoteThread thread) {
		// se la nota non è vuota posso eliminarla
		if (thread.isWithText() && JOptionPane.showConfirmDialog(thread.getFrame(), "Sicuro?", "Elimina nota", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				new ImageIcon(Main.class.getResource("/resources/images/minus_big.png"))) == 0) {
			thread.dispose();
			threads.remove(thread);
			if (threads.size() == 0)
				Main.saveAndClose(0);
		}
	}
	
	public static void exit(int code) {
		Main.saveAndClose(code);
	}

}
