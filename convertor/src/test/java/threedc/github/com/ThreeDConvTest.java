package threedc.github.com;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.SimpleLayout;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ThreeDConvTest
{
	static File scad;
	static File root = new File("src/test/resources");
	static FileWriter fw;
	
	@BeforeClass
	static public void setup() throws IOException
	{
		BasicConfigurator.configure();
		try
		{
			BasicConfigurator.configure(new FileAppender(new SimpleLayout(), "dump.log"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		scad = new File(root, "results.scad");
		fw = new FileWriter(scad);
		fw.write("import(\"extruder_cable_support.stl\");\n");
	}
	
	@AfterClass
	static public void tearDown() throws IOException
	{
		fw.close();
	}
	
	@Test
	public void testBroad() throws Exception
	{
		String cmdline = "-v -i src/test/resources/cube.stl -u millimeter -i src/test/resources/kwartzlab.stl -u meter -r 0:0:0 -i src/test/resources/rook.amf -u micron -t 10:10:10 -r 180:0:180 -i src/test/resources/rook.stl -o src/test/resources/result.amf -u feet";
		String[] args = splitLine(cmdline);
		
		ThreeDConv.process(args);
	}

	@Test
	public void testRotation() throws Exception
	{
		String cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -r 90:0:0 -o src/test/resources/rotationX90.stl -u millimeter";
		String[] args = splitLine(cmdline);
		ThreeDConv.process(args);
		
		fw.write("import(\"rotationX90.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -r 0:90:0 -o src/test/resources/rotationY90.stl -u millimeter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"rotationY90.stl\");\n");
		
		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -r 0:0:90 -o src/test/resources/rotationZ90.stl -u millimeter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"rotationZ90.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -r 90:90:90 -o src/test/resources/rotationXYZ90.stl -u millimeter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"rotationXYZ90.stl\");\n");
	}

	@Test
	public void testTranslation() throws Exception
	{
		String cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -t 100:0:0 -o src/test/resources/translateX10.stl -u millimeter";
		String[] args = splitLine(cmdline);
		ThreeDConv.process(args);
		
		fw.write("import(\"translateX10.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -t 0:100:0 -o src/test/resources/translateY10.stl -u millimeter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"translateY10.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -t 0:0:100 -o src/test/resources/translateZ10.stl -u millimeter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"translateZ10.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -t 200:200:200 -o src/test/resources/translateXYZ10.stl -u millimeter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"translateXYZ10.stl\");\n");
	}

	
	@Test
	public void testScale() throws Exception
	{
		String cmdline = "-i src/test/resources/extruder_cable_support.stl  -t 100:0:0 -o src/test/resources/scaleInches.stl -u inch";
		String[] args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"scaleInches.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -t 0:100:0 -o src/test/resources/scaleMeters.stl -u meter";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"scaleMeters.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -t 0:0:100 -o src/test/resources/scaleFeet.stl -u feet";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"scaleFeet.stl\");\n");

		cmdline = "-i src/test/resources/extruder_cable_support.stl -u millimeter -o src/test/resources/scaleMicrons.stl -u micron";
		args = splitLine(cmdline);
		ThreeDConv.process(args);
		fw.write("import(\"scaleMicrons.stl\");\n");
	}

	private String[] splitLine(String cmdline)
	{
		return cmdline.split("\\s+");
	}

}
