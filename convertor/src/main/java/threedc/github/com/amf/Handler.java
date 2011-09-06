package threedc.github.com.amf;

import org.xml.sax.Attributes;

public class Handler
{
	
	public void endElement(ParserState currentParser, String currentValue)
	{
	}

	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		return currentParser;
	}

}
