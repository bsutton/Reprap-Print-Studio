package threedc.github.com.util;

import java.util.Comparator;

import threedc.github.com.model.Vertex;

public class VertexComparator implements Comparator<Vertex>
{
	public int compare(Vertex lhs, Vertex rhs)
	{
		if (lhs.getX() > rhs.getX())
			return (int) (lhs.getX() - rhs.getX());

		if (lhs.getY() > rhs.getY())
			return (int) (lhs.getY() - rhs.getY());

		return (int) (lhs.getZ() - rhs.getZ());
	}

}
