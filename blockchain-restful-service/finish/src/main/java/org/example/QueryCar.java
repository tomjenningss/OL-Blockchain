package org.example;

import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;



@javax.ws.rs.Path("QueryCar")

public class QueryCar {
	
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
	@GET
	@javax.ws.rs.Path("CarByKeyID")
	@Produces(MediaType.APPLICATION_JSON)
	public String Querycar(@QueryParam("Key")String Key) 
	{
		byte[] result = null;
		String outputString = "";
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

			result = contract.evaluateTransaction("queryCar", Key);
			System.out.println(new String(result));
			outputString = new String(result);

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return "Queried car Successfully. \nKey = " + Key + "\nDetails = " + outputString;
	}

	
	@GET
	@javax.ws.rs.Path("AllCars")
	@Produces(MediaType.APPLICATION_JSON)
	public String Querycar() {
		
		byte[] result = null;
		String outputString = "";
		
		
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

				

				result = contract.evaluateTransaction("queryAllCars");
				System.out.println(new String(result));
				outputString = new String(result);
				
			}
				catch(Exception ex) 
				{
				ex.printStackTrace();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return "Queried all Cars Successfully. \nCars are:\n " + outputString;
		
	}
}
