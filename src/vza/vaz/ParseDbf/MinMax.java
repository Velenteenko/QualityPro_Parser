package vza.vaz.ParseDbf;

interface MinMax<T extends Comparable<T>> {
	public T min();

	public T max();
}

class MinMaxClass<T extends Comparable<T>> implements MinMax<T>{

	private T[] vals;
	public MinMaxClass(T[] o ) {
		this.vals = o;
	}
	
	@Override
	public T min() {
		T v = vals[0];
		for (int i = 1; i < vals.length; i++) {
			if(vals[i].compareTo(v) < 0)
				v = vals[i];
		}
		return v;
	}

	@Override
	public T max() {
		T v = vals[0];
		for (int i = 1; i < vals.length; i++) {
			if(vals[i].compareTo(v) > 0)
				v = vals[i];
		}
		return v;
	}
	
}