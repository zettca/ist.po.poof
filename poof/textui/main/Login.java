package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;
import poof.textui.main.Message;
import poof.textui.main.MenuEntry;
import poof.textui.exception.UserUnknownException;

/**
 * Command for the login option.
 * §2.1.2.
 */
public class Login extends Command<Session> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Login(Session session) {
    super(MenuEntry.LOGIN, session);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    Display d = new Display();
    InputString username = new InputString(f, Message.usernameRequest());
    f.parse();

    entity().fs().login(username.toString());
  }
}
