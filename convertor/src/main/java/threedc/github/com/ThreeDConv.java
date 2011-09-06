package threedc.github.com;

import java.io.IOException;

public class ThreeDConv
{
	public static void main(String[] args)
	{
		if (args.length == 4)
		{
			Decoder d;
			Encoder e;
			try
			{
				if (args[0].compareToIgnoreCase("obj") == 0)
				{
					d = new ObjDecoder(args[1]);
				}
				else if (args[0].compareToIgnoreCase("stl") == 0)
				{
					d = new StlDecoder(args[1]);
				}
				else if (args[0].compareToIgnoreCase("stlb") == 0)
				{
					d = new StlbDecoder(args[1]);
				}
				else
				{
					show_usage();
					return;
				}

				if (args[2].compareToIgnoreCase("obj") == 0)
				{
					e = new ObjEncoder();
				}
				else if (args[2].compareToIgnoreCase("stl") == 0)
				{
					e = new StlEncoder();
				}
				else if (args[2].compareToIgnoreCase("stlb") == 0)
				{
					e = new StlbEncoder();
				}
				else
				{
					show_usage();
					return;
				}

				Model model = d.decode();
				System.out.println("Loaded: " + model.triangle_count() + " triangles '" + model.getDescription()
						+ "' from " + d.getFilePath());
				
				e.encode(model, args[3]);
				System.out.println("Saved: " + model.triangle_count() + " triangles from '" + model.getDescription()
						+ "' to " + e.getFilePath());

			}
			catch (IOException e1)
			{
				e1.printStackTrace();
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

}