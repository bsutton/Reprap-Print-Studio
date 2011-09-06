package threedc.github.com.amf;

import org.xml.sax.Attributes;

public class V3Handler extends Handler
{

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.setCurrentV3(currentValue);
	}

	@Override
	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		return currentParser;
	}

}
