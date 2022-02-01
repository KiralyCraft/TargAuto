package com.example.targauto.improvtests;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.interfaces.services.OfferService;
import com.example.targauto.interfaces.services.SaveCarService;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("testBean")
@ManagedBean
@RequestScoped
public class TestManagedBean
{
	class TestResults
	{
		private List<String> testResults;
		public TestResults(List<String> testResults)
		{
			this.testResults = testResults;
		}
		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			for (String s:testResults)
			{
				sb.append(s);
				sb.append('\n');
			}
			return sb.toString();
		}
		public void addLog(String toAdd)
		{
			testResults.add(toAdd);
			
		}
		public void clear()
		{
			testResults.clear();
			
		}
		
	}
	@EJB 
	CarService theCarService;
	@EJB
	OfferService theOffserService;
	@EJB
	AuthenticationService theAuthService;
	@EJB
	SaveCarService theSaveCarService;
	

	
	private TestResults results;
	private int testCount;
	private int testsPassed;

	
	@PostConstruct
	public void dummyConstructor()
	{
		results = new TestResults(new LinkedList<String>());
		testCount = 0;
		testsPassed = 0;
	}
	
	private void assertTrue(boolean expression) throws RuntimeException
	{
		testCount++;
		StackTraceElement stackTrace = new Exception().getStackTrace()[1];
		String commonText = "Test at <u>"+stackTrace.getMethodName()+":"+stackTrace.getLineNumber()+"</u> has ";
		try
		{
			if (expression)
			{
				commonText+= "<b>PASSED</b>";
				testsPassed++;
			}
			else
			{
				commonText+= "<b>FAILED</b>";
				throw new RuntimeException(); //Break execution
			}
		}
		finally
		{
			results.addLog(commonText);
		}
	}
	
	String returnedStuff = null;
	public String runTests()
	{
		if (returnedStuff == null)
		{
			results.addLog("Executing the super-duper fancy testing routine");
			results.addLog("\n");
			try
			{
				actuallyRunTheTests();
			}
			catch(Exception e)
			{
				results.addLog("Unexpected exception thrown. Check console for more details: "+e.getMessage());
				e.printStackTrace();
				; //Don't care
			}
			results.addLog("\n");
			results.addLog("Test routine completed. Passed: "+testsPassed+"/"+testCount);
			return (returnedStuff = results.toString());
		}
		else
		{
			return returnedStuff;
		}
	}

	/*
	 * Go wild bois!!
	 */
	private void actuallyRunTheTests() throws Exception
	{
		Method[] allMethods = this.getClass().getDeclaredMethods();
		for (Method theMethod:allMethods)
		{
			if (theMethod.getDeclaredAnnotationsByType(JankTest.class).length!=0)
			{
				testCount++;
				try
				{
					theMethod.invoke(this);
					testsPassed++;
				}
				catch(RuntimeException re)
				{
					; //Ignore
				}
				catch (Exception exception)
				{
					results.addLog("Test method <u>"+theMethod.getName()+"</u> has <b>TRAGICALLY FAILED</b>: "+exception.getMessage());
				}
			}
		}
		
	}
	
	/*
	 * SE DEFINESC MAI JOS TESTE CU ADNOTAREA {@link JankTest}
	 */
	
	
	@JankTest
	private void test1()
	{
		
	}
}
