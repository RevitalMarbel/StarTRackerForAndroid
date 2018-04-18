package RaDecCalc;

import static org.junit.Assert.*;

import org.junit.Test;

public class RaDecToAngTest {

	@Test
	public void test() {
		//castro
		double ra= RaDecToAng.RaToAng(7.57666793);
		System.out.println(ra);
		double dec= RaDecToAng.DecToAng(31.531430);
		System.out.println(dec);
	}

}
