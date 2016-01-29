
public class Point {
	
	private int x;
	private int y;
	
	public double slopeTo(Point that) { // find slope between 2 points
        if (that == null) {
            throw new java.lang.NullPointerException();
        }
        if (this.x != that.x) {
            if (this.y != that.y) {
                return (double) (this.y - that.y)/(this.x - that.x);
            } else {
                return +0.0;
            }
        } else if (this.y != that.y) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }
	
	public String toString(){ // formatting
		return ("(" + x + "," + y + ")");
	}

	public Point(int x, int y){ // base constructor
		this.setX(x);
		this.setY(y);
	}

	public int getX() { // getters and setters
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
