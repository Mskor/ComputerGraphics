package oyakov.graph.base.trajectories;

import oyakov.graph.base.Trajectory;

public class EpicycloidTrajectory implements Trajectory {

	private double R, x, y;
	
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

	public EpicycloidTrajectory(double R, double x, double y) {
		this.R = R;
		this.x = x;
		this.y = y;
	}
	
	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}

	@Override
	public double getX(double t) {
		return 2*R*Math.cos(t) - R*Math.cos(2*t) + x;
	}

	@Override
	public double getY(double t) {
		return 2*R*Math.sin(t) - R*Math.sin(2*t) + y;
	}

}
