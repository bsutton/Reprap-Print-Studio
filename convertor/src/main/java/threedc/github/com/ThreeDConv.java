package threedc.github.com;

/** 
 * provides the ability to merge multiple input files into a single amf file.
 */

import java.io.IOException;

import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;

public class ThreeDConv
{
	public static void main(String[] args)
	{
		if (args.length == 4)
		{
			Decoder decoder;
			Encoder encoder;
			try
			{
				try
				{
					FileType decoderFileType = FileType.valueOf(args[0].toUpperCase());
					decoder = decoderFileType.getDecoder(args[1]);

					FileType encoderFileType = FileType.valueOf(args[2].toUpperCase());
					encoder = encoderFileType.getEncoder();
				}
				catch (IllegalArgumentException e)
				{
					show_usage();
					return;
				}

				ModelImpl model = decoder.decode();
				System.out.println("Loaded: " + model.getTriangleCount() + " triangles" 
						+ " from " + decoder.getFilePath());

				encoder.encode(model, args[3]);
				System.out.println("Saved: " + model.getTriangleCount() + " triangles" 
						+ " to " + encoder.getFilePath());
				
				dump(model);

			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			catch (DecodeException e)
			{
				e.printStackTrace();
			}

		}
		else
		{
			show_usage();
		}

	}

	static void show_usage()
	{
		System.out.println("Error: invalid parameters");
		System.out.println("Usage: java threedc.github.com.ThreeDConv input_type input_file output_type output_file");
	}
	
	static void dump(ModelImpl model)
	{
		for (PrintableObject object : model.getPrintableObjects())
			object.dump();
	}

}
