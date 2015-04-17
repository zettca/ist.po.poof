package poof.textui.shell.recursive;

import poof.textui.shell.recursive.*;
import poof.core.Directory;
import poof.core.Entry;
import poof.core.File;

public interface Visitor{
	public void visit(Directory dir);
	public void visit(Entry dir);
	public void visit(File file);
}