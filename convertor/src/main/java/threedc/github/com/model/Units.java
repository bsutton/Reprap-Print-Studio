package threedc.github.com.model;

/**
 * The set of supported units used by the 'amf' tag in the AMF definition.
 * @author bsutton
 *
 */
public enum Units
{

	millimeter,  
	inch,
	feet,
	meter,
	micron;

	public static String valuesAsString()
	{
		StringBuffer sb = new StringBuffer();

		for (Units unit : Units.values())
		{
			if (sb.length() > 0)
				sb.append(", ");
			
			sb.append(unit.toString());
		}
		return sb.toString();
	}
	
	/**
	 * provides a conversion factor from this Unit to the 'To' units.
	 * @param from
	 * @param To
	 * @return
	 */
	public float conversionFactor(Units to)
	{
		return UnitConversion.getUnitConversion(this, to).conversionFactor();
	}
	
	


}
