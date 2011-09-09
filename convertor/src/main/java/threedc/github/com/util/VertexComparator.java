package threedc.github.com.util;

import java.util.Comparator;

import threedc.github.com.model.Vertex;

public class VertexComparator implements Comparator<Vertex>
{
	public int compare(Vertex lhs, Vertex rhs)
	{
		int result = Float.compare(lhs.getX(), rhs.getX());

		if (result == 0)
			result = Float.compare(lhs.getY(), rhs.getY());

		if (result == 0)
			result = Float.compare(lhs.getZ(), rhs.getZ());
/*		float factor = 10;

		result = (int) (lhs.getX() * factor - rhs.getX() * factor);
		if (result == 0)
			result = (int) (lhs.getY() * factor - rhs.getY() * factor);
		if (result == 0)
			result = (int) (lhs.getZ() * factor - rhs.getZ() * factor);
			*/
		return result;
	}

}

/*
 * inline bool IsNear(const aVertex& v, double WeldThresh) {
 * if(abs(v.Loc.z-Loc.z)>WeldThresh) return false;
 * if(abs(v.Loc.y-Loc.y)>WeldThresh) return false;
 * if(abs(v.Loc.x-Loc.x)>WeldThresh) return false; return true;};
 * 
 * 
 * //figure out a reasonable weld distance Vec3D p1, p2;
 * pSTL->ComputeBoundingBox(p2, p1); p1 -= p2; aWeldVertex::WeldThresh =
 * min(min(p1.x, p1.y), p1.z)/100000.0;
 * 
 * 
 * // Approximation Algorithms for Lawn Mowing and Milling, by Arkin, Fekete,
 * and Mitchell.
 * 
 * http://education.theage.com.au/cmspage.php?intid=147&intversion=25
 */

