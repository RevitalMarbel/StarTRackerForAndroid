package loadDB;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class CreateStarsFileHashMapTest {

	@Test
	public void test() {
		try {
			CreateStarsFileHashMap.fileToMAp();
			System.out.println(CreateStarsFileHashMap.getSTAR_TABLE().size());
			System.out.println(CreateStarsFileHashMap.getSTAR_TABLE().get(6.0)[1]);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
