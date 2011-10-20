package threedc.github.com;

import threedc.github.com.model.Units;
import threedc.github.com.util.Actor;

class FileLoadActor implements Actor<InputFile>
{
	private final Units units;

	FileLoadActor(Units units)
	{
		this.units = units;
	}

	@Override
	public void prep()
	{
		// no op
	}

	public void apply(InputFile inputFile) throws Exception 
	{
		inputFile.load(this.units);

	}
	
	public String toString()
	{
		return "units: " + units;
	}
};
