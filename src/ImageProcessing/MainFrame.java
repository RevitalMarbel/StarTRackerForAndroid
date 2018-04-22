package ImageProcessing;
import java.awt.ImageCapabilities;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import StaticElements.StarFrame;
import StaticElements.videoFile;
 
public class MainFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
 
    static Mat imag = null;
    static int rezx=4032;
    static int rezy=3024;
 
    public static void main(String[] args) {
        JFrame jframe = new JFrame("Star Tracker KCG LAB Ariel University");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(rezx, rezy);
        jframe.setVisible(true);
 
        Mat outerBox = new Mat();

        ArrayList<Rect> array = new ArrayList<Rect>();
     
                Mat frame = Imgcodecs.imread(StarFrame.starFrameName);
                System.out.println(frame.size());
                Size sz = new Size(frame.width(), frame.height());    
       
                imag = frame.clone();
                outerBox = new Mat(frame.size(), CvType.CV_8UC1);
                Imgproc.cvtColor(frame, outerBox, Imgproc.COLOR_BGR2GRAY);
                Imgproc.GaussianBlur(outerBox, outerBox, new Size(5, 5), 0);
                //Core.subtract(outerBox, tempon_frame, diff_frame);
               // Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
                //Imgproc.ADAPTIVE_THRESH_MEAN_C,
               // Imgproc.THRESH_BINARY_INV, 5, 2);
                array = detection_contours(outerBox);
                System.out.println(array.size());
                if (array.size() > 0) {
                        Iterator<Rect> it2 = array.iterator();
                        while (it2.hasNext()) {
                            Rect obj = it2.next();
                            Imgproc.rectangle(imag, obj.br(), obj.tl(),
                                    new Scalar(0, 255, 0), 1);
                        }
 
                    }
 
                ImageIcon image = new ImageIcon(Mat2bufferedImage(imag));
                vidpanel.setIcon(image);
                vidpanel.repaint();
                
 
            }
        

    public static BufferedImage Mat2bufferedImage(Mat in)
    {
        BufferedImage out;
        byte[] data = new byte[rezx * rezy * (int)in.elemSize()];
        int type;
        in.get(0, 0, data);

        if(in.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(rezx, rezy, type);

        out.getRaster().setDataElements(0, 0, rezx, rezy, data);
        return out;
    } 
 
    public static ArrayList<Rect> detection_contours(Mat outmat) {
        Mat v = new Mat();
        Mat cannyEdges = new Mat();
        Mat vv = outmat.clone();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.Canny(vv, cannyEdges, 10, 100);

        Imgproc.findContours(cannyEdges, contours, v, Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);
 
        double maxArea = 1;
        int maxAreaIdx = -1;
        Rect r = null;
        ArrayList<Rect> rect_array = new ArrayList<Rect>();
     
        for (int idx = 0; idx < contours.size(); idx++) 
        { Mat contour = contours.get(idx); 
        double contourarea = Imgproc.contourArea(contour); 
        System.out.println(" contor area:"+contourarea);
        if (contourarea > maxArea) {
                // maxArea = contourarea;
                maxAreaIdx = idx;
                r = Imgproc.boundingRect(contours.get(maxAreaIdx));
                rect_array.add(r);
                Imgproc.drawContours(imag, contours, maxAreaIdx, new Scalar(0,0, 255));
                System.out.println("contor : "+ r.x+ " "+r.y);
        }
 
        }
 
        v.release();
 
        return rect_array;
 
    }
}