package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.shell.MenuEntry;
import poof.textui.shell.Message;
import poof.core.FileSystem;


/**
 * Command for writing in a file of the current working directory.
 * §2.2.8.
 */
public class WriteFile extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public WriteFile(FileSystem fs) {
		super(MenuEntry.APPEND, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title());
		InputString fileName = new InputString(f, Message.fileRequest());
		InputString content = new InputString(f, Message.textRequest());
		f.parse();

		entity().writeLine(fileName.toString(), content.toString());
	}
}
