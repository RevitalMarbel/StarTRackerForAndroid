package RaDecCalc;

public class RaDecToAng {

	public RaDecToAng() {
		// TODO Auto-generated constructor stub
	}
	
	public static double RaToAng(double ra)
	{
		double newRA;
		double hours =(int) ra;
		double second = (int)((ra-hours)*100);
		double miliseconds= (((ra-hours)*100)-second)*10000;
		//System.out.println("hours "+ hours +" second "+second+ " miliseconds "+miliseconds );
		return 15*(hours+second/60+miliseconds/3600);
	}
	
	public static double DecToAng(double dec)
	{
		double newRA;
		double hours =(int) dec;
		double second = (int)((dec-hours)*100);
		double miliseconds= (((dec-hours)*100)-second)*10000;
		//System.out.println("hours "+ hours +" second "+second+ " miliseconds "+miliseconds );
		return hours+second/60+miliseconds/3600;
	}
	public static double AngToRad(double ang){
		return Math.toRadians(ang);
	}
	public static double RadToDeg(double rad){
		return Math.toDegrees(rad);
	}

}
