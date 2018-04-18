package loadDB;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//import org.junit.Test;

public class LoadHashTableTest {

	public static void main(String[] args) {
				
		LoadHashTable.loadStatus();
		System.out.println(LoadHashTable.getRs().size());
		System.out.println(LoadHashTable.getRs().get("197_268_330"));
	

	//	System.out.println(LoadHashTable.getRs().get("688_763_854"));
	//	System.out.println(LoadHashTable.getRs().get("635_688_754"));
	}

}
