package loadDB;

import java.util.Arrays;
import java.util.Random;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import skyElement.Star;
import StaticElements.*;

//this class will have static methods for calculating hash map key
public class AccuracyLevel {

	public static String createTkey(Star s1, Star s2, Star s3)
	{
	
		double dist[] =new double[3];
		dist[0]=dist(s1,s2);
		dist[1]=dist(s2,s3);
		dist[2]=dist(s1,s3);
		Arrays.sort(dist);
		//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
		String key1= RoundToRange(dist[0]);
		String key2= RoundToRange(dist[1]);
		String key3= RoundToRange(dist[2]);
		return key1.concat("_").concat(key2).concat("_").concat(key3);
	}
	
	public static String createTkeyForMap(Star s1, Star s2, Star s3)
	{
		double dist[] =new double[3];
		dist[0]=angDist(s1,s2);
		dist[1]=angDist(s2,s3);
		dist[2]=angDist(s1,s3);
		Arrays.sort(dist);
//		String key1= RoundToRange(dist[0]*StaticElements.scaling);
//		String key2= RoundToRange(dist[1]*StaticElements.scaling);
//		String key3= RoundToRange(dist[2]*StaticElements.scaling);
		String key1= RoundToRange(dist[0]);
		String key2= RoundToRange(dist[1]);
		String key3= RoundToRange(dist[2]);
		return key1.concat("_").concat(key2).concat("_").concat(key3);
	}
	public static String[] createTkeyValForMAp(Star s1, Star s2, Star s3)
	{
		String[] keyVal=new String[2]; 
		double dist[] =new double[3];
		dist[0]= angDist(s1, s2);
		dist[1]=angDist(s2,s3);
		dist[2]=angDist(s1,s3);
		//Arrays.sort(dist);
		String key1="",key2="",key3="",val1="",val2="",val3="";
		if(dist[0]<dist[1] && dist[1]<dist[2])
		{
		key1= RoundToRange(dist[0]);
		key2= RoundToRange(dist[1]);
		key3= RoundToRange(dist[2]);
		 val1=s3.getName();
		 val2=s1.getName();
		 val3=s2.getName();
		}
		if(dist[1]<dist[0] && dist[0]<dist[2])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[1]*StaticElements.scaling);
//			 key2= RoundToRange(dist[0]*StaticElements.scaling);
//			 key3= RoundToRange(dist[2]*StaticElements.scaling);
			 key1= RoundToRange(dist[1]);
			 key2= RoundToRange(dist[0]);
			 key3= RoundToRange(dist[2]);
			 val1=s1.getName();
			 val2=s3.getName();
			 val3=s2.getName();
			}
		if(dist[2]<dist[1] && dist[1]<dist[0])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[2]*StaticElements.scaling);
//			 key2= RoundToRange(dist[1]*StaticElements.scaling);
//			 key3= RoundToRange(dist[0]*StaticElements.scaling);
			 key1= RoundToRange(dist[2]);
			 key2= RoundToRange(dist[1]);
			 key3= RoundToRange(dist[0]);
			 val1=s2.getName();
			 val2=s1.getName();
			 val3=s3.getName();
			}
		if(dist[2]<dist[0] && dist[0]<dist[1])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[2]*StaticElements.scaling);
//			 key2= RoundToRange(dist[0]*StaticElements.scaling);
//			 key3= RoundToRange(dist[1]*StaticElements.scaling);
			 key1= RoundToRange(dist[2]);
			 key2= RoundToRange(dist[0]);
			 key3= RoundToRange(dist[1]);
			 val1=s2.getName();
			 val2=s3.getName();
			 val3=s1.getName();
			}
		if(dist[1]<dist[2] && dist[2]<dist[0])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[1]*StaticElements.scaling);
//			 key2= RoundToRange(dist[2]*StaticElements.scaling);
//			 key3= RoundToRange(dist[0]*StaticElements.scaling);
			 key1= RoundToRange(dist[1]);
			 key2= RoundToRange(dist[2]);
			 key3= RoundToRange(dist[0]);
			 val1=s1.getName();
			 val2=s2.getName();
			 val3=s3.getName();
			}
		if(dist[0]<dist[2] && dist[2]<dist[1])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[0]*StaticElements.scaling);
