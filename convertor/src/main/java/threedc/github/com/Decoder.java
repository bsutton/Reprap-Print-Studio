package threedc.github.com;

import java.io.EOFException;
import java.io.IOException;

import threedc.github.com.model.ModelImpl;
import threedc.github.com.util.InvalidFormatException;

//Base decoder class that all decoders should extend. Responsible for turning input files into Model objects.
abstract public interface Decoder
{
	  abstract ModelImpl decode() throws EOFException, IOException, DecodeException, InvalidFormatException;

	  abstract String getFilePath() throws IOException;
	  
	  

}
