package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputInteger;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.shell.MenuEntry;
import poof.textui.shell.Message;
import poof.core.FileSystem;
import poof.core.Directory;
import poof.core.Entry;
import poof.core.File;

/**
 * Command for showing the content of working directory.
 * §2.2.1.
 */
public class ListCurrentDir extends Command<FileSystem> {

	/**
	 * Constructor.
	 * 
	 * @param entity the target entity.
	 */
	public ListCurrentDir(FileSystem fs) {
		super(MenuEntry.LS, fs);
	}

	/**
	 * Execute the command.
	 */
	@Override
	@SuppressWarnings("nls")
	public final void execute() {
		Display d = new Display(title());
		for (Entry e: entity().getCurrentDir().getContent()) {
			String perm = (e.isPublic() ? "w" : "-");
			if (e instanceof Directory){
				Directory dir = (Directory) e;
				d.addNewLine("d "+perm+" "+dir.getOwner()+" "+
							String.valueOf(dir.getSize())+" "+dir.getName());
			} else {
				File file = (File) e;
				d.addNewLine("- "+perm+" "+file.getOwner()+" "+
							String.valueOf(file.getSize())+" "+file.getName());
			}
		}
		d.display();
	}
}
