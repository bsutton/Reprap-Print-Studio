package threedc.github.com.model;

//Basic object for representing geometry for a triangle, with normal

public class Triangle
{
	//The three vertexes in counter-clockwise order as viewed from outside geometry.
	private Vertex v1, v2, v3;
	
	// The normal is generally used by graphic rendering engines to determine which direction
	// the triangle is facing. Some formats (e.g. amf) do not store the normal as it can be 
	// calculated on the fly.
	// The triangle normal should not be confused with the vertex normal which is used to define a curved line.
	private Vertex normal;

	public String toString()
	{
		return "v1:" + v1 + ", v2:" + v2 + ",v3" + v3 + ", normal:" + normal;
	}
	
	public Triangle(Vertex v1, Vertex v2, Vertex v3, Vertex normal)
	{
		setV1(v1);
		setV2(v2);
		setV3(v3);
		setNormal(normal);
	}


	public Triangle()
	{
	}


	public void setV2(Vertex v2_)
	{
		this.v2 = v2_;
	}


	public Vertex getV2()
	{
		return v2;
	}


	public void setV1(Vertex v1_)
	{
		this.v1 = v1_;
	}


	public Vertex getV1()
	{
		return v1;
	}


	public void setV3(Vertex v3_)
	{
		this.v3 = v3_;
	}


	public Vertex getV3()
	{
		return v3;
	}


	public void setNormal(Vertex normal)
	{
		this.normal = normal;
	}
	
	public Vertex getNormal()
	{
		if (normal == null)
			normal = calculateNormal(v1,v2,v3);
		
		return normal;
	}

	/** 
	 * Create a deep clone of the triangle.
	 * 
	 * Note: the ordinals of each vertex within the triangle will be set to -1.
	 */
	public Triangle clone()
	{
		return new Triangle(v1.clone(), v2.clone(), v3.clone(), normal.clone());
	}
	
	
	// From http://www.linux.com/community/blogs/python-stl-model-loading-and-display-with-opengl.html
	public Vertex calculateNormal(Vertex p1, Vertex p2, Vertex p3)
	{
		Vertex a = calculateVector(p3, p2);
		Vertex b = calculateVector(p3, p1);

		return crossProduct(a, b);
	}

	public Vertex calculateVector(Vertex p1, Vertex p2)
	{
		return new Vertex(-p1.getX() + p2.getX(), -p1.getY() + p2.getY(), -p1.getZ() + p2.getZ());
	}

	// calculate the cross product 
	// returns a vector which is the cross product of the two passed vectors.
	public Vertex crossProduct(Vertex p1, Vertex p2)
	{
		return new Vertex(p1.getY() * p2.getZ() - p2.getY() * p1.getZ()
				, p1.getZ() * p2.getX() - p2.getZ() * p1.getX()
				, p1.getX() * p2.getY() - p2.getX() * p1.getY());
	}


}
