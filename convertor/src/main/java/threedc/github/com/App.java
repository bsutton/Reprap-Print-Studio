package threedc.github.com;

import java.io.IOException;

/**
 * Hello world!
 * 
 */
public class App
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
		System.out.println("Usage: 3dc input_type input_file output_type output_file");
	}

	// assuming little endian
	/*
	 * float byteArrayToFloat(const char * source, int len) { int sign =
	 * source[3] & 0x80; sign >>= 7; //std::cout << "s3 " << source[3]; int exp
	 * = source[3] & 0x7F; exp <<= 1; exp += source[2] & 80; exp -= 126; int val
	 * = (source[2] & 0x7F) + 0x80; val <<= 8; val += source[1]; val <<= 8; val
	 * += source[0]; //std::cout << "sign: " << sign << " exp: " << exp <<
	 * " val: " << val << std::endl; float result = pow(-1, sign) * val / pow(2,
	 * 23) * pow(2, exp); return result; }
	 */
}