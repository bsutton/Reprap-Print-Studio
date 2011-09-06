package threedc.github.com.amf;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class AmfHandler extends Handler
{
	static Logger logger = Logger.getLogger(AmfHandler.class);

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		logger.info("Loaded: " + currentParser.getPrintableObject().getTriangleCount() + " triangles '" +currentParser.getPrintableObject().getId());

	}

	@Override
	public ParserState startElement(ParserState currentParser, Attributes attributes)
	{
		return currentParser;
	}

}
