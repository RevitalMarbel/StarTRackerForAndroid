package ImageProcessing;

import java.util.Vector;

import skyElement.*;
import StaticElements.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;



public class Output {

	static Vector<Vector<Double>> distances =new Vector<Vector<Double>>();
	static Vector<Vector<StarPixel>> starPixels =new Vector<Vector<StarPixel>>();
	static String outputStarFile = videoFile.StarVideoName+"stars.csv";
	static String outputDistFile = videoFile.StarVideoName+"dist.csv";
	
	 //Delimiter used in CSV file
	    private static final String COMMA_DELIMITER = ",";
	    private static final String NEW_LINE_SEPARATOR = "\n";

	
	public Output() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void add(Vector<Double> dist ,Vector<StarPixel> sPs){
		distances.add(dist);
		starPixels.add(sPs);
	}
	public static void writeCSVFiles(){
		writeCsvDist();
		writeCsvStar();
	}
	public static void writeCsvStar(){
		//write Stars:
		System.out.println("making the star pixel file at: "+videoFile.StarCoordFile);
		FileWriter fileWriter=null;
		try {
			// use FileWriter constructor that specifies open for appending
			 fileWriter = new FileWriter(videoFile.StarCoordFile);
			 //fileWriter.append("X,Y,Radius \n" );
			 for (int i = 0; i < starPixels.size(); i++) {
				 fileWriter.append(NEW_LINE_SEPARATOR);
				 //fileWriter.append("Frame"+i );
				 for (int j = 0; j < starPixels.get(i).size(); j++) {
					 fileWriter.append(NEW_LINE_SEPARATOR);
					 fileWriter.append(String.valueOf(starPixels.get(i).get(j).getX()));
					 fileWriter.append(COMMA_DELIMITER);
					 fileWriter.append(String.valueOf(starPixels.get(i).get(j).getY()));
					 fileWriter.append(COMMA_DELIMITER);
					 fileWriter.append(String.valueOf(starPixels.get(i).get(j).getRadius()));
					
				 }
			}
			
		 } catch (Exception e) {			 
			             System.out.println("Error in CsvFileWriter !!!");			 
			             e.printStackTrace();			 
			         } finally {						          			 
			             try {			 
			                 fileWriter.flush();			 
			                 fileWriter.close();			
			             } catch (IOException e) {			 
			                 System.out.println("Error while flushing/closing fileWriter !!!");			 
			                 e.printStackTrace();			 
			             }

			         }

		
	}
	public static void writeCsvDist(){
		//write Stars:
		System.out.println("making the star dists file at: "+videoFile.StarDistFile);
		FileWriter fileWriter=null;
		try {
			// use FileWriter constructor that specifies open for appending
			 fileWriter = new FileWriter(videoFile.StarDistFile);
			 fileWriter.append("Sorted Dist" );
			 for (int i = 0; i < distances.size(); i++) {
				 fileWriter.append(NEW_LINE_SEPARATOR);
				 fileWriter.append("Frame"+i );
				 for (int j = 0; j < distances.get(i).size(); j++) {
					 fileWriter.append(COMMA_DELIMITER);
					 fileWriter.append(String.valueOf(distances.get(i).get(j)));				
				 }
			}
			
		 } catch (Exception e) {			 
			             System.out.println("Error in CsvFileWriter !!!");			 
			             e.printStackTrace();			 
			         } finally {						          			 
			             try {			 
			                 fileWriter.flush();			 
			                 fileWriter.close();			
			             } catch (IOException e) {			 
			                 System.out.println("Error while flushing/closing fileWriter !!!");			 
			                 e.printStackTrace();			 
			             }

			         }
		
	}
		
}
