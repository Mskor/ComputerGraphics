package oyakov.graph.base;

import java.util.Random;

public class Point {
	public double _x;
	public double _y;
	
	Point(){}
	Point(double x, double y){
		_x = x;
		_y = y;
	}
	public static Point getRandom(double factor){
		Random random = new Random(System.nanoTime());
		return new Point(factor * random.nextDouble(), factor * random.nextDouble());
	}
	
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
	
	public Point minus(Point another){
		Point res = new Point();
		res._x = this._x - another._x;
		res._y = this._y - another._y;
		return res;
	}
	
	public static double implicit(Point a, Point b, Point p){
		Point pa = p.minus(a), pb = p.minus(b);
		return pa._x * pb._y - pa._y * pb._x;
	}
	
	public static boolean intersection(Point a, Point b, Point c, Point d){
		double implicit1 = implicit(a, b, c),
				implicit2 = implicit(a, b, d);
		if(implicit1 * implicit2 == 0 || implicit1 * implicit2 > 0){
			// not intersected
			return false;
		} else {
			// intersected
			return true;
		}
	}
}
