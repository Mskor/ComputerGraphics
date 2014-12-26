package oyakov.graph.base;

import java.util.LinkedList;

public class ValidatorChain<T> {
	public LinkedList<Validator<T>> validators;
	
	public ValidatorChain(){
		validators = new LinkedList<Validator<T>>();
	}
	
	public ValidatorChain<T> add(Validator<T> tail){
		validators.add(tail);
		return this;
	}
	
	public void clear(){
		validators.clear();
	}
	
	public boolean test(T value){
		boolean result = true;
		for(Validator<T> v: validators){
			result &= v.validate(value);
			if(!result)
				return result;
		}
		return result;
	}
}
