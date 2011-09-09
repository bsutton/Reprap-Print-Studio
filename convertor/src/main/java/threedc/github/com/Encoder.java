package threedc.github.com;

import java.io.File;
import java.io.IOException;

import threedc.github.com.model.Model;

//Base encoder class that all encoders should extend. Turns Models into files.

abstract public interface Encoder
{
	abstract boolean encode(Model model, String outputPath) throws IOException;
	
	abstract boolean encode(Model model, File outputPath) throws IOException;

	abstract String getFilePath() throws IOException;

}
