package poof.textui.shell.recursive;

import java.util.ArrayList;

import poof.textui.shell.recursive.Visitor;
import poof.core.Directory;
import poof.core.Entry;
import poof.core.File;

public class PrintSubPaths implements Visitor{
	ArrayList<String> _strs = new ArrayList<String>();
	
	@Override
	public void visit(Directory dir){
		for (Entry e : dir.getContent())
			visit(e);
	}

	@Override
	public void visit(Entry ent){
		System.out.println((ent.getPath()));
	}

	@Override
	public void visit(File file){
		System.out.println(file.getPath());
	}


	public ArrayList<String> getPaths(){
		return _strs;
	}

}