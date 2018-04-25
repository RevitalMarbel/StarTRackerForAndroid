package loadDB;


import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import RaDecCalc.RaDecToAng;
import skyElement.*;


import StaticElements.HashTableFile;
import StaticElements.videoFile;


public class CreateHashTableFromTXT {

	/* This class read the stars DB file 
	 *and create hash table of stars triangle.
	 *It also creates a Hash map for a star with its id
	 **/ 	
	private static File  startsDB  ;
	private static Scanner in;
	private static int Counter =1;
	static double duplicateCounter=0;
	static double trig_counter=0;
	static double errCount=0;
	//name of the catalog csv file 
	static String csvFile= HashTableFile.hashFileName ;
    static BufferedReader br = null;
    
	private static HashMap<Integer,Star> starVector=new HashMap<Integer,Star>(); 
	//private static HashMap<String, String> hmap = new HashMap <String, String>();
	private static HashMap<String,ArrayList<String>> STARTRACK_TABLE = new HashMap<String,ArrayList<String>>();
	private static HashMap<Integer,ArrayList<String>> STARTRACK_TABLE_pairs = new HashMap<Integer,ArrayList<String>>();

	static HashMap<Integer,Star> STARS_TABLE = new HashMap<Integer,Star>();
	//getters////////
	
	
	public static HashMap<Integer, ArrayList<String>> getSTARTRACK_TABLE_pairs() {
		return STARTRACK_TABLE_pairs;
	}
	public static HashMap<String, ArrayList<String>> getSTARTRACK_TABLE() {
		return STARTRACK_TABLE;
	}

	

	private static void scanFileToObj() //return Star elemnts one by one from DB.
	{
		String line = "";
	    String cvsSplitBy = ",";
		try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	
            	if(Counter!=1)
            	{
            		
    			int ID= Counter;
    			String[] starLine = line.split(cvsSplitBy);
    			double RA= Double.valueOf(starLine[3]);
    			RA= RaDecToAng.RaToAng(RA);
    			
    			double DE= Double.valueOf(starLine[4]);
    			DE= RaDecToAng.DecToAng(DE);
    			double MAG=Double.valueOf(starLine[5]);
    			String name= starLine[2];
    			Star tempStar= new Star(RA,DE, MAG ,ID, name); 
    			STARS_TABLE.put(Counter,tempStar);
    			starVector.put(ID,tempStar);
            	if(Counter ==1)
            		System.out.println(starVector.get(0).toString());
            	}
            	Counter ++; 
                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	
	}

	/* creating hash map fro m DB stars :  key- String of 3 angles of stars sorted min to max seperated by ;
	 * value-  Staring of s names separated by ;
	 * than save the hash map to a file .  
	 */
	
	private static void generatePairTable(){
		int counter=0;
		for (int i=2;i<starVector.size();i++)
		{
			for( int j=i+1;j<starVector.size();j++)
			{
				if((starVector.get(i).getDec()>=0 && starVector.get(j).getDec()>=0)
						||(starVector.get(i).getDec()<0 && starVector.get(j).getDec()<0 ))
						{
							//if angular distance is larger than threshold skip
							if(AccuracyLevel.angDist(starVector.get(i), starVector.get(j)) < HashTableFile.maxAngdist)
								{
								
						counter++;
						Double key2D= AccuracyLevel.angDist(starVector.get(i),starVector.get(j));
						int intkey2D=Integer.valueOf(AccuracyLevel.RoundToRange(key2D));
						String value2D= starVector.get(i).getName().concat("_").concat(starVector.get(j).getName());
						put2DKey(intkey2D, value2D);
						//System.out.println("key 2D : "+intkey2D +" val2D: "+value2D);
								}
						}
			}
		}
				
	}
	private static void generateTable()
	{
		int counter=0;
		for (int i=2;i<starVector.size();i++)
		{
			for( int j=i+1;j<starVector.size();j++)
			{// check if the stars are close enough to be at the same frame(distance less than 30 deg)
				for(int t=j+1;t<starVector.size();t++)
				{  
					if((starVector.get(i).getDec()>=0 && starVector.get(j).getDec()>=0 &&starVector.get(t).getDec()>=0)
						||(starVector.get(i).getDec()<0 && starVector.get(j).getDec()<0 &&starVector.get(t).getDec()<0)){
						{
							//if angular distance is larger than threshold skip
							if(AccuracyLevel.angDist(starVector.get(i), starVector.get(j)) < HashTableFile.maxAngdist
								&&  AccuracyLevel.angDist(starVector.get(i), starVector.get(t)) < HashTableFile.maxAngdist
								&& AccuracyLevel.angDist(starVector.get(j), starVector.get(t)) < HashTableFile.maxAngdist	
							
								&& AccuracyLevel.angDist(starVector.get(i), starVector.get(j)) > HashTableFile.minAngdist
										&&  AccuracyLevel.angDist(starVector.get(i), starVector.get(t)) > HashTableFile.minAngdist
										&& AccuracyLevel.angDist(starVector.get(j), starVector.get(t)) > HashTableFile.minAngdist	)
									{
								
						counter++;	
					String tempVal;
					//create key for triplet hash map
					String[] keyVal=AccuracyLevel.createTkeyValForMAp(starVector.get(i),starVector.get(j),starVector.get(t));
					//create key  for pairs hash map
					
					
					// 	System.out.println(keyVal[0]+" "+keyVal[1]);
					tempVal=keyVal[1];
					String key=keyVal[0];
					//String errkey=AccuracyLevelErr.createTkeyForMap(starVector.elementAt(i),starVector.elementAt(j),starVector.elementAt(t));
					putDuplicteKey(key, tempVal);
					//putKey(key, tempVal);//put key and value .in map
					if(counter==2 || counter ==10 || counter ==20){
						System.out.println("key: "+key +" val: "+tempVal);
						
						
					}
						trig_counter++;
					}}
				}}
			}
		}
		
		
	}	  

