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
	private static int Counter =0;
	static double duplicateCounter=0;
	static double trig_counter=0;
	static double errCount=0;
	//name of the catalog csv file 
	static String csvFile= HashTableFile.hashFileName ;
    static BufferedReader br = null;
    
	private static Vector<Star> starVector=new Vector(); 
	//private static HashMap<String, String> hmap = new HashMap <String, String>();
	private static HashMap<String,ArrayList<String>> STARTRACK_TABLE = new HashMap<String,ArrayList<String>>();
	static HashMap<Integer,Star> STARS_TABLE = new HashMap<Integer,Star>();
	
	
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
            	
            	if(Counter!=0)
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
    			starVector.add(tempStar);
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
	private static void generateTable()
	{
		for (int i=0;i<starVector.size();i++)
		{
			for( int j=i+1;j<starVector.size();j++)
			{// check if the stars are close enough to be at the same frame(distance less than 30 deg)
				for(int t=j+1;t<starVector.size();t++)
				{  
					if((starVector.elementAt(i).getDec()>=0 && starVector.elementAt(j).getDec()>=0 &&starVector.elementAt(t).getDec()>=0)
						||(starVector.elementAt(i).getDec()<0 && starVector.elementAt(j).getDec()<0 &&starVector.elementAt(t).getDec()<0)){
//	
					String tempVal;
					String[] keyVal=AccuracyLevel.createTkeyValForMAp(starVector.elementAt(i),starVector.elementAt(j),starVector.elementAt(t));
				// 	System.out.println(keyVal[0]+" "+keyVal[1]);
					tempVal=keyVal[1];
					String key=keyVal[0];
					//String errkey=AccuracyLevelErr.createTkeyForMap(starVector.elementAt(i),starVector.elementAt(j),starVector.elementAt(t));
					putKey(key, tempVal);//put key and value .in map
					if(i==0 && j==1 && t==2)
						System.out.println("key: "+key +" val: "+tempVal);
					trig_counter++;
					}}
				}
			}
		
		saveStatus();
		
	}	  

	public static void main() throws FileNotFoundException
	{
		//scan to vector
		System.out.println("scanning to file ");
		scanFileToObj();
		//vector to map
		System.out.println("generating table to - "+ HashTableFile.hashFileOutName);
		generateTable();
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
	}
	
	public static String[] duplicateKey(String key)
	{
		String[] keys=key.split("_");
		double key1= Double.valueOf(keys[0]);
		double key2= Double.valueOf(keys[1]);
		double key3= Double.valueOf(keys[2]);
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
}


