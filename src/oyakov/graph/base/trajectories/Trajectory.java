package oyakov.graph.base.trajectories;

import oyakov.graph.base.util.ValidatorChain;

import java.util.List;

public interface Trajectory {

    public enum TrajectoryMode {
        ELLIPSE_MODE, ASTROID_MODE, EPITROHOID_MODE, EPICYCLOID_MODE, MANUAL_MODE
    }

    double getX(double t);

    double getY(double t);

    Trajectory buildAndValidate(ValidatorChain<String> argsValidator, List<String> args);
}
