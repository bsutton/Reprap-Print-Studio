package threedc.github.com.model;

//Basic object for representing a vertex in 3 dimensions
public class Vertex
{
	private float x, y, z;

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
