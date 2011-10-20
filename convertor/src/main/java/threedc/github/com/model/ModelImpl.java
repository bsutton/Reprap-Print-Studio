package threedc.github.com.model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import threedc.github.com.model.transforms.Transform;

/*
 * A model may consist of one or more PrintablObject's
 * 
 */

public class ModelImpl implements Model
{
	Vector<PrintableObject> printableObjects = new Vector<PrintableObject>();
	private Units units = Units.millimeter;
	private String version;
	private Vector<Material> materials = new Vector<Material>();

	public String toString()
	{
		return "version:" + version + " units:" + units + " objects:" + printableObjects.size() + " materials:" + materials.size();
	}
	
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
		return this.version;
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
		logger.info("Model: objects:" + printableObjects.size() + " triangles: " + getTriangleCount() + " Verticies:"
				+ this.getVertexCount());

	}

	public void setVersion(String version)
	{
		this.version = version;

	}

	/*
	 * Merges two models into a single model applying the passed transformations
	 * into the second model before merging them.
	 * 
	 * Essentially the objects of the second model will be extracted and added
	 * to that of the first model.
	 * 
	 * Limitations: current we don't do any unit conversions so both models must
	 * use the same units.
	 */

	public void merge(ModelImpl rhs, Transform[] transforms)
	{
		// Maps a set of old PrintableObject id's to the new ID's they will have
		// in the
		// merged model.
		Map<String, String> objectIDMap = new Hashtable<String, String>();

		if (!this.units.equals(rhs.units))
			throw new IllegalStateException("The units of both models must match. " + this.getUnits() + " != " + rhs.getUnits());

		for (PrintableObject object : rhs.getPrintableObjects())
		{
			PrintableObject transformedObject = (PrintableObject) object.clone();

			// We must be certain that the merged object has an id which is
			// unique
			// within our model.
			String newID = getUniqueObjectID();
			objectIDMap.put(object.getId(), newID);
			transformedObject.setID(newID);

			if (transforms != null)
				transformedObject.applyTransforms(transforms);
			this.addPrintableObject(transformedObject);
		}
	}

	/**
	 * Generates a new object id which is guaranteed to be unique.
	 * 
	 * The id will be an integer.
	 * 
	 * @return
	 */
	private String getUniqueObjectID()
	{
		int maxId = 0;
		for (PrintableObject object : printableObjects)
		{
			try
			{
				int id = new Integer(object.getId());
				maxId = Math.max(maxId, id) + 1;
			}
			catch (NumberFormatException e)
			{
				// noop - the id isn't a number so we don't need to worry about
				// it as it can't
				// conflict with our new id which will be an integer.
			}
		}
		return new Integer(maxId).toString();
	}

	public Material addMaterial(String materialName)
	{
		Material found = null;
		for (Material material : materials)
		{
			if (material.getName().compareToIgnoreCase("materialName") == 0)
			{
				found = material;
				break;
			}
		}

		if (found == null)
			found = new Material(materialName);

		this.materials.addElement(found);

		return found;

	}

	public Vector<Material> getMaterials()
	{
		return materials;
	}

	// Forces all objects to use the specified material.
	public void forceMaterial(Material material)
	{
		for (PrintableObject object : printableObjects)
		{
			object.forceMaterial(material);
		}

	}

	public Bounds computeBoundingBox()
	{
		Bounds bounds = new Bounds();

		if (printableObjects.size() == 0)
		{
			bounds.setMin(new Vertex(0, 0, 0));
			bounds.setMax(new Vertex(0, 0, 0));
		}
		else
		{
			// Initialise max and min to a random vertex (it doesn't matter at
			// this stage)
			bounds.setMin(printableObjects.elementAt(0).computeBoundingBox());
			bounds.setMax(printableObjects.elementAt(0).computeBoundingBox());

			for (PrintableObject printableObject : printableObjects)
			{
				bounds.updateMin(printableObject.computeBoundingBox());
				bounds.updateMin(printableObject.computeBoundingBox());
			}
		}

		return bounds;
	}

}
