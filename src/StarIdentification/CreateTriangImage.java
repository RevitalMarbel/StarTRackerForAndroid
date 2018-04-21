package StarIdentification;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.xml.stream.events.Namespace;

import StaticElements.StarFrame;
import loadDB.AccuracyLevel;
import skyElement.*;

public class CreateTriangImage {

	public static void createImage2(HashMap<Integer,Star> sv, Vector<StarTriplet2> st  ,String fileName ,int ovalSize,int sv_realsize){
		//BufferedImage background;
		try {
		BufferedImage pixelImage = ImageIO.read(new File(StarFrame.backgroundImage));
//	    pixelImage.setRGB(0, 0, width, height, pixels, 0, width);
	    File outputfile = new File(fileName);
	    	ovalSize=50;
	    	Graphics2D g2 = pixelImage.createGraphics();
	    	g2.setBackground(Color.WHITE);
	    	Font f;
	    	if(ovalSize<3){
	    	f= new Font (Font.DIALOG,Font.BOLD,150);
	    	}
	    	else{
	    	f= new Font (Font.DIALOG,Font.BOLD,ovalSize+50);}
			g2.setFont(f);
	    	for(int i=0; i<sv.size();i++)
	    	{
	    		if(i>sv_realsize)
	    			g2.setColor(Color.YELLOW);
	    		else
	    			g2.setColor(Color.YELLOW);
	    		if(ovalSize<3){
	    			int x=(int)sv.get(i).getRa();int y= 90+(int)sv.get(i).getDec();
	    		g2.drawOval(x,y, ovalSize, ovalSize);
	    		g2.drawString(x+", "+y, x, y+70);
	    		}
	    		else{
	    			if(sv.get(i)!= null){
	    			int x=(int)sv.get(i).getRa();int y= (int)sv.get(i).getDec();
	    		g2.drawOval(x-ovalSize/2,y-ovalSize/2, ovalSize, ovalSize);
	    		g2.drawString(x+", "+y, x, y+70);
	    		}}
	    	}
	    	
	 		double maxDiff=0;
	 		double minDiff=5000;
	    	int x1,x2,x3,y1,y2,y3;
	    	//for(int i=0; i<st.size();i++)//draw matching triangles
	    		for(int i=0; i<st.size();i++)//draw
	    	{
	    		//System.out.println("st size"+st.size() );
	    		//if(st.elementAt(i).getSize() >3)
	    	//	{
	    			x1=(int)st.elementAt(i).getS_pix1().getX();
	    			x2=(int)st.elementAt(i).getS_pix2().getX();
	    			x3=(int)st.elementAt(i).getS_pix3().getX();
	    			y1=(int)st.elementAt(i).getS_pix1().getY();
	    			y2=(int)st.elementAt(i).getS_pix2().getY();
	    			y3=(int)st.elementAt(i).getS_pix3().getY();
	    			System.out.println(x1+" "+x2+" "+y1+" "+y2);
	    			g2.setColor(Color.green);
	    			g2.drawLine( x1,y1,x2,y2);
	    			g2.drawLine( x2,y2,x3,y3);
	    			g2.drawLine( x1,y1,x3,y3);
	    		//	for(int j=0;j<(int)st.elementAt(i).getS_dbp1().size();j++){
	    			for(int j=0;j<1;j++){
//	    			double d1=Math.sqrt((st.elementAt(i).s_dbp1.elementAt(j).getRA()-st.elementAt(i).s_dbp2.elementAt(j).getRA())*(st.elementAt(i).s_dbp1.elementAt(j).getRA()-st.elementAt(i).s_dbp2.elementAt(j).getRA())
//	    					+(st.elementAt(i).s_dbp1.elementAt(j).getDEC()-st.elementAt(i).s_dbp2.elementAt(j).getDEC())*(st.elementAt(i).s_dbp1.elementAt(j).getDEC()-st.elementAt(i).s_dbp2.elementAt(j).getDEC()));
//	    			double d2=Math.sqrt((st.elementAt(i).s_dbp1.elementAt(j).getRA()-st.elementAt(i).s_dbp3.elementAt(j).getRA())*(st.elementAt(i).s_dbp1.elementAt(j).getRA()-st.elementAt(i).s_dbp3.elementAt(j).getRA())
//	    					+(st.elementAt(i).s_dbp1.elementAt(j).getDEC()-st.elementAt(i).s_dbp3.elementAt(j).getDEC())*(st.elementAt(i).s_dbp1.elementAt(j).getDEC()-st.elementAt(i).s_dbp3.elementAt(j).getDEC()));
//	    			double d3=Math.sqrt((st.elementAt(i).s_dbp3.elementAt(j).getRA()-st.elementAt(i).s_dbp2.elementAt(j).getRA())*(st.elementAt(i).s_dbp3.elementAt(j).getRA()-st.elementAt(i).s_dbp2.elementAt(j).getRA())
//	    					+(st.elementAt(i).s_dbp3.elementAt(j).getDEC()-st.elementAt(i).s_dbp2.elementAt(j).getDEC())*(st.elementAt(i).s_dbp3.elementAt(j).getDEC()-st.elementAt(i).s_dbp2.elementAt(j).getDEC()));
	    			double d1=AccuracyLevel.angDist(st.elementAt(i).s_dbp1.elementAt(j), st.elementAt(i).s_dbp2.elementAt(j));
	    			double d2=AccuracyLevel.angDist(st.elementAt(i).s_dbp1.elementAt(j), st.elementAt(i).s_dbp3.elementAt(j));
	    			double d3=AccuracyLevel.angDist(st.elementAt(i).s_dbp2.elementAt(j), st.elementAt(i).s_dbp3.elementAt(j));
	    			double p1= Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	    			double p2= Math.sqrt((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3));
	    			double p3= Math.sqrt((x3-x2)*(x3-x2)+(y3-y2)*(y3-y2));
	    			double temp_diff=Math.abs(d1-p1);
	    			///System.out.println(d1+"-"+ p1);
	    			if(maxDiff <temp_diff)
	    				maxDiff=temp_diff;
	    			if(minDiff >temp_diff)
	    				minDiff=temp_diff;
	    			temp_diff=Math.abs(d2-p2);
	    			//System.out.println(d2+"-"+ p2);
	    			if(maxDiff <temp_diff)
	    				maxDiff=temp_diff;
	    			if(minDiff >temp_diff)
	    				minDiff=temp_diff;
	    			temp_diff=Math.abs(d3-p3);
	    			//System.out.println(d3+"-"+ p3);
	    			if(maxDiff <temp_diff)
	    				maxDiff=temp_diff;
	    			if(minDiff >temp_diff)
	    				minDiff=temp_diff;
	    			
	    			
//	    			System.out.println("s_dbp1 RA"+st.elementAt(i).s_dbp1.get(j).getRA()+" "+x1+" "+y1);
//	    			System.out.println("s_dbp2 RA"+st.elementAt(i).s_dbp2.get(j).getRA()+" "+x2+" "+y2);
//	    			System.out.println("s_dbp3 RA"+st.elementAt(i).s_dbp3.get(j).getRA()+" "+x3+" "+y3);
//	    			g2.drawString(String.valueOf(st.elementAt(i).s_dbp1.elementAt(j).getRA())+","+st.elementAt(i).s_dbp1.elementAt(j).getDEC(), x1,y1-j*100);
//	    			g2.drawString(String.valueOf(st.elementAt(i).s_dbp2.elementAt(j).getRA())+","+st.elementAt(i).s_dbp2.elementAt(j).getDEC(), x2,y2-j*100);
//	    			g2.drawString(String.valueOf(st.elementAt(i).s_dbp3.elementAt(j).getRA())+","+st.elementAt(i).s_dbp3.elementAt(j).getDEC(), x3,y3-j*100);
//	    			
//	    			g2.drawString(st.elementAt(i).s_dbp1.elementAt(j).get_name(), x1,y1);
//	    			g2.drawString(st.elementAt(i).s_dbp2.elementAt(j).get_name(), x2,y2);
//	    			g2.drawString(st.elementAt(i).s_dbp3.elementAt(j).get_name(), x3,y3);
//	    			
//	    			g2.drawString(String.valueOf(st.elementAt(i).s_dbp1.elementAt(0).getRA())+","+st.elementAt(i).s_dbp1.elementAt(0).getDEC(), x1,y1-j*100);
//	    			g2.drawString(String.valueOf(st.elementAt(i).s_dbp2.elementAt(0).getRA())+","+st.elementAt(i).s_dbp2.elementAt(0).getDEC(), x2,y2-j*100);
//	    			g2.drawString(String.valueOf(st.elementAt(i).s_dbp3.elementAt(0).getRA())+","+st.elementAt(i).s_dbp3.elementAt(0).getDEC(), x3,y3-j*100);
	    			}
	    			}
	    		//}
	    	//}
//	    	for (int i = 0; i < matches.size(); i++) {
//	    		g2.drawString(matches.elementAt(i).label, matches.elementAt(i).x,matches.elementAt(i).y-i*5);
//			}
	    		System.out.println("max dist diff  :"+ maxDiff);
    			System.out.println("min diff  :"+ minDiff);
    		
	    			ImageIO.write(pixelImage, "jpg", outputfile);			
				
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createImage3(HashMap<Integer,Star> sv, pixelMatch[] pm  ,String fileName ,int ovalSize,int sv_realsize, int size ,double time) throws FileNotFoundException{
			BufferedImage background;
			createStarName.fileToMAp();
			
			try {
				Vector<String> names=createStarName.getTABLE();
				double confRatio= size/pm.length;
				
			//background = ImageIO.read(new File(StaticElements.imageToProcess));
			//int width =background.getWidth();
			//int height = background.getHeight();
			//BufferedImage pixelImage = new BufferedImage(width, height,  BufferedImage.TYPE_INT_ARGB);   
				File back=new File(StarFrame.backgroundImage);
				back.canRead();
				BufferedImage pixelImage = ImageIO.read(back);
				//BufferedImage pixelImage = ImageIO.read(new File(StaticElements.imageToProcess));
//		    pixelImage.setRGB(0, 0, width, height, pixels, 0, width);
		    File outputfile = new File(fileName+".jpg");
		    	ovalSize=50;
		    	Graphics2D g2 = pixelImage.createGraphics();
		    	g2.setBackground(Color.WHITE);
		    	Font f;
		    	f= new Font (Font.DIALOG,Font.BOLD,80);
				g2.setFont(f);
		    	for(int i=0; i<sv.size();i++)
		    	{
		    		if(sv.get(i)!= null){
		    		g2.setColor(Color.YELLOW);
		    		int x=(int)sv.get(i).getRa();int y= (int)sv.get(i).getDec();
		    		g2.drawOval(x-25,y-25, 50, 50);
		    	//	g2.drawString(String.valueOf(sv.elementAt(i).getRA())+","+String.valueOf(sv.elementAt(i).getDEC()), x,y);
		    		//g2.drawString(String.valueOf(x)+","+String.valueOf(y), x,y+100);
		    	}}
		    	g2.setColor(Color.GREEN);
		    	int x1,y1;double RA, DEC;
	
		    	for(int i=0; i<pm.length;i++)//draw matching triangles
		    	{
		    		
		    		if(pm[i]!=null && pm[i].getBestCount()>=0){//&& pa[i].getBestCount()>sv.size()-16){
		    		if(pm[i].getBestCount()> pm.length/2+1){
		    			double tmpRatio = Math.floor(pm[i].getBestCount()/confRatio * 1000) / 1000;
		    			System.out.println("tmp"+tmpRatio);
		    			RA=pm[i].getBestStar().getRa();
		    			DEC=pm[i].getBestStar().getDec();
		    			x1=(int)pm[i].getS().getX();
		    			y1=(int)pm[i].getS().getY();
		    			g2.setColor(Color.green);
		    			int index=Integer.valueOf(pm[i].getBestStar().getId());
		    			int indexI=(int)index-1;
		    			//g2.drawString(pm[i].getBestStar().getRA()+","+pm[i].getBestStar().getDEC(), x1,y1);
		    			//g2.drawString(pm[i].getBestStar().get_name(), x1,y1);
		    			
		    			g2.drawString(names.elementAt(indexI), x1+10,y1+30);
		    			
		    			g2.setColor(Color.pink);
		    			
		    			
		    		
		    			if(confRatio!=0)
		    				g2.drawString(String.valueOf(tmpRatio), x1+50,y1+100);
		    			else
		    				g2.drawString(String.valueOf(pm[i].getBestCount()),  x1+10,y1+60);
		    			
				    	
		    		}
		    		 }
		    	}
		    	g2.setColor(Color.pink);
		    	f= new Font (Font.DIALOG,Font.BOLD,50);
		    	g2.drawString("Confident ",50,200);
		    	g2.setColor(Color.green);
		    	g2.drawString("Star name ",50,350);
//		    	for (int i = 0; i < matches.size(); i++) {
//		    		g2.drawString(matches.elementAt(i).label, matches.elementAt(i).x,matches.elementAt(i).y-i*5);
//				}
		    	g2.drawString("triangles:  "+size,50,50);
		    	g2.drawString("time:  "+time+ " ms",50,100);
				ImageIO.write(pixelImage, "jpg", outputfile);
//				BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	//
//				// paint both images, preserving the alpha channels
//				Graphics g = combined.getGraphics();
//				
//				 g2.setComposite(AlphaComposite.Src);
//				g.drawImage(background, 0, 0, null);
//				 g2.setComposite(OVER_HALF);
//				g.drawImage(pixelImage, 0, 0, null);

				// Save as new image
		//		ImageIO.write(combined, "jpg", new File(outputfile+"combined.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
}
}
