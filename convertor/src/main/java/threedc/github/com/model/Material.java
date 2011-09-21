package threedc.github.com.model;

/**
 * Defines the material an object or volume is to be printed in.
 * @author bsutton
 *
 */
public class Material
{
	static private int uniqueIDSource = 0;
	
	static Material DEFAULT = new Material("Default");
	
	private String id;
	private String name;

	public Material(String id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}

	public Material(String materialName)
	{
		this.id = Integer.toString(uniqueIDSource++);
		this.name = materialName;
	}

	public String getName()
	{
		return name;
	}

	public String getId()
	{
		return id;
	}
	
}
