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
 * Command for changing the owner of an entry of the current working directory.
 * §2.2.11.
 */
public class ChangeOwner extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ChangeOwner(FileSystem fs) {
		super(MenuEntry.CHOWN, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title()); // V Trocado no enunciado ?? V
		InputString entryName = new InputString(f, Message.nameRequest());
		InputString username = new InputString(f, Message.usernameRequest());
		f.parse();

		entity().changeOwner(entity().getCurrentDir().find(entryName.toString()), username.toString());
	}
}
