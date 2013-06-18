package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class TestRule
{
	public final String rule;
	public int count = -1;
	public Calendar until = null;
	public Set<Integer> months = null;
	public Set<Integer> weekdays = null;
	public Set<Integer> monthdays = null;
	public Set<Integer> weeks = null;
	public boolean floating = false;
	public boolean allday = false;
	public Calendar start = null;
	public int instances = -1;
	public boolean printInstances = false;


	public TestRule(String rule)
	{
		this.rule = rule;
	}


	public TestRule print()
	{
		printInstances = true;
		return this;
	}


	public TestRule setStart(String start)
	{
		this.start = Calendar.parse(start);
		return this;
	}


	public TestRule setInstances(int instances)
	{
		this.instances = instances;
		return this;
	}


	public TestRule setCount(int instances)
	{
		count = instances;
		return this;
	}


	public TestRule setUntil(String lastInstance)
	{
		until = Calendar.parse(lastInstance);
		floating = !lastInstance.endsWith("Z");
		allday = !lastInstance.contains("T");
		return this;
	}


	public TestRule setMonths(Integer... months)
	{
		this.months = new HashSet<Integer>();
		this.months.addAll(Arrays.asList(months));
		return this;
	}


	public TestRule setWeekdays(Integer... days)
	{
		this.weekdays = new HashSet<Integer>();
		this.weekdays.addAll(Arrays.asList(days));
		return this;
	}


	public TestRule setMonthdays(Integer... days)
	{
		this.monthdays = new HashSet<Integer>();
		this.monthdays.addAll(Arrays.asList(days));
		return this;
	}


	public TestRule setWeeks(Integer... days)
	{
		this.weeks = new HashSet<Integer>();
		this.weeks.addAll(Arrays.asList(days));
		return this;
	}


	public void assertCount(int count)
	{
		if (this.count > 0 && until == null)
		{
			assertEquals(this.count, count);
		}
	}


	public void assertUntil(Calendar instance)
	{
		if (count == -1 && until != null)
		{
			assertTrue("instance " + instance + " after " + until, !instance.after(until));
		}
	}


	public void assertMonth(Calendar instance)
	{
		if (months != null)
		{
			assertTrue("month of " + instance + " not in " + months + " rule: " + rule, months.contains(instance.get(Calendar.MONTH) + 1));
		}
	}


	public void assertWeekday(Calendar instance)
	{
		if (weekdays != null)
		{
			assertTrue("weekday of " + instance + " not in " + weekdays + " rule: " + rule, weekdays.contains(instance.get(Calendar.DAY_OF_WEEK)));
		}
	}


	public void assertMonthday(Calendar instance)
	{
		if (monthdays != null)
		{
			assertTrue("monthday of " + instance + " not in " + monthdays + " rule: " + rule, monthdays.contains(instance.get(Calendar.DAY_OF_MONTH)));
		}
	}


	public void assertWeek(Calendar instance)
	{
		if (weeks != null)
		{
			assertTrue("week of " + instance + " not in " + weeks + " rule: " + rule, weeks.contains(instance.get(Calendar.WEEK_OF_YEAR)));
		}
	}


	public void testInstance(Calendar instance)
	{
		if (printInstances)
		{
			System.out.println(instance.toString());
		}
		assertMonth(instance);
		assertWeekday(instance);
		assertUntil(instance);
	}


	public void assertInstances(int instances)
	{
		if (this.instances > 0)
		{
			assertEquals("invalid number of instances for " + rule + " with start " + start, this.instances, instances);
		}
	}

}
