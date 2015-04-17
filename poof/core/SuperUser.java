package poof.core;

import java.io.Serializable;

import poof.core.User;

public class SuperUser extends User 
						implements Serializable, Comparable<User>{

	public SuperUser(String username, String name, Directory home){
		super(username, name, home);
	}

	public boolean canRead(Entry e){
		return true;
	}

	public boolean canWrite(Entry e){
		return true;
	}

	public boolean isOwner(Entry e){
		return true;
	}

	public boolean canAddUsers(){
		return true;
	}

	public boolean canDeleteUsers(){
		return true;
	}

	public boolean canChangeOwner(){
		return true;
	}

	public boolean canChangePermission(){
		return true;
	}

	public int compareTo(User u){
		return super.compareTo(u);
	}

}
