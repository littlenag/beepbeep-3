/*
    BeepBeep, an event stream processor
    Copyright (C) 2008-2015 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cep.eml.tuples;

import java.util.Stack;

import ca.uqac.lif.cep.Processor;

public class ProcessorDefinitionPlain extends ProcessorDefinitionAs
{
	public ProcessorDefinitionPlain(Processor p)
	{
		super();
		m_processorName = "";
		m_processor = p;
	}

	public static void build(Stack<Object> stack)
	{
		Processor proc = null;
		Object o = stack.pop();
		if (o instanceof String)
		{
			// Parentheses around the definition
			proc = (Processor) stack.pop();
			stack.pop(); // (
		}
		else
		{
			proc = (Processor) o;	
		}
		ProcessorDefinitionPlain pdp = new ProcessorDefinitionPlain(proc);
		stack.push(pdp);
	}
	
	@Override
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		out.append(m_processor);
		return out.toString();
	}
}
