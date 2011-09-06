package threedc.github.com.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import threedc.github.com.model.Vertex;

public class SortedVector<T> extends Vector<T>
{
	private static final long serialVersionUID = 1L;
	private Comparator<T> comparator;

	public SortedVector(Comparator<T> comp)
	{
		super();
		this.comparator = comp;
	}

	public void addElement(T o)
	{
		super.addElement(o);
		Collections.sort(this, this.comparator); 
	}
	
	@SuppressWarnings("unchecked")
	public boolean contains(Object key)
	{
		return Collections.binarySearch(this, (T)key, this.comparator) == 0;
	}

	@SuppressWarnings("unchecked")
	public T find(Vertex key)
	{
		int index =  Collections.binarySearch(this, (T)key, this.comparator);
		
		T vertex = null;
		if (index != -1)
			this.elementAt(index);
		
		return vertex;
	}
}

