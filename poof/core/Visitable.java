package poof.core;

import poof.textui.shell.recursive.Visitor;

public interface Visitable{

	public void accept(Visitor visitor);

}