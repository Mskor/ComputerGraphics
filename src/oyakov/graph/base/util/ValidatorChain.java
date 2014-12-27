package oyakov.graph.base.util;

import java.util.LinkedList;

public class ValidatorChain<T> {
	private LinkedList<Validator<T>> validators;
	
	public ValidatorChain(){
		validators = new LinkedList<>();
	}
	
	public ValidatorChain<T> add(Validator<T> tail){
		validators.add(tail);
		return this;
	}
	
	public void clear(){
		validators.clear();
	}
	
	public boolean test(T value){
		boolean result;
		for(Validator<T> v: validators){
			result = v.validate(value);
			if(!result)
				return false;
		}
		return true;
	}
}
