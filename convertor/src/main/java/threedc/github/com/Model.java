package threedc.github.com;

import java.util.Vector;

/*
 * A model is an internal representation of a 3D object.
 */

public class Model
{
	String description;
	
	Vector<Triangle> tris_ = new Vector<Triangle>();
	
	Model(String description)
	{
		this.description = description;
	}

	int triangle_count()
	{
		return tris_.size();
	}

	Triangle get_triangle(int i)
	{
		return tris_.elementAt(i);
	}

	void add_triangle(Triangle t)
	{
		tris_.add(t);
	}

	public String getDescription()
	{
		return description;
	}
}
