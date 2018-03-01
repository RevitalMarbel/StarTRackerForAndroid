package StarFarmeDistFile;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.synth.Region;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import StaticElements.*;

public class VideoStarPixelsExtraction {
	
	static int rezX=1280;
	static int rezy=720;
	static int rezyn=720-50;
	//static int diagX
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
 
    static Mat imag = null;
 
    public static void main(String[] args) {
        JFrame jframe = new JFrame("Star Detector");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(rezX, rezy);
        jframe.setVisible(true);
 
        Mat frame = new Mat();
        Mat outerBox = new Mat();
        VideoCapture camera = new VideoCapture(
               videoFile.StarVideoName);
        Size sz = new Size(rezX, rezy);

        
        while (true) {
            if (camera.read(frame)) {
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
                Point[] center = new Point[centroids.rows()-1];
                //Imgproc.findContours( thr, contours, new Mat(),Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0) );
                frame.copyTo(outerBox, edges);
                //for (int i = 0; i < center.length; i++) {
				//	Imgproc.circle(outerBox, center[i], 5,new Scalar(100));
				//} 
                // Free memory
                for (int i = 0; i < edges; i++) {
					
				}
                centroids.release();
                edges.release();
            }
                
                ImageIcon image = new ImageIcon(Mat2bufferedImage(outerBox));
        
                vidpanel.setIcon(image);
                vidpanel.repaint();
               
            }
        
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
 