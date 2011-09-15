package threedc.github.com.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class SortedVector<T> extends Vector<T>
{
	private static final long serialVersionUID = 1L;
	private Comparator<T> comparator;

	public SortedVector(Comparator<T> comp)
	{
		super();
		this.comparator = comp;

	}

	public synchronized boolean add(T o)
	{
		int index = Collections.binarySearch(this, o, this.comparator);
		if (index > -1)
			throw new RuntimeException("Duplicate Vertex");

		super.add(-index - 1, o);
		
		return true;
	}

	/*
	 * public void addElement(T o) { int index = Collections.binarySearch(this,
	 * o, this.comparator); if (index > -1) throw new
	 * RuntimeException("Duplicate Vertex");
	 * 
	 * super.add(-index - 1, o); //Collections.sort(this, this.comparator); }
	 */

	@SuppressWarnings("unchecked")
	public boolean contains(Object key)
	{
		return Collections.binarySearch(this, (T) key, this.comparator) == 0;
	}

	/**
	 * returns the matching element or null if we did not find a match.
	 * @param key
	 * @return
	 */
	public T find(T key)
	{
		int index = Collections.binarySearch(this, key, this.comparator);

		// index = -1;
		T t = null;
		if (index > -1)
			t = this.elementAt(index);

		return t;
	}
}
