package ImageProcessing;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.synth.Region;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import StaticElements.*;
import skyElement.StarPixel;

public class FramesStarPixelsExtraction {
	
	static int rezX=4032;
	static int rezy=3024;
	//static int rezyn=720-50;
	//static int diagX
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    static Mat imag = null;
    
    
   public static void main(String[] args) {
    	startProcces();
    	Output.writeCSVFiles();   
   }
   
    public static void startProcces(){   	
        JFrame jframe = new JFrame("Star Detector KCG");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JPanel p = new JPanel(); 
        //jframe.getContentPane().add(p);
        
       // p.add(vidpanel);
        JLabel vidpanel = new JLabel();
        //JPanel vidpanel = new JPanel();
        jframe. setLayout(new BorderLayout());
        //jframe.setContentPane(vidpanel);
        jframe.setSize(rezX/4, rezy/4);
        jframe.add(vidpanel);
        jframe.pack();
        jframe.setVisible(true);
 
        Mat frame = new Mat();
        Mat outerBox = new Mat();
        frame = Imgcodecs.imread(StarFrame.starFrameName);
        
        
        Size sz = new Size(rezX, rezy);
        //create output vectors (dist and pixels files)  
        Output op= new Output();

                Imgproc.resize(frame, frame, sz);
                imag = frame.clone();
                Rect rect = new Rect(0,0,rezX,rezy-50);         
                frame=frame.submat(rect);
                
                outerBox = new Mat(frame.size(), CvType.CV_8UC1);
                
                Imgproc.cvtColor(frame, outerBox, Imgproc.COLOR_BGR2GRAY);
                Imgproc.GaussianBlur(outerBox, outerBox, new Size(3, 3), 0);
                System.out.println(outerBox.get(1,1)[0]+" "+ rezX+" "+rezy);
                //Rect rect = new Rect(rezX/2,rezy/2,400,360);
                
                Mat labels= new Mat(frame.size(), CvType.CV_8U);
                Mat edges= new Mat(frame.size(), CvType.CV_8UC1);
                Mat centroids= new Mat(frame.size(), CvType.CV_8UC1);
                Imgproc.connectedComponentsWithStats(outerBox, labels, edges, centroids);
                Imgproc.Canny(outerBox, edges, 200, 250);
                //Point[] center = new Point[centroids.rows()-1];
                List<MatOfPoint> contours = new ArrayList<>();
                Imgproc.findContours( edges, contours, new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0) );
                FrameDistVector fdv= new FrameDistVector();
                MatOfPoint2f f_contour =new MatOfPoint2f(contours.get(contours.size()-1).toArray());
                Point f_center = new Point();
                float[] f_radius = new float[1];
                Imgproc.minEnclosingCircle(f_contour, f_center, f_radius);  
                for (int i = 0; i < contours.size(); i++) {
                	MatOfPoint2f contour =new MatOfPoint2f(contours.get(i).toArray());
            		double contourArea = Imgproc.contourArea(contour);
            		if (contourArea > videoFile.minArea) {
            			float[] radius = new float[1];
    					Point center = new Point();
    					if(i==0)
    						f_center=center;
    					Imgproc.minEnclosingCircle(contour, center, radius);   
         			   //frame.copyTo(outerBox, center);
    					System.out.println("center:" +center.toString()+ "radius"+radius[0]);
    					Imgproc.circle(outerBox, center,(int)radius[0], new Scalar(150), 10);
    					//Imgproc.putText(outerBox, center.toString(), center, Core.FONT_ITALIC, 1.0 ,new  Scalar(100));
    					
    					StarPixel tempSP=new StarPixel(center,radius[0]);
    					fdv.addStarPixel(tempSP);
    					if(tempSP.distTo(f_center) >100){
    					Imgproc.line(outerBox, center, f_center, new Scalar(150));
    					Point mid = new Point ((center.x+f_center.x)/2,(center.y+f_center.y)/2);
    					Imgproc.putText(outerBox, String.valueOf(tempSP.distTo(f_center)), mid, Core.FONT_ITALIC, 1.0 ,new  Scalar(200));
    					}
						
            		}	
            		}
                	fdv.sort();
                	op.add(fdv.getDists(), fdv.getStars());
                
                //Imgproc.findContours(img,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
                centroids.release();
                edges.release();
                BufferedImage imageb = Mat2bufferedImage(outerBox);
                ImageIcon imageI = new ImageIcon(Mat2bufferedImage(outerBox));
                
                File outputfile = new File(StarFrame.outPutImage+"_rez.png");
                try {
					ImageIO.write(imageb, "png", outputfile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                vidpanel.setPreferredSize(new Dimension(rezX, rezy));
                vidpanel.setIcon(imageI);
                jframe.pack();
              
                vidpanel.repaint(); 
       
        }

    public static BufferedImage Mat2bufferedImage(Mat in)
    {
        BufferedImage out;
        byte[] data = new byte[rezX * rezy * (int)in.elemSize()];
        int type;
        in.get(0, 0, data);

        if(in.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;
        

        out = new BufferedImage(rezX, rezy, type);

        out.getRaster().setDataElements(0, 0, rezX, rezy, data);
        return out;
    } 
   
}
 