package StarIdentification;

import java.util.HashMap;
import java.util.Vector;

import skyElement.*;


public class StarTriplet2 {
	
	StarPixel s_pix1 ;
	StarPixel s_pix2 ;
	StarPixel s_pix3 ;
	
	public Vector<Star> s_dbp1  ;
	public Vector<Star> s_dbp2  ;
	public Vector<Star> s_dbp3  ;
	Vector<Integer[]> index; 
	private int size=0;
	//static pixelMatch[] bestMatchPix;
//	
//	public static void setPixelMatchSize(int size)
//	{
//		bestMatchPix= new pixelMatch[size];
//	}
//	public static pixelMatch[] getBestMatchPix(){
//		return bestMatchPix;
//	}
	public StarTriplet2(StarPixel sp1,StarPixel sp2 ,StarPixel sp3, Star sd1,Star sd2 ,Star sd3)
	{
		s_dbp1 =new Vector<Star>() ;
		s_dbp2 =new Vector<Star>() ;
		s_dbp3 =new Vector<Star>() ;
		s_dbp1.add(sd1);
		s_dbp2.add(sd2);
		s_dbp3.add(sd3);
		s_pix1 =sp1;
		s_pix2 =sp2;
		s_pix3 =sp3;
		size=1;
	}
	public StarTriplet2(StarPixel sp1,StarPixel sp2 ,StarPixel sp3 , HashMap<Integer, Star> starTable ,String[] value)
	{
		s_dbp1 =new Vector<Star>() ;
		s_dbp2 =new Vector<Star>() ;
		s_dbp3 =new Vector<Star>() ;
		s_dbp1.add(starTable.get(Integer.valueOf(value[0])));
		s_dbp2.add(starTable.get(Integer.valueOf(value[1])));
		s_dbp3.add(starTable.get(Integer.valueOf(value[2])));
		s_pix1 =sp1;
		s_pix2 =sp2;
		s_pix3 =sp3;
		index= new Vector<Integer[]>();
		Integer[] names= new Integer[3];
		names[0]=starTable.get(Integer.valueOf(value[0])).getId();
		names[1]=starTable.get(Integer.valueOf(value[1])).getId();;
		names[2]=starTable.get(Integer.valueOf(value[2])).getId();;
		index.add(names);
		size++;
		if(pixelMatchArr.bestMatchPix[Integer.valueOf(sp1.getName())] ==null)
			pixelMatchArr.bestMatchPix[Integer.valueOf(sp1.getName())]=new pixelMatch(sp1, starTable.get(Integer.valueOf(value[0]) ));
		else
			pixelMatchArr.bestMatchPix[Integer.valueOf(sp1.getName())].add( starTable.get(Integer.valueOf(value[0]) ));
		if(pixelMatchArr.bestMatchPix[Integer.valueOf(sp2.getName())] ==null)
			pixelMatchArr.bestMatchPix[Integer.valueOf(sp2.getName())]=new pixelMatch(sp2,  starTable.get(Integer.valueOf(value[1]) ));
		else
			pixelMatchArr.bestMatchPix[Integer.valueOf(sp2.getName())].add( starTable.get(Integer.valueOf(value[1]) ));
		if(pixelMatchArr.bestMatchPix[Integer.valueOf(sp3.getName())] ==null)
			pixelMatchArr.bestMatchPix[Integer.valueOf(sp3.getName())]=new pixelMatch(sp3,  starTable.get(Integer.valueOf(value[2]) ));
		else
			pixelMatchArr.bestMatchPix[Integer.valueOf(sp3.getName())].add( starTable.get(Integer.valueOf(value[2]) ));
	}
	public void addMatch(HashMap<Integer, Star> starTable ,String[] value ) throws Exception
	{
		if(value!=null && value.length==3)
		{
//			Double RA=starTable.get(Double.valueOf(value[0]))[0];
//			Double DEC= starTable.get(Double.valueOf(value[0]))[1];
//			Double MAG=starTable.get(Double.valueOf(value[0]))[2];
//		//	Double NAME=starTable.get(Double.valueOf(value[0]))[3];
			Double NAME=Double.valueOf(value[0]);
//			Double RA2=starTable.get(Double.valueOf(value[1]))[0];
//			Double DEC2= starTable.get(Double.valueOf(value[1]))[1];
//			Double MAG2=starTable.get(Double.valueOf(value[1]))[2];
////			Double NAME2=starTable.get(Double.valueOf(value[1]))[3];
			Double NAME2=Double.valueOf(value[1]);
//			Double RA3=starTable.get(Double.valueOf(value[2]))[0];
//			Double DEC3= starTable.get(Double.valueOf(value[2]))[1];
//			Double MAG3=starTable.get(Double.valueOf(value[2]))[2];
//		//	Double NAME3=starTable.get(Double.valueOf(value[2]))[3];
			Double NAME3=Double.valueOf(value[2]);
			//System.out.println("add:"+ RA+" "+RA2+" "+RA3+" "+DEC+" "+DEC2+" "+DEC3+"size:"+size);
			s_dbp1.add(starTable.get(Integer.valueOf(value[0])));
			s_dbp2.add(starTable.get(Integer.valueOf(value[1])));
			s_dbp3.add(starTable.get(Integer.valueOf(value[2])));
			Integer[] names= new Integer[3];
			names[0]=starTable.get(Integer.valueOf(value[0])).getId();
			names[1]=starTable.get(Integer.valueOf(value[1])).getId();
			names[2]=starTable.get(Integer.valueOf(value[2])).getId();
			index.add(names);
		size++;
		pixelMatchArr.bestMatchPix[Integer.valueOf(s_pix1.getName())].add(starTable.get(Integer.valueOf(value[0])));
		pixelMatchArr.bestMatchPix[Integer.valueOf(s_pix2.getName())].add(starTable.get(Integer.valueOf(value[1])));
		pixelMatchArr.bestMatchPix[Integer.valueOf(s_pix3.getName())].add(starTable.get(Integer.valueOf(value[2])));
		}
		else throw (new Exception("try to add null value to triplet ("+s_pix1.getX()+", "+s_pix1.getX()+") ("+
				+s_pix2.getX()+", "+s_pix3.getX()+" )"+s_pix3.getX()+", ("+s_pix3.getX()+" )"));
	
	}
	
//	public void FilterByOrientation()
//	{
//		int grade=0;
//		Vector<Integer> badMatch =new Vector<Integer>();
//		for (int i=0;i<size;i++)
//		{
//			if(s_dbp1.elementAt(i).getDec() <StaticElements.EstOrientation_DEC+StaticElements.radius_DEC &&
//					s_dbp1.elementAt(i).getDec() >StaticElements.EstOrientation_DEC-StaticElements.radius_DEC && 
//					s_dbp2.elementAt(i).getDec() <StaticElements.EstOrientation_DEC+StaticElements.radius_DEC &&
//					s_dbp2.elementAt(i).getDec() >StaticElements.EstOrientation_DEC-StaticElements.radius_DEC 
//					&& s_dbp3.elementAt(i).getDec() <StaticElements.EstOrientation_DEC+StaticElements.radius_DEC
//					&& s_dbp3.elementAt(i).getDec() >StaticElements.EstOrientation_DEC-StaticElements.radius_DEC  )
//			{
//				grade++;
//			}
//			else
//			{
//				badMatch.add(i);
//				System.out.println("Filter:" +s_dbp1.elementAt(i).getDEC()+" ,"+s_dbp2.elementAt(i).getDEC()+", "+s_dbp3.elementAt(i).getDEC());
//				}
//		}
//		for(int i=0;i<badMatch.size();i++)
//		{
//			this.remove(badMatch.get(i)-i);
//		}
//		
//	}
	