//			 key2= RoundToRange(dist[2]*StaticElements.scaling);
//			 key3= RoundToRange(dist[1]*StaticElements.scaling);
			 key1= RoundToRange(dist[0]);
			 key2= RoundToRange(dist[2]);
			 key3= RoundToRange(dist[1]);
			 val1=s3.getName();
			 val2=s2.getName();
			 val3=s1.getName();
			}
		keyVal[0]=key1.concat("_").concat(key2).concat("_").concat(key3);
		keyVal[1]=val2.concat("_").concat(val3).concat("_").concat(val1);
		return  keyVal;
	}
	
	public static String[] createTkeyVal(Star s1, Star s2, Star s3)
	{
		String[] keyVal=new String[2]; 
		double dist[] =new double[3];
		dist[0]= dist(s1,s2);
		dist[1]=dist(s2,s3);
		dist[2]=dist(s1,s3);
		//Arrays.sort(dist);
		String key1="",key2="",key3="",val1="",val2="",val3="";
		if(dist[0]<dist[1] && dist[1]<dist[2])
		{
		key1= RoundToRange(dist[0]);
		key2= RoundToRange(dist[1]);
		key3= RoundToRange(dist[2]);
		 val1=s3.getName();
		 val2=s1.getName();
		 val3=s2.getName();
		}
		if(dist[1]<dist[0] && dist[0]<dist[2])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[1]*StaticElements.scaling);
//			 key2= RoundToRange(dist[0]*StaticElements.scaling);
//			 key3= RoundToRange(dist[2]*StaticElements.scaling);
			 key1= RoundToRange(dist[1]);
			 key2= RoundToRange(dist[0]);
			 key3= RoundToRange(dist[2]);
			 val1=s1.getName();
			 val2=s3.getName();
			 val3=s2.getName();
			}
		if(dist[2]<dist[1] && dist[1]<dist[0])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[2]*StaticElements.scaling);
//			 key2= RoundToRange(dist[1]*StaticElements.scaling);
//			 key3= RoundToRange(dist[0]*StaticElements.scaling);
			 key1= RoundToRange(dist[2]);
			 key2= RoundToRange(dist[1]);
			 key3= RoundToRange(dist[0]);
			 val1=s2.getName();
			 val2=s1.getName();
			 val3=s3.getName();
			}
		if(dist[2]<dist[0] && dist[0]<dist[1])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[2]*StaticElements.scaling);
//			 key2= RoundToRange(dist[0]*StaticElements.scaling);
//			 key3= RoundToRange(dist[1]*StaticElements.scaling);
			 key1= RoundToRange(dist[2]);
			 key2= RoundToRange(dist[0]);
			 key3= RoundToRange(dist[1]);
			 val1=s2.getName();
			 val2=s3.getName();
			 val3=s1.getName();
			}
		if(dist[1]<dist[2] && dist[2]<dist[0])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[1]*StaticElements.scaling);
//			 key2= RoundToRange(dist[2]*StaticElements.scaling);
//			 key3= RoundToRange(dist[0]*StaticElements.scaling);
			 key1= RoundToRange(dist[1]);
			 key2= RoundToRange(dist[2]);
			 key3= RoundToRange(dist[0]);
			 val1=s1.getName();
			 val2=s2.getName();
			 val3=s3.getName();
			}
		if(dist[0]<dist[2] && dist[2]<dist[1])
		{//System.out.println(dist[0]+", "+dist[1]+" ,"+dist[2]);
//			 key1= RoundToRange(dist[0]*StaticElements.scaling);
//			 key2= RoundToRange(dist[2]*StaticElements.scaling);
//			 key3= RoundToRange(dist[1]*StaticElements.scaling);
			 key1= RoundToRange(dist[0]);
			 key2= RoundToRange(dist[2]);
			 key3= RoundToRange(dist[1]);
			 val1=s3.getName();
			 val2=s2.getName();
			 val3=s1.getName();
			}
		keyVal[0]=key1.concat("_").concat(key2).concat("_").concat(key3);
		keyVal[1]=val2.concat("_").concat(val3).concat("_").concat(val1);
		return  keyVal;
	}
	//round value to Integer value after multiply by key range
	public static String RoundToRange(double value )
	{
		double Al=HashTableFile.Al;
		double angToPixratio=HashTableFile.angPixrRatio;
		String key= String.valueOf(Math.round(value*angToPixratio*Al));
		return key;
	}
	//compute rounded distance with error probabilty of err(gaussian) 
	public static String errRoundToRange(double dist )
	{
		double Al=HashTableFile.Al;
		double err= ErrCheck.PixErr;
		double err_dist=prob(err);
		dist=dist+err_dist;
		String key= String.valueOf(Math.round(dist*Al));
		System.err.println(err_dist);
		return key;
	}
	//return abs gaussian probability of standart deviation :err .
	public static double prob(double err)
	{
			// TODO Auto-generated method stub
			Random rand = new Random(); 
			double prob = err*rand.nextGaussian();
			
		//}
			return prob;
	}
	public static double dist(Star s1, Star s2)
	{
		double d1= (s1.getRa()-s2.getRa() );
		double d2= (s1.getDec()-s2.getDec());
		return Math.sqrt( d1*d1 +d2*d2);
	}
	public static double intRoundToRange(double value )
	{
		//double keyRange=StaticElements.keyRange;
		double key=  (int)Math.round(value*100)/100 ;///100;
		return key;
	}
	public static double angDist(Star s1, Star s2)
	{
		double distC=Math.sin(Math.toRadians(s1.getDec()))*Math.sin(Math.toRadians(s2.getDec()))+Math.cos(Math.toRadians(s1.getDec()))*Math.cos(Math.toRadians(s2.getDec()))*Math.cos(Math.toRadians(s1.getRa()-s2.getRa()));
		double dist= Math.acos(distC);
		return Math.toDegrees(dist);
	}
	
	
