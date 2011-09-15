package threedc.github.com.amf;


public class ZHandler extends Handler
{

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.endZ(Float.valueOf(currentValue));
	}

}
