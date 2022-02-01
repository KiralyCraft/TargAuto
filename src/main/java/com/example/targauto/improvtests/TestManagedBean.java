package com.example.targauto.improvtests;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.interfaces.services.OfferService;
import com.example.targauto.interfaces.services.SaveCarService;
import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;

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
		StackTraceElement stackTrace = new Exception().getStackTrace()[1];
		String commonText = "Assertion at <u>"+stackTrace.getMethodName()+" - "+stackTrace.getLineNumber()+"</u> has ";
		try
		{
			if (expression)
			{
				commonText+= "<b>PASSED</b>";
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
	public synchronized String runTests()
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
			
			if (testsPassed == testCount)
			{
				results.addLog("<h2>All tests passed!</h2>");
			}
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
				catch (Exception exception)
				{
					Throwable theCause = exception.getCause();
					if (theCause != null && !(theCause instanceof RuntimeException))
					{
						exception.printStackTrace();
						results.addLog("Test method <u>"+theMethod.getName()+"</u> has <b>TRAGICALLY FAILED</b>: "+exception.getMessage());
					}
				}
			}
		}
		
	}
	
	/*
	 * SE DEFINESC MAI JOS TESTE CU ADNOTAREA {@link JankTest}
	 * Testam chestiile din interfetele de service
	 */
	
	
	@JankTest
	private void testBogusUsername()
	{
		Optional<User> t = this.theAuthService.getUserByUsername(String.valueOf(System.currentTimeMillis()));
		assertTrue(t.isEmpty());
	}
	
	@JankTest
	private void testRealUsername()
	{
		String username = "Martie"+System.currentTimeMillis();
		String email = "alune@f.com";
		
		User userToCreate = new User(username,email,"12345");		
		Optional<User> the = this.theAuthService.createUser(userToCreate);
		assertTrue(the.isPresent());
		assertTrue(theAuthService.verifyUser(Optional.of(userToCreate), username, "12345").isPresent());
		assertTrue(theAuthService.removeUser(userToCreate));
		
	}
	
	@JankTest
	private void testCars()
	{
		String name = "Martie"+System.currentTimeMillis();
		String desc = "alune";
		double price = Math.random();
		Car newCar = new Car(name,desc,price);
		
		String carID = newCar.getId();
		
		String username = "Martie"+System.currentTimeMillis();
		String email = "alune@f.com";
		
		User userToCreate = new User(username,email,"12345");		
		this.theAuthService.createUser(userToCreate);
		
		assertTrue(this.theCarService.getCarById(carID).isEmpty());
		assertTrue(this.theCarService.auctionCar(newCar,userToCreate).isPresent());
		assertTrue(this.theCarService.getCarById(carID).isPresent());
		assertTrue(this.theCarService.getAllCarsInAuctionOwnedByUser(userToCreate).size() == 1);
		assertTrue(this.theCarService.delistCarID(carID));
		assertTrue(this.theCarService.getCarById(carID).isEmpty());
		assertTrue(this.theCarService.getAllCarsInAuctionOwnedByUser(userToCreate).size() == 0);
		
		theAuthService.removeUser(userToCreate);
		
	}
	
	@JankTest
	private void testOffers()
	{
		String name = "Martie"+System.currentTimeMillis();
		String desc = "alune";
		double price = Math.random();
		Car newCar = new Car(name,desc,price);
		
		String carID = newCar.getId();
		
		String username = "Martie"+System.currentTimeMillis();
		String email = "alune@f.com";
		
		User userToCreate = new User(username,email,"12345");		
		this.theAuthService.createUser(userToCreate);
		
		User userToCreate2 = new User(username+"_copy",email,"12345");		
		this.theAuthService.createUser(userToCreate2);
		
		User userToCreate3 = new User(username+"_copy2",email,"12345");		
		this.theAuthService.createUser(userToCreate3);
		
		this.theCarService.auctionCar(newCar,userToCreate);
		
		Optional<Offer> offerOne = this.theOffserService.processUserOffer(userToCreate2, newCar, 3.0);
		assertTrue(offerOne.isPresent());
		assertTrue(this.theOffserService.getAllPendingOffersForUser(userToCreate).size() == 1);
		assertTrue(this.theOffserService.getOffersMadeByUser(userToCreate2).size() == 1);
		
		Optional<Offer> offerTwo = this.theOffserService.processUserOffer(userToCreate3, newCar, 2.0);
		assertTrue(offerTwo.isPresent());
		assertTrue(this.theOffserService.getAllPendingOffersForUser(userToCreate).size() == 2);
		assertTrue(this.theOffserService.getOffersMadeByUser(userToCreate3).size() == 1);
		
		this.theOffserService.acceptCarOffer(offerTwo.get());
		
		List<Offer> firstUserOffers = this.theOffserService.getOffersMadeByUser(userToCreate2);
		assertTrue(firstUserOffers.size()==1);
		assertTrue(firstUserOffers.get(0).getStatus().equalsIgnoreCase("rejected"));
		
		List<Offer> secondUserOffers = this.theOffserService.getOffersMadeByUser(userToCreate3);
		assertTrue(secondUserOffers.size()==1);
		assertTrue(secondUserOffers.get(0).getStatus().equalsIgnoreCase("accepted"));
		
		assertTrue(this.theCarService.getCarById(carID).get().getStatus().equalsIgnoreCase("sold"));
		
		assertTrue(this.theOffserService.getOffersForCar(newCar).size()==2);
	}

}
