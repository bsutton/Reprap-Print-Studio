package threedc.github.com.model.transforms;

import threedc.github.com.model.Bounds;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;

import com.beust.jcommander.ParameterException;

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
	final private int xRotation;
	final private int yRotation;
	final private int zRotation;
	private Bounds bounds;
	private Vertex centre;

	// We cache these calculations as they are expensive and we use them for
	// every vertex.
	float xSin = (float) Math.sin(Math.toRadians(this.xRotation));
	float xCos = (float) Math.cos(Math.toRadians(this.xRotation));

	float ySin = (float) Math.sin(Math.toRadians(this.yRotation));
	float yCos = (float) Math.cos(Math.toRadians(this.yRotation));

	float zSin = (float) Math.sin(Math.toRadians(-this.zRotation));
	float zCos = (float) Math.cos(Math.toRadians(-this.zRotation));

	/**
	 * Applies a translation to the object by moving it by the specified amount
	 * for each axis.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public RotationTransform(int xRotation, int yRotation, int zRotation)
	{
		this.xRotation = xRotation;
		this.yRotation = yRotation;
		this.zRotation = zRotation;
	}

	/**
	 * takes a string of the form XXX:YYY:ZZZ where XXX, YYY and ZZZ and the
	 * corresponding rotational degrees for each axis.
	 * 
	 * @param args
	 */
	public RotationTransform(String args)
	{
		String[] degrees = args.split(":");
		if (degrees.length != 3)
			throw new ParameterException("A rotation must have three parts xx:yy:zz");

		this.xRotation = Integer.parseInt(degrees[0]);
		this.yRotation = Integer.parseInt(degrees[1]);
		this.zRotation = Integer.parseInt(degrees[2]);
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
		// Extract the co-ordinates and re-centre them around the origin
		// so we rotate the object without moving it.
		float x = vertex.getX() - this.centre.getX();
		float y = vertex.getY() - this.centre.getY();
		float z = vertex.getZ() - this.centre.getZ();

		// intermediate storage for the co-ordinates as they go through the
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
		vertex.setX(xB + this.centre.getX());
		vertex.setY(yB + this.centre.getY());
		vertex.setZ(zB + this.centre.getZ());
	}


}
