package threedc.github.com.model.transforms;

import threedc.github.com.model.Bounds;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;
import threedc.github.com.util.ParameterException;

/**
 * performs are rotation of each vertex. The rotation is intended to be done 'on
 * the spot'. As such we use the bounds of the object we are rotating to
 * calculate the geometric centre of the object and then rotate around that
 * centre.
 * 
 * @author bsutton
 * 
 */
public class RotationTransform implements Transform
{
	// The angle to rotate the object in degrees
	final private float xRotation;
	final private float yRotation;
	final private float zRotation;

	private Bounds bounds;
	private Vertex centre;

	
	// We cache these calculations as they are expensive and we use them for
	// every vertex.
	float xSin;
	float xCos;

	float ySin;
	float yCos;

	float zSin;
	float zCos;

	
	public String toString()
	{
		return "xRotation:" + xRotation + ", yRotation:" + yRotation + ", zRotation:" + zRotation;
	}

	/**
	 * Applies a translation to the object by moving it by the specified amount
	 * for each axis.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public RotationTransform(float xRotation, float yRotation, float zRotation)
	{
		this.xRotation = xRotation;
		this.yRotation = yRotation;
		this.zRotation = zRotation;
		init();
	}

	/**
	 * takes a string of the form XXX:YYY:ZZZ where XXX, YYY and ZZZ and the
	 * corresponding rotational degrees for each axis.
	 * 
	 * @param args
	 * @throws ParameterException
	 */
	public RotationTransform(String args) throws ParameterException
	{
		String[] degrees = args.split(":");
		if (degrees.length != 3)
			throw new ParameterException("A rotation must have three parts xx:yy:zz");

		this.xRotation = Float.parseFloat(degrees[0]);
		this.yRotation = Float.parseFloat(degrees[1]);
		this.zRotation = Float.parseFloat(degrees[2]);
		
		init();
	}
	
	private void init()
	{
		this.xSin = (float) Math.sin(Math.toRadians(this.xRotation));
		this.xCos = (float) Math.cos(Math.toRadians(this.xRotation));

		this.ySin = (float) Math.sin(Math.toRadians(this.yRotation));
		this.yCos = (float) Math.cos(Math.toRadians(this.yRotation));

		this.zSin = (float) Math.sin(Math.toRadians(-this.zRotation));
		this.zCos = (float) Math.cos(Math.toRadians(-this.zRotation));

	}

	public void prep(PrintableObject printableObject)
	{
		this.bounds = printableObject.computeBoundingBox();
		this.centre = bounds.calculateGeometricCentre();
	}

	/**
	 * Apply this transformation to the given vertex.
	 */
	public void apply(Vertex vertex)
	{
		// cache these values for performance.
		float xCentre = this.centre.getX();
		float yCentre = this.centre.getY();
		float zCentre = this.centre.getZ();

		// Extract the co-ordinates and re-centre them around the origin
		// so we rotate the object without moving it.
		float x = vertex.getX() - xCentre;
		float y = vertex.getY() - yCentre;
		float z = vertex.getZ() - zCentre;

		// intermediate storage for the co-ordinates as they go through the0;//
		// three axis' of rotation.
		float zA, zB, xA, xB, yA, yB;

		zA = z * xCos - y * xSin;
		yA = z * xSin + y * xCos;

		xA = x * yCos - zA * ySin;
		zB = x * ySin + zA * yCos;

		xB = xA * zCos - yA * zSin;
		yB = xA * zSin + yA * zCos;

		// Update the new co-ordinates moving them back from the origin to
		// their original position.
		vertex.setX(xB + xCentre);
		vertex.setY(yB + yCentre);
		vertex.setZ(zB + zCentre);
	}

	public boolean equals(Object obj)
	{
		RotationTransform rhs = (RotationTransform) obj;
		return this.xRotation == rhs.xRotation && this.yRotation == rhs.yRotation && this.zRotation == rhs.zRotation;
	}

}
