package threedc.github.com;

/** 
 * provides the ability to merge multiple graphics input files into a single file.
 * 
 * 3dc -i a.stl -t 10:10:10 -r 180:0:180 -i b.stl -t 0:0:0 -r 0:0:0 -i c.st -t 10:10:10 -r 180:0:180 -o result.amf
 * 
 * The above command specifies three input files a.stl, b.stl, c.st.
 * The files a.stl and c.stl are both put through a translation and a rotation before being
 * combined into the final result.amf file. b.stl has a null translation and rotation. 
 * 
 * 
 */

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import threedc.github.com.model.ModelImpl;
import threedc.github.com.model.PrintableObject;
import threedc.github.com.model.Units;
import threedc.github.com.util.FileUtility;
import threedc.github.com.util.ParameterException;
import threedc.github.com.util.Tasker;

public class ThreeDConv
{
	static Logger logger = Logger.getLogger(ThreeDConv.class);
	static String VERSION = "1.0";

	public static void main(String[] args)
	{
		initLogger();
		try
		{
			process(args);
		}
		catch (Exception e)
		{
			logger.error(e,e);
		}
	}

	static void process(String[] args) throws Exception
	{
		ThreeDConv self = new ThreeDConv();
		CmdLineParser clp = new CmdLineParser();
		try
		{
			clp.parse(args);
			if (clp.showVersion())
				System.out.println("3DC Version: " + VERSION);
			
			self.doRun(clp, args);
		}
		catch (ParameterException e)
		{
			logger.error(e);
			show_usage();
		}

	}

	void doRun(CmdLineParser cmd, String[] args) throws Exception
	{
		Encoder encoder;
		ModelImpl outputModel = new ModelImpl();
		outputModel.setUnits(cmd.getOutputFile().getUnits());
		

		try
		{
			FileLoadActor loader = new FileLoadActor(outputModel.getUnits());

			// Import each file in parallel
			Tasker<InputFile> tasker = new Tasker<InputFile>(cmd.getInputFiles(), loader, Tasker.ACTOR_TYPE.SLOW);
			tasker.run();
			if (tasker.failed())
			{
				for (Tasker<InputFile>.FailCause<InputFile> cause : tasker.getFailCauses())
				{
					System.out.println("Error loading " + cause.toString());
				}
			}
			else
			{

				// load each input file and merge it into the output model
				// applying
				// any transformations as we go.
				for (InputFile inputFile : cmd.getInputFiles())
				{
					outputModel.merge(inputFile.getModel(), null);
				}

				FileType encoderFileType;
				try
				{
					encoderFileType = FileType.valueOf(FileUtility.getExtension(cmd.getOutputFile().getFile())
							.toUpperCase());
				}
				catch (IllegalArgumentException e)
				{
					throw new ParameterException("Unknown file extension: " + cmd.getOutputFile().getFile());
				}

				encoder = encoderFileType.getEncoder();
				encoder.encode(outputModel, cmd.getOutputFile().getFile());

				System.out.println("Saved: " + outputModel.getTriangleCount() + " triangles" + " to "
						+ encoder.getFilePath());
			}
		}
		catch (IOException e)
		{
			logger.error(e, e);
		}

		// dump(outputModel);

	}

	static void show_usage()
	{
		System.out
				.println("Usage: java threedc.github.com.ThreeDConv -i <input_file>  -u <units> -t|-translate xx:yy:zz  -r|-rotate x:y:z -i <output_file> -u <units>\n"
						+ "Any number of input files may be specified.\n"
						+ "Each input file may have one or zero translates and one or zero rotates.\n"
						+ " -i <input_file> specifies an input file to be merged into the output file.\n"
						+ " -t xx:yy:zz specifies a translation in 3d space to be applied by the preceeding input file. Each component is a decimal number in the given units.\n"
						+ " -r xx:yy:zz specifies a rotation in 3d space to be applied by the preceeding input file. Each component is an decimal angle in degrees.\n"
						+ " -m 'material' specifies the name of the material to associated with the preceeding input file.\n"
						+ " -o <output_file> specifies the output file that all input files will be merged into.\n"
						+ " -u <units> that the input or output file use. If different then a conversion will be applied.\n"
						+ "    The set of supported units are: "
						+ Units.valuesAsString()
						+ " -v displays the version number."
						+ ".\n"
						+ "A single output file must be specified.");
	}

	static void dump(ModelImpl model)
	{
		for (PrintableObject object : model.getPrintableObjects())
			object.dump();
	}

	static void initLogger()
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

}
