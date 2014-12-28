package oyakov.graph.base.math;

import java.util.Random;

public class Point {
	public double _x;
	public double _y;
	
	Point(){}

	public Point(double x, double y) {
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

	public Point plus(Point another) {
		Point res = new Point();
		res._x = this._x + another._x;
		res._y = this._y + another._y;
		return res;
	}
	
	public Point minus(Point another){
		Point res = new Point();
		res._x = this._x - another._x;
		res._y = this._y - another._y;
		return res;
	}

	public Point mulScalar(Double val) {
		Point res = new Point();
		res._x = this._x * val;
		res._y = this._y * val;
		return res;
	}

	public static double implicit(Point a, Point b, Point p) {
		Point pa = p.minus(a), pb = p.minus(b);
		return pa._x * pb._y - pa._y * pb._x;
	}
	
	public static boolean intersection(Point a, Point b, Point c, Point d){
		double implicit1 = implicit(a, b, c),
				implicit2 = implicit(a, b, d);
		return !(implicit1 * implicit2 == 0 || implicit1 * implicit2 > 0);
	}

	public static Point center3(Point a, Point b, Point c) {
		Point center = new Point(0.0, 0.0);

		center._x += (a._x + b._x + c._x) / 3;
		center._y += (a._y + b._y + c._y) / 3;

		return center;
	}
}
