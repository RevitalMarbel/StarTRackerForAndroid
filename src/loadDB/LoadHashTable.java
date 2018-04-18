package loadDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import StaticElements.HashTableFile;



public class LoadHashTable {

	private static HashMap<String,ArrayList<String>> rs;
	
	public static HashMap<String, ArrayList<String>> getRs() {
		return rs;
	}

	public static void loadStatus(){
		Object result = null;
		try {
			FileInputStream saveFile = new FileInputStream(HashTableFile.hashFileOutName);
			//FileInputStream saveFile = new FileInputStream(StaticElements.hashTableName);
			ObjectInputStream in = new ObjectInputStream(saveFile);
			try {
				result = in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			in.close();
			//  fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		rs = (HashMap<String, ArrayList<String>>) result;
		
	}
}
