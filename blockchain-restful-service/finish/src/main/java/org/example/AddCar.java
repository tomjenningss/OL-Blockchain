package org.example;

import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;



@javax.ws.rs.Path("AddCar")


public class AddCar {
	
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addCar(
			@QueryParam("make") String make,
			@QueryParam("model") String model,
			@QueryParam("colour") String colour,
			@QueryParam("owner") String owner) 
	{
		
		
		
		try {
			
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		
		// load a CCP
		Path networkConfigPath = Paths.get("first-network", "connection-org1.yaml");
		
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
		
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("fabcar");

			//byte[] result;
			
			//result = contract.evaluateTransaction("queryAllCars");
			//System.out.println(new String(result));
			
			String Key = make + owner;
			
			System.out.print("Make is " + make);
			System.out.print("Model is " + model);
			System.out.print("Colour is " + colour);
			System.out.print("Owner is " + owner);
			
			System.out.println("Key = " + Key);
			
			if (make == null) {
				make = "unknown";
			}
			
			contract.submitTransaction("createCar", Key, make, model, colour, owner);

			//result = contract.evaluateTransaction("queryCar", "CAR10000000");
			//System.out.println(new String(result));

			//contract.submitTransaction("changeCarOwner", "CAR10", "Archie");

			//result = contract.evaluateTransaction("queryCar", "CAR10");
			//System.out.println(new String(result));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		String parameters = "\nOwner = " + owner + "\nMake = " + make + "\nModel = " + model + "\nColour = " + colour;
		String key = "\nTo Query recently added contents the Key is \n" + make + owner;
		
		return "Car added successfully. " + parameters + key;
	}	
}