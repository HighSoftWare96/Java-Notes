package it.gb.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import javax.swing.JOptionPane;

import it.gb.gui.OneNoteThread;

public class SaverThread extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			saveAll();
		}
	}

	public static void saveAll() {
		System.out.println("Saving...");
		HashSet<NoteData> notes = new HashSet<>();

		for (OneNoteThread item : Controller.getThreads()) {
			NoteData temp = item.getData();
			if (!temp.getText().equals(""))
				notes.add(item.getData());
		}

		if (!Main.noteFile.exists()) { // se il file non esiste lo creo
			try {
				Main.noteFile.getParentFile().mkdirs();
				Main.noteFile.createNewFile();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error while creating output file", "Critical error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(-1);
			}
		}

		try {
			FileOutputStream outToFile;
			ObjectOutputStream byteStreamToSave;

			// creo un file di output (stesso di prima)
			outToFile = new FileOutputStream(Main.noteFile);

			// creo uno stream di output che punta al file
			byteStreamToSave = new ObjectOutputStream(outToFile);

			// scrivo l'oggetto nel file in modo da poterlo recuperare la
			// prossima volta
			byteStreamToSave.writeObject((Object) notes);
			byteStreamToSave.close();
			outToFile.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error while saving data on file", "Critical error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
