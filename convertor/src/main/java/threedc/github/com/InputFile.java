package threedc.github.com;

import java.io.File;

import threedc.github.com.model.Units;
import threedc.github.com.model.transforms.RotationTransform;
import threedc.github.com.model.transforms.Transform;
import threedc.github.com.model.transforms.TranslationTransform;

class InputFile
{
	private final File name;
	private TranslationTransform translationTransform;
	private RotationTransform rotationTransform;
	private String material;
	private String colour;
	private Units unit;

	public InputFile(String name)
	{
		this.name = new File(name);
	}

	public boolean hasTranslation()
	{
		return translationTransform != null;
	}

	public void setTranslation(TranslationTransform translation)
	{
		this.translationTransform = translation;
	}

	public void setRotation(RotationTransform rotation)
	{
		this.rotationTransform = rotation;
	}

	public File getFile()
	{
		return name;
	}

	public Transform getRotation()
	{
		return rotationTransform;
	}

	public Transform getTranslation()
	{
		return translationTransform;
	}

	public boolean hasRotation()
	{
		return rotationTransform != null;
	}

	public void setMaterial(String material)
	{
		this.material = material;

	}

	public String getMaterial()
	{
		return material;
	}

	public boolean hasMaterial()
	{
		return material != null;
	}

	public boolean hasColour()
	{
		return colour != null;
	}

	public void setColour(String colour)
	{
		this.colour = colour;

	}

	public void setUnits(Units unit)
	{
		this.unit = unit;
	}

	public boolean hasUnits()
	{
		return this.unit != null;
	}

	public Units getUnits()
	{
		return this.unit;
	}
}
