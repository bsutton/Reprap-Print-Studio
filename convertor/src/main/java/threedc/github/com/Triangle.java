package threedc.github.com;

//Basic object for representing geometry for a triangle, with normal

public class Triangle
{
	private Vertex v1, v2, v3;
	private Vertex normal;

	Triangle(Vertex v1, Vertex v2, Vertex v3, Vertex normal)
	{
		setV1(v1);
		setV2(v2);
		setV3(v3);
		setNorm(normal);
	}


	public Vertex getNorm()
	{
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
}
