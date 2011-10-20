package threedc.github.com.model;

import java.util.Vector;

public class Volume
{
	Material material = Material.DEFAULT;

	Vector<Triangle> triangles = new Vector<Triangle>();

	Bounds bounds = null;

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public Triangle getTriangle(int index)
	{
		return triangles.get(index);
	}

	public String getMaterialId()
	{
		return material.getId();
	}

	public Vector<Triangle> getTriangles()
	{
		return triangles;
	}

	public void addTriangle(Triangle t)
	{
		triangles.add(t);
		bounds = null;
	}

	public Volume clone(PrintableObject objectClone)
	{
		Volume clone = new Volume();

		for (Triangle triangle : triangles)
		{
			// We need to find the corresponding vertex in the clone.
			Vertex v1 = objectClone.getVertex(triangle.getV1());
			Vertex v2 = objectClone.getVertex(triangle.getV2());
			Vertex v3 = objectClone.getVertex(triangle.getV3());
			Vertex normal = objectClone.getNormal(triangle.getNormal());
			clone.addTriangle(new Triangle(v1, v2, v3, normal));
		}
		return clone;

	}

	/**
	 * Calculates the smallest cube shaped envelope which encapsulates all
	 * vertexes.
	 */
	public Bounds computeBoundingBox()
	{
		if (bounds == null)
		{
			bounds = new Bounds();
			if (triangles.size() == 0)
			{
				bounds.setMin(new Vertex(0, 0, 0));
				bounds.setMax(new Vertex(0, 0, 0));
			}
			else
			{

				// Initialise max and min to a random vertex (it doesn't matter
				// at
				// this stage)
				bounds.setMin(triangles.elementAt(0).getV1());
				bounds.setMax(triangles.elementAt(0).getV1());

				for (Triangle triangle : triangles)
				{
					bounds.updateMin(triangle.getV1());
					bounds.updateMin(triangle.getV2());
					bounds.updateMin(triangle.getV3());

					bounds.updateMax(triangle.getV1());
					bounds.updateMax(triangle.getV2());
					bounds.updateMax(triangle.getV3());
				}
			}
		}
		return bounds;
	}

	public String toString()
	{
		return "material:" + material + ", triangles:" + triangles.size();
	}
}
