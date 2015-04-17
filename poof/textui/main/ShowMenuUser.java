package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Menu;
import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.user.CreateUser;
import poof.textui.user.ListUsers;
import poof.core.*;

/**
 * Command for showing the user menu.
 */
public class ShowMenuUser extends Command<Session> {
	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ShowMenuUser(Session session) {
		super(MenuEntry.MENU_USER_MGT, session);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Command<?>[] commands = {
			new CreateUser(entity().fs()),
			new ListUsers(entity().fs())
		};
		
		Menu userMenu = new Menu(poof.textui.user.MenuEntry.TITLE, commands);
		userMenu.open();
	}
}
