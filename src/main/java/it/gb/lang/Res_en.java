package it.gb.lang;

import java.util.*;

public class Res_en extends ListResourceBundle {

	private static final Object[][] contents = { { "s_default_title", "Untitled" }, { "t_title", "Change the title" },
			{ "t_add", "New note" }, { "t_close", "Close JNotes" }, { "t_delete", "Delete note" },
			{ "t_customize", "Customize" }, { "s_delete_title", "Delete note" }, { "s_delete_text", "Sure?" } };

	@Override
	public Object[][] getContents() {
		return contents;
	}
}
