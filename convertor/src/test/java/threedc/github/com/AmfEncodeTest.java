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
			decoder = decoderFileType.getDecoder(from);

			FileType encoderFileType = FileType.STL;
			encoder = encoderFileType.getEncoder();

			ModelImpl model = decoder.decode();
			logger.info("Loaded model from: " + from);
			model.dump(logger, false);
			
			
			int stlTriangleCount = model.getTriangleCount();

			encoder.encode(model, to);
			
			// Now reload the output amf file, convert it back to an stl to ensure it is identical.
			decoderFileType = FileType.STL;
			decoder = decoderFileType.getDecoder(to);

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
			decoder = decoderFileType.getDecoder(validation);
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

}
