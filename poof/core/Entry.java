package poof.core;

import java.io.Serializable;

import poof.textui.shell.recursive.Visitor;
import poof.core.Visitable;
import poof.core.Directory;
import poof.core.File;

public abstract class Entry implements Serializable, Comparable<Entry>, Visitable{
	private String _name;
	private String _owner;
	private String _permission;
	private Directory _parent;

	public Entry(String name, Directory parent,
				String owner, String permission){
		_name = name;
		_parent = parent;
		_owner = owner;
		_permission = permission;
	}

	public int size(){
		return 8;
	}

	public String getName(){
		return _name;
	}

	public Directory getParent(){
		return _parent;
	}

	public String getOwner(){
		return _owner;
	}

	public String getPermission(){
		return _permission;
	}

	public boolean isPublic(){
		return _permission.equals("public");
	}

	public boolean isPrivate(){
		return _permission.equals("private");
	}

	public void setName(String name){
		_name = name;
	}

	public void setParent(Directory dir){
		_parent = dir;
	}

	public void setOwner(String owner){
		_owner = owner;
	}

	public void setPermission(String perm){
		if (perm.equals("public") || perm.equals("private"))
			_permission = perm;
	}

	public String getPath(){
		if (_parent.getName().equals("") || _name.equals("") ||
			_parent.getParent().equals(getParent()))
			return "/" + getName();
		return _parent.getPath() + "/" + getName();
	}

	public boolean equals(Entry e){
		return getName().equals(e.getName());
	}

	public int compareTo(Entry e){
		return getName().compareTo(e.getName());
	}

	@Override
	public void accept(Visitor visitor){
		visitor.visit(this);
	}

	public abstract int getSize();
	public abstract boolean isDir();
	public abstract boolean isFile();

	public abstract void addContent(String str);

}