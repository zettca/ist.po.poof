package poof.textui.main;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.main.*;
import poof.core.Session;

/**
 * Command for creating a new file system and logging the root user.
 */
public class New extends Command<Session> {
	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public New(Session session) {
		super(MenuEntry.NEW, session);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		if (!entity().isSaved()){
			Form f = new Form(title());
			InputBoolean opt = new InputBoolean(f, Message.saveBeforeExit());
			f.parse();
		}

		entity().create();

		entity().setIsSaved(false);
		((MainMenu) menu()).showOptionsNonEmpty();
	}
}
