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
	Vector<Vertex> normals = new Vector<Vertex>();

	private Vector<Volume> volumes = new Vector<Volume>();

	/**
	 * The units the object is specified in. This is inherited from the model
	 * that owns the object.
	 */
	private Units units;

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
		int ordinal = -1;

		if (this.vertexMode == VertexMode.Sorted)
		{
			int index = ((SortedVector<Vertex>) vertexes).binarySearch(vertex);
			if (index > -1)
			{
				vertex = vertexes.elementAt(index);
				ordinal = index;
			}
			else
			{
				vertexes.add(-index - 1, vertex);
				ordinal = -index - 1;
			}
		}
		else
		{
			ordinal = vertexes.size();
			vertexes.add(vertex);
		}

		vertex.setOrdinal(ordinal);

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
		return normals.elementAt(index);
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
		return ((SortedVector<Vertex>) vertexes).find(vertex);
	}

	/**
	 * Finds the matching vertex in the list of vertexes for this object.
	 * 
	 * @param vertex
	 * @return the matching vertex
	 */
	Vertex getNormal(Vertex normal)
	{
		return ((SortedVector<Vertex>) normals).find(normal);
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

	public void applyTransforms(Transform[] transforms)
	{
		
		// Allow each transform to do any last minute preparation that
		// may require knowledge of the object being transformed.
		for (Transform transform : transforms)
		{
			transform.prep(this);
		}

		int processors = Runtime.getRuntime().availableProcessors();

		int count = vertexes.size();

		int allocation = count / processors;

		int first = 0; // inclusive
		int last = allocation; // exclusive
		
		Vector<Transformer> transformers = new Vector<Transformer>();
		do
		{
			Transformer t = new Transformer(vertexes, first, last, transforms);
			t.start();
			transformers.add(t);
			first = last;
			last = Math.min(last + allocation, count);

		} while (first < count);

		// wait for all of the threads to finish.
		for (Transformer transformer : transformers)
		{
			try
			{
				transformer.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}

	// Used so that we can apply transform using multiple threads.
	class Transformer extends Thread
	{
		final Vector<Vertex> vertexes;
		final int first;
		final int last;
		final Transform[] transforms;

		Transformer(Vector<Vertex> vertexes, int first, int last, Transform[] transforms)
		{
			this.vertexes = vertexes;
			this.first = first;
			this.last = last;
			this.transforms = transforms;
			this.setPriority(MIN_PRIORITY);
			this.setName("Transformer");
		}

		public void run()
		{
			// Apply the set of transforms to each vertex.
			for (Vertex vertex : vertexes.subList(first, last))
			{
				for (Transform transform : transforms)
				{
					transform.apply(vertex);
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
