/*
    BeepBeep, an event stream processor
    Copyright (C) 2008-2016 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cep.tmf;

/**
 * Returns one input event and discards the next *n*-1. The value *n* is called
 * the **decimation interval**. However, a mode can be specified in order to
 * output the *n*-th input event if it is the last event of the trace and it has
 * not been output already.
 * 
 * It is represented graphically as:
 * 
 * ![QueueSink]({@docRoot}/doc-files/tmf/CountDecimate.png)
 *
 * @author Sylvain Hallé
 *
 */
@SuppressWarnings("squid:S2160")
public class CountDecimate extends Decimate
{
  /**
   * The decimation interval
   */
  private final int m_interval;

  /**
   * Index of last event received
   */
  protected int m_current;

  /**
   * Creates a new count decimate processor with a decimation interval of 1
   */
  public CountDecimate()
  {
    this(1);
  }

  /**
   * Creates a new count decimate processor
   * 
   * @param interval
   *          The decimation interval
   */
  public CountDecimate(int interval)
  {
    this(interval, false);
  }

  /**
   * Creates a new count decimate processor
   *
   * @param interval
   *          The decimation interval
   * @param should_process_last_inputs
   *          Default to false. Indicates if the processor should output the last
   *          input events of the trace even if it does not correspond to the
   *          decimation interval.
   */
  public CountDecimate(int interval, boolean should_process_last_inputs)
  {
    super(should_process_last_inputs);
    m_interval = interval;
    m_current = 0;
  }

  /**
   * Gets the decimation interval
   * 
   * @return The interval
   */
  public int getInterval()
  {
    return m_interval;
  }

  @Override
  protected boolean shouldOutput()
  {
    return m_current == 0;
  }

  @Override
  protected void postCompute()
  {
    m_current = (m_current + 1) % m_interval;
  }

  @Override
  public void reset()
  {
    super.reset();
    m_current = 0;
  }

  @Override
  public CountDecimate duplicate(boolean with_state)
  {
    return new CountDecimate(m_interval, m_shouldProcessLastInputs);
  }

}
