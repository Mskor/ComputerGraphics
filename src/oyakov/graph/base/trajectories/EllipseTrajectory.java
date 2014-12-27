package oyakov.graph.base.trajectories;

import oyakov.graph.base.util.ValidatorChain;

import java.util.ArrayList;
import java.util.List;

public class EllipseTrajectory implements Trajectory {

    private double Rx = 100.0, Ry = 100.0, xBase = 400.0, yBase = 400.0;

    private final List<Double> defaultArgs = new ArrayList<Double>() {{
        add(100.0);
        add(100.0);
        add(400.0);
        add(400.0);
    }};

    public EllipseTrajectory() {
    }

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

    @Override
    public Trajectory buildAndValidate(ValidatorChain<String> argsValidator, List<String> args) {

        List<Double> parsedArgs = new ArrayList<>();

        for (int i = 0; i < args.size(); i++) {
            if (argsValidator.test(args.get(i)))
                parsedArgs.add(Double.parseDouble(args.get(i)));
            else
                parsedArgs.add(defaultArgs.get(i));
        }
        this.setRx(parsedArgs.get(0));
        this.setRy(parsedArgs.get(1));
        this.setxBase(parsedArgs.get(2));
        this.setyBase(parsedArgs.get(3));

        return this;
    }
}
