package threedc.github.com.model.transforms;

import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;
import threedc.github.com.util.ParameterException;


public class TranslationTransform implements Transform
{

	private float xOffset;
	private float yOffset;
	private float zOffset;

	public String toString()
	{
		return "xOffset:" + xOffset + ", yOffset:" + yOffset + ", zOffset:" + zOffset;
	}
	
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
	 * @throws ParameterException 
	 */
	public TranslationTransform(String args) throws ParameterException
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

	public boolean equals(Object obj)
	{
		TranslationTransform rhs = (TranslationTransform) obj;
		return this.xOffset == rhs.xOffset && this.yOffset == rhs.yOffset && this.zOffset == rhs.zOffset;
	}

}
