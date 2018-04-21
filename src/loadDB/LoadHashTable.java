package loadDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import skyElement.Star;
import StaticElements.HashTableFile;



public class LoadHashTable {

	private static HashMap<String,ArrayList<String>> rs;
	private static HashMap<Integer,ArrayList<String>> rs_pairs;
	private static HashMap<Integer, Star> rs_id;
	
	public static HashMap<Integer, Star> getRs_id() {
		return rs_id;
	}

	public static HashMap<Integer, ArrayList<String>> getRs_pairs() {
		return rs_pairs;
	}

	public static void setRs_pairs(HashMap<Integer, ArrayList<String>> rs_pairs) {
		LoadHashTable.rs_pairs = rs_pairs;
	}

	public static HashMap<String, ArrayList<String>> getRs() {
		return rs;
	}

	public static void loadTripletHashMap(){
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
	public static void loadPairsHashMap(){
		Object result = null;
		try {
			FileInputStream saveFile = new FileInputStream(HashTableFile.hashFileOutName+"_pairs");
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
		rs_pairs = (HashMap<Integer, ArrayList<String>>) result;
		
	}
	
	// load stars id hash table
	
	public static void loadIDHashMap(){
		Object result = null;
		try {
			FileInputStream saveFile = new FileInputStream(HashTableFile.hashFileOutName+"_id");
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
		rs_id = ( HashMap<Integer,Star>) result;
		
	}
}
