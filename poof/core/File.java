package poof.core;

import java.io.Serializable;

import poof.textui.shell.recursive.Visitor;
import poof.core.Visitable;
import poof.core.Entry;

public class File extends Entry implements Serializable, Visitable{
	private String _content;

	public File(String name, Directory parent,
				String owner, String permission, String content){
		super(name, parent, owner, permission);
		_content = content;
	}

	public boolean isDir(){ return false; }
	public boolean isFile(){ return true; }

	public int getSize(){
		return _content.length();
	}

	public String getContent(){
		return _content;
	}

	public void addContent(String content){
		_content += content;
	}

	public int compareTo(Entry e){
		return getName().compareTo(e.getName());
	}

	@Override
	public void accept(Visitor visitor){
		visitor.visit(this);
	}

}