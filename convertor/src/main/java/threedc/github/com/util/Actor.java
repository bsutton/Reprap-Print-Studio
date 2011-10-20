package threedc.github.com.util;

public interface Actor<T>
{

	void prep();
	
	void apply(T t) throws Exception;
	
	public String toString();

}
