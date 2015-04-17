package poof.textui;

import poof.parser.ParseFile;
import poof.textui.main.*;
import poof.core.Session;


public class Shell{
	public static void main (String[] args){
		String property = System.getProperty("import");
		Session session = new Session();
		ParseFile parser = new ParseFile();

		if (property != null){
			session.create(parser.parse(property));
			session.setIsSaved(false);
		} else
			session.setIsSaved(true);
		
		MainMenu mainMenu = new MainMenu(session);
		
		mainMenu.open();
	}
}