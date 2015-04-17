package poof.textui.user;

import java.io.IOException;
import java.util.Iterator;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.*;

/**
 * Command for the showing existing users.
 * §2.3.2.
 */
public class ListUsers extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ListUsers(FileSystem fs) {
		super(MenuEntry.LIST_USERS, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Display d = new Display(title());
		for (User u : entity().getUsers())
			d.addNewLine(u.getUsername()+":"+ u.getName()+":"+u.getHomeDir().getPath());
		d.display();
	}
}
