package poof.parser;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import poof.textui.exception.UserExistsException;
import poof.textui.exception.AccessDeniedException;
import poof.core.FileSystem;
import poof.core.Directory;

public class ParseFile{
	private FileSystem _fs;

	public FileSystem parse(String fileName){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			_fs = new FileSystem();

			String line;

			while ((line = reader.readLine()) != null) {
				parseLine(line);
			}
		} catch (IOException e) {
			return null;
		}
		
		return _fs;
	}

	private void parseLine(String line){
		String[] args = line.split("\\|");
		
		if (args[0].equals("USER"))
			createUser(args[1], args[2], args[3]);
		else if (args[0].equals("DIRECTORY"))
			createDirectory(args[1], args[2], args[3]);
		else if (args[0].equals("FILE"))
			createFile(args[1], args[2], args[3], args[4]);
	}

	private void createUser(String username, String name, String homeDir){
		_fs.addUser(username, name, _fs.getDirFromString(homeDir,username,"private",true));
	}

	private void createDirectory(String path, String owner, String perm){
		createEntity(path, owner, perm, true, null);
	}   

	private void createFile(String path, String owner, String perm, String content){
		createEntity(path, owner, perm, false, content);
	} 

	private void createEntity(String path, String owner, String perm, boolean isDir, String content){
		Directory dir = _fs.getDirFromString(path, owner, "private", false);
		String name = path.substring(path.lastIndexOf("/")+1);

		if (isDir)
			_fs.addDir(dir, name, owner, perm);
		else
			_fs.addFile(dir, name, owner, perm, content+"\n");
	}
}
