package loadDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import org.omg.CORBA.portable.ValueBase;


public class CreateStarsFileHashMap {
	
	private static HashMap<Double, Double[]> STARS_TABLE = new HashMap<Double,Double[]>();
	private static File  startsDB  ;
	private static Scanner in;
	private static int Counter =0;
	//private static Vector<Sky_Elements.Star> starVector=new Vector(); 

	public static HashMap<Double, Double[]> getSTAR_TABLE() {
		return STARS_TABLE;
	}

	public static void setFileScanner() throws FileNotFoundException
	{
		if(startsDB==null  && in ==null)
		{
			startsDB= new File(StaticElements.starsFrameFileName);
			//startsDB= new File(StaticElements.starsDBFileName);
			in = new Scanner(startsDB);
		}
	}

	private static void scanFileToObj() //return Star elemnts one by one from DB.
	{

		while(in.hasNext())
		{
			Counter ++; 
			int ID= Counter;
			Double[] value =new Double[4];
			value[0]= Double.parseDouble(in.next());
			value[1]= Double.parseDouble(in.next());
			value[2]=Double.parseDouble (in.next());
			value[3]=Double.valueOf(Counter);
			
			STARS_TABLE.put(Double.valueOf(Counter),value );
			//System.out.println(RA);

		}
	}
	public static void fileToMAp() throws FileNotFoundException
	{
		setFileScanner();
		scanFileToObj();
	}
}
