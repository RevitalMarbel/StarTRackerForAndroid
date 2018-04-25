package StarIdentification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.StyleContext.SmallAttributeSet;

import RaDecCalc.RaDecToAng;
import StaticElements.HashTableFile;
import StaticElements.StarFrame;
import loadDB.AccuracyLevel;
import loadDB.LoadHashTable;
import skyElement.Star;
import skyElement.StarPixel;

public class IdentifyStarsInImageFile2 {
	
	private static File  startsDB  ;
	private static Scanner in;
	private static int Counter =0;
	private static Vector<StarPixel> starVectorFrame=new Vector<StarPixel>(); 
	//private static Vector<Star> starVectorDB=new Vector<Star>(); 
	private static Vector<StarTriplet2> matchVector=new Vector<StarTriplet2>();
	private static pixelMatchArr bestMatchPix;
	private static String csvFile= StarFrame.starFrameNamecsv;
	private static BufferedReader br;
	static HashMap<Integer, Star> starVectorDB;
	
	////geters
	public static Vector<StarPixel> getStarVector() {
		return starVectorFrame;
	}
	//read image file

	private static void scanImageFile() //return Star elemnts one by one from DB.
	{
		String line = "";
	    String cvsSplitBy = ",";
		try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            		
    			String[] starLine = line.split(cvsSplitBy);
    			double x= Double.valueOf(starLine[0]);
    			double y= Double.valueOf(starLine[1]);
    			double radius=Double.valueOf(starLine[2]);
    			StarPixel tempSP= new StarPixel(x,y, radius,Counter); 
    			starVectorFrame.add(tempSP);
            	if(Counter ==1)
            		System.out.println(tempSP.toString());
            	
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
	
	public static void checkImageDists() throws FileNotFoundException
	{
			scanImageFile();//load image stars file to vector.
			//load stars db hash maps
			LoadHashTable.loadTripletHashMap(); 
			LoadHashTable.loadPairsHashMap();
			LoadHashTable.loadIDHashMap(); 
			HashMap<String, ArrayList<String>> starTripHashMap = LoadHashTable.getRs();
			bestMatchPix =new pixelMatchArr(starVectorFrame.size());

		//for(int i=1;i<LoadHashTable.getRs().size();i++)
			starVectorDB= LoadHashTable.getRs_id();
		//	StarTriplet2.setPixelMatchSize(starVector.size());
				
			//	two first objects in this vector are garbage 
			for (int i =0; i<starVectorFrame.size(); i++){
						for (int j=i+1; j<starVectorFrame.size(); j++) {
						for(int t=j+1;t<starVectorFrame.size();t++)
					{  
					if(AccuracyLevel.dist(starVectorFrame.get(i),starVectorFrame.get(j)) > HashTableFile.minAngdist
					&& AccuracyLevel.dist(starVectorFrame.get(i),starVectorFrame.get(t)) > HashTableFile.minAngdist
					&&AccuracyLevel.dist(starVectorFrame.get(t),starVectorFrame.get(j)) > HashTableFile.minAngdist   )
					{
					String[] keyVal=AccuracyLevel.createTkeyVal(starVectorFrame.get(i),starVectorFrame.get(j),starVectorFrame.get(t));
					String key=keyVal[0];
					String val[]=keyVal[1].split("_");
					StarPixel[] mySortedStars =new StarPixel[3];
					mySortedStars[0]=starVectorFrame.get(Integer.valueOf(val[0]));
					mySortedStars[1]=starVectorFrame.get(Integer.valueOf(val[1]));
					mySortedStars[2]=starVectorFrame.get(Integer.valueOf(val[2]));
					ArrayList<String> value= LoadHashTable.getRs().get(key);
		//			System.out.println(val[0]+" "+val[1]+" "+val[2] );
					if(null!= value )
					{
						String[] tmpVal=value.get(0).split("_");
						StarTriplet2 tempMatch =new StarTriplet2(mySortedStars[0],mySortedStars[1],mySortedStars[2],starVectorDB,tmpVal);
						for(int v=1;v<value.size();v++)//size3
						{
							
							tmpVal=value.get(v).split("_");
							if (starVectorDB.get(Integer.valueOf(tmpVal[0]))!=null &&
									starVectorDB.get(Integer.valueOf(tmpVal[1]))!=null &&
											starVectorDB.get(Integer.valueOf(tmpVal[2]))!=null)
							{
							try {
							//	System.out.println(value.get(v) +"size");
								tempMatch.addMatch(starVectorDB, tmpVal);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							};

							}
						}
						matchVector.add(tempMatch);
				}			
		}}}}
		 
		//filter out matches with far angles if tracking mode.
		//if(StaticElements.istrackMode){
		//for(int i=0;i<matchVector.size();i++)
		//{
		//	matchVector.elementAt(i).FilterByOrientation();
		//	System.out.println("filtered");
		//}
		//}
		for (int i=0;i<pixelMatchArr.bestMatchPix.length;i++) 
		{
			if(pixelMatchArr.bestMatchPix[i]!=null){
		System.out.println("pix"+pixelMatchArr.bestMatchPix[i].bestStar.getRa()+" ;"+pixelMatchArr.bestMatchPix[i].bestStar.getDec());
			for (int j = 0; j <pixelMatchArr.bestMatchPix[i].matches.size(); j++) {
		System.out.println(pixelMatchArr.bestMatchPix[i].matches.get(j).getRa()+" ;"+pixelMatchArr.bestMatchPix[i].matches.get(j).getDec());
			}
		}}
		Vector<StarTriplet2> fourmatch = new Vector<StarTriplet2>();
		for (int i = 0; i < matchVector.size(); i++) {
			for (int j = i+1; j < matchVector.size(); j++) {
				StarTriplet2[] st1=StarTriplet2.isIntersect(matchVector.get(i),matchVector.get(j));
				if( st1!= null){
					fourmatch.addAll(getaKyte(st1));
				}
			}
		}
 
		for(int i=0;i<matchVector.size();i++)
		{if(matchVector.elementAt(i).s_dbp1.size()>0 &&matchVector.elementAt(i).s_dbp3.size()>0&& matchVector.elementAt(i).s_dbp2.size()>0){
			System.out.println(matchVector.elementAt(i).s_dbp1.get(0).getRa()+ " "+matchVector.elementAt(i).s_dbp2.get(0).getRa()+" "+matchVector.elementAt(i).s_dbp3.get(0).getRa()+" "+i);
			System.out.println(matchVector.elementAt(i).s_dbp1.get(0).getDec()+ " "+matchVector.elementAt(i).s_dbp2.get(0).getDec()+" "+matchVector.elementAt(i).s_dbp3.get(0).getDec()+" "+i);
		}}
		for(int i=0;i<fourmatch.size();i++)
		{
			System.out.println(fourmatch.elementAt(i).s_dbp1.get(0).getRa()+ " "+matchVector.elementAt(i).s_dbp2.get(0).getRa()+" "+matchVector.elementAt(i).s_dbp3.get(0).getRa()+" "+i);
			System.out.println(fourmatch.elementAt(i).s_dbp1.get(0).getDec()+ " "+matchVector.elementAt(i).s_dbp2.get(0).getDec()+" "+matchVector.elementAt(i).s_dbp3.get(0).getDec()+" "+i);
		}
		System.out.println("stars:"+ Counter+ "all triplets: " + matchVector.size()+" kytes: "+ fourmatch.size());
		//CreateTriangImage.createImage2(starVectorDB, fourmatch, StarFrame.outPutImage,20,starVectorDB.size());
		//CreateTriangImage.createImage2(starVectorDB, matchVector, StarFrame.outPutImage,20,starVectorDB.size());
		CreateTriangImage.createImage3(starVectorDB,pixelMatchArr.bestMatchPix , StarFrame.outPutImage+"3",20,starVectorFrame.size(), 20, 30);
		//CreateImageFromTxt.createImageDB(starVectorDB, matchVector, StarTriplet2.getBestMatchPix(),9000, 9000,  StaticElements.imageTestFileName+"_"+StaticElements.keyRange+"_"+StaticElements.istrackMode+"_D_"+".jpg");
	}
	public static double[] triagleCenter(double x1,double y1,double x2,double y2,double x3,double y3)
	{
		double[] center= new double[2];
		center[0]=(x1+x2+x3)/3;
		center[1]=(y1+y2+y3)/3;
		return center;
	}
	public static Vector<StarTriplet2> getaKyte(StarTriplet2[] st1){ 
		Vector<StarTriplet2> fourmatch = new Vector<StarTriplet2>();
		//11,22
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix1) && 
				StarTriplet2.Samestar( st1[0].s_pix2,st1[1].s_pix2)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix3,st1[1].s_pix3 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp3.get(0),st1[1].s_dbp3.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}//11,23
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix1) && 
				StarTriplet2.Samestar( st1[0].s_pix2,st1[1].s_pix3)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix3,st1[1].s_pix2 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp3.get(0),st1[1].s_dbp2.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}//11,33
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix1) && 
				StarTriplet2.Samestar( st1[0].s_pix3,st1[1].s_pix3)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix2,st1[1].s_pix2 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp2.get(0),st1[1].s_dbp2.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}//12,23
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix2) && 
				StarTriplet2.Samestar( st1[0].s_pix2,st1[1].s_pix3)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix3,st1[1].s_pix1 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp3.get(0),st1[1].s_dbp1.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}//12,32
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix2) && 
				StarTriplet2.Samestar( st1[0].s_pix3,st1[1].s_pix1)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix2,st1[1].s_pix3 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp2.get(0),st1[1].s_dbp1.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}}
		//12,33
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix2) && 
				StarTriplet2.Samestar( st1[0].s_pix3,st1[1].s_pix3)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix2,st1[1].s_pix1 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp2.get(0),st1[1].s_dbp1.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}}
		//13,21
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix3) && 
				StarTriplet2.Samestar( st1[0].s_pix2,st1[1].s_pix1)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix3,st1[1].s_pix2 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp3.get(0),st1[1].s_dbp2.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}//13,22
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix3) && 
				StarTriplet2.Samestar( st1[0].s_pix2,st1[1].s_pix2)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix3,st1[1].s_pix1 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp3.get(0),st1[1].s_dbp1.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}//13,31
		if(StarTriplet2.Samestar(st1[0].s_pix1,st1[1].s_pix3) && 
				StarTriplet2.Samestar( st1[0].s_pix3,st1[1].s_pix1)){
			StarTriplet2 k1=new StarTriplet2(st1[0].s_pix1, st1[0].s_pix2,st1[1].s_pix2 ,(st1[0].s_dbp1.get(0)), st1[0].s_dbp2.get(0),st1[1].s_dbp2.get(0)); 
			if(StarTriplet2.contains(k1, matchVector)){
			fourmatch.add(st1[0]);
			fourmatch.add(st1[1]);}
		}
		return fourmatch;
	}
	
}



