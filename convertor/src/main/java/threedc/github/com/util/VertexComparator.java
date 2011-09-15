package threedc.github.com.util;

import java.util.Comparator;

import threedc.github.com.model.Vertex;

public class VertexComparator implements Comparator<Vertex>
{
	public int compare(Vertex lhs, Vertex rhs)
	{
		int result = Float.compare(lhs.getX(), rhs.getX());

		if (result == 0)
			result = Float.compare(lhs.getY(), rhs.getY());

		if (result == 0)
			result = Float.compare(lhs.getZ(), rhs.getZ());
		
		if (result == 0)
			result = Float.compare(lhs.getNormal(), rhs.getNormal());
		
		return result;
	}

}