	public StarPixel getS_pix1() {
		return s_pix1;
	}
	public StarPixel getS_pix2() {
		return s_pix2;
	}
	public StarPixel getS_pix3() {
		return s_pix3;
	}
	public Vector<Star> getS_dbp1() {
		return s_dbp1;
	}
	public Vector<Star> getS_dbp2() {
		return s_dbp2;
	}
	public Vector<Star> getS_dbp3() {
		return s_dbp3;
	}
	public int getSize() {
		return size;
	}
	//@Override
	//public int compareTo(StarTriplet o) {
	//if(this)
		
	//	return 0;
	public boolean indContain(double x, double y){
		for(int i=0;i<index.size();i++)
		{
			if(index.elementAt(i)[0] ==x || index.elementAt(i)[1] ==x || index.elementAt(i)[1] ==x )
			{
				if(index.elementAt(i)[0] ==y || index.elementAt(i)[1] ==y || index.elementAt(i)[1] ==y )
				{
					return true;
				}
			}
		}
		return false;
	}
	public boolean isXalsoHere(int name, double RA, double DEC){
		
		if(s_pix1.getX()==RA && s_pix1.getY()==DEC){
		for(int i=0;i<s_dbp1.size();i++)
		{
			//System.out.println(s_pix1.getRA()+", "+s_pix1.getDEC() +" ,"+s_dbp1.elementAt(i).get_name());
			if(Double.valueOf(s_dbp1.elementAt(i).getId()) ==name )
				//System.out.println(s_pix1.getRA()+", "+s_pix1.getDEC() +" ,"+s_dbp1.elementAt(i).get_name());
				return true;
		}}
		if(s_pix2.getX()==RA && s_pix2.getY()==DEC){
			for(int i=0;i<s_dbp2.size();i++)
			{
				if(Double.valueOf(s_dbp2.elementAt(i).getId()) ==name )
					return true;
			}}
		if(s_pix3.getX()==RA && s_pix3.getY()==DEC){
			for(int i=0;i<s_dbp3.size();i++)
			{
				if(Double.valueOf(s_dbp3.elementAt(i).getId()) ==name )
					return true;
			}}
		return false;
	}
	
