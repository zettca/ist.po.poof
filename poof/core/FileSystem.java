package poof.core;

import java.io.Serializable;
import java.util.TreeSet;

import poof.textui.exception.*;
import poof.core.*;

/**
 * 
 *
 * @author Grupo 24
 * @version 1.0
 */

public class FileSystem implements Serializable {
	private TreeSet<User> _users;
	private Directory _rootDir;
	private Directory _currentDir;
	private User _loggedUser;

	public FileSystem(){
		_users = new TreeSet<User>();
		_rootDir = new Directory("", null, "root", "private");
		_rootDir.setParent(_rootDir);
		_rootDir.addDots();
		_rootDir.addDir("home","root","private");

		addSuperUser("root", "Super User");

		try{
			login("root");
		} catch (UserUnknownException e){
			// shouldn't ever happen
		}
	}

	/* ===== GETTERS ===== */

	public TreeSet<User> getUsers(){
		return _users;
	}

	public Directory getRootDir(){
		return _rootDir;
	}

	public Directory getCurrentDir(){
		return _currentDir;
	}

	public User getLoggedUser(){
		return _loggedUser;
	}

	private User getUser(String username){ // aux. method
		for (User u : _users)
			if (u.getUsername().equals(username))
				return u;
		return null;
	}

	/* ===== SETTERS ===== */

	public void setCurrentDir(Directory dir){
		_currentDir = dir;
	}

	public void setCurrentDir(String dir) 
				throws IsNotDirectoryException, EntryUnknownException{
		for (Entry e : _currentDir.getContent())
			if (e.getName().equals(dir)){
				if (e.isDir()){
					if (dir.equals("."))		// mantains directory
						_currentDir = _currentDir; 
					else if (dir.equals(".."))	// goes up one directory
						_currentDir = _currentDir.getParent(); 
					else						// goes down selected directory
						_currentDir = _currentDir.getDir(dir);
					return;
				} else
					throw new IsNotDirectoryException(dir);
			}
		throw new EntryUnknownException(dir);
	}

	public void addUser(String user, String name) 
				throws AccessDeniedException, UserExistsException{
		if (_loggedUser.canAddUsers()){
			if (!userExists(user)){
				try{
					Directory d = _rootDir.getDir("home").addDir(user, user, "private");
					addUser(user,name, d);
					return;
				} catch (EntryUnknownException e) { }
			}
			throw new UserExistsException(user);
		}
		throw new AccessDeniedException(_loggedUser.getUsername());
	}

	public void addUser(String user, String name, Directory homeDir){
		_users.add(new User(user, name, homeDir));
	}

	public void addSuperUser(String user, String name){
		try{
			Directory d = _rootDir.getDir("home").addDir(user, user, "private");
			_users.add(new SuperUser(user, name, d));
		} catch (EntryUnknownException e){
		}
	}


	/* ===== RECONHECEDORES ===== */

	public boolean userExists(String username){
		return (getUser(username) != null);
	}

	/* =====  ===== */

	public User login(String username) throws UserUnknownException{
		User u = getUser(username);
		if (u != null){
			_loggedUser = u;
			_currentDir = u.getHomeDir();
			return u;
		} else
			throw new UserUnknownException(username);
	}

	/* ===== OPERATIONS OVER OTHER CLASSES ===== */

	public void deleteUser(String username) throws AccessDeniedException,
													UserUnknownException{
		User u = getUser(username);
		if (u != null){
			if (_loggedUser.canDeleteUsers())
				_users.remove(u);
			else
				throw new AccessDeniedException(_loggedUser.getUsername());
		} else
			throw new UserUnknownException(username);
	}

	public void changePermission(Entry e, String perm) throws AccessDeniedException {
		if (_loggedUser.canChangePermission(e))
			e.setPermission(perm);
		else
			throw new AccessDeniedException(_loggedUser.getUsername());
	}

