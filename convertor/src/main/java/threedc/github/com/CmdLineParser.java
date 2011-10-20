package threedc.github.com;

import java.io.File;
import java.util.Vector;

import threedc.github.com.model.Units;
import threedc.github.com.model.transforms.RotationTransform;
import threedc.github.com.model.transforms.TranslationTransform;
import threedc.github.com.util.ParameterException;

/**
 * Parses the command line and sets up the options
 * 
 * @author bsutton
 * 
 */
public class CmdLineParser
{
	private Vector<InputFile> inputFiles = new Vector<InputFile>();
	private OutputFile outputFile = null;
	private boolean showVersion = false;

	class OutputFile
	{
		private final File name;
		private Units unit;

		OutputFile(File name)
		{
			this.name = name;
		}

		public File getFile()
		{
			return name;
		}

		public void setUnits(Units unit)
		{
			this.unit = unit;

		}

		public Units getUnits()
		{
			return this.unit;
		}
	}

	// 3dc -i a.stl -t 10:10:10 -r 180:0:180 -i b.stl -t 0:0:0 -r 0:0:0 -i c.st
	// -t 10:10:10 -r 180:0:180 -o result.amf
	public void parse(String args[]) throws ParameterException
	{
		InputFile inputFile = null;

		for (int index = 0; index < args.length; index++)
		{
			String arg = args[index];

			if (arg.compareTo("-c") == 0 || arg.compareTo("-colour") == 0)
			{
				if (inputFile == null)
					throw new ParameterException("A -c option may only appear after a -i option.");
				if (inputFile.hasColour())
					throw new ParameterException("An input file may only have one -c option.");

				if (args.length < index + 1)
					throw new ParameterException("Missing rotation for -m parameter at argument " + index + ".");
				inputFile.setColour(args[index + 1]);
				index++;
			}
			else if (arg.compareTo("-m") == 0 || arg.compareTo("-material") == 0)
			{
				if (inputFile == null)
					throw new ParameterException("A -m option may only appear after a -i option.");
				if (inputFile.hasMaterial())
					throw new ParameterException("An input file may only have one -m option.");

				if (args.length < index + 1)
					throw new ParameterException("Missing rotation for -m parameter at argument " + index + ".");
				inputFile.setMaterial(args[index + 1]);
				index++;
			}

			else if (arg.compareTo("-i") == 0 || arg.compareTo("-input") == 0)
			{
				if (outputFile != null)
					throw new ParameterException("Input files must be specified before the output file.");

				// First check if we are already processing a file. If so we
				// have now finished that file
				// and can add it to the list of input files.
				if (inputFile != null)
				{
					if (inputFile.getUnits() == null)
						inputFile.setUnits(Units.millimeter); // set default
																// units.
					inputFiles.add(inputFile);
					inputFile = null;
				}
				if (args.length < index + 1)
					throw new ParameterException("Missing filename for -i parameter at argument " + index + ".");

				inputFile = new InputFile(args[index + 1]);
				index++;
			}

			else if (arg.compareTo("-o") == 0 || arg.compareTo("-output") == 0)
			{
				// First check if we are already processing a file. If so we
				// have now finished that file
				// and can add it to the list of input files.
				if (inputFile != null)
				{
					if (inputFile.getUnits() == null)
						inputFile.setUnits(Units.millimeter); // set default
																// units.

					inputFiles.add(inputFile);
					inputFile = null;
				}

				// Check that we don't already have an output file
				if (outputFile != null)
					throw new ParameterException("Only one -o option is allowed");

				if (args.length < index + 1)
					throw new ParameterException("Missing filename for -o parameter at argument " + index + ".");

				outputFile = new OutputFile(new File(args[index + 1]));
				index++;
			}
			else if (arg.compareTo("-r") == 0 || arg.compareTo("-rotation") == 0)
			{
				if (inputFile == null)
					throw new ParameterException("A -r option may only appear after a -i option.");
				if (inputFile.hasRotation())
					throw new ParameterException("An input file may only have one -r option.");

				if (args.length < index + 1)
					throw new ParameterException("Missing rotation for -r parameter at argument " + index + ".");
				RotationTransform rotation = new RotationTransform(args[index + 1]);
				inputFile.setRotation(rotation);
				index++;
			}
			else if (arg.compareTo("-t") == 0 || arg.compareTo("-translate") == 0)
			{
				if (inputFile == null)
					throw new ParameterException("A -t option may only appear after a -i option.");
				if (inputFile.hasTranslation())
					throw new ParameterException("An input file may only have one -t option.");

				if (args.length < index + 1)
					throw new ParameterException("Missing translation for -t parameter at argument " + index + ".");
				TranslationTransform translation = new TranslationTransform(args[index + 1]);
				inputFile.setTranslation(translation);
				index++;
			}

			else if (arg.compareTo("-u") == 0 || arg.compareTo("-units") == 0)
			{
				if (inputFile == null && outputFile == null)
					throw new ParameterException("The units may only be specified after the -o or -i option.");

				if (args.length < index + 1)
					throw new ParameterException("Missing units for -u parameter at argument " + index + ".");

				if (inputFile != null && inputFile.hasUnits())
					throw new ParameterException("Only one -u may be specified for each input file.");

				if (outputFile != null && outputFile.getUnits() != null)
					throw new ParameterException("Only one -u may be specified for the output file.");
				try
				{
					Units unit = Units.valueOf(args[index + 1]);
					if (inputFile != null)
						inputFile.setUnits(unit);
					else
						outputFile.setUnits(unit);
				}
				catch (IllegalArgumentException e)
				{
					throw new ParameterException("Unknown units '" + args[index + 1] + "' at argument " + (index + 1)
							+ ".");
				}
				index++;
			}
			else if (arg.compareTo("-v") == 0 || arg.compareTo("-version") == 0)
			{
				this.showVersion  = true;
			}

			else
				throw new ParameterException("Invalid option '" + args[index] + "' at argument " + index + ".");
		}

		if (inputFiles.size() == 0)
			throw new ParameterException("There must be at least one '-i' (input file) option.");

		if (outputFile == null)
			throw new ParameterException("There must be one '-o' (output file) option.");

	}

	public Vector<InputFile> getInputFiles()
	{
		return inputFiles;
	}

	public OutputFile getOutputFile()
	{
		return outputFile;
	}

	public boolean showVersion()
	{
		return showVersion;
	}

}
