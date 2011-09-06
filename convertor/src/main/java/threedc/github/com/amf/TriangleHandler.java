package threedc.github.com.amf;

import org.xml.sax.Attributes;

public class TriangleHandler extends Handler
{
	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.endTriangle();
	}

	@Override
	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		currentParser.startTriangle();
		return currentParser;
	}


}
