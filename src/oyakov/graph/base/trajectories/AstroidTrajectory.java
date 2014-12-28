package oyakov.graph.base.trajectories;

import oyakov.graph.base.util.ValidatorChain;

import java.util.ArrayList;
import java.util.List;

public class AstroidTrajectory implements Trajectory {

    private double R = 100.0, xBase = 400.0, yBase = 400.0;

    private final List<Double> defaultArgs = new ArrayList<Double>() {{
        add(100.0);
        add(400.0);
        add(400.0);
    }};

    public AstroidTrajectory() {
    }

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

    @Override
    public double getScale(double t) {
        return Math.cos(t) + 0.0001;
    }

    public void setR(double r) {
        R = r;
    }

    public void setxBase(double x) {
        this.xBase = x;
    }

    public void setyBase(double y) {
        this.yBase = y;
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
        this.setR(parsedArgs.get(0));
        this.setxBase(parsedArgs.get(1));
        this.setyBase(parsedArgs.get(2));

        return this;
    }
}
