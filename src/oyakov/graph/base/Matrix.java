package oyakov.graph.base;

import java.util.function.Function;

/*
 * Matrix representation and operations impl
 * @author: oyakov
 */
public class Matrix {
	public double[][] _values;
	public int _m;
	public int _n;

	public Matrix(int cols, int rows) {
		_values = new double[rows][cols];
		this._m = rows;
		this._n = cols;
	}

	public Matrix(Matrix proto) {
		this._values = proto._values;
		this._m = proto._m;
		this._n = proto._n;
	}

	public Matrix(double[] xpoint, double[] ypoint){
				
		if(xpoint.length != ypoint.length) 
			return;
		
		// n x 2 matrix case
		_values = new double[xpoint.length][2];
		_m = xpoint.length;
		_n = 2;		
		
		for(int i = 0; i < _m; i++){
			_values[i][0] = xpoint[i];
			_values[i][1] = ypoint[i];
		}
	}
	
	public Matrix(Double[] xpoint, Double[] ypoint){
		
		if(xpoint.length != ypoint.length) 
			return;
		
		// n x 2 matrix case
		_values = new double[xpoint.length][2];
		_m = xpoint.length;
		_n = 2;		
		
		for(int i = 0; i < _m; i++){
			_values[i][0] = xpoint[i];
			_values[i][1] = ypoint[i];
		}
	}
	
	public int[] getXPoints(double baseX){
		int[] xp = new int[_m];
		for(int i = 0; i < _m; i++){
			xp[i] = (int)(_values[i][0] + baseX);
		}
		return xp;
	}
	
	public int[] getYPoints(double baseY){
		int[] yp = new int[_m];
		for(int i = 0; i < _m; i++){
			yp[i] = (int)(_values[i][1] + baseY);
		}
		return yp;
	}

	public static Matrix getEn(int n) {
		Matrix En = new Matrix(n, n);
		for (int i = 0; i < n; i++) {
			En._values[i][i] = 1.0;
		}
		return En;
	}

	public Matrix each(Function<Double, Double> expr) {
		Matrix result = new Matrix(_n, _m);
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				result._values[i][j] = expr.apply(_values[i][j]);
			}
		}
		return result;
	}

	// Using Le Verrier - Fadeev algorithm
	public Matrix rpclSqr() {
		// Only for square matrixes
		if (this._n != this._m)
			return null;

		// Initial values
		Matrix L = Matrix.getEn(this._n);
		Matrix En = Matrix.getEn(this._n);
		int i = 1;

		// Process
		while (true) {
			// MLi
			Matrix ML = this.mul(L);
			// alpha i
			double alpha = (ML.tr() / i);
			// Li+1
			L = ML.sub(En.each(val -> val * alpha));
			if (i == _n - 1) {
				ML = this.mul(L);
				double alphan = (ML.tr() / _n);
				return L.each(val -> val / alphan);
			}
			i++;
		}
	}

	// Using Le Verrier - Fadeev algorithm
	public double detSqr() {
		if (this._n != this._m)
			return Double.MAX_VALUE;
		Matrix L = Matrix.getEn(this._n);
		Matrix En = Matrix.getEn(this._n);
		for (int i = 1;; i++) {
			// MLi
			Matrix ML = this.mul(L);
			// alpha i
			double alpha = (ML.tr() / i);
			// Li+1
			L = ML.sub(En.each(val -> val * alpha));
			if (i == _n)
				return ((_n - 1) % 2 != 0 ? -1 : 1) * alpha;
		}
	}

	public double tr() {
		double trace = 0.0;
		for (int i = 0; i < (_m > _n ? _n : _m); i++)
			trace += _values[i][i];
		return trace;
	}

	public Matrix add(Matrix another) {
		if (_m != another._m || _n != another._n) {
			return null;
		}
		Matrix sum = new Matrix(_m, _n);
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				sum._values[i][j] = _values[i][j] + another._values[i][j];
			}
		}
		return sum;
	}

	public Matrix sub(Matrix another) {
		if (_m != another._m || _n != another._n)
			return null;
		Matrix diff = new Matrix(_m, _n);
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				diff._values[i][j] = _values[i][j] - another._values[i][j];
			}
		}
		return diff;
	}

	public Matrix mul(Matrix another) {
		if (this._n != another._m)
			return null;
		Matrix product = new Matrix(another._n, _m);
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < another._n; j++) {
				for (int k = 0; k < this._n; k++) {
					product._values[i][j] += _values[i][k]
							* another._values[k][j];
				}
			}
		}
		return product;
	}

	public void print(int precision, int width) {
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				System.out.print(String.format("%1$" + width + "." + precision
						+ "f", _values[i][j]));
			}
			System.out.println();
		}
	}
	
	public static Matrix createRotationMatrix(double angle){
		Matrix res = new  Matrix(2, 2);
		res._values[0][0] = Math.cos(angle) ;
		res._values[0][1] = -Math.sin(angle) ;
		res._values[1][0] = Math.sin(angle) ;
		res._values[1][1] = Math.cos(angle) ;
		
		return res;
	}

	@Override
	public String toString() {
		String res = new String("");
		for (int i = 0; i < _m; i++) {
			for (int j = 0; j < _n; j++) {
				res += String.format("%1$6.2f", _values[i][j]);
			}
			res += "\n";
		}
		return res;
	}
}