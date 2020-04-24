package it.gb.lang;

import java.util.*;

@SuppressWarnings("SpellCheckingInspection")
public class Res_it extends ListResourceBundle {

	private static final Object[][] contents = { { "s_default_title", "Senza nome" }, { "t_title", "Cambia il titolo" },
			{ "t_add", "Nuova nota" }, { "t_close", "Chiudi JNotes" }, { "t_delete", "Elimina nota" },
			{ "t_customize", "Personalizza" }, { "s_delete_title", "Elimina nota" }, { "s_delete_text", "Sicuro?" } };

	@Override
	public Object[][] getContents() {
		return contents;
	}
}
