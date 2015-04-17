package poof.core;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;

import poof.textui.exception.UserExistsException;
import poof.textui.exception.AccessDeniedException;
import poof.core.*;

public class Session{
	private FileSystem _fs;
	private boolean _isSaved;

	public FileSystem fs(){
		return _fs;
	}

	public void create(){
		create(new FileSystem());
	}

	public void create(FileSystem fs){
		_fs = fs;
	}

	public boolean hasFileSystem(){
		return (_fs != null);
	}

	public boolean isSaved(){
		return _isSaved;
	}

	public void setIsSaved(boolean status){
		_isSaved = status;
	}

	// ================ SERIALIZATION ================== //

	// Serialize FileSystem
	public void save(String path) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(path);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(_fs);
		objOut.close();
		fileOut.close();
	}
	// Deserialize FileSystem
	public void open(String path) 
							throws IOException, ClassNotFoundException{
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		_fs = (FileSystem) objIn.readObject();
		// directory has to go back to User's HomeDir
		_fs.setCurrentDir(_fs.getLoggedUser().getHomeDir());
		objIn.close();
		fileIn.close();
	}

}
