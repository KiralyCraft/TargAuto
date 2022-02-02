package com.example.targauto.managed;

import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.models.Car;
import com.example.targauto.utils.SessionUtils;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("carsManagedBean")
@ManagedBean
@SessionScoped
public class CarsManagedBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7871726187538533243L;
	@EJB
	CarService carService;

	/**
	 * Lists all {@link Car}s in the auction, regardless of the owner and auction status. 
	 */
	public List<Car> getListOfAllCarsInAuction()
	{
		return carService.getAllCarsInAuction();
	}
	
	/**
	 * Lists all {@link Car}s in the auction specifically belonging to the 
	 * {@link User} associated with the current {@link HttpSession}.
	 * @return
	 */
	public List<Car> getListOfAllCarsInAuctionOfAUser()
	{
		var user = SessionUtils.getUser();
		if (user.isEmpty())
			return List.of();
		return carService.getAllCarsInAuctionOwnedByUser(user.get());
	}

	/**
	 * Called by JSF to change a car's status from inAuction to "withdrawn".
	 * Takes a {@link Car}'s ID as argument and provides feedback using the Session Feedback route.
	 */
	public void delistCar(String carID)
	{
		if (carService.delistCarID(carID))
		{
			SessionUtils.queueUserFeedback("Delist successful!");
		} else
		{
			SessionUtils.queueUserFeedback("Could not unlist your car.");
		}
	}
}
