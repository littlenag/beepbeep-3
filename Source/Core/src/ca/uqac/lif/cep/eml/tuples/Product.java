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

public class Product extends CombinableExpression
{
	public Product()
	{
		super(Multiplication.s_singleton);
	}
	
	@Override
	public Object[] initialize()
	{
		Object[] out = new Object[1];
		out[0] = new EmlNumber(1);
		return out;
	}
	
	@Override
	public String toString()
	{
		return "PRODUCT";
	}
	
	public static void build(Stack<Object> stack)
	{
		stack.pop(); // combiner's name
		stack.push(new Product());
	}
}
