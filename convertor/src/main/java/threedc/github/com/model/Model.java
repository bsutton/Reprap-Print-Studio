package threedc.github.com.model;

import java.util.Vector;

import org.apache.log4j.Logger;

/*
 * A model may consist of one or more PrintablObject's
 * 
 */

public class Model
{
	Vector<PrintableObject> printableObjects = new Vector<PrintableObject>();
	private Units units = Units.millimeter;

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

	public int getVertexCount()
	{
		int count = 0;
		for (PrintableObject object : printableObjects)
			count += object.getVertexCount();
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

	public Units getUnits()
	{
		return this.units;
	}

	public void setUnits(Units units)
	{
		this.units = units;
	}

	public String getVersion()
	{
		return "1.0";
	}

	public void dump(Logger logger, boolean dumpVectors)
	{
		if (dumpVectors)
		{
			for (PrintableObject object : this.getPrintableObjects())
			{
				for (Vertex vertex : object.getVertexes())
				{
					logger.info(vertex.getOrdinal() + "," + vertex.getX() + "," + vertex.getY() + "," + vertex.getZ());
				}
			}
		}
		logger.info("Model: object:" + printableObjects.size() + " triangles: " + getTriangleCount() + " Verticies:"
				+ this.getVertexCount());

	}
}
