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
	
	

}
