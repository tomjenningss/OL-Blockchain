package org.example;

import java.nio.file.Paths;
import java.io.StringWriter;
import java.io.PrintWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import java.nio.file.Path;
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
		String passedOutput = "";
		String failedOutput = "";
		String noCar = "";
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

			result = contract.evaluateTransaction("queryCar", Key);
			System.out.println(new String(result));
			outputString = new String(result);
			passedOutput = "Queried car Successfully. \nKey = " + Key + "\nDetails = " + outputString;
			
			
			}
		}
		catch (Exception e){
			e.printStackTrace();
			failedOutput = "Failed \n";
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String sStackTrace = sw.toString();
			if (sStackTrace.contains("org.hyperledger.fabric.gateway.ContractException: transaction returned with failure: Error:")) {
				noCar = "Car not found";
			}
			
		}
		
		return passedOutput + failedOutput + noCar;
	}

	
	@GET
	@javax.ws.rs.Path("AllCars")
	@Produces(MediaType.APPLICATION_JSON)
	public String Querycar() {
		
		byte[] result = null;
		String outputString = "";
		String passedOutput = "";
		String failedOutput = "";
		
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

				result = contract.evaluateTransaction("queryAllCars");
				System.out.println(new String(result));
				outputString = new String(result);
				passedOutput = "Queried all Cars Successfully. \nCars are:\n " + outputString;
			}
				catch(Exception ex) 
				{
				ex.printStackTrace();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				failedOutput = "Failed to retrieve all cars"; 
			}
			
			return passedOutput + failedOutput;
		
	}
}
