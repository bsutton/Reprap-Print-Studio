package threedc.github.com;

import java.io.IOException;

import threedc.github.com.model.Model;

//Base encoder class that all encoders should extend. Turns Models into files.

abstract public interface Encoder
{
	abstract boolean encode(Model model, String output_path) throws IOException;

	abstract String getFilePath() throws IOException;

}
