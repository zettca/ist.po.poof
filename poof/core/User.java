package poof.core;

import java.io.Serializable;

import poof.core.Entry;
import poof.core.Directory;

public class User implements Serializable, Comparable<User>{
	private String _username;
	private String _name;
	private Directory _homeDir;

	public User(String username, String name, Directory home){
		_username = username;
		_name = name;
		_homeDir = home;
	}

	public String getUsername(){
		return _username;
	}

	public String getName(){
		return _name;
	}

	public Directory getHomeDir(){
		return _homeDir;
	}

	public boolean isOwner(Entry e){
		return (e.getOwner().equals(_username));
	}

	public boolean canRead(Entry e){
		return (isOwner(e) || e.isPublic());
	}

	public boolean canWrite(Entry e){
		return isOwner(e) || e.isPublic();
	}

	public boolean canAddUsers(){
		return false;
	}

	public boolean canDeleteUsers(){
		return false;
	}

	public boolean canChangeOwner(Entry e){
		return getUsername().equals(e.getOwner());
	}

	public boolean canChangePermission(Entry e){
		return isOwner(e);
	}

	public int compareTo(User u){
		return getUsername().compareTo(u.getUsername());
	}

	public boolean equals(User u){
		return getUsername().equals(u.getUsername());
	}
	
}
