package StarIdentification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import CreateSPHT.AccuracyLevel;
import Sky_Elements.Star;
import Tools.StaticElements;

public class BestMatch {
	private static StarTriplet st1;
	private static StarTriplet st2;
	
	
	public static double fiterOutliers(StarTriplet st1_, StarTriplet st2_,HashMap<String, ArrayList<String>> spht,HashMap<Double, Double[]> db)
	{		
		double grade=0;
		st1=st1_;
 		st2=st2_;	 
		String tempKey[]= AccuracyLevel.createTkeyVal(st1.s_pix1, st1.s_pix2, st2.s_pix1);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix1, st1.s_pix2, st2.s_pix2);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix1, st1.s_pix2, st2.s_pix3);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix3, st1.s_pix2, st2.s_pix1);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix3, st1.s_pix2, st2.s_pix2);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix2, st1.s_pix3, st2.s_pix3);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix1, st1.s_pix3, st2.s_pix1);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix1, st1.s_pix3, st2.s_pix2);
		grade = grade+getMatchGrade(tempKey,spht,db);
		tempKey= AccuracyLevel.createTkeyVal(st1.s_pix1, st1.s_pix3, st2.s_pix3);
		grade = grade+getMatchGrade(tempKey,spht,db);
		return grade;
	} 
	public static double getMatchGrade(String tempKey[], HashMap<String,ArrayList<String>> spht,HashMap<Double, Double[]> db)
			 {
				ArrayList<String> valueFormtch= spht.get(tempKey[0]);
				double grade=0;
				if(valueFormtch!=null){
				for(int t=0;t<valueFormtch.size();t++){//match the DB matches results to the stars in question
				String matchsortedStar[]=valueFormtch.get(t).split("_");
				//System.out.println(db.get(Double.valueOf(matchsortedStar[0])));
				double matchRA1=db.get(Double.valueOf(matchsortedStar[0]))[0];
				double matchRA2=db.get(Double.valueOf(matchsortedStar[1]))[0];
				double matchRA3=db.get(Double.valueOf(matchsortedStar[2]))[0];
//				double matchDEC1=db.get(Double.valueOf(matchsortedStar[0]))[1];
//				double matchDEC2=db.get(Double.valueOf(matchsortedStar[1]))[1];
//				double matchDEC3=db.get(Double.valueOf(matchsortedStar[2]))[1];	
				String mysortedStar[]=tempKey[1].split("_");
				double myRA1=db.get(Double.valueOf(mysortedStar[0]))[0];
				double myRA2=db.get(Double.valueOf(mysortedStar[1]))[0];
				double myRA3=db.get(Double.valueOf(mysortedStar[2]))[0];
				double myDEC1=db.get(Double.valueOf(mysortedStar[0]))[1];
				double myDEC2=db.get(Double.valueOf(mysortedStar[1]))[1];
				double myDEC3=db.get(Double.valueOf(mysortedStar[2]))[1];
				//System.out.println(myRA1 +" "+matchRA1+" "+myRA2+" "+matchRA2+" "+myRA3+" "+ matchRA3);
				if(	myRA1== matchRA1 && myRA2==matchRA2&& myRA3==matchRA3)//check all options!!!!!  
					grade++;
//				else
//				{
//					st1.s_dbp1.remove(t);
//					st1.s_dbp2.remove(t);
//					st1.s_dbp3.remove(t);
//				}
		}
//					grade++;
				}
			return grade;							
	}
	
	public static Vector<double[]> fiterOutliers(Vector<StarTriplet> matchs)
	{		//first two are pixels second two are the match. size 4
		double grade1=0,grade2=0,grade3=0;
		Vector<double[]> good =new Vector<double[]>();
		for(int i=0;i<matchs.size();i++)//iterate over triplets 
		{
			double RA1= matchs.elementAt(i).s_pix1.getRA();
			double DEC1= matchs.elementAt(i).s_pix1.getDEC();
			double RA2= matchs.elementAt(i).s_pix2.getRA();
			double DEC2= matchs.elementAt(i).s_pix2.getDEC();
			double RA3= matchs.elementAt(i).s_pix3.getRA();
			double DEC3= matchs.elementAt(i).s_pix3.getDEC();
			int bestkey =0;
			
		for (int j=0;j<matchs.elementAt(i).getSize();j++)//iterate over keys
		{	
			for(int t=i+1;t<matchs.size();t++){//iterate over other triplets
			if(matchs.elementAt(t).isXalsoHere(Double.valueOf(matchs.elementAt(i).s_dbp1.elementAt(j).get_name()), RA1, DEC1))
				grade1++;
			//System.out.println(grade1+"size:: "+matchs.size());
			if(matchs.elementAt(t).isXalsoHere(Double.valueOf(matchs.elementAt(i).s_dbp2.elementAt(j).get_name()), RA2, DEC2))
				grade2++;
			if(matchs.elementAt(t).isXalsoHere(Double.valueOf(matchs.elementAt(i).s_dbp3.elementAt(j).get_name()), RA3, DEC3))
				grade3++;}
			
			if (grade1> StaticElements.matchesTreshhold)
			{
				double[] reals={RA1, DEC1,matchs.elementAt(i).s_dbp1.elementAt(j).getRA(),matchs.elementAt(i).s_dbp1.elementAt(j).getDEC()};
				good.add(reals);
			}

//			for(int t=0;t<matchs.size();t++){//iterate over other triplets
//			if(matchs.elementAt(t).isXalsoHere(Double.valueOf(matchs.elementAt(i).s_dbp2.elementAt(j).get_name()), RA2, DEC2))
//				grade2++;}
			if (grade2> StaticElements.matchesTreshhold)
			{
				double[] reals={RA2, DEC2,matchs.elementAt(i).s_dbp2.elementAt(j).getRA(),matchs.elementAt(i).s_dbp2.elementAt(j).getDEC()};
				good.add(reals);
			}
//			for(int t=0;t<matchs.size();t++){//iterate over other triplets
//			if(matchs.elementAt(t).isXalsoHere(Double.valueOf(matchs.elementAt(i).s_dbp3.elementAt(j).get_name()), RA3, DEC3))
//				grade3++;}
			if (grade3> StaticElements.matchesTreshhold)
			{
				double[] reals={RA3, DEC3,matchs.elementAt(i).s_dbp3.elementAt(j).getRA(),matchs.elementAt(i).s_dbp2.elementAt(j).getDEC()};
				good.add(reals);
			}
		}
	}
		return good;
	}
//		for(int i=0;i<badMatch.size();i++)
//		{
//			st1.remove(badMatch.get(i)-i);
//		}
		
//		return grade;
//	}

}
