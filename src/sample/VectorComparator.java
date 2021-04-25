package sample;

import java.util.Comparator;

public class VectorComparator implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		Vector a = (Vector)o1;
		Vector b = (Vector)o2;
		return (int)(a.calculateMagnitude() - b.calculateMagnitude());
	}
}
