package skyElement ;

import org.opencv.core.Point;

public class StarPixel {

	
	double x, y , radius;
	int name;
	
	public StarPixel(double x, double y, double radius, int name) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.name=name;
	}
	public int getName() {
		return name;
	}
	
	public StarPixel(Point p, double radius) {
		super();
		this.x = p.x;
		this.y = p.y;
		this.radius = radius;
	}
	
	
	public double distTo(StarPixel sp)
	{
		double temp= (this.x-sp.x)*(this.x-sp.x)+(this.y-sp.y)*(this.y-sp.y);
		return Math.sqrt(temp);		
	}
	
	public double distTo(Point sp)
	{
		double temp= (this.x-sp.x)*(this.x-sp.x)+(this.y-sp.y)*(this.y-sp.y);
		return Math.sqrt(temp);		
	}
	
	
	@Override
	public String toString() {
		return "StarPixel [x=" + x + ", y=" + y + ", radius=" + radius + "]";
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
