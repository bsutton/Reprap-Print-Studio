package threedc.github.com.model.transforms;

import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;

public class TranslationTransform implements Transform
{

	private int xOffset;
	private int yOffset;
	private int zOffset;

	/**
	 * Applies a translation to the object by moving it by the specified amount for each axis.
	 * @param x
	 * @param y
	 * @param z
	 */
	public TranslationTransform(int xOffset, int yOffset, int zOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	/**
	 * Apply this transformation to the object by modifying every vertex in the object
	 * by the defined offsets in the transform.
	 */
	public void apply(PrintableObject object)
	{
		object.transform(this);
		
	}

	public void apply(Vertex vertex)
	{
		vertex.setX(vertex.getX() + this.xOffset);
		vertex.setY(vertex.getY() + this.yOffset);
		vertex.setZ(vertex.getZ() + this.zOffset);
		
	}

}
