package threedc.github.com.model;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;

import threedc.github.com.STLMergeTest;
import threedc.github.com.model.PrintableObject.VertexMode;
import threedc.github.com.model.transforms.RotationTransform;
import threedc.github.com.model.transforms.Transform;
import threedc.github.com.model.transforms.UnitTransform;

public class PrintableObjectTest
{
	static Logger logger = Logger.getLogger(STLMergeTest.class);

	@Before
	public void setUp()
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
	}

	@Test
	public void unitTransformTest()
	{
		PrintableObject po = new PrintableObject("1", VertexMode.Ordered, Units.feet);
		int count = 10000000;
		for (int i = 0; i < count; i++)
		{
			po.addVertex(new Vertex(1, 1, 1));
		}

		Transform[] transforms = new Transform[]
		{ new UnitTransform(Units.feet, Units.inch) };
		po.applyTransforms(transforms);

		for (Vertex vertex : po.getVertexes())
		{
			Assert.assertEquals(vertex.getX(), 12.0F);
			Assert.assertEquals(vertex.getY(), 12.0F);
			Assert.assertEquals(vertex.getZ(), 12.0F);
		}
		Assert.assertEquals(po.getVertexCount(), count);

		po = new PrintableObject("1", VertexMode.Sorted, Units.feet);
		for (int i = 0; i < count; i++)
		{
			po.addVertex(new Vertex(i, i, i));
		}

		transforms = new Transform[]
		{ new UnitTransform(Units.feet, Units.inch) };
		po.applyTransforms(transforms);

		float conversion = UnitConversion.getUnitConversion(Units.feet, Units.inch).conversionFactor();
		float i = 0;
		for (Vertex vertex : po.getVertexes())
		{
			Assert.assertEquals(vertex.getX(), i * conversion);
			Assert.assertEquals(vertex.getY(), i * conversion);
			Assert.assertEquals(vertex.getZ(), i * conversion);
			i++;
		}
		Assert.assertEquals(po.getVertexCount(), count);

	}

	@Test
	public void rotationTransformTest()
	{
		PrintableObject po = new PrintableObject("1", VertexMode.Ordered, Units.millimeter);
		int count = 10000;
		po.addVertex(new Vertex(0, 0, 0));
		for (int i = 0; i < count; i++)
		{
			po.addVertex(new Vertex(1, 1, 1));
		}

		Transform[] transforms = new Transform[]
		{ new RotationTransform(90, 0, 0) };
		po.applyTransforms(transforms);

		int i = 0;
		for (Vertex vertex : po.getVertexes())
		{
			if (i == 0)
			{
				Assert.assertEquals(0.0F, vertex.getX());
				Assert.assertEquals(0.0F, vertex.getY());
				Assert.assertEquals(0.0F, vertex.getZ());
			}
			else
			{
				Assert.assertEquals(1.0F, vertex.getX());
				Assert.assertEquals(1.0F, vertex.getY());
				Assert.assertEquals(0.0F, vertex.getZ());
			}
			i++;
		}
		Assert.assertEquals(po.getVertexCount(), count);

		po = new PrintableObject("1", VertexMode.Sorted, Units.millimeter);
		// We need to insert at least one different vertex otherwise we are
		// dealing with a point
		// and a point rotated on the spot looks just the same after it gets
		// rotated.
		po.addVertex(new Vertex(1, 1, 1));

		for (i = 0; i < count; i++)
		{
			po.addVertex(new Vertex(2, 2, 2));
		}

		transforms = new Transform[]
		{ new RotationTransform(180, 180, 180) };
		po.applyTransforms(transforms);

		i = 0;
		for (Vertex vertex : po.getVertexes())
		{
			if (i == 0)
			{
				Assert.assertEquals(vertex.getX(), 1);
				Assert.assertEquals(vertex.getY(), 1);
				Assert.assertEquals(vertex.getZ(), 1);
			}
			else
			{

				Assert.assertEquals(vertex.getX(), -1);
				Assert.assertEquals(vertex.getY(), 0);
				Assert.assertEquals(vertex.getZ(), -1);
			}
			i++;
		}
		Assert.assertEquals(po.getVertexCount(), count);

	}

}
