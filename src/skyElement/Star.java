package skyElement;

public class Star {

	
	double ra , dec,mag, name;
	
	public Star(double ra, double dec, double mag, double name) {
		super();
		this.ra = ra;
		this.dec = dec;
		this.mag = mag;
		this.name = name;
	}

	public Star() {
		// TODO Auto-generated constructor stub
	}

	public double getRa() {
		return ra;
	}

	public void setRa(double ra) {
		this.ra = ra;
	}

	public double getDec() {
		return dec;
	}

	public void setDec(double dec) {
		this.dec = dec;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}

	public double getName() {
		return name;
	}

	public void setName(double name) {
		this.name = name;
	}

}
