package threedc.github.com.model;

import java.util.Vector;

/*
 * A model may consist of one or more PrintablObject's
 * 
 */

public class Model
{
	Vector<PrintableObject> printableObjects = new Vector<PrintableObject>();

	public void addPrintableObject(PrintableObject object)
	{
		printableObjects.add(object);

	}

	public int getTriangleCount()
	{
		int count = 0;
		for (PrintableObject object : printableObjects)
			count += object.getTriangleCount();
		return count;
	}

	public Vector<PrintableObject> getPrintableObjects()
	{
		return printableObjects;
	}

	void computeBoundingBox(Vector<Vertex> vertices)
	{
		if (vertices.size() > 0)
		{
			Vertex minBound = vertices.elementAt(0);
			Vertex maxBound = vertices.elementAt(0);

			for (Vertex vertex : vertices)
			{
				minBound.setX(Math.min(minBound.getX(), vertex.getX()));
				minBound.setY(Math.min(minBound.getY(), vertex.getY()));
				minBound.setZ(Math.min(minBound.getZ(), vertex.getZ()));
				maxBound.setX(Math.max(maxBound.getX(), vertex.getX()));
				maxBound.setY(Math.max(maxBound.getY(), vertex.getY()));
				maxBound.setZ(Math.max(maxBound.getZ(), vertex.getZ()));
			}
		}
	}
}
