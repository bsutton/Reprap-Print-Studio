package threedc.github.com;

import junit.framework.Assert;

import org.junit.Test;

import threedc.github.com.CmdLineParser.OutputFile;
import threedc.github.com.model.Units;
import threedc.github.com.model.transforms.RotationTransform;
import threedc.github.com.model.transforms.TranslationTransform;
import threedc.github.com.util.ParameterException;

public class CmdLineParserTest
{
	@Test
	public void test1() throws ParameterException
	{
		String test = "-i a.stl -t 10:10:10 -r 180:0:180 -i b.stl -t 0:0:0 -r 0:0:0 -i c.st -t 10:10:10 -r 180:0:180 -o result.amf";

		parse(test);
	}

	@Test
	public void test2() throws ParameterException
	{
		String test = "-i a.stl -t 10:10:10 -i b.stl -r 0:0:0 -i c.st -t 10:10:10 -r 180:0:180 -i d.stl -o result.amf -u millimeter";

		parse(test);
		
	}

	@Test
	public void test3() throws ParameterException
	{
		String test = "-i a.stl -i b.stl -r 0:0:0 -i c.st -t 10:10:10 -r 180:0:180 -i d.stl -o result.amf -u inch";

		parse(test);
	}

	@Test
	public void test4() throws ParameterException
	{
		// this should fail
		String test = "-i a.stl -i b.stl -r 0:0:0 -i c.st -t 10:10:10 -r 180:0:180 -i d.stl -u inch -u millimeter";

		try
		{
			parse(test);
		}
		catch (ParameterException e)
		{
			Assert.assertEquals("Only one -u may be specified for each input file.", e.getMessage());
		}
	}

	@Test
	public void test5() throws ParameterException
	{
		// this should fail
		String test = "-i a.stl -u millimeter -i b.stl -u meter -r 0:0:0 -i c.st -u micron -t 10:10:10 -r 180:0:180 -i d.stl -u inch";

		try
		{
			parse(test);
		}
		catch (ParameterException e)
		{
			Assert.assertEquals("There must be one '-o' (output file) option.", e.getMessage());
		}
	}

	@Test
	public void test6() throws ParameterException
	{
		String test = "-i a.stl -u millimeter -i b.stl -u meter -r 0:0:0 -i c.stl -u micron -t 10:10:10 -r 180:0:180 -i d.amf -o fred -u feet";

		CmdLineParser clp = parse(test);
		
		OutputFile of = clp.getOutputFile();
		Assert.assertEquals(Units.feet, of.getUnits());
		Assert.assertEquals("fred", of.getFile().getName());
		
		InputFile inFile = clp.getInputFiles().elementAt(0);
		Assert.assertEquals(Units.millimeter, inFile.getUnits());
		Assert.assertEquals("a.stl", inFile.getFile().getName());
		
		inFile = clp.getInputFiles().elementAt(1);
		Assert.assertEquals(Units.meter, inFile.getUnits());
		Assert.assertEquals("b.stl", inFile.getFile().getName());
		Assert.assertEquals(new RotationTransform(0,0,0), inFile.getRotation());
		
		inFile = clp.getInputFiles().elementAt(2);
		Assert.assertEquals(Units.micron, inFile.getUnits());
		Assert.assertEquals("c.stl", inFile.getFile().getName());
		Assert.assertEquals(new TranslationTransform(10,10,10), inFile.getTranslation());
		Assert.assertEquals(new RotationTransform(180,0,180), inFile.getRotation());

		inFile = clp.getInputFiles().elementAt(3);
		Assert.assertEquals(Units.millimeter, inFile.getUnits());
		Assert.assertEquals("d.amf", inFile.getFile().getName());
		Assert.assertEquals(null, inFile.getTranslation());
		Assert.assertEquals(null, inFile.getRotation());

		
	
	}

	CmdLineParser parse(String test) throws ParameterException
	{
		CmdLineParser clp = new CmdLineParser();

		String[] args = test.split(" ");
		clp.parse(args);
		return clp;
	}
}