	public void changeOwner(Entry e, String username) 
				throws AccessDeniedException, UserUnknownException{
		User u = getUser(username);
		if (u != null){
			if (_loggedUser.canChangeOwner(e))
				e.setOwner(username);
			else
				throw new AccessDeniedException(_loggedUser.getUsername());
		} else
			throw new UserUnknownException(username);
	}


	public Directory addDir(Directory dir, String name, String owner,
							String perm){
		Directory newDir = new Directory(name, dir, owner, perm);
		dir.getContent().add(newDir);
		newDir.addDots();
		return newDir;
	}


	public File addFile(Directory dir, String name, String owner, 
						String perm, String content){
		File f = new File(name, dir, owner, perm, content);
		dir.getContent().add(f);
		return f;
	}

	// DIRECTORY: AddDirectory
	public Directory addDir(Directory dir, String name) 
					throws AccessDeniedException, EntryExistsException{
		if (_loggedUser.canWrite(dir)){
			if (!dir.hasEntry(name))
				return addDir(dir, name, _loggedUser.getUsername(), "private");
			throw new EntryExistsException(name);
		}
		throw new AccessDeniedException(_loggedUser.getUsername());
	}

	// DIRECTORY: AddFile
	public File addFile(Directory dir, String name) 
					throws AccessDeniedException, EntryExistsException{
		if (_loggedUser.canWrite(dir)){
			if (!dir.hasEntry(name))
				return addFile(dir, name, _loggedUser.getUsername(), "private", "");
			throw new EntryExistsException(name);
		}
		throw new AccessDeniedException(_loggedUser.getUsername());
	}

	// ENTRY: RemoveEntry
	public void removeEntry(Directory dir, String name) 
					throws EntryUnknownException, AccessDeniedException, 
							IllegalRemovalException{
		if (name.equals(".") || name.equals(".."))
			throw new IllegalRemovalException();

		for (Entry e : dir.getContent())
			if (e.getName().equals(name)){
				if ((_loggedUser.isOwner(dir) || dir.isPublic()) &&
					(_loggedUser.isOwner(dir.find(name)) || dir.find(name).isPublic())){
					dir.getContent().remove(e);
					return;
				}
				throw new AccessDeniedException(_loggedUser.getUsername());
			}
		throw new EntryUnknownException(name);
	}


	// FILE: ReadContent
	public String read(File f) throws AccessDeniedException{
		if (_loggedUser.canRead(f))
			return f.getContent();
		else
			throw new AccessDeniedException(_loggedUser.getUsername());
	}
	// FILE: WriteLine
	public void writeLine(String fileName, String content) 
						throws EntryUnknownException, IsNotFileException, 
								AccessDeniedException{
		for (Entry e : _currentDir.getContent()) // find Entry
			if (e.getName().equals(fileName)){
				if (e.isFile()){
					if (_loggedUser.canWrite(e)){
						e.addContent(content+"\n");
						return;
					}
					throw new AccessDeniedException(_loggedUser.getUsername());
				}
				throw new IsNotFileException(fileName);
			}
		throw new EntryUnknownException(fileName);
	}



	/**
	 * Given String path, creates all the directories required
	 * and returns the last/last-1 (depending on fullDir) created.
	 * 
	 * @param path path to the directory to be created
	 * @param owner owner's username
	 * @param perm whether is private or public
	 * @return dir last directory to be created
	 */
	public Directory getDirFromString(String path, String owner, 
									String perm, boolean fullDir){
		Directory dir = _rootDir;
		String[] dirv = path.split("/");
		
		int limit = (fullDir ? 0 : 1); // wether to create the last Directory
		try{
			for (int i=1; i<dirv.length-limit;i++){
				if (! dir.hasDir(dirv[i]))
					dir.addDir(dirv[i], owner, perm);
				dir = dir.getDir(dirv[i]);
			}
		} catch(EntryUnknownException e){ }
		return dir;
	}

}