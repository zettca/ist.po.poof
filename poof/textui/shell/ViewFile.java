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
 * Command for viewing the content of a file of the current working directory.
 * §2.2.9.
 */
public class ViewFile extends Command<FileSystem>{

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ViewFile(FileSystem fs) {
		super(MenuEntry.CAT, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() throws InvalidOperation {
		Form f = new Form(title());
		InputString s = new InputString(f, Message.fileRequest());
		f.parse();

		Display d = new Display(title());
		d.addNewLine(entity().getCurrentDir().getFile(s.toString()).getContent());
		d.display();
	}
}
