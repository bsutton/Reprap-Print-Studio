package threedc.github.com;

//Basic object for representing geometry for a triangle, with normal

public class Triangle
{
	private Vertex v1_, v2_, v3_;
	private Vertex norm_;

	Triangle(Vertex v1, Vertex v2, Vertex v3, Vertex normal)
	{
		setV1(v1);
		setV2(v2);
		setV3(v3);
		setNorm(normal);
	}


	public Vertex getNorm()
	{
		return norm_;
	}


	public void setV2(Vertex v2_)
	{
		this.v2_ = v2_;
	}


	public Vertex getV2()
	{
		return v2_;
	}


	public void setV1(Vertex v1_)
	{
		this.v1_ = v1_;
	}


	public Vertex getV1()
	{
		return v1_;
	}


	public void setV3(Vertex v3_)
	{
		this.v3_ = v3_;
	}


	public Vertex getV3()
	{
		return v3_;
	}


	public void setNorm(Vertex norm_)
	{
		this.norm_ = norm_;
	}
}
