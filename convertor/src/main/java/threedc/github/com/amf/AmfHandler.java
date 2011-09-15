package threedc.github.com.amf;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class AmfHandler extends Handler
{
	static Logger logger = Logger.getLogger(AmfHandler.class);

	static int ATTRIBUTE_VERSION = 0;
	static int ATTRIBUTE_UNITS = 1;

	@Override
	public void startElement(ParserState parserState, Attributes attributes)
	{
		String units = attributes.getValue(ATTRIBUTE_UNITS);
		parserState.setUnits(units);
		String version = attributes.getValue(ATTRIBUTE_VERSION);
		parserState.setVersion(version);
	}

}