	public void remove(int index)
	{
		s_dbp1.remove(index);
		s_dbp2.remove(index);
		s_dbp3.remove(index);
		size--;
	}

	public static StarTriplet2[] isIntersect(StarTriplet2 s1, StarTriplet2 s2)
	{
		StarTriplet2[] res= new StarTriplet2[2];
		for (int i = 0; i < s1.getSize(); i++) {
		int tempi=isInStar(s1.s_dbp1.get(i), s1.s_dbp2.get(i),s2);
		if(tempi!=-1) {
			res[0]=new StarTriplet2(s1.s_pix1 ,s1.s_pix2,s1.s_pix3,s1.s_dbp1.get(i), s1.s_dbp2.get(i), s1.s_dbp3.get(i) );
			res[1]=new StarTriplet2(s2.s_pix1 ,s2.s_pix2,s2.s_pix3,s2.s_dbp1.get(tempi), s2.s_dbp2.get(tempi), s2.s_dbp3.get(tempi));
			return res;
		}
		 tempi=isInStar(s1.s_dbp1.get(i), s1.s_dbp3.get(i),s2);
		if(tempi!=-1) {
			res[0]=new StarTriplet2(s1.s_pix1 ,s1.s_pix2,s1.s_pix3,s1.s_dbp1.get(i), s1.s_dbp2.get(i), s1.s_dbp3.get(i) );
			res[1]=new StarTriplet2(s2.s_pix1 ,s2.s_pix2,s2.s_pix3,s2.s_dbp1.get(tempi), s2.s_dbp2.get(tempi), s2.s_dbp3.get(tempi));
			return res;
		}
		tempi=isInStar(s1.s_dbp1.get(i), s1.s_dbp3.get(i),s2);
		if(tempi!=-1) {
			res[0]=new StarTriplet2(s1.s_pix1 ,s1.s_pix2,s1.s_pix3,s1.s_dbp1.get(i), s1.s_dbp2.get(i), s1.s_dbp3.get(i));
			res[1]=new StarTriplet2(s2.s_pix1 ,s2.s_pix2,s2.s_pix3,s2.s_dbp1.get(tempi), s2.s_dbp2.get(tempi), s2.s_dbp3.get(tempi));
			return res;
		}}		
		return null;
	}
	
	public static int isInStar(Star s1,Star s2, StarTriplet2 st)
	{//the stars that not similar to s1 or s2;
		if(s1!=s2){
		for (int i = 0; i < st.getSize(); i++) {			
				if(Samestar(s1, st.s_dbp1.get(i))||Samestar(s1, st.s_dbp2.get(i))||Samestar(s1, st.s_dbp3.get(i)))
					if(Samestar(s2, st.s_dbp1.get(i))||Samestar(s2, st.s_dbp2.get(i))||Samestar(s2, st.s_dbp3.get(i))){
						return i;
					}
					
//					if(Samestar(s1, st.s_dbp1.get(i)) && Samestar(s2, st.s_dbp2.get(i))||
//							Samestar(s1, st.s_dbp2.get(i)) && Samestar(s2, st.s_dbp1.get(i)))
//						return (st.s_dbp3.get(i));
//					if(Samestar(s1, st.s_dbp1.get(i)) && Samestar(s2, st.s_dbp3.get(i))||
//							Samestar(s1, st.s_dbp3.get(i)) && Samestar(s2, st.s_dbp1.get(i)))
//						return (st.s_dbp2.get(i));
//					if(Samestar(s1, st.s_dbp2.get(i)) && Samestar(s2, st.s_dbp3.get(i))||
//							Samestar(s1, st.s_dbp3.get(i)) && Samestar(s2, st.s_dbp2.get(i)))
//						return (st.s_dbp1.get(i));
				}}
		return -1;
	}
	public static boolean Samestar(StarPixel s1,StarPixel s2)
	{
		if(s1.getX()==s2.getX() && s1.getY()==s2.getY())
			return true;
		return false;
	}
	public static boolean Samestar(Star s1,Star s2)
	{
		if(s1.getRa()==s2.getRa() && s1.getDec()==s2.getDec())
			return true;
		return false;
	}
	public static boolean contains(StarTriplet2 st , Vector<StarTriplet2> vst)
	{
		//if first match of st equals any of vst matches angkles.
		for (int i = 0; i < vst.size(); i++) {
			if(Samestar(st.s_pix1, vst.get(i).s_pix1 )&& Samestar(st.s_pix2,vst.get(i).s_pix2 )&&Samestar(st.s_pix3,vst.get(i).s_pix3)){
				for (int j = 0; j < vst.get(i).getSize(); j++) {
				if(Samestar( st.s_dbp1.get(0), vst.get(i).s_dbp1.get(j)) 
						&& Samestar( st.s_dbp2.get(0), vst.get(i).s_dbp2.get(j))
						&& Samestar( st.s_dbp3.get(0), vst.get(i).s_dbp3.get(j))){
					return true;
				}}
			}
		}return false;
	}
}
