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
import poof.core.Directory;
import poof.core.Entry;

/**
 * Command for showing an entry of the current working directory.
 * §2.2.2.
 */
public class ListEntry extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ListEntry(FileSystem fs) {
		super(MenuEntry.LS_ENTRY, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title());
		InputString s = new InputString(f, Message.nameRequest());
		f.parse();
		
		Display d = new Display(title());
		Entry e = entity().getCurrentDir().find(s.toString());

		String type = (e instanceof Directory) ? "d" : "-";
		String perm = e.isPublic() ? "w" : "-";

		d.addNewLine(type+" "+perm+" "+e.getOwner()+" "+
					String.valueOf(e.getSize())+" "+e.getName());

		d.display();
		
	}
}
