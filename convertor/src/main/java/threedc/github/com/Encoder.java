package threedc.github.com;

import java.io.File;
import java.io.IOException;

import threedc.github.com.model.ModelImpl;

//Base encoder class that all encoders should extend. Turns Models into files.

abstract public interface Encoder
{
	abstract void encode(ModelImpl model, String outputPath) throws IOException;
	
	abstract void encode(ModelImpl model, File outputPath) throws IOException;

	abstract String getFilePath() throws IOException;

	abstract void encode(ModelImpl model1, File outputPath, boolean split) throws IOException;

}
