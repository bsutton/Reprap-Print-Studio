package threedc.github.com.model;

import java.util.Vector;

import org.apache.log4j.Logger;

import threedc.github.com.amf.YHandler;
import threedc.github.com.util.SortedVector;
import threedc.github.com.util.VertexComparator;

/*
 * A model is an internal representation of a 3D object.
 */

public class PrintableObject
{
	static Logger logger = Logger.getLogger(PrintableObject.class);
	
	String id; // Model wide unique identifier for this PrintableObject

	public enum VertexMode
	{
		Ordered, Sorted
	};

	private VertexMode vertexMode;

	// Vertex's in printable objects need to be handled carefully.
	// In some implementations they are just a set of un-ordered vertexes (STL)
	// in other implementations the order the vertexes are stored in is critical
	// as triangles refer to the vertexes via their ordinal (position in the
	// original file).
	// It should also be noted that typically a single vertex is referenced by 6
	// triangles
	// so allowing triangles to share these vertexes will reduce the memory
	// footprint.
	Vector<Vertex> vertexes = new Vector<Vertex>();
	Vector<Triangle> triangles = new Vector<Triangle>();
	Vector<Vertex> normals = new Vector<Vertex>();

	public PrintableObject(String id, VertexMode mode)
	{
		this.id = id;
		this.vertexMode = mode;

		if (this.vertexMode == VertexMode.Sorted)
		{
			vertexes = new SortedVector<Vertex>(new VertexComparator());
			normals = new SortedVector<Vertex>(new VertexComparator());
		}

	}

	public int getTriangleCount()
	{
		return triangles.size();
	}

	public Triangle getTriangle(int index)
	{
		return triangles.elementAt(index);
	}

	public void addTriangle(Triangle t)
	{
		triangles.add(t);
	}

	public String getId()
	{
		return id;
	}

	public Vertex getVertex(int index)
	{
		return vertexes.elementAt(index);
	}

	public Vertex addVertex(float x, float y, float z)
	{
		Vertex vertex = new Vertex(x, y, z);
		if (this.vertexMode == VertexMode.Sorted)
		{
			if (vertexes.contains(vertex))
				vertex = ((SortedVector<Vertex>)vertexes).find(vertex);
			else
				vertexes.add(vertex);
		}
		else
			vertexes.add(vertex);

		return vertex;
	}

	public void addNormal(Vertex index)
	{
		normals.add(index);

	}

	public Vertex getNormal(int index)
	{
		return normals.elementAt(index);
	}

	public void dump()
	{
		logger.debug("Object=" + this.id);
		logger.debug("Vertexes");
		int count = 0;
		for (Vertex vertex : vertexes)
		{
			logger.debug("Vertex ordinal: " + count++ + " x: " + vertex.getX() + " y: " + vertex.getY() + " z: " + vertex.getZ());
		}
	}

	public int getVertexCount()
	{
		return vertexes.size();
	}
}
