package poof.textui.user;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.textui.user.MenuEntry;
import poof.textui.user.Message;
import poof.core.*;

/**
 * Command for creating a user.
 * §2.3.1.
 */
public class CreateUser extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public CreateUser(FileSystem fs) {
		super(MenuEntry.CREATE_USER, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title());
		InputString user = new InputString(f, Message.usernameRequest());  
		InputString name = new InputString(f, Message.nameRequest());
		f.parse();

		entity().addUser(user.toString(), name.toString());
	}
}