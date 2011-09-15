package threedc.github.com.model;

import java.util.Vector;

import org.apache.log4j.Logger;

import threedc.github.com.model.transforms.Transform;
import threedc.github.com.util.SortedVector;
import threedc.github.com.util.VertexComparator;

/*
 * A model is an internal representation of a 3D object.
 */

public class PrintableObject implements Cloneable
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
	
	public Vector<Vertex> getVertexes()
	{
		return this.vertexes;
	}

	public Vertex addVertex(float x, float y, float z)
	{
		Vertex vertex = new Vertex(x, y, z);
		return addVertex(vertex);
	}
		
	public Vertex addVertex(Vertex vertex)
	{
		
		if (this.vertexMode == VertexMode.Sorted)
		{
			Vertex existing = ((SortedVector<Vertex>)vertexes).find(vertex);
			if (existing != null)
				vertex = existing;
			else
				vertexes.add(vertex);
		}
		else
			vertexes.add(vertex);
		
		int ordinal = vertexes.indexOf(vertex);
		vertex.setOrdinal(ordinal);

		return vertex;
	}
	
	public Vertex addVertex(float x, float y, float z, float normal)
	{
		Vertex vertex = new Vertex(x,y,z,normal);
		return addVertex(vertex);
		
	}


	public void addNormal(Vertex vertex)
	{
		normals.add(vertex);

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
	
	/**
	 * performs a deep clone of the PrintableObject.
	 * The ID is also cloned and needs to be changed when added to a model to ensure that
	 * it is unique within the target model.
	 */
	public PrintableObject clone()
	{
		
		PrintableObject clone = new PrintableObject(this.id, this.vertexMode);
		for (Vertex vertex : vertexes)
		{
			clone.addVertex(vertex.clone());
		}
		
		for (Triangle triangle : triangles)
		{
			// We need to find the corresponding vertex in the clone.
			Vertex v1 = clone.getVertex(triangle.getV1());
			Vertex v2 = clone.getVertex(triangle.getV2());
			Vertex v3 = clone.getVertex(triangle.getV3());
			Vertex normal = clone.getNormal(triangle.getNormal());
			clone.addTriangle(new Triangle(v1, v2, v3, normal));
		}
		
		for (Vertex normal : normals)
		{
			clone.addVertex(normal.clone());
		}
		
		return clone;
	}


	/** Finds the matching vertex in the list of vertexes for this object.
	 * 
	 * @param vertex
	 * @return the matching vertex
	 */
	private Vertex getVertex(Vertex vertex)
	{
		return ((SortedVector<Vertex>)vertexes).find(vertex);
	}

	/** Finds the matching vertex in the list of vertexes for this object.
	 * 
	 * @param vertex
	 * @return the matching vertex
	 */
	private Vertex getNormal(Vertex normal)
	{
		return ((SortedVector<Vertex>)normals).find(normal);
	}


	public void setID(String newID)
	{
		this.id = newID;
	}

	public void transform(Transform transform)
	{
		for (Vertex vertex : vertexes)
		{
			transform.apply(vertex);
		}

	}


}
