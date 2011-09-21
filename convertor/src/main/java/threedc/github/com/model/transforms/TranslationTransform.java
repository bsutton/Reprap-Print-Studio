package threedc.github.com.model.transforms;

import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;

import com.beust.jcommander.ParameterException;

public class TranslationTransform implements Transform
{

	private float xOffset;
	private float yOffset;
	private float zOffset;

	/**
	 * Applies a translation to the object by moving it by the specified amount for each axis.
	 * @param x
	 * @param y
	 * @param z
	 */
	public TranslationTransform(float xOffset, float yOffset, float zOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
	
	/** takes a string of the form XXX:YYY:ZZZ
	 * where XXX, YYY and ZZZ and the corresponding translation for each axis.
	 * @param args
	 */
	public TranslationTransform(String args)
	{
	    String[] degrees = args.split(":");
	    
	    if (degrees.length != 3)
	    	throw new ParameterException("A translation must have three parts xx:yy:zz");
	    
		this.xOffset = Float.parseFloat(degrees[0]);
		this.yOffset = Float.parseFloat(degrees[1]);
		this.zOffset = Float.parseFloat(degrees[2]);
	}


	/**
	 * Apply this transformation to the given vertex.
	 */
	public void apply(Vertex vertex)
	{
		vertex.setX(vertex.getX() + this.xOffset);
		vertex.setY(vertex.getY() + this.yOffset);
		vertex.setZ(vertex.getZ() + this.zOffset);
		
	}

	public void prep(PrintableObject printableObject)
	{
		// no op.
	}

}
