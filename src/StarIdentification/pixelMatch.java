package StarIdentification;

import java.util.Vector;

import skyElement.*;


public class pixelMatch {
	
	StarPixel s;
	Vector<Star> matches ;
	Vector<Integer> appear;
	Star bestStar;
	int Bestcount=0;
//	static pixelMatch[] pixMatch;
	
	public pixelMatch(StarPixel _s, Star m)
	{
		s=_s;
		matches = new Vector<Star>();
		appear =new Vector<Integer>();
		bestStar=m;
	//	if(  m.getDEC() <  StaticElements.EstOrientation_DEC+ StaticElements.radius_DEC  &&
	//	 m.getDEC() > StaticElements.EstOrientation_DEC- StaticElements.radius_DEC)
		Bestcount=1;
		matches.add(m);
		appear.add(1);
	}
	public Star getBestStar() {
		return bestStar;
	}
	
	public int getBestCount() {
		return Bestcount;
	}
	public StarPixel getS() {
		return s;
	}
	public void add(Star m)
	{
		 boolean exist=false;
	//if the star already appers- add to count		
		for (int i = 0; i < matches.size(); i++) 
		{
			if(matches.elementAt(i).getRa()==m.getRa() && matches.elementAt(i).getDec()==m.getDec())
				appear.set(i,appear.elementAt(i)+1);
				exist=true;
		}
		if(!exist){
		matches.add(m);
		appear.add(1);
		}
		int bestCount=0;
		int bestInd=0;
		//get new best match
		for (int i = 0; i < appear.size(); i++) {
			if(appear.get(i)>bestCount){
				bestCount=appear.get(i);
				bestInd=i;
			}
		}
		bestStar=matches.get(bestInd);
		Bestcount=bestCount;
}
}
