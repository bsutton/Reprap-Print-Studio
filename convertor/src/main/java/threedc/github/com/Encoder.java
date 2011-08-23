package threedc.github.com;

import java.io.IOException;

//Base encoder class that all encoders should extend. Turns Models into files.

abstract public interface Encoder
{
	abstract boolean encode(Model model, String output_path) throws IOException;

	abstract String getFilePath() throws IOException;

}
