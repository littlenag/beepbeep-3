package ca.uqac.lif.cep;

import static org.junit.Assert.assertEquals;

import java.util.Queue;
import java.util.Vector;

import org.junit.Test;

import ca.uqac.lif.cep.epl.QueueSink;
import ca.uqac.lif.cep.epl.QueueSource;
import ca.uqac.lif.cep.epl.QueueSourceBatch;

/**
 * Unit tests for {@link Fork} and {@link SmartFork}
 * @author Sylvain Hallé
 */
public class ForkTest 
{
	@SuppressWarnings("unchecked")
	@Test
	public void testFork()
	{
		Passthrough p1 = new Passthrough(1);
		Fork fork = new Fork(2);
		Connector.connect(p1, fork);
		Pushable push = p1.getPushableInput(0);
		QueueSink[] sinks = new QueueSink[3];
		Queue<Object>[] queues = new Queue[3];
		for (int i = 0; i < 3; i++)
		{
			sinks[i] = new QueueSink(1);
			Connector.connect(fork, sinks[i], i, 0);
			queues[i] = sinks[i].getQueue(0);
		}
		for (int k = 0; k < 2; k++)
		{
			for (int i = 0; i < 5; i++)
			{
				push.push(i);
				for (int j = 0; j < 2; j++)
				{
					Utilities.queueContains(i, queues[j]);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSmartFork()
	{
		Passthrough p1 = new Passthrough(1);
		SmartFork fork = new SmartFork(2);
		Connector.connect(p1, fork);
		Pushable push = p1.getPushableInput(0);
		QueueSink[] sinks = new QueueSink[3];
		Queue<Object>[] queues = new Queue[3];
		for (int i = 0; i < 3; i++)
		{
			sinks[i] = new QueueSink(1);
			Connector.connect(fork, sinks[i], i, 0);
			queues[i] = sinks[i].getQueue(0);
		}
		for (int k = 0; k < 2; k++)
		{
			for (int i = 0; i < 5; i++)
			{
				push.push(i);
				for (int j = 0; j < 2; j++)
				{
					Utilities.queueContains(i, queues[j]);
				}
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testExtendArityFork()
	{
		Passthrough p1 = new Passthrough(1);
		Fork fork = new Fork(2);
		Connector.connect(p1, fork);
		Pushable push = p1.getPushableInput(0);
		QueueSink[] sinks = new QueueSink[3];
		Queue<Object>[] queues = new Queue[3];
		for (int i = 0; i < 2; i++)
		{
			sinks[i] = new QueueSink(1);
			Connector.connect(fork, sinks[i], i, 0);
			queues[i] = sinks[i].getQueue(0);
		}
		fork.extendOutputArity(3);
		sinks[2] = new QueueSink(1);
		Connector.connect(fork, sinks[2], 2, 0);
		queues[2] = sinks[2].getQueue(0);
		for (int k = 0; k < 2; k++)
		{
			for (int i = 0; i < 5; i++)
			{
				push.push(i);
				for (int j = 0; j < 3; j++)
				{
					Utilities.queueContains(i, queues[j]);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testExtendAritySmartFork()
	{
		Passthrough p1 = new Passthrough(1);
		SmartFork fork = new SmartFork(2);
		Connector.connect(p1, fork);
		Pushable push = p1.getPushableInput(0);
		QueueSink[] sinks = new QueueSink[3];
		Queue<Object>[] queues = new Queue[3];
		for (int i = 0; i < 2; i++)
		{
			sinks[i] = new QueueSink(1);
			Connector.connect(fork, sinks[i], i, 0);
			queues[i] = sinks[i].getQueue(0);
		}
		fork.extendOutputArity(3);
		sinks[2] = new QueueSink(1);
		Connector.connect(fork, sinks[2], 2, 0);
		queues[2] = sinks[2].getQueue(0);
		for (int k = 0; k < 2; k++)
		{
			for (int i = 0; i < 5; i++)
			{
				push.push(i);
				for (int j = 0; j < 3; j++)
				{
					Utilities.queueContains(i, queues[j]);
				}
			}
		}
	}

	
	@Test
	public void testFork1()
	{
		Vector<Object> events = new Vector<Object>();
		events.add("A");
		events.add("B");
		events.add("C");
		events.add("D");
		QueueSource cp = new QueueSource("", 1);
		cp.setEvents(events);
		Fork f = new Fork(2);
		Connector.connect(cp,  f);
		Pullable p1 = f.getPullableOutput(0);
		Pullable p2 = f.getPullableOutput(1);
		String recv;
		recv = (String) p1.pull();
		assertEquals("A", recv);
		recv = (String) p1.pull();
		assertEquals("B", recv);
		recv = (String) p2.pull();
		assertEquals("A", recv);
		recv = (String) p1.pull();
		assertEquals("C", recv);
		recv = (String) p2.pull();
		assertEquals("B", recv);		
	}
	
	@Test
	public void testSmartFork1()
	{
		Vector<Object> events = new Vector<Object>();
		events.add("A");
		events.add("B");
		events.add("C");
		events.add("D");
		QueueSource cp = new QueueSource("", 1);
		cp.setEvents(events);
		SmartFork f = new SmartFork(2);
		Connector.connect(cp,  f);
		Pullable p1 = f.getPullableOutput(0);
		Pullable p2 = f.getPullableOutput(1);
		String recv;
		assertEquals(Pullable.NextStatus.YES, p1.hasNext());
		recv = (String) p1.pull();
		assertEquals("A", recv);
		assertEquals(Pullable.NextStatus.YES, p1.hasNext());
		recv = (String) p1.pull();
		assertEquals("B", recv);
		assertEquals(Pullable.NextStatus.YES, p1.hasNext());
		recv = (String) p2.pull();
		assertEquals("A", recv);
		assertEquals(Pullable.NextStatus.YES, p1.hasNext());
		recv = (String) p1.pull();
		assertEquals("C", recv);
		assertEquals(Pullable.NextStatus.YES, p1.hasNext());
		recv = (String) p2.pull();
		assertEquals("B", recv);		
	}
	
	@Test
	public void testSmartFork2()
	{
		Vector<Object> events = new Vector<Object>();
		events.add("A");
		events.add("B");
		events.add("C");
		events.add("D");
		QueueSource cp = new QueueSource("", 1);
		cp.setEvents(events);
		SmartFork f = new SmartFork(2);
		Connector.connect(cp,  f);
		SmartFork new_f = new SmartFork(3, f);
		Pullable p1 = new_f.getPullableOutput(0);
		Pullable p2 = new_f.getPullableOutput(1);
		Pullable p3 = new_f.getPullableOutput(2);
		String recv;
		recv = (String) p3.pull();
		assertEquals("A", recv);
		recv = (String) p1.pull();
		assertEquals("A", recv);
		recv = (String) p2.pull();
		assertEquals("A", recv);
		recv = (String) p1.pull();
		assertEquals("B", recv);
		recv = (String) p2.pull();
		assertEquals("B", recv);		
	}
	
	@Test
	public void testSmartFork3()
	{
		Vector<Object> events = new Vector<Object>();
		events.add(0);
		events.add(1);
		events.add(2);
		events.add(3);
		QueueSource cp = new QueueSource("", 1);
		cp.setEvents(events);
		SmartFork f = new SmartFork(2);
		Connector.connect(cp,  f);
		Pullable p1 = f.getPullableOutput(0);
		Pullable p2 = f.getPullableOutput(1);
		int recv;
		for (int i = 0; i < SmartFork.s_cleanInterval + 3; i++)
		{
			assertEquals(Pullable.NextStatus.YES, p1.hasNextHard());
			recv = (int) p1.pullHard();
			p2.pullHard();
			assertEquals(i % 4, recv);
		}
		f.reset();
		cp.reset();
		for (int i = 0; i < SmartFork.s_cleanInterval + 3; i++)
		{
			assertEquals(Pullable.NextStatus.YES, p1.hasNextHard());
			recv = (int) p1.pullHard();
			p2.pullHard();
			assertEquals(i % 4, recv);
		}
	}
	
	@Test
	public void testSmartFork4()
	{
		/* Only difference with previous test: we never consume
		 * anything from the second queue
		 */
		Vector<Object> events = new Vector<Object>();
		events.add(0);
		events.add(1);
		events.add(2);
		events.add(3);
		QueueSource cp = new QueueSource("", 1);
		cp.setEvents(events);
		SmartFork f = new SmartFork(2);
		Connector.connect(cp,  f);
		Pullable p1 = f.getPullableOutput(0);
		int recv;
		for (int i = 0; i < SmartFork.s_cleanInterval + 3; i++)
		{
			assertEquals(Pullable.NextStatus.YES, p1.hasNextHard());
			recv = (int) p1.pullHard();
			// Here we don't do p2.pullHard()
			assertEquals(i % 4, recv);
		}
		f.reset();
		cp.reset();
		for (int i = 0; i < SmartFork.s_cleanInterval + 3; i++)
		{
			assertEquals(Pullable.NextStatus.YES, p1.hasNextHard());
			recv = (int) p1.pullHard();
			// Here we don't do p2.pullHard()
			assertEquals(i % 4, recv);
		}
	}
	
	@Test
	public void testSmartFork5()
	{
		/*
		 * In this test, we push events faster than we pull them
		 */
		QueueSourceBatch qsource = new QueueSourceBatch(null, 1);
		Vector<Object> events = new Vector<Object>();
		events.add(0);
		events.add(1);
		events.add(2);
		qsource.setEvents(events);
		SmartFork fork = new SmartFork(2);
		Connector.connect(qsource, fork);
		QueueSink qs1 = new QueueSink(1);
		Connector.connect(fork, qs1, 0, 0);
		QueueSink qs2 = new QueueSink(1);
		Connector.connect(fork, qs2, 1, 0);
		Queue<Object> q1 = qs1.getQueue(0);
		Queue<Object> q2 = qs2.getQueue(0);
		qs1.pull();
		Utilities.queueContains(0, q1);
		assertEquals(0, q2.size());
	}

}
