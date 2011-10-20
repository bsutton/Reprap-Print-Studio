package threedc.github.com;

import java.io.File;
import java.io.IOException;

import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Units;
import threedc.github.com.model.transforms.RotationTransform;
import threedc.github.com.model.transforms.Transform;
import threedc.github.com.model.transforms.TranslationTransform;
import threedc.github.com.model.transforms.UnitTransform;
import threedc.github.com.util.Actor;
import threedc.github.com.util.FileUtility;
import threedc.github.com.util.InvalidFormatException;

class InputFile implements Actor<InputFile>
{
	private final File name;
	private TranslationTransform translationTransform;
	private RotationTransform rotationTransform;
	private String material;
	private String colour;
	private Units unit;
	
	// The model the input file will be loaded into when load() is called.
	private ModelImpl model;

	public String toString()
	{
		return "File:" + name + ", material:" + material + ", colour:" + colour + ", unit:" + unit + ", translation:" + translationTransform + " rotation:" + rotationTransform;
	}

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

	public void load(Units targetUnits) throws IOException, DecodeException, InvalidFormatException
	{
		FileType decoderFileType = FileType.valueOf(FileUtility.getExtension(this.getFile()).toUpperCase());
		Decoder decoder = decoderFileType.getDecoder(this.getFile(), this.getUnits());
		
		this.model = decoder.decode();
		this.model.addMaterial(this.getMaterial());
		Transform[] transforms = new Transform[]
		{ new UnitTransform(this.getUnits(), targetUnits), this.getTranslation(), this.getRotation() };
		
		for (PrintableObject object : this.model.getPrintableObjects())
		{
			object.applyTransforms(transforms);
		}
		
		// We have transformed the input file to the target units 
		this.setUnits(targetUnits);
		this.model.setUnits(targetUnits);
	}

	@Override
	public void prep()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(InputFile t)
	{
		// TODO Auto-generated method stub
		
	}

	public ModelImpl getModel()
	{
		return this.model;
	}
}
