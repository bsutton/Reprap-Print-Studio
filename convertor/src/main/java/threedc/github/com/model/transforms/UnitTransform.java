package threedc.github.com.model.transforms;

import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Units;
import threedc.github.com.model.Vertex;

/**
 * Converts a vertex from its existing units of measure
 * to the targetUnits of measure
 * 
 * Its important that the Unit transformation occurs before
 * other transformations as all other transformations are defined in terms
 * of the output file's units of measure.
 * 
 * @author bsutton
 *
 */
public class UnitTransform implements Transform
{
	final Units targetUnit;
	final Units existingUnit;
	final float conversionFactor;
	
	public UnitTransform(Units existingUnit, Units targetUnit)
	{
		this.existingUnit = existingUnit;
		this.targetUnit = targetUnit;
		conversionFactor = existingUnit.conversionFactor(targetUnit);
	}

	/**
	 * Apply this transformation to the given vertex.
	 */
	public void apply(Vertex vertex)
	{
		vertex.setNormal(vertex.getNormal() * this.conversionFactor);
		vertex.setX(vertex.getX() * this.conversionFactor);
		vertex.setY(vertex.getY() * this.conversionFactor);
		vertex.setZ(vertex.getZ() * this.conversionFactor);
	}
	
	public void prep(PrintableObject printableObject)
	{
		// no op.
	}


}
