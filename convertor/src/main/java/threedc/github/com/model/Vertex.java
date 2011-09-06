package threedc.github.com.model;

//Basic object for representing a vertex in 3 dimensions
public class Vertex
{
	private float x, y, z;
	
	// The normal is specific to the AMF file format and should not be confused with a Triangles normal.
	// The vertex normal allows an AMF file to define a curved line between two vertexes.
	private float normal;

	public Vertex(float x, float y, float z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public Vertex()
	{
		// TODO Auto-generated constructor stub
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getX()
	{
		return x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getY()
	{
		return y;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

	public float getZ()
	{
		return z;
	}

}