	public static void main() throws FileNotFoundException
	{
		//scan to vector
		System.out.println("scanning to file ");
		scanFileToObj();
		//vector to map
		System.out.println("generating table to - "+ HashTableFile.hashFileOutName);
		
		generatePairTable();
		generateTable();
		saveStatus();
		System.out.println("size of table: "+trig_counter+"size of vector "+(trig_counter-duplicateCounter) );
		System.out.println("load factor: "+trig_counter/(trig_counter-duplicateCounter) );
		double res =errCount/trig_counter;
		System.out.println("match key : "+res);

		
	}
	public static void clear()
	{
		duplicateCounter=0;
		trig_counter=0;
		STARTRACK_TABLE.clear();
		errCount=0;
	}
	public static void saveStatus(){
		try {
			
			//The name of the output file.
			FileOutputStream saveFile = new FileOutputStream(HashTableFile.hashFileOutName);
			ObjectOutputStream out = new ObjectOutputStream(saveFile);
			out.writeObject(STARTRACK_TABLE);
			out.close();
			//  fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//save pair hash map to File
		try{

			//The name of the output file.
			FileOutputStream saveFile = new FileOutputStream(HashTableFile.hashFileOutName+"_pairs");
			ObjectOutputStream out = new ObjectOutputStream(saveFile);
			out.writeObject(getSTARTRACK_TABLE_pairs());
			out.close();
			//  fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	// save star id hashmap
		try{

			//The name of the output file.
			FileOutputStream saveFile = new FileOutputStream(HashTableFile.hashFileOutName+"_id");
			ObjectOutputStream out = new ObjectOutputStream(saveFile);
			out.writeObject(getStarVector());
			out.close();
			//  fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static HashMap<Integer, Star> getStarVector() {
		return starVector;
	}
	public static void setStarVector(HashMap<Integer, Star> starVector) {
		CreateHashTableFromTXT.starVector = starVector;
	}
	public static String[] duplicateKey(String key)
	{
		String[] keys=key.split("_");
		int key1= Integer.valueOf(keys[0]);
		int key2= Integer.valueOf(keys[1]);
		int key3= Integer.valueOf(keys[2]);
		String k1=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2-1).concat("_").concat(String.valueOf(key3-1)));
		String k2=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3)));
		String k3=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3-1)));
		String k4=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2-1).concat("_").concat(String.valueOf(key3)));
		String k5=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2+1).concat("_").concat(String.valueOf(key3+1)));
		String k6=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3+1)));
		String k7=String.valueOf(key1-1).concat("_").concat(String.valueOf(key2+1).concat("_").concat(String.valueOf(key3)));
		
		String k8=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2-1).concat("_").concat(String.valueOf(key3-1)));
		String k9=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3)));
		String k10=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3-1)));
		String k11=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2-1).concat("_").concat(String.valueOf(key3)));
		String k12=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2+1).concat("_").concat(String.valueOf(key3+1)));
		String k13=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3+1)));
		String k14=String.valueOf(key1+1).concat("_").concat(String.valueOf(key2+1).concat("_").concat(String.valueOf(key3)));
		
		String k15=String.valueOf(key1).concat("_").concat(String.valueOf(key2-1).concat("_").concat(String.valueOf(key3-1)));
		String k16=String.valueOf(key1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3-1)));
		String k17=String.valueOf(key1).concat("_").concat(String.valueOf(key2-1).concat("_").concat(String.valueOf(key3)));
		String k18=String.valueOf(key1).concat("_").concat(String.valueOf(key2+1).concat("_").concat(String.valueOf(key3+1)));
		String k19=String.valueOf(key1).concat("_").concat(String.valueOf(key2).concat("_").concat(String.valueOf(key3+1)));
		String k20=String.valueOf(key1).concat("_").concat(String.valueOf(key2+1).concat("_").concat(String.valueOf(key3)));
		

		String[] res= {k1,k2,k3,k4,k5,k6,k7,k8,k9,k10,k11,k12,k13,k14,k15,k16,k17,k18,k19,k20};
		
		//System.out.println(k1+ " "+k3+" "+k12);
		return res;
		
	}
public static void putDuplicteKey(String key, String tempVal){	
	String[] newKey =duplicateKey(key);
	for(int i=0;i<newKey.length;i++)
	{
		putKey(newKey[i], tempVal);
	}

}
public static void putKey(String key, String tempVal){	
	ArrayList<String> value= STARTRACK_TABLE.get(key);
	if(null== value )
	{
		value =new ArrayList<String>();
	}
	else
		duplicateCounter ++;
	value.add(tempVal);
	STARTRACK_TABLE.put(key, value);
	//System.out.println( value);
}
public static void put2DKey(int key, String tempVal){	
	ArrayList<String> value= STARTRACK_TABLE_pairs.get(key);
	if(null== value )
	{
		value =new ArrayList<String>();
	}
	else
		duplicateCounter ++;
	value.add(tempVal);
	STARTRACK_TABLE_pairs.put(key, value);
	//System.out.println( value);
}
}


