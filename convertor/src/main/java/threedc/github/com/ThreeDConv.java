package threedc.github.com;

import java.io.IOException;

import com.sun.org.apache.xml.internal.serialize.Printer;

import threedc.github.com.model.Model;
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

				Model model = decoder.decode();
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
	
	static void dump(Model model)
	{
		for (PrintableObject object : model.getPrintableObjects())
			object.dump();
	}

}