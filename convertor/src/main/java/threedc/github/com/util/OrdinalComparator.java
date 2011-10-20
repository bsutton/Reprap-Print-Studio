package threedc.github.com.util;

import java.util.Comparator;

import threedc.github.com.model.Vertex;

public class OrdinalComparator implements Comparator<Vertex>
{
	public int compare(Vertex lhs, Vertex rhs)
	{
		// An ordinal of -1 does not match any other ordinal
		// we use this to insert new Vertexes which won't get an ordinal
		// until after they have been inserted.
		if (lhs.getOrdinal() == -1 || rhs.getOrdinal() == -1)
			return -1;
		else
			return lhs.getOrdinal() - rhs.getOrdinal();

	}

}
