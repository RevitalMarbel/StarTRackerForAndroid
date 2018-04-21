	package StarIdentification;

	import java.io.File;
	import java.io.FileNotFoundException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Scanner;
	import java.util.Vector;

	import org.omg.CORBA.portable.ValueBase;

import StaticElements.StarFrame;

	
	
public class createStarName {

		private static Vector<String> starsName = new Vector<String>();
		private static File  startsDB  ;
		private static Scanner in;
		private static int Counter =0;
		//private static Vector<Sky_Elements.Star> starVector=new Vector(); 

		public static Vector<String> getTABLE() {
			return starsName;
		}

		public static void setFileScanner() throws FileNotFoundException
		{
			if(startsDB==null  && in ==null)
			{
				startsDB= new File(StarFrame.backgroundImage);
				//startsDB= new File(StaticElements.starsDBFileName);
				in = new Scanner(startsDB);
			}
		}

		private static void scanFileToObj() //return Star elemnts one by one from DB.
		{

			while(in.hasNext())
			{
				//Counter ++; 
				//int ID= Counter;
				String name=in.next();
				starsName.add(name);
				//System.out.println(RA);

			}
		}
		public static void fileToMAp() throws FileNotFoundException
		{
			setFileScanner();
			scanFileToObj();
		}
	}




