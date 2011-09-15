package threedc.github.com.model;

//Basic object for representing a vertex in 3 dimensions
public class Vertex
{
	private float x, y, z;
	
	// The normal is specific to the AMF file format and should not be confused with a Triangle's normal.
	// The vertex normal allows an AMF file to define a curved line between two vertexes.
	private float normal;

	// The index/position this vertex is stored in the set of verticies.
	private int ordinal;

	public Vertex(float x, float y, float z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public Vertex(float x, float y, float z, float normal)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setNormal(normal);
	}

	private void setNormal(float normal)
	{
		this.normal = normal;
		
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
	
	public void setOrdinal(int ordinal)
	{
		this.ordinal = ordinal;
	}
	
	// Returns the index of this vertex in the list of vertices for the model.
	public int getOrdinal()
	{
		return this.ordinal;
	}

	public float getNormal()
	{
		return normal;
	}

	/**
	 * Creates a deep clone of the Vertex.
	 *  
	 * The ordinal is set to -1.
	 */
	public Vertex clone()
	{
		Vertex vertex = new Vertex(x,y,z, normal);
		vertex.ordinal = -1;
		return vertex;
	}


}
