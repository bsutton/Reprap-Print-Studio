package threedc.github.com.amf;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class XHandler extends Handler
{
	static Logger logger = Logger.getLogger(XHandler.class);

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.endX(Float.valueOf(currentValue));
	}

	@Override
	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		return currentParser;
	}

}
