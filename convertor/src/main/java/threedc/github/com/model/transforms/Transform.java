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
	public void apply(PrintableObject object);
	public void apply(Vertex vertex);
}
