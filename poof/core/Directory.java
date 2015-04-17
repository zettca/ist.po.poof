package poof.core;

import java.io.Serializable;
import java.util.TreeSet;

import poof.textui.exception.IsNotFileException;
import poof.textui.exception.EntryUnknownException;
import poof.textui.shell.recursive.Visitor;
import poof.core.Visitable;
import poof.core.Entry;
import poof.core.File;

public class Directory extends Entry implements Serializable, Visitable{
	private TreeSet<Entry> _content;

	public Directory(String name, Directory parent, String owner, 
					String permission){
		super(name, parent, owner, permission);
		_content = new TreeSet<Entry>();
	}

	public boolean isDir(){ return true; }
	public boolean isFile(){ return false; }

	public TreeSet<Entry> getContent(){
		return _content;
	}

	public int getSize(){
		if (getName().equals("."))
			return getParent().getSize();
		if (getName().equals(".."))
			return getParent().getParent().getSize();
		
		return size() * _content.size();
	}
	
	public boolean hasDir(String name){
		for (Entry e : _content)
			if (e.getName().equals(name) && e.isDir())
				return true;
		return false;
	}

	/* Returns Entry with named name in the current Directory */
	public Entry find(String name) throws EntryUnknownException{
		for (Entry e : _content)
			if (e.getName().equals(name))
				return e;
		throw new EntryUnknownException(name);
	}

	public Directory getDir(String name) throws EntryUnknownException{
		for (Entry e : _content)
			if (e.getName().equals(name) && e.isDir())
				return (Directory) e;
		throw new EntryUnknownException(name);
	}

	public File getFile(String name) throws EntryUnknownException, 
											IsNotFileException{
		for (Entry e : _content)
			if (e.getName().equals(name)){
				if (e.isFile())
					return (File) e;
				throw new IsNotFileException(name);
			}
		throw new EntryUnknownException(name);
	}

	public Directory addDir(String name, String owner, String privacy) {
		Directory dir = new Directory(name, this, owner, privacy);
		_content.add(dir);
		dir.addDots();
		return dir;
	}

	public File addFile(String name, String owner, 
						String privacy, String content){
		File f = new File(name, this, owner, privacy, content);
		_content.add(f);
		return f;
	}

	public void addDots(){ // Add the special directories . and ..
		_content.add(new Directory(".",this, getOwner(), getPermission()));
		_content.add(new Directory("..",this, getParent().getOwner(),
					getParent().getPermission()));
	}

	// Check if Entry already exists. Auxiliar method
	public boolean hasEntry(String name){
		for (Entry e : _content)
			if (e.getName().equals(name))
				return true;

		return false;
	}

	// Polimorfism thing
	public void addContent(String e){ }

	@Override
	public void accept(Visitor visitor){
		for (Entry e : _content)
			e.accept(visitor);
		visitor.visit(this);
	}

}