//  Old amg distance:
	
	
//	public static double angDist(Star s1, Star s2)
//	{
//		double x1=0,y1=0,a1,x2=0,y2=0,a2;
//		if (s1.getDec()>0 && s2.getDec()>0){
//		 a1=Math.cos(Math.toRadians(s1.getDec()))*Math.cos(Math.toRadians(s1.getRa()));
//		 x1=-StaticElements.scaling*(180/Math.PI)*Math.cos(Math.toRadians(s1.getDec()))*Math.sin(Math.toRadians(s1.getRa()));
//		 y1=StaticElements.scaling*(180/Math.PI)*(a1-Math.sin(Math.toRadians(s1.getDec()))*Math.cos(Math.toRadians(s1.getDec())));
//		 a2=Math.cos(Math.toRadians(s2.getDec()))*Math.cos(Math.toRadians(s2.getRa()));
//		 x2=-StaticElements.scaling*(180/Math.PI)*Math.cos(Math.toRadians(s2.getDec()))*Math.sin(Math.toRadians(s2.getRa()));
//		 y2=StaticElements.scaling*(180/Math.PI)*(a2-Math.sin(Math.toRadians(s2.getDec()))*Math.cos(Math.toRadians(s2.getDec())));
//		}
//		if (s1.getDec()<0 && s2.getDec()<0){
//		 a1=Math.cos(Math.toRadians(s1.getDec()))*Math.cos(Math.toRadians(s1.getRa()));
//		 x1=-StaticElements.scaling*(180/Math.PI)*Math.cos(Math.toRadians(s1.getDec()))*Math.sin(Math.toRadians(s1.getRa()));
//		 y1=StaticElements.scaling*(180/Math.PI)*(a1*Math.cos(Math.PI/2)-Math.sin(Math.toRadians(s1.getDec()))*Math.cos(Math.toRadians(s1.getDec())));
//		 a2=Math.cos(Math.toRadians(s2.getDec()))*Math.cos(Math.toRadians(s2.getRa()));
//		 x2=-StaticElements.scaling*(180/Math.PI)*Math.cos(Math.toRadians(s2.getDec()))*Math.sin(Math.toRadians(s2.getRa()));
//		 y2=StaticElements.scaling*(180/Math.PI)*(a2*Math.cos(Math.PI/2)-Math.sin(Math.toRadians(s2.getDec()))*Math.cos(Math.toRadians(s2.getDec())));
//			}
//		//System.out.println(Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)));
//		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
//		
//	}
	
	
	
	
	public static String[] get4keyMap(Star s1, Star s2 , Star s3, Star s4)
	{ //assuming s1 is near s3 and s4
		String[] res= new String[2];
		double d1= angDist(s1,s2);
		double d2= angDist(s1,s3);
		double d3= angDist(s1,s4);
		double d4= angDist(s2,s3);
		double d5= angDist(s2,s4);
		double d6= angDist(s3,s4);
	if(d1>d6)
	{
		if(d2>d3)
		{
			String[] t1=createTkeyValForMAp(s1, s2, s3);
			String[] t2=createTkeyValForMAp(s1, s2, s4);
			res[0]=t1[0]+";"+t2[0];
			res[1]=t1[1]+";"+t2[1];
		}
		if(d3>d2)
		{
			String[] t1=createTkeyValForMAp(s1, s2, s3);
			String[] t2=createTkeyValForMAp(s1, s2, s4);
			res[0]=t1[0]+"_"+t2[0];
			res[1]=t1[1]+"_"+t2[1];
		}
	}
	if(d5>d6)
	{
		if(d2>d3)
		{
			String[] t1=createTkeyValForMAp(s1, s2, s3);
			String[] t2=createTkeyValForMAp(s1, s2, s4);
			res[0]=t1[0]+";"+t2[0];
			res[1]=t1[1]+";"+t2[1];
		}
		if(d3>d2)
		{
			String[] t1=createTkeyValForMAp(s1, s2, s3);
			String[] t2=createTkeyValForMAp(s1, s2, s4);
			res[0]=t1[0]+"_"+t2[0];
			res[1]=t1[1]+"_"+t2[1];
		}
	}
		return res ;
		
	
}
}

