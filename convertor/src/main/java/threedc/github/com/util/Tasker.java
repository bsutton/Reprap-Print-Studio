package threedc.github.com.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Designed to allow a list to be split and processed using multiple threads.
 * 
 * @author bsutton
 * 
 */
public class Tasker<T>
{
	static Logger logger = Logger.getLogger(Tasker.class); 
	public enum ACTOR_TYPE
	{
		FAST, SLOW;
	};

	List<T> list;
	Actor<T> actor;
	ACTOR_TYPE actorType;
	List<FailCause<T>> failCauses = new ArrayList<FailCause<T>>();

	public Tasker(List<T> list, Actor<T> actor, ACTOR_TYPE actorType)
	{
		this.list = list;
		this.actor = actor;
		this.actorType = actorType;
	}

	public void run()
	{

		// Allow each actor to do any last minute preparation that
		// may require knowledge of the object being transformed.
		actor.prep();

		int processors = Runtime.getRuntime().availableProcessors();

		int count = list.size();

		int allocation = Math.max(count / processors, 1);
		

		int first = 0; // inclusive
		int last = allocation; // exclusive

		ArrayList<Transformer> transformers = new ArrayList<Transformer>();
		do
		{
			Transformer t = new Transformer(list, first, last, actor);
			t.start();
			transformers.add(t);
			first = last;
			last = Math.min(last + allocation, count);

		} while (first < count);

		// wait for all of the threads to finish.
		for (Transformer transformer : transformers)
		{
			try
			{
				transformer.join();
				if (transformer.failed())
					this.failCauses.add(transformer.getFailCause());
			}
			catch (InterruptedException e)
			{
				logger.error(e,e);
			}
		}
	}

	public Actor<T> getActor()
	{
		return this.actor;
	}
	
	class Transformer extends Thread
	{
		private final List<T> list;
		private final int first;
		private final int last;
		private final Actor<T> actor;
		private FailCause<T> failCause = null;

		Transformer(List<T> list, int first, int last, Actor<T> actor)
		{
			this.list = list;
			this.first = first;
			this.last = last;
			this.actor = actor;
			this.setPriority(MIN_PRIORITY);
			this.setName("Transformer");
		}

		public FailCause<T> getFailCause()
		{
			return this.failCause;
		}

		public void run()
		{
			int i = -1;
			List<T> subList = list.subList(first, last);
			try
			{
				// Apply the actor to each element in the sublist.
				
				for (i = 0; i < subList.size(); i++)
				{
					actor.apply(subList.get(i));
				}
			}
			catch (Exception e)
			{
				this.failCause = new FailCause<T>(subList.get(i), e);
				logger.error(e,e);
			}
		}

		public boolean failed()
		{
			return this.failCause != null;
		}
	}

	public boolean failed()
	{
		return failCauses.size() > 0;
	}

	public List<FailCause<T>> getFailCauses()
	{
		return this.failCauses;
	}
	
	public class FailCause<I>
	{
		I t;
		Exception failCause;

		public FailCause(I t, Exception failCause)
		{
			this.t = t;
			this.failCause = failCause;
		}

		public String toString()
		{
			return t.toString() + ", cause:" + failCause.getClass() + failCause.getMessage();
		}
		
		public Exception getCause()
		{
			return failCause;
		}
	}

}
