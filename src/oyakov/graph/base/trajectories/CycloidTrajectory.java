package oyakov.graph.base.trajectories;

import oyakov.graph.base.Trajectory;

public class CycloidTrajectory implements Trajectory {
	
	private double R,  x,  y;
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getR() {
		return R;
	}
	
	public void setR(double r) {
		R = r;
	}
	
	public CycloidTrajectory(double R, double x, double y) {
		this.R = R;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getX(double t) {
		double m = x/R;
		return R * (m + 1) * Math.cos(m*t) - y * Math.cos((m + 1) * t) + 400.0;
	}

	@Override
	public double getY(double t) {
		double m = x/R;
		return R * (m + 1) * Math.sin(m*t) - y * Math.sin((m + 1) * t) + 400.0;
	}
}
