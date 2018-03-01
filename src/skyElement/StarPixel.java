package skyElement ;

public class StarPixel {

	
	double x, y , radius;
	
	
	public StarPixel(double x, double y, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	
	public double distTo(StarPixel sp)
	{
		double temp= (this.x-sp.x)*(this.x-sp.x)+(this.y-sp.y)*(this.y-sp.y);
		return Math.sqrt(temp);		
	}
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public StarPixel() {
		// TODO Auto-generated constructor stub
	}

}
