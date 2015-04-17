package poof.textui.main;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.main.Message;
import poof.textui.main.MenuEntry;
import poof.core.Session;

/**
 * Command for saving the relevant applicaion state.
 */
public class Save extends Command<Session> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public Save(Session session) {
		super(MenuEntry.SAVE, session);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title());
		InputString s = new InputString(f, Message.newSaveAs());
		f.parse();
		
		try{
			entity().save(s.toString());
		} catch (IOException e){
			// no permission to open file
		}

		entity().setIsSaved(true);
		((MainMenu) menu()).showOptionsNonEmpty();
	}
}
