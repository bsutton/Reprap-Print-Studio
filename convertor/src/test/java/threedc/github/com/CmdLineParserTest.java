package threedc.github.com;

import junit.framework.Assert;

import org.junit.Test;

import threedc.github.com.CmdLineParser.ParameterException;

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
			Assert.assertEquals("Only one -u may be specified for the output file.", e.getMessage());
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
			Assert.assertEquals("The units may only be specified after the -o option.", e.getMessage());
		}
	}

	@Test
	public void test6() throws ParameterException
	{
		// this should fail
		String test = "-i a.stl -u millimeter -i b.stl -u meter -r 0:0:0 -i c.st -u micron -t 10:10:10 -r 180:0:180 -i d.amf";

		parse(test);
	}

	void parse(String test) throws ParameterException
	{
		CmdLineParser clp = new CmdLineParser();

		String[] args = test.split(" ");
		clp.parse(args);
	}
}
