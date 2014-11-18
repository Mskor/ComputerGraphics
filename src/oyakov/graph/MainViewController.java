package oyakov.graph;

/**
 * Sample Skeleton for 'MainView.fxml' Controller Class
 */

import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import oyakov.graph.base.Poly;
import oyakov.graph.base.Trajectory;
import oyakov.graph.base.trajectories.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;

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

	private Trajectory currentTrajectory;

	enum TrajectoryMode {
		ELLIPSE_MODE, ASTROID_MODE, CYCLOID_MODE, EPICYCLOID_MODE, MANUAL_MODE
	}

	TrajectoryMode mode = TrajectoryMode.MANUAL_MODE;

	@FXML
	void raisePathTracker(ActionEvent event) {
		if (isPathTracked.isSelected()) {
			path.setStrokeWidth(2.0);
		} else {
			path.setStrokeWidth(0.0);
		}
	}

	@FXML
	EventHandler<MouseEvent> handleMouseManual = e -> {
		internalPolygon.move(e.getX(), e.getY());
		currentX.setText(((Double) e.getX()).toString());
		currentY.setText(((Double) e.getY()).toString());
	};

	@FXML
	void raisePathChanged(ActionEvent e) {
		path.getElements().clear();
		if (mode == TrajectoryMode.ELLIPSE_MODE) {
			EllipseTrajectory et = (EllipseTrajectory) currentTrajectory;
			// Validate
			if ("".equals(cir_radius.getText()))
				et.setRy(100.0);
			else
				et.setRy(Double.parseDouble(cir_radius.getText()));
			if ("".equals(cir_center_y.getText()))
				et.setyBase(400.0);
			else
				et.setyBase(Double.parseDouble(cir_center_y.getText()));
			if ("".equals(cir_radius_2.getText()))
				et.setRx(100.0);
			else
				et.setRx(Double.parseDouble(cir_radius_2.getText()));
			if ("".equals(cir_center_x.getText()))
				et.setxBase(400.0);
			else
				et.setxBase(Double.parseDouble(cir_center_x.getText()));
		} else if (mode == TrajectoryMode.ASTROID_MODE) {
			AstroidTrajectory at = (AstroidTrajectory) currentTrajectory;
			if ("".equals(astr_radius.getText()))
				at.setR(100.0);
			else
				at.setR(Double.parseDouble(astr_radius.getText()));
			if ("".equals(astr_center_y.getText()))
				at.setYBase(400.0);
			else
				at.setYBase(Double.parseDouble(astr_center_y.getText()));
			if ("".equals(astr_center_x.getText()))
				at.setXBase(400.0);
			else
				at.setXBase(Double.parseDouble(astr_center_x.getText()));
		} else if(mode == TrajectoryMode.CYCLOID_MODE){
			CycloidTrajectory ct = (CycloidTrajectory) currentTrajectory;
			if("".equals(cyc_radius)){
				ct.setR(100);
			} else
				ct.setR(Double.parseDouble(cyc_radius.getText()));
			if ("".equals(cyc_x.getText()))
				ct.setX(0.2);
			else
				ct.setX(Double.parseDouble(cyc_x.getText()));
			if ("".equals(cyc_y.getText()))
				ct.setY(0.3);
			else
				ct.setY(Double.parseDouble(cyc_y.getText()));
		} else if(mode == TrajectoryMode.EPICYCLOID_MODE){
			EpicycloidTrajectory ect = (EpicycloidTrajectory) currentTrajectory;
			if("".equals(epc_radius)){
				ect.setR(100);
			} else
				ect.setR(Double.parseDouble(epc_radius.getText()));
			if ("".equals(epc_x.getText()))
				ect.setX(400.0);
			else
				ect.setX(Double.parseDouble(epc_x.getText()));
			if ("".equals(epc_y.getText()))
				ect.setY(400.0);
			else
				ect.setY(Double.parseDouble(epc_y.getText()));
		}
	};
	
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
		polygonSize.valueProperty().addListener((observableVal, old_val, new_val) -> {
			internalPolygon.scale(((Double) new_val / (Double) old_val));
		});

		// Rotation slider configuration
		polygonAngle.showTickMarksProperty().setValue(true);
		polygonAngle.showTickLabelsProperty().setValue(true);
		polygonAngle.setMin(0.0);
		polygonAngle.setMax(360.0);
		polygonAngle.valueProperty().addListener((obs_val, old_val, new_val) -> {
			internalPolygon.rotate((Math.toRadians((Double) new_val - (Double) old_val)));
		});

		// Speed slider configuration
		polygonSpeed.showTickMarksProperty().setValue(true);
		polygonSpeed.showTickLabelsProperty().setValue(true);
		polygonSpeed.setMin(1.0);
		polygonSpeed.setMax(10.0);
		polygonSpeed.valueProperty().addListener((obs_val, old_val, new_val) -> {
			internalPolygon.setSpeed((Double) new_val / 1000.0);
		});
		
		star_method.setUserData(Poly.GenerationLayout.STAR);
		regular_method.setUserData(Poly.GenerationLayout.REGULAR);
		reass_group.selectedToggleProperty().addListener((obs_val, old_val, new_val) -> {
			int n;
			if("".equals(poly_pt_number.getText()))
				n = 10;
			else
				n = Integer.parseInt(poly_pt_number.getText());
			polygonSize.setValue(1.0);
			polygonAngle.setValue(0.0);
			polygonSpeed.setValue(1.0);
			internalPolygon.reassemblePolygon((Poly.GenerationLayout)new_val.getUserData(), n);
		});

		// Trajectory switch configuration
		man_tr_rbutton.setUserData(TrajectoryMode.MANUAL_MODE);
		cir_tr_rbutton.setUserData(TrajectoryMode.ELLIPSE_MODE);
		astroid_tr_rbutton.setUserData(TrajectoryMode.ASTROID_MODE);
		ep_tr_button.setUserData(TrajectoryMode.EPICYCLOID_MODE);
		cyc_tr_button.setUserData(TrajectoryMode.CYCLOID_MODE);
		
		trGroup.selectedToggleProperty().addListener((obs_val, old_val, new_val) -> {
			mode = (TrajectoryMode) new_val.getUserData();
			double x, y;
			switch (mode) {
			case ELLIPSE_MODE:
				internalPolygon.stopFollowingTrajectory();
				// Parametric circle trajectory
				// Validate
				double Rx,
				Ry;
				if ("".equals(cir_radius.getText()))
					Ry = 100.0;
				else
					Ry = Double.parseDouble(cir_radius.getText());
				if ("".equals(cir_center_y.getText()))
					y = 400.0;
				else
					y = Double.parseDouble(cir_center_y.getText());
				if ("".equals(cir_radius_2.getText()))
					Rx = 100.0;
				else
					Rx = Double.parseDouble(cir_radius_2.getText());
				if ("".equals(cir_center_x.getText()))
					x = 400.0;
				else
					x = Double.parseDouble(cir_center_x.getText());

				currentTrajectory = new EllipseTrajectory(Rx, Ry, x, y);
				internalPolygon.startFollowingTrajectory(currentTrajectory);
				// Unwire mouse motion handler
				viewPane.setOnMouseMoved(null);
				break;

			case ASTROID_MODE:
				internalPolygon.stopFollowingTrajectory();
				// Parametric circle trajectory
				// Validate
				double R;
				if ("".equals(astr_radius.getText()))
					R = 100.0;
				else
					R = Double.parseDouble(astr_radius.getText());
				if ("".equals(astr_center_y.getText()))
					y = 400.0;
				else
					y = Double.parseDouble(cir_center_y.getText());
				if ("".equals(astr_center_x.getText()))
					x = 400.0;
				else
					x = Double.parseDouble(astr_center_x.getText());
				currentTrajectory = new AstroidTrajectory(R, x, y);
				internalPolygon.startFollowingTrajectory(currentTrajectory);
				// Unwire mouse motion handler
				viewPane.setOnMouseMoved(null);
				break;
			case CYCLOID_MODE:
				internalPolygon.stopFollowingTrajectory();
				double Rc, xc, yc;
				if ("".equals(cyc_radius.getText()))
					Rc = 100.0;
				else
					Rc = Double.parseDouble(cyc_radius.getText());
				if ("".equals(cyc_x.getText()))
					xc = 0.2;
				else
					xc = Double.parseDouble(cyc_x.getText());
				if ("".equals(cyc_y.getText()))
					yc = 0.3;
				else
					yc = Double.parseDouble(cyc_y.getText());
				currentTrajectory = new CycloidTrajectory(Rc, xc, yc);
				internalPolygon.startFollowingTrajectory(currentTrajectory);
				// Unwire mouse motion handler
				viewPane.setOnMouseMoved(null);
				break;
			case EPICYCLOID_MODE:
				internalPolygon.stopFollowingTrajectory();
				double Re, xe, ye;
				if ("".equals(epc_radius.getText()))
					Re = 100.0;
				else
					Re = Double.parseDouble(epc_radius.getText());
				if ("".equals(epc_x.getText()))
					xe = 400.0;
				else
					xe = Double.parseDouble(epc_x.getText());
				if ("".equals(astr_center_x.getText()))
					ye = 400.0;
				else
					ye = Double.parseDouble(epc_y.getText());
				currentTrajectory = new EpicycloidTrajectory(Re, xe, ye);
				internalPolygon.startFollowingTrajectory(currentTrajectory);
				// Unwire mouse motion handler
				viewPane.setOnMouseMoved(null);
				break;
			case MANUAL_MODE:
				internalPolygon.stopFollowingTrajectory();
				// Wire up mouse motion handler
				viewPane.setOnMouseMoved(handleMouseManual);
				break;
			default:
				break;
			}
		});
	}
}
