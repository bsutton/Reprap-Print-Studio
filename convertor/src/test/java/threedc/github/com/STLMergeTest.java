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

import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.transforms.RotationTransform;
import threedc.github.com.model.transforms.Transform;
import threedc.github.com.model.transforms.TranslationTransform;

public class STLMergeTest
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
	public void amfStlRoundTripTestCase()
	{
		Decoder decoder;
		Encoder encoder;

		File fromRook = new File("src/test/resources/rook.stl");
		File fromCube = new File("src/test/resources/cube.stl");
		File toAMF = new File("src/test/testResults/merged-rook-cube.amf");
		File toSTL = new File("src/test/testResults/merged-rook-cube.stl");
		File finalSTL = new File("src/test/testResults/final-rook-cube.stl");

		if (toAMF.exists())
			toAMF.delete();

		if (toSTL.exists())
			toSTL.delete();

		if (finalSTL.exists())
			finalSTL.delete();

		try
		{
			FileType decoderFileType = FileType.STL;
			decoder = decoderFileType.getDecoder(fromRook);
			ModelImpl model1 = decoder.decode();
			logger.info("Loaded model from: " + fromRook);
			model1.dump(logger, false);

			decoderFileType = FileType.STL;
			decoder = decoderFileType.getDecoder(fromCube);
			ModelImpl model2 = decoder.decode();
			logger.info("Loaded model from: " + fromCube);
			model2.dump(logger, false);

			Transform[] transforms = new Transform[]
			{ new TranslationTransform(9, 0, -1), new RotationTransform(90, 90, 90) };
			model2.merge(model1, transforms);
			model1.dump(logger, false);

			// Output an AMF version of the model
			FileType encoderFileType = FileType.AMF;
			encoder = encoderFileType.getEncoder();
			encoder.encode(model2, toAMF, false);

			// Output an STL version of the merged model
			encoderFileType = FileType.STL;
			encoder = encoderFileType.getEncoder();
			encoder.encode(model2, toSTL, false);

			// Now reload the output amf file, convert it back to an stl to
			// ensure it is identical.
			decoderFileType = FileType.AMF;
			decoder = decoderFileType.getDecoder(toAMF);
			ModelImpl model = decoder.decode();
			logger.info("Loaded model from: " + toAMF);
			model.dump(logger, false);

			encoderFileType = FileType.STL;
			encoder = encoderFileType.getEncoder();
			encoder.encode(model, finalSTL, false);

			//TestCase.assertEquals(stlTriangleCount, model.getTriangleCount());
			logger.info("Done");

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
