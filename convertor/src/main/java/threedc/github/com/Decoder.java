package threedc.github.com;

import java.io.EOFException;
import java.io.IOException;

import threedc.github.com.model.ModelImpl;

//Base decoder class that all decoders should extend. Responsible for turning input files into Model objects.
abstract public interface Decoder
{
	  abstract ModelImpl decode() throws EOFException, IOException, DecodeException;

	  abstract String getFilePath() throws IOException;
	  
	  

}
