
public class Line {
	
	private Point p1;
	private Point p2;
	private Point p3;
	
	public double slope(Line line){ // get slope easily
		return line.p1.slopeTo(line.p3);
	}
	
	public String toString(){ // formatting
		return ("(" + p1.getX() + ", " + p1.getY() + ") " + "(" + p2.getX() + ", " + p2.getY() + ") " + "(" + p3.getX() + ", " + p3.getY() + ") ");
	}

	public Line(Point p1, Point p2, Point p3){ // base constructor
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public Point getp1(){ // getters
		return this.p1;
	}
	
	public Point getp2(){
		return this.p2;
	}
	
	public Point getp3(){
		return this.p3;
	}
	
	public int getp1x(){
		return p1.getX();
	}
	
	public int getp1y(){
		return p1.getY();
	}
	
	public int getp2x(){
		return p2.getX();
	}
	
	public int getp2y(){
		return p2.getY();
	}
	
	public int getp3x(){
		return p3.getX();
	}
	
	public int getp3y(){
		return p3.getY();
	}

}
