package threedc.github.com.amf;

import org.apache.log4j.Logger;

public class YHandler extends Handler
{
	static Logger logger = Logger.getLogger(YHandler.class);
	
	static int vertex = 0;
	
	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		//logger.debug("Vertex y:" + currentValue);
		currentParser.endY(Float.valueOf(currentValue));
	}

}
