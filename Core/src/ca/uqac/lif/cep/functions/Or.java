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
package ca.uqac.lif.cep.functions;

import java.util.Stack;

import ca.uqac.lif.cep.Connector.ConnectorException;

/**
 * Implementation of the logical disjunction
 * 
 * @author Sylvain Hallé
 */
public class Or extends BinaryFunction<Boolean,Boolean,Boolean> 
{
	public static final transient Or instance = new Or();
	
	Or()
	{
		super(Boolean.class, Boolean.class, Boolean.class);
	}

	@Override
	public Boolean getValue(Boolean x, Boolean y)
	{
		return x.booleanValue() || y.booleanValue();
	}
	
	@Override
	public String toString()
	{
		return "∨";
	}
	
	public static void build(Stack<Object> stack) throws ConnectorException
	{
		stack.pop(); // (
		Function right = (Function) stack.pop();
		stack.pop(); // )
		stack.pop(); // symbol
		stack.pop(); // (
		Function left = (Function) stack.pop();
		stack.pop(); // )
		FunctionTree ft = new FunctionTree(instance, left, right);
		stack.push(ft);
	}
}