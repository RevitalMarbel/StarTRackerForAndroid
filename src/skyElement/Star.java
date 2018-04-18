package skyElement;

public class Star {

	
	double ra , dec,mag;
	int id;
	String name;
	
	public Star(double ra, double dec, double mag, int id,String name) {
		super();
		this.ra = ra;
		this.dec = dec;
		this.mag = mag;
		this.id = id;
		this.name=name;
	}


	public Star() {
		// TODO Auto-generated constructor stub
	}

	public double getRa() {
		return ra;
	}



	public double getDec() {
		return dec;
	}



	public double getMag() {
		return mag;
	}



	public String getName() {
		return String.valueOf(this.id);
	}

	public String getStringName() {
		return this.name;
	}


	@Override
	public String toString() {
		return "Star [ra=" + ra + ", dec=" + dec + ", mag=" + mag + ", id=" + id + ", name=" + name + "]";
	}

	

}
