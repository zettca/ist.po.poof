package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.shell.Message;
import poof.textui.shell.MenuEntry;
import poof.core.FileSystem;

/**
 * Command for changing current working directory.
 * §2.2.4.
 */
public class ChangeCurrentDirectory extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ChangeCurrentDirectory(FileSystem fs){
		super(MenuEntry.CD, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title());
		InputString s = new InputString(f, Message.directoryRequest());
		f.parse();
		
		entity().setCurrentDir(s.toString());
	}
}
