package oyakov.graph.base.trajectories;

import oyakov.graph.base.Trajectory;

public class AstroidTrajectory implements Trajectory {

	private double R, xBase, yBase;
	
	public AstroidTrajectory(double R, double x, double y) {
		this.R = R;
		this.xBase = x;
		this.yBase = y;
	}
	
	@Override
	public double getX(double t) {		
		return R * Math.pow(Math.cos(t), 3) + xBase;
	}

	@Override
	public double getY(double t) {
		return R * Math.pow(Math.sin(t), 3) + yBase;
	}

	public void setR(double r) {
		R = r;
	}

	public void setXBase(double x) {
		this.xBase = x;
	}

	public void setYBase(double y) {
		this.yBase = y;
	}

}
