package oyakov.graph;

/**
 * Sample Skeleton for 'MainView.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import oyakov.graph.base.math.Poly;
import oyakov.graph.base.trajectories.Trajectory;
import oyakov.graph.base.util.TrajectoryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainViewController {

    @FXML
    private RadioButton star_method;

    @FXML
    private RadioButton ar_method;

    @FXML
    private RadioButton regular_method;

    @FXML
    private ToggleGroup reass_group;

    @FXML
    private Polygon subjectPoly;

    @FXML
    private RadioButton cir_tr_rbutton;

    @FXML
    private Pane viewPane;

    @FXML
    private ToggleGroup trGroup;

    @FXML
    private TextField cir_center_y;

    @FXML
    private TextField epc_y;

    @FXML
    private TextField epc_x;

    @FXML
    private TextField cyc_y;

    @FXML
    private TextField cyc_x;

    @FXML
    private RadioButton man_tr_rbutton;

    @FXML
    private TextField cir_radius;

    @FXML
    private TextField cyc_radius;

    @FXML
    private TextField epc_radius;

    @FXML
    private TextField cir_radius_2;

    @FXML
    private TextField astr_center_y;

    @FXML
    private TextField astr_center_x;

    @FXML
    private TextField astr_radius;

    @FXML
    private RadioButton astroid_tr_rbutton;

    @FXML
    private RadioButton cyc_tr_button;

    @FXML
    private RadioButton ep_tr_button;

    @FXML
    private TextField cir_center_x;

    @FXML
    private Label currentX;

    @FXML
    private Label currentY;

    @FXML
    private Slider polygonSize;

    @FXML
    private Slider polygonAngle;

    @FXML
    private Slider polygonSpeed;

    @FXML
    private TextField poly_pt_number;

    @FXML
    private CheckBox isPathTracked;

    // Polygon model repository
    private Poly internalPolygon;

    private Path path;

    private final TrajectoryBuilder trajectoryBuilder = new TrajectoryBuilder();

    private Trajectory currentTrajectory;

    private Trajectory.TrajectoryMode mode = Trajectory.TrajectoryMode.MANUAL_MODE;

    @FXML
    void raisePathTracker(ActionEvent event) {
        if (isPathTracked.isSelected()) {
            path.setStrokeWidth(2.0);
        } else {
            path.setStrokeWidth(0.0);
        }
    }

    @FXML
    private final EventHandler<MouseEvent> handleMouseManual = e -> {
        internalPolygon.move(e.getX(), e.getY());
        currentX.setText(((Double) e.getX()).toString());
        currentY.setText(((Double) e.getY()).toString());
    };

    @FXML
    void raisePathChanged(ActionEvent e) {
        List<String> args = null;

        // Unwire mouse motion handler
        viewPane.setOnMouseMoved(null);
        switch (mode) {
            case ELLIPSE_MODE:
                args = new ArrayList<String>() {{
                    add(cir_radius.getText());
                    add(cir_radius_2.getText());
                    add(cir_center_x.getText());
                    add(cir_center_y.getText());
                }};
                break;
            case ASTROID_MODE:
                args = new ArrayList<String>() {{
                    add(astr_radius.getText());
                    add(astr_center_x.getText());
                    add(astr_center_y.getText());
                }};
                break;
            case EPITROHOID_MODE:
                args = new ArrayList<String>() {{
                    add(cyc_radius.getText());
                    add(cyc_x.getText());
                    add(cyc_y.getText());
                }};
                break;
            case EPICYCLOID_MODE:
                args = new ArrayList<String>() {{
                    add(epc_radius.getText());
                    add(epc_x.getText());
                    add(epc_y.getText());
                }};
                break;
            case MANUAL_MODE:
                internalPolygon.stopFollowingTrajectory();
                // Wire up mouse motion handler
                viewPane.setOnMouseMoved(handleMouseManual);
                return;
        }

        internalPolygon.stopFollowingTrajectory();
        currentTrajectory = trajectoryBuilder.build(mode, args);
        internalPolygon.startFollowingTrajectory(currentTrajectory);

    }

    @FXML
    void initialize() {
        // If any of injected properties is null -> something is wrong
        assert subjectPoly != null : "fx:id=\"subjectPoly\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_tr_rbutton != null : "fx:id=\"cir_tr_rbutton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert viewPane != null : "fx:id=\"viewPane\" was not injected: check your FXML file 'MainView.fxml'.";
        assert trGroup != null : "fx:id=\"trGroup\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_center_y != null : "fx:id=\"cir_center_y\" was not injected: check your FXML file 'MainView.fxml'.";
        assert man_tr_rbutton != null : "fx:id=\"man_tr_rbutton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_radius != null : "fx:id=\"cir_radius\" was not injected: check your FXML file 'MainView.fxml'.";
        assert cir_center_x != null : "fx:id=\"cir_center_x\" was not injected: check your FXML file 'MainView.fxml'.";
        assert polygonSize != null : "fx:id=\"polygonSize\" was not injected: check your FXML file 'MainView.fxml'.";

        // Dealing with path tracker
        path = new Path();
        path.setStrokeWidth(2.0);
        path.setStroke(Color.MAGENTA);
        viewPane.getChildren().add(path);
        internalPolygon = new Poly(subjectPoly, path);

        viewPane.setOnMouseMoved(handleMouseManual);

        // Scaling slider configuration
        polygonSize.showTickMarksProperty().setValue(true);
        polygonSize.showTickLabelsProperty().setValue(true);
        polygonSize.setMin(1.0);
        polygonSize.setMax(20.0);
        polygonSize.valueProperty().addListener((observableVal, old_val, new_val) -> internalPolygon.scale(((Double) new_val / (Double) old_val)));

        // Rotation slider configuration
        polygonAngle.showTickMarksProperty().setValue(true);
        polygonAngle.showTickLabelsProperty().setValue(true);
        polygonAngle.setMin(0.0);
        polygonAngle.setMax(360.0);
        polygonAngle.valueProperty().addListener((obs_val, old_val, new_val) -> internalPolygon.rotate((Math.toRadians((Double) new_val - (Double) old_val))));

        // Speed slider configuration
        polygonSpeed.showTickMarksProperty().setValue(true);
        polygonSpeed.showTickLabelsProperty().setValue(true);
        polygonSpeed.setMin(1.0);
        polygonSpeed.setMax(10.0);
        polygonSpeed.valueProperty().addListener((obs_val, old_val, new_val) -> internalPolygon.setSpeed((Double) new_val / 1000.0));

        star_method.setUserData(Poly.GenerationLayout.STAR);
        regular_method.setUserData(Poly.GenerationLayout.REGULAR);
        ar_method.setUserData(Poly.GenerationLayout.ABSOLUTE_RANDOM);
        reass_group.selectedToggleProperty().addListener((obs_val, old_val, new_val) -> {
            int n;
            if ("".equals(poly_pt_number.getText()))
                n = 10;
            else
                n = Integer.parseInt(poly_pt_number.getText());
            polygonSize.setValue(1.0);
            polygonAngle.setValue(0.0);
            polygonSpeed.setValue(1.0);
            internalPolygon.reassemblePolygon((Poly.GenerationLayout) new_val.getUserData(), n);
        });

        // Trajectory switch configuration
        man_tr_rbutton.setUserData(Trajectory.TrajectoryMode.MANUAL_MODE);
        cir_tr_rbutton.setUserData(Trajectory.TrajectoryMode.ELLIPSE_MODE);
        astroid_tr_rbutton.setUserData(Trajectory.TrajectoryMode.ASTROID_MODE);
        ep_tr_button.setUserData(Trajectory.TrajectoryMode.EPICYCLOID_MODE);
        cyc_tr_button.setUserData(Trajectory.TrajectoryMode.EPITROHOID_MODE);

        trGroup.selectedToggleProperty().addListener((obs_val, old_val, new_val) -> {
                    mode = (Trajectory.TrajectoryMode) new_val.getUserData();
                    List<String> args = null;

                    // Unwire mouse motion handler
                    viewPane.setOnMouseMoved(null);
                    switch (mode) {
                        case ELLIPSE_MODE:
                            args = new ArrayList<String>() {{
                                add(cir_radius.getText());
                                add(cir_radius_2.getText());
                                add(cir_center_x.getText());
                                add(cir_center_y.getText());
                            }};
                            break;
                        case ASTROID_MODE:
                            args = new ArrayList<String>() {{
                                add(astr_radius.getText());
                                add(astr_center_x.getText());
                                add(astr_center_y.getText());
                            }};
                            break;
                        case EPITROHOID_MODE:
                            args = new ArrayList<String>() {{
                                add(cyc_radius.getText());
                                add(cyc_x.getText());
                                add(cyc_y.getText());
                            }};
                            break;
                        case EPICYCLOID_MODE:
                            args = new ArrayList<String>() {{
                                add(epc_radius.getText());
                                add(epc_x.getText());
                                add(epc_y.getText());
                            }};
                            break;
                        case MANUAL_MODE:
                            internalPolygon.stopFollowingTrajectory();
                            // Wire up mouse motion handler
                            viewPane.setOnMouseMoved(handleMouseManual);
                            return;
                        default:
                            break;
                    }

                    internalPolygon.stopFollowingTrajectory();
                    currentTrajectory = trajectoryBuilder.build(mode, args);
                    internalPolygon.startFollowingTrajectory(currentTrajectory);

                }
        );
    }
}
