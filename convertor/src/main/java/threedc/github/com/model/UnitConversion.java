package threedc.github.com.model;

// Provides conversion factors between different units of measure.
public class UnitConversion
{
	// NOOP is used when the two units are identical.
	private static final UnitConversion NOOP = new UnitConversion(null, null, 1.0F);
	
	private final Units from;
	private final Units to;
	private final float conversionFactor;

	public UnitConversion(Units from, Units to, float conversionFactor)
	{
		this.from = from;
		this.to = to;
		this.conversionFactor = conversionFactor;
	}

	public float conversionFactor()
	{
		return this.conversionFactor;
	}

	static UnitConversion getUnitConversion(Units from, Units to)
	{
		UnitConversion result = null;

		if (from.equals(to))
			result = UnitConversion.NOOP;
		else
		{

			for (UnitConversion conversion : conversionTable)
			{
				if (conversion.from.equals(from) && conversion.to.equals(to))
				{
					result = conversion;
					break;
				}
			}

			if (result == null)
				throw new IllegalArgumentException("Unsupported conversion attempted: " + from.toString() + " to "
						+ to.toString());
		}
		return result;
	}

	// Provides a list of standard conversion factors.
	static UnitConversion[] conversionTable =

	{ new UnitConversion(Units.millimeter, Units.inch, 0.0393701F),
			new UnitConversion(Units.millimeter, Units.feet, 0.00328084F),
			new UnitConversion(Units.millimeter, Units.meter, 0.001F),
			new UnitConversion(Units.millimeter, Units.micron, 1000F)

			, new UnitConversion(Units.inch, Units.millimeter, 25.4F),
			new UnitConversion(Units.inch, Units.feet, 0.0833333F),
			new UnitConversion(Units.inch, Units.meter, 0.0254F), new UnitConversion(Units.inch, Units.micron, 25400F)

			, new UnitConversion(Units.feet, Units.millimeter, 304.8F), new UnitConversion(Units.feet, Units.inch, 12),
			new UnitConversion(Units.feet, Units.meter, 0.3048F), new UnitConversion(Units.feet, Units.micron, 304800F)

			, new UnitConversion(Units.meter, Units.millimeter, 1000F),
			new UnitConversion(Units.meter, Units.inch, 39.37008F),
			new UnitConversion(Units.meter, Units.feet, 3.28084F),
			new UnitConversion(Units.meter, Units.micron, 1000000F)

			, new UnitConversion(Units.micron, Units.millimeter, 0.001F),
			new UnitConversion(Units.micron, Units.inch, 0.00003937F),
			new UnitConversion(Units.micron, Units.feet, 0.000003281F),
			new UnitConversion(Units.micron, Units.meter, 0.000001F)

	};

}
