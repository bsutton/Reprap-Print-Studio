package threedc.github.com.model;

//Basic object for representing geometry for a triangle, with normal

public class Triangle
{
	private Vertex v1, v2, v3;
	private Vertex normal;

	public Triangle(Vertex v1, Vertex v2, Vertex v3, Vertex normal)
	{
		setV1(v1);
		setV2(v2);
		setV3(v3);
		setNorm(normal);
	}


	public Triangle()
	{
	}


	public Vertex getNorm()
	{
		if (normal == null)
			//normal = calculateNormal(v1,v2,v3);
			normal = new Vertex(0,0,0);
		
		return normal;
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


	public void setNorm(Vertex norm_)
	{
		this.normal = norm_;
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

	// calculate the cross product returns a vector
	public Vertex crossProduct(Vertex p1, Vertex p2)
	{
		return new Vertex(p1.getY() * p2.getZ() - p2.getY() * p1.getZ()
				, p1.getZ() * p2.getX() - p2.getZ() * p1.getX()
				, p1.getX() * p2.getY() - p2.getX() * p1.getY());
	}

}
