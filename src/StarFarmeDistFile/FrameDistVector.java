package StarFarmeDistFile;

import java.util.Collections;
import java.util.Vector;
import skyElement.*;

public class FrameDistVector {
	//distances vector (sorted)
	Vector<Double> dists = new Vector<Double>();
	Vector<StarPixel> stars = new Vector<StarPixel>();
	
	public FrameDistVector(){
		// TODO Auto-generated constructor stub
	}
	
	public void addStarPixel(StarPixel sp  )
	{
		
		for (int i = 0; i < stars.size(); i++) {
			dists.add(sp.distTo(stars.elementAt(i)));
		}
		stars.add(sp);
	}
	public void sort ( )
	{
		Collections.sort(dists);
	}
	public void addToCsv()
	{
		Output.distances.add(dists);
		Output.starPixels.add(stars);
	}


}
