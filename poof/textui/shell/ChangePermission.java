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
 * Command for changing the permission of an entry of the current working directory.
 * §2.2.10.
 */
public class ChangePermission extends Command<FileSystem> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangePermission(FileSystem fs) {
    super(MenuEntry.CHMOD, fs);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString entry = new InputString(f, Message.nameRequest());
    InputString privacy = new InputString(f, Message.writeMode());
    f.parse();

    entity().changePermission(entity().getCurrentDir().find(entry.toString()), privacy.toString());
  }
}
