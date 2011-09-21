package threedc.github.com.model.transforms;

import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;

/**
 * Provides a base for applying transformations to an object stored in a model.
 * 
 * @author bsutton
 *
 */
public interface Transform
{
	/**
	 * Apply the transformation to the given vertex.
	 */
	public void apply(Vertex vertex);

	/** 
	 * Called just before the transform is applied to allow it to do any last minute
	 * initialisation that requires up to date knowledge of the object it is applying
	 * the transform to.
	 * 
	 * @param printableObject
	 */
	public void prep(PrintableObject printableObject);
}
