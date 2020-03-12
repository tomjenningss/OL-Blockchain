package sample.hyperledger.blockchain.communication;

import java.nio.file.Paths;


import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

import java.nio.file.Path;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import sample.hyperledger.blockchain.model.*;

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
			@QueryParam("owner") String owner) throws JsonProcessingException 
	{
		
		String retString = "";
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    
		try {
			
			Path walletPath = Paths.get("wallet");
			Wallet wallet = Wallet.createFileSystemWallet(walletPath);
			
			// load a CCP
			Path networkConfigPath = Paths.get("1-Org-Local-Fabric-Org1_connection.json");
			
			Gateway.Builder builder = Gateway.createBuilder();
			builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
			
			try (Gateway gateway = builder.connect()) {
	
				// get the network and contract
				Network network = gateway.getNetwork("mychannel");
				Contract contract = network.getContract("fabcar");
				
				String Key = make + owner;
				
				//initialise a new car from the class car and populate the properties
				//this is used to return the json car object
				car newCar = new car();
				
				newCar.setMake(make);
				newCar.setModel(model);
				newCar.setColour(colour);
				newCar.setOwner(owner);
				newCar.setKey(Key);
				
				if (make == null) {
					make = "unknown";
				}
				
				//use the car object to add to the ledger
				contract.submitTransaction("createCar", newCar.getKey(), newCar.getMake(), newCar.getModel(), newCar.getColour(), newCar.getOwner());
			    
				//convert the newCar object to a json string
				retString = mapper.writeValueAsString(newCar);
				}
			}
			catch (Exception e){
				error newError = new error();
				
				newError.setCode("50001");
				newError.setMessage(e.getMessage());
				
				retString = mapper.writeValueAsString(newError);
				
				e.printStackTrace();
			}
		
		return retString;
	}	
}