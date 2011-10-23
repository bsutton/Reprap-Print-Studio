package threedc.github.com.model;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import threedc.github.com.model.transforms.Transform;
import threedc.github.com.util.OrdinalComparator;
import threedc.github.com.util.SortedVector;
import threedc.github.com.util.Tasker;
import threedc.github.com.util.Tasker.ACTOR_TYPE;
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
	// in other implementations (AMF) the order the vertexes are stored in is
	// critical
	// as triangles refer to the vertexes via their ordinal (position in the
	// original file).
	// It should also be noted that typically a single vertex is referenced by 6
	// triangles
	// so allowing triangles to share these vertexes will reduce the memory
	// footprint.
	SortedVector<Vertex> vertexes;
	SortedVector<Vertex> normals;

	private Vector<Volume> volumes = new Vector<Volume>();

	/**
	 * The units the object is specified in. This is inherited from the model
	 * that owns the object.
	 */
	private Units units;

	public String toString()
	{
		return "id:" + id + " mode: " + vertexMode + " vertexes:" + vertexes.size() + " volumes:" + volumes.size();
	}

	public PrintableObject(String id, VertexMode mode, Units units)
	{
		// Currently we just support a single volume per object.
		volumes.add(new Volume());
		this.id = id;
		this.vertexMode = mode;
		this.units = units;

		if (this.vertexMode == VertexMode.Sorted)
		{
			vertexes = new SortedVector<Vertex>(new VertexComparator());
			normals = new SortedVector<Vertex>(new VertexComparator());
		}
		else
		{
			vertexes = new SortedVector<Vertex>(new OrdinalComparator());
			normals = new SortedVector<Vertex>(new OrdinalComparator());
		}

	}

	public int getTriangleCount()
	{

		return volumes.get(0).getTriangles().size();
	}

	public Triangle getTriangle(int index)
	{
		return volumes.get(0).getTriangle(index);

	}

	public void addTriangle(Triangle t)
	{
		volumes.get(0).addTriangle(t);
	}

	public String getId()
	{
		return id;
	}

	public Vertex getVertex(int index)
	{
		return vertexes.get(index);
	}

	public ArrayList<Vertex> getVertexes()
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

		int index = vertexes.binarySearch(vertex);
		if (index > -1)
		{
			vertex = vertexes.get(index);
		}
		else
		{
			vertex.setOrdinal(-index - 1);
			vertexes.add(-index - 1, vertex);
		}

		return vertex;
	}

	public Vertex addVertex(float x, float y, float z, float normal)
	{
		Vertex vertex = new Vertex(x, y, z, normal);
		return addVertex(vertex);

	}

	public void addNormal(Vertex vertex)
	{
		normals.add(vertex);

	}

	public Vertex getNormal(int index)
	{
		return normals.get(index);
	}

	public void dump()
	{
		logger.debug("Object=" + this.id);
		logger.debug("Vertexes");
		int count = 0;
		for (Vertex vertex : vertexes)
		{
			logger.debug("Vertex ordinal: " + count++ + " x: " + vertex.getX() + " y: " + vertex.getY() + " z: "
					+ vertex.getZ());
		}
	}

	public int getVertexCount()
	{
		return vertexes.size();
	}

	/**
	 * performs a deep clone of the PrintableObject. The ID is also cloned and
	 * needs to be changed when added to a model to ensure that it is unique
	 * within the target model.
	 */
	public PrintableObject clone()
	{

		PrintableObject clone = new PrintableObject(this.id, this.vertexMode, this.units);
		for (Vertex vertex : vertexes)
		{
			clone.addVertex(vertex.clone());
		}

		// The object starts with a default empty volume so we need to remove it
		// before
		// we clone the first volume
		clone.volumes.removeAllElements();
		for (Volume volume : volumes)
		{
			clone.addVolume(volume.clone(clone));
		}

		for (Vertex normal : normals)
		{
			clone.addVertex(normal.clone());
		}

		return clone;
	}

	private void addVolume(Volume volume)
	{
		this.volumes.add(volume);

	}

	/**
	 * Finds the matching vertex in the list of vertexes for this object.
	 * 
	 * @param vertex
	 * @return the matching vertex
	 */
	Vertex getVertex(Vertex vertex)
	{
		return vertexes.find(vertex);
	}

	/**
	 * Finds the matching vertex in the list of vertexes for this object.
	 * 
	 * @param vertex
	 * @return the matching vertex
	 */
	Vertex getNormal(Vertex normal)
	{
		return normals.find(normal);
	}

	public void setID(String newID)
	{
		this.id = newID;
	}

	public Vector<Volume> getVolumes()
	{
		return volumes;
	}

	public void forceMaterial(Material material)
	{
		for (Volume volume : this.volumes)
		{
			volume.setMaterial(material);
		}

	}

	public Units getUnits()
	{
		return this.units;
	}

	public void applyTransforms(Transform[] transforms) throws Exception
	{

		// Allow each transform to do any last minute preparation that
		// may require knowledge of the object being transformed.
		for (Transform transform : transforms)
		{
			if (transform != null)
			{
				transform.prep(this);
				TransformActor ta = new TransformActor(transform);
				Tasker<Vertex> tasker = new Tasker<Vertex>(vertexes, ta, ACTOR_TYPE.FAST);
				tasker.run();
				if (tasker.failed())
				{
					for (Tasker<Vertex>.FailCause<Vertex> cause : tasker.getFailCauses())
					{
						throw cause.getCause();
					}
				}

			}

		}
	

	}

	public Bounds computeBoundingBox()
	{
		Bounds bounds = new Bounds();

		if (volumes.size() == 0)
		{
			bounds.setMin(new Vertex(0, 0, 0));
			bounds.setMax(new Vertex(0, 0, 0));
		}
		else
		{
			// Initialise max and min to a random vertex (it doesn't matter at
			// this stage)
			Bounds volume0 = volumes.elementAt(0).computeBoundingBox();
			bounds.setMin(volume0);
			bounds.setMax(volume0);

			for (Volume volume : volumes)
			{
				// we have already added in the bounds of the first volume so as
				// an optimisation we avoid doing it again.
				if (volume0 != null)
				{
					volume0 = null;
					continue;
				}
				bounds.updateMin(volume.computeBoundingBox());
				bounds.updateMin(volume.computeBoundingBox());
			}
		}

		return bounds;
	}

}
