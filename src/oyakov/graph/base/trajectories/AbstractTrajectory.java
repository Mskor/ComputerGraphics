package oyakov.graph.base.trajectories;

import oyakov.graph.base.Trajectory;
import oyakov.graph.base.Validator;
import oyakov.graph.base.ValidatorChain;

public abstract class AbstractTrajectory implements Trajectory {

	private final ValidatorChain<String> propertyValidator = new ValidatorChain<String>().add(new Validator<String>() {				
				@Override
				public Boolean validate(String value) {
					if(value == null || value == ""){
						return false;
					}
					return true;
				}
			}).add(new Validator<String>(){
				@Override
				public Boolean validate(String value) {
					return value.matches("^[0-9]*$");					
				}
			});
	
	public AbstractTrajectory(){
		
	}
}
