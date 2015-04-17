package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Menu;
import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputInteger;
import pt.utl.ist.po.ui.InvalidOperation;
import poof.core.*;

import poof.textui.shell.*;

/**
 * Command for showing the shell menu.
 */
public class ShowMenuShell extends Command<Session> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ShowMenuShell(Session session) {
		super(MenuEntry.MENU_SHELL, session);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Command<?>[] commands = {
			new ListCurrentDir(entity().fs()),
			new ListEntry(entity().fs()),
			new RemoveEntry(entity().fs()),
			new ChangeCurrentDirectory(entity().fs()),
			new CreateFile(entity().fs()),
			new CreateDirectory(entity().fs()),
			new ShowPathOfCurrentDirectory(entity().fs()),
			new WriteFile(entity().fs()),
			new ViewFile(entity().fs()),
			new ChangePermission(entity().fs()),
			new ChangeOwner(entity().fs()),
			//new AmostraVisitor(entity().fs().getRootDir()),
		};
		
		Menu shellMenu = new Menu(poof.textui.shell.MenuEntry.TITLE, commands);
		shellMenu.open();
	}
}