package threedc.github.com.amf;

import org.xml.sax.Attributes;

public class ZHandler extends Handler
{

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.endZ(Float.valueOf(currentValue));
	}

	@Override
	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		return currentParser;
	}

}
