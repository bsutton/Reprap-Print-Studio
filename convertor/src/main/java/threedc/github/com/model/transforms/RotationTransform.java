package threedc.github.com.model.transforms;

import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Vertex;

public class RotationTransform implements Transform
{
	private int xRotation;
	private int yRotation;
	private int zRotation;

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
	 * Apply this transformation to the object by modifying every vertex in the
	 * object by the defined offsets in the transform.
	 */
	public void apply(PrintableObject object)
	{
		object.transform(this);

	}

	public void apply(Vertex vertex)
	{
		float x = vertex.getX();
		float y = vertex.getY();
		float z = vertex.getZ();
		
		// intermediate storage for the co-ordinates as they go through the three axis' of rotation.
		float zA, zB, xA, xB, yA, yB;

		float sin = (float) Math.sin(Math.toRadians(this.xRotation));
		float cos = (float) Math.cos(Math.toRadians(this.xRotation));
		zA = z * cos - y * sin;
		yA = z * sin + y * cos;
		
		sin = (float) Math.sin(Math.toRadians(this.yRotation));
		cos = (float) Math.cos(Math.toRadians(this.yRotation));
		//
		 xA = x * cos - zA * sin;
		 zB = x * sin + zA * cos;

		sin = (float) Math.sin(Math.toRadians(-this.zRotation));
		cos = (float) Math.cos(Math.toRadians(-this.zRotation));
		xB = xA * cos - yA * sin;
		yB = xA * sin + yA * cos;
		
		vertex.setX(xB);
		vertex.setY(yB);
		vertex.setZ(zB);
	}

}
