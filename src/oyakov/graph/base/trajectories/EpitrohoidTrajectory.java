package oyakov.graph.base.trajectories;

import oyakov.graph.base.util.ValidatorChain;

import java.util.ArrayList;
import java.util.List;

public class EpitrohoidTrajectory implements Trajectory {

    private double R = 100.0, x = 20.0, y = 20.0;

    private final List<Double> defaultArgs = new ArrayList<Double>() {{
        add(100.0);
        add(20.0);
        add(20.0);
    }};


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

    public EpitrohoidTrajectory() {
    }

    public EpitrohoidTrajectory(double R, double x, double y) {
        this.R = R;
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX(double t) {
        double m = x / R;
        return R * (m + 1) * Math.cos(m * t) - y * Math.cos((m + 1) * t) + 400.0;
    }

    @Override
    public double getY(double t) {
        double m = x / R;
        return R * (m + 1) * Math.sin(m * t) - y * Math.sin((m + 1) * t) + 400.0;
    }

    @Override
    public double getScale(double t) {
        return 2 * Math.cos(t) + 0.1;
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
        this.setX(parsedArgs.get(1));
        this.setY(parsedArgs.get(2));

        return this;
    }
}
