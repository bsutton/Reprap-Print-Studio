package threedc.github.com;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;

import threedc.github.com.model.Material;
import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.Units;


public class AmfEncodeTest
{
	static Logger logger = Logger.getLogger(AmfEncodeTest.class);
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void amfStlRoundTripTestCase()
	{
		Decoder decoder;
		Encoder encoder;

		File from = new File("src/test/resources/rook.amf");
		File to = new File("src/test/testResults/rook.stl");
		File validation = new File("src/test/testResults/rook.amf");

		if (to.exists())
			to.delete();

		if (validation.exists())
			validation.delete();

		try
		{
			FileType decoderFileType = FileType.AMF;
			decoder = decoderFileType.getDecoder(from, Units.millimeter);
			ModelImpl model = decoder.decode();
			logger.info("Loaded model from: " + from);
			model.dump(logger, false);
			
			
			int stlTriangleCount = model.getTriangleCount();

			FileType encoderFileType = FileType.STL;
			encoder = encoderFileType.getEncoder();
			encoder.encode(model, to);
			
			// Now reload the output amf file, convert it back to an stl to ensure it is identical.
			decoderFileType = FileType.STL;
			decoder = decoderFileType.getDecoder(to, Units.millimeter);

			encoderFileType = FileType.AMF;
			encoder = encoderFileType.getEncoder();

			model = decoder.decode();
			logger.info("Loaded model from: " + to);
			model.dump(logger, false);
			
			TestCase.assertEquals(stlTriangleCount, model.getTriangleCount());

			encoder.encode(model, validation);

			// Compare the original and final file sizes
//			TestCase.assertEquals(from.length(), validation.length());
			
			decoderFileType = FileType.AMF;
			decoder = decoderFileType.getDecoder(validation, Units.millimeter);
			model = decoder.decode();
			logger.info("Loaded model from: " + validation);
			model.dump(logger, false);


		}
		catch (IOException e)
		{
			logger.error(e, e);
			TestCase.fail(e.getMessage());
		}
		catch (DecodeException e)
		{
			logger.error(e, e);
			TestCase.fail(e.getMessage());
		}

	}
	
	/**
	 * test to merge two stl files into a single AMF file
	 */
	@Test
	public void mergeTest()
	{
		Decoder decoder;
		Encoder encoder;

		File cube = new File("src/test/resources/cube.stl");
		File rook = new File("src/test/resources/rook.stl");
		File output = new File("src/test/testResults/rook-cube.amf");

		if (output.exists())
			output.delete();

		try
		{
			FileType decoderFileType = FileType.STL;
			decoder = decoderFileType.getDecoder(cube, Units.millimeter);
			ModelImpl modelCube = decoder.decode();
			Material material = modelCube.addMaterial("PLA");
			modelCube.forceMaterial(material);
			logger.info("Loaded model from: " + cube);

			decoderFileType = FileType.STL;
			decoder = decoderFileType.getDecoder(rook, Units.millimeter);
			ModelImpl modelRook = decoder.decode();
			modelRook.addMaterial("ABS");
			logger.info("Loaded model from: " + rook);
			
			// Do the merge
			modelRook.merge(modelCube, null);
			
			FileType encoderFileType = FileType.AMF;
			encoder = encoderFileType.getEncoder();
			encoder.encode(modelRook, output);
		}
		catch (IOException e)
		{
			logger.error(e, e);
			TestCase.fail(e.getMessage());
		}
		catch (DecodeException e)
		{
			logger.error(e, e);
			TestCase.fail(e.getMessage());
		}

	}


}
