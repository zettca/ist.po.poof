package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.main.MenuEntry;
import poof.textui.main.Message;
import poof.core.Session;

/**
 * Command for loading a file system and the last logged user stored in the given file.
 */
public class Open extends Command<Session> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public Open(Session session) {
		super(MenuEntry.OPEN, session);
	}
	
	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f;
		if (!entity().isSaved()){
			f = new Form(title());
			InputBoolean opt = new InputBoolean(f, Message.saveBeforeExit());
			f.parse();

			// Guardar onde ??
		}
		
		f = new Form(title());
		InputString file = new InputString(f, Message.openFile());
		f.parse();

		try{
			entity().open(file.toString());
			entity().setIsSaved(true);
			((MainMenu) menu()).showOptionsNonEmpty();
		} catch (IOException e){
			Display d = new Display(title());
			d.addNewLine(Message.fileNotFound());
			d.display();
		} catch (ClassNotFoundException e){
			// nothing to do
		}
	}
}
