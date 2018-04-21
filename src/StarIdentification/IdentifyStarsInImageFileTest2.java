package StarIdentification;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.Test;


public class IdentifyStarsInImageFileTest2 {

	@Test
	public void test() {
//		try {
//			CreateHashTableFromTXT.main();
//			System.out.println(CreateHashTableFromTXT.getSTARTRACK_TABLE().size());
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			IdentifyStarsInImageFile2.checkImageDists();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//IdentifyStarsInImageFileWithLyers.checkImageDists();
		
	}

}
