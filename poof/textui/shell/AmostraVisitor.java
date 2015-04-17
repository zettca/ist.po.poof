package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.textui.shell.recursive.CalcSubSizes;
import poof.textui.shell.recursive.PrintSubPaths;
import poof.textui.shell.Message;
import poof.core.Visitable;
import poof.core.Directory;
import poof.core.Entry;

public class AmostraVisitor extends Command<Directory> {

	public AmostraVisitor(Directory dir){
		super("Recursive Visitor Stuff", dir);
	}

	public final void execute() throws InvalidOperation{

		Visitable dir = entity();
		dir.accept(new PrintSubPaths());

		/* 
		* Para adicionar um novo comando, basta criar uma classe
		* e evocá-la no accept() do Visitable.
		*/

	}
}
