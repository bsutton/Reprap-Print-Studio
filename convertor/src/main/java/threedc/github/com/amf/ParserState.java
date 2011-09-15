package threedc.github.com.amf;

import java.util.Vector;

import org.apache.log4j.Logger;

import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Triangle;
import threedc.github.com.model.Units;

public class ParserState
{
	static Logger logger = Logger.getLogger(ParserState.class);
	
	private PrintableObject currentObject;
	private Triangle currentTriangle;
	private float x;
	private float y;
	private float z;
	private Vector<Subscriber> subscribers = new Vector<Subscriber>();

	final private ModelImpl model;

	
	public ParserState(ModelImpl model)
	{
		this.model = model;
	}

	public void startTriangle()
	{
		if (currentTriangle != null)
			throw new IllegalStateException("Triangle already under construction.");
		
		currentTriangle =  new Triangle();
	}

	public void endTriangle()
	{
		currentObject.addTriangle(currentTriangle);
		currentTriangle = null;
		
	}

	public void setCurrentV1(String currentValue)
	{
		currentTriangle.setV1(currentObject.getVertex(Integer.valueOf(currentValue)));
		
	}

	public void setCurrentV2(String currentValue)
	{
		currentTriangle.setV2(currentObject.getVertex(Integer.valueOf(currentValue)));
		
	}

	public void setCurrentV3(String currentValue)
	{
		currentTriangle.setV3(currentObject.getVertex(Integer.valueOf(currentValue)));
		
	}

	public void endVertex()
	{
		currentObject.addVertex(x,y,z);
		
	}

	public void startVertex()
	{
	}

	public void endX(float value)
	{
		x = value;
		
	}
	public void endY(float value)
	{
		y = value;
		
	}
	public void endZ(float value)
	{
		z = value;		
	}

	public PrintableObject getPrintableObject()
	{
		return currentObject;
	}

	public void subscribe(Subscriber subscriber)
	{
		subscribers.add(subscriber);
	}

	public void notifyObjectComplete(PrintableObject object)
	{
		for (Subscriber subscriber: subscribers)
		{
			subscriber.notifyObjectComplete(object);
		}
		
	}

	public void setUnits(String units)
	{
		model.setUnits(Units.valueOf(units));
	}

	public void setVersion(String version)
	{
		model.setVersion(version);
	}

	public void addPrintableObject(PrintableObject printableObject)
	{
		currentObject = printableObject;
		model.addPrintableObject(printableObject);
		
	}


}
