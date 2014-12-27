package oyakov.graph.base.util;

import oyakov.graph.base.trajectories.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by oyakov on 27.12.2014.
 */
public class TrajectoryBuilder {
    private final ValidatorChain<String> propsValidator = new ValidatorChain<String>()
            .add((v) -> v != null && !v.isEmpty())
            .add((v) -> v.matches("^[0-9]*$"));

    private HashMap<Trajectory.TrajectoryMode, Trajectory> mapping = new HashMap<>();

    public TrajectoryBuilder() {
        initMapping();
    }

    void initMapping() {
        mapping.put(Trajectory.TrajectoryMode.ASTROID_MODE, new AstroidTrajectory());
        mapping.put(Trajectory.TrajectoryMode.ELLIPSE_MODE, new EllipseTrajectory());
        mapping.put(Trajectory.TrajectoryMode.EPITROHOID_MODE, new EpitrohoidTrajectory());
        mapping.put(Trajectory.TrajectoryMode.EPICYCLOID_MODE, new EpicycloidTrajectory());
    }

    public Trajectory build(Trajectory.TrajectoryMode mode, List<String> args) {
        return mapping.get(mode).buildAndValidate(propsValidator, args);
    }
}
