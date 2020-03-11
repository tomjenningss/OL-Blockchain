package io.openliberty.blockchain.communication;

import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.file.Path;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;


@javax.ws.rs.Path("ClientApp")
public class ClientApp {
	
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String Clientapp() {
		try {
		System.out.println("Entered try");
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		System.out.println("Created Wallet");
		// load a CCP
		Path networkConfigPath = Paths.get("1-Org-Local-Fabric-Org1_connection.json");
		
		System.out.println("Passed path networkConfigPath");
		
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
		
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("fabcar");

			byte[] result;

			result = contract.evaluateTransaction("queryAllCars");
			System.out.println(new String(result));

			contract.submitTransaction("createCar", "CAR10", "VW", "Polo", "Grey", "Mary");

			result = contract.evaluateTransaction("queryCar", "CAR10");
			System.out.println(new String(result));

			contract.submitTransaction("changeCarOwner", "CAR10", "Archie");

			result = contract.evaluateTransaction("queryCar", "CAR10");
			System.out.println(new String(result));
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "ClientApp Successfully evaluated";
	}

}
