package oyakov.graph.base.trajectories;

import oyakov.graph.base.Trajectory;

public class EllipseTrajectory implements Trajectory {

	private double Rx, Ry, xBase, yBase;

	public EllipseTrajectory(double rx, double ry, double x, double y) {
		Rx = rx;
		Ry = ry;
		this.xBase = x;
		this.yBase = y;
	}

	@Override
	public double getY(double t) {
		return Ry * Math.sin(t) + yBase;
	}

	@Override
	public double getX(double t) {
		return Rx * Math.cos(t) + xBase;
	}

	public void setRx(double rx) {
		Rx = rx;
	}

	public void setRy(double ry) {
		Ry = ry;
	}

	public void setxBase(double xBase) {
		this.xBase = xBase;
	}

	public void setyBase(double yBase) {
		this.yBase = yBase;
	}
}
