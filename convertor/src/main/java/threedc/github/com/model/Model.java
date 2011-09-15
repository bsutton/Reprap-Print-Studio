package threedc.github.com.model;

import java.util.Vector;

public interface Model
{

	public void addPrintableObject(PrintableObject object);

	public int getTriangleCount();

	public int getVertexCount();

	public Vector<PrintableObject> getPrintableObjects();

	public Units getUnits();

	public void setUnits(Units units);

	public String getVersion();

	public void setVersion(String version);

}
