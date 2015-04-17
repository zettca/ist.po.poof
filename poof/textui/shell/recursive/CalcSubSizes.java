package poof.textui.shell.recursive;

import poof.textui.shell.recursive.Visitor;
import poof.core.Directory;
import poof.core.Entry;
import poof.core.File;

public class CalcSubSizes implements Visitor{
	private int _size;

	@Override
	public void visit(Directory dir){
		_size += dir.size();
		for (Entry e : dir.getContent())
			visit(e);
	}

	@Override
	public void visit(Entry ent){
		_size += ent.size();
	}

	@Override
	public void visit(File file){
		_size += file.getSize();
	}

}