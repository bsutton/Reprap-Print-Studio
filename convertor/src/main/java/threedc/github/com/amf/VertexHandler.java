package threedc.github.com.amf;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class VertexHandler extends Handler
{
	static Logger logger = Logger.getLogger(VertexHandler.class);
	static int vertex = 0;

	@Override
	public void endElement(ParserState currentParser, String currentValue)
	{
		currentParser.endVertex();
	}

	@Override
	public void startElement(ParserState currentParser, Attributes attributes)
	{
		//logger.debug("Vertex ordinal:" + vertex + " line " + (8 + (vertex * 7)));
		vertex++;
		currentParser.startVertex();
	}

}
