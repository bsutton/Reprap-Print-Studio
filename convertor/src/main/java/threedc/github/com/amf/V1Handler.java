package threedc.github.com.amf;

import org.xml.sax.Attributes;

public class V1Handler extends Handler
{
	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.setCurrentV1(currentValue);
	}

	@Override
	public void startElement(ParserState currentParser, Attributes attributes)
	{
	}

}
