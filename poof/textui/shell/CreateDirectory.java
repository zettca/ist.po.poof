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
 * Command for creating a directory in the current working directory.
 * §2.2.6.
 */
public class CreateDirectory extends Command<FileSystem> {
	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public CreateDirectory(FileSystem fs) {
		super(MenuEntry.MKDIR, fs);
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

		entity().addDir(entity().getCurrentDir(), s.toString());
	}
}