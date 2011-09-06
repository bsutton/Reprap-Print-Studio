package threedc.github.com;

import java.util.Vector;

/*
 * A model is an internal representation of a 3D object.
 */

public class Model
{
	String description;
	
	Vector<Triangle> triangles = new Vector<Triangle>();
	
	Model(String description)
	{
		this.description = description;
	}

	int triangle_count()
	{
		return triangles.size();
	}

	Triangle get_triangle(int i)
	{
		return triangles.elementAt(i);
	}

	void add_triangle(Triangle t)
	{
		triangles.add(t);
	}

	public String getDescription()
	{
		return description;
	}
}
