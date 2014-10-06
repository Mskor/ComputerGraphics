package oyakov.graph.base;

public class Point {
	public double _x;
	public double _y;
	
	public int x(){
		return (int) _x;
	}
	
	public int y(){
		return (int) _y;
	}
	
	public String toString(){
		return _x + ", " + _y;
	}
	
	public Point rotated(double angle){
		Point res = new Point();
		res._x = Math.cos(angle) * this._x - Math.sin(angle) * this._y;
		res._y = Math.sin(angle) * this._x + Math.cos(angle) * this._y;
		return res;
	}
}
