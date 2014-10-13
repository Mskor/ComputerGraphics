package oyakov.graph.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

// @author oyakov1994

public class Poly {
	private Matrix points;
	private Point base;
	private Polygon polygon;
	private Timer timer;
	private double speed;
	private Path p;

	public enum GenerationLayout {
		REGULAR, STAR, ABSOLUTE_RANDOM
	}

	public Poly(Polygon poly, Path path) {
		base = new Point();
		base._y = 0.0;
		base._x = 0.0;
		polygon = poly;
		ObservableList<Double> pts = poly.getPoints();
		Double xp[] = new Double[pts.size() / 2], yp[] = new Double[pts.size() / 2];
		for (int i = 0; i < pts.size(); i++) {
			if (i % 2 == 0)
				xp[i / 2] = pts.get(i);
			else
				yp[i / 2] = pts.get(i);
		}
		points = new Matrix(xp, yp);
		speed = 1.0 / 1000.0;
		p = path;
	}

	public void reassemblePolygon(GenerationLayout layout, int n) {
		if (n < 3)
			return;
		switch (layout) {
		case REGULAR:
			reassembleAsRegular(n);
			break;
		case STAR:
			reassembleAsStar(n);
			break;
		case ABSOLUTE_RANDOM:
			reassembleAsArpoly(n);
			break;
		default:
			break;
		}
	}

	private void reassembleAsRegular(int n) {
		if (n < 3)
			return;
		Point start = new Point();
		start._x = 0.0;
		start._y = 5.0;
		ObservableList<Double> pts = polygon.getPoints();
		Double xp[] = new Double[n];
		Double yp[] = new Double[n];
		pts.clear();
		for (int i = 0; i < n; i++) {
			xp[i] = start.rotated((2 * Math.PI / n) * i)._x;
			pts.add(xp[i] + base._x);
			yp[i] = start.rotated((2 * Math.PI / n) * i)._y;
			pts.add(yp[i] + base._y);
		}
		points = new Matrix(xp, yp);
	}

	private void reassembleAsArpoly(int n) {
		List<Point> edges = new ArrayList<Point>();
		boolean self_intersected = false;
		edges.add(Point.getRandom(10));
		edges.add(Point.getRandom(10));
		edges.add(Point.getRandom(10));
		for (int i = 3; i < n - 1; i++) {
			Point candidate = Point.getRandom(10);
			self_intersected = false;
			for (int j = 1; j < i - 1; j++) {
				if (!Point.intersection(edges.get(j), edges.get(j - 1), candidate, edges.get(edges.size() - 1))) {
					edges.add(candidate);
				} else {
					self_intersected = true;
				}
				if(self_intersected){
					i--;
					break;					
				}					
			}
		}		
		for (int j = 2; j < edges.size() - 1; j++) {
			if (!Point.intersection(edges.get(j), edges.get(j - 1), edges.get(edges.size() - 1), edges.get(0))) {
				continue;
			} else {
				self_intersected = true;
			}
		}
		if (self_intersected) {
			this.reassembleAsArpoly(n);
			return;
		} else {
			double[] xpoint = new double[edges.size()], ypoint = new double[edges.size()];
			for (int i = 0; i < edges.size(); i++) {
				xpoint[i] = edges.get(i)._x;
				ypoint[i] = edges.get(i)._y;
			}
			Matrix new_points = new Matrix(xpoint, ypoint);
			points = new_points;
			raisePointsChanged();
		}
	}

	private void reassembleAsStar(int n) {
		if (n < 3)
			return;
		Point start = new Point();
		Random random = new Random(System.currentTimeMillis());
		start._x = 0.0;
		start._y = 100.0 * random.nextDouble();
		ObservableList<Double> pts = polygon.getPoints();
		Double xp[] = new Double[n];
		Double yp[] = new Double[n];
		pts.clear();
		for (int i = 0; i < n; i++) {
			xp[i] = start.rotated((2 * Math.PI / n) * i)._x;
			pts.add(xp[i] + base._x);
			yp[i] = start.rotated((2 * Math.PI / n) * i)._y;
			pts.add(yp[i] + base._y);
			start._y = 100.0 * random.nextDouble();
		}
		points = new Matrix(xp, yp);
	}

	public void move(double x, double y) {
		base._x = x;
		base._y = y;
		raisePointsChanged();
	}

	public void scale(double ratio) {
		points = points.each(e -> e * ratio);
		raisePointsChanged();
	}

	public void rotate(double angle) {
		points = points.mul(Matrix.createRotationMatrix(angle));
		raisePointsChanged();
	}

	public void startFollowingTrajectory(Trajectory t) {
		timer = new Timer(true);
		p.getElements().clear();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					double newX = t.getX(System.currentTimeMillis() * speed), newY = t.getY(System.currentTimeMillis() * speed);
					move(newX, newY);
					if (p.getElements().isEmpty())
						p.getElements().add(new MoveTo(newX, newY));
					else
						p.getElements().add(new LineTo(newX, newY));
				});
			}
		}, 0l, 10l);
	}

	public void stopFollowingTrajectory() {
		if (timer == null)
			return;
		p.getElements().clear();
		timer.cancel();
		timer.purge();
	}

	private void raisePointsChanged() {
		ObservableList<Double> pts = polygon.getPoints();
		for (int i = 0; i < pts.size(); i++) {
			if (i % 2 == 0)
				pts.set(i, points._values[i / 2][i % 2] + base._x);
			else
				pts.set(i, points._values[i / 2][i % 2] + base._y);
		}
	}

	public void setSpeed(double value) {
		speed = value;
	}
}
