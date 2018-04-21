package loadDB;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//import org.junit.Test;

public class LoadHashTableTest {

	public static void main(String[] args) {
				
		LoadHashTable.loadTripletHashMap();
		LoadHashTable.loadPairsHashMap();
		LoadHashTable.loadIDHashMap();
		System.out.println(LoadHashTable.getRs().size());
		System.out.println(LoadHashTable.getRs().get("8_13_20"));
		System.out.println(LoadHashTable.getRs().get("2_4_7"));
		//System.out.println(LoadHashTable.getRs().get("8_10_11"));
		System.out.println(LoadHashTable.getRs_id().get(4));
		System.out.println(LoadHashTable.getRs_id().get(5));
		System.out.println(LoadHashTable.getRs_id().get(7));
		System.out.println(LoadHashTable.getRs_pairs().get(7));
		System.out.println(LoadHashTable.getRs_pairs().get(11));
		System.out.println(LoadHashTable.getRs_pairs().get(10));
		System.out.println(LoadHashTable.getRs_pairs().get(5));
		
	

	//	System.out.println(LoadHashTable.getRs().get("688_763_854"));
	//	System.out.println(LoadHashTable.getRs().get("635_688_754"));
	}

}
