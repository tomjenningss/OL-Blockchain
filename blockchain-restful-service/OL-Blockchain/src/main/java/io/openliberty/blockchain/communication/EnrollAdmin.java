package io.openliberty.blockchain.communication;

import java.nio.file.Paths;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;


@javax.ws.rs.Path("EnrollAdmin")
public class EnrollAdmin {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String Clientapp() {
		try {
			// Create a CA client for interacting with the CA.
			Properties props = new Properties();
			System.out.println("Passed");
			props.put("pemFile",
				"first-network/crypto-config/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
			System.out.println(props);
			props.put("allowAllHostNames", "true");
			System.out.println("Passed pem file");
			HFCAClient caClient = HFCAClient.createNewInstance("http://localhost:17055", props);
			System.out.println("Passed: HFCAClient caClient = HFCAClient.createNewInstance(\"https://localhost:7054\", props);");
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			System.out.println("Passed: pass Crypto Suite");
			caClient.setCryptoSuite(cryptoSuite);
			System.out.println("Passed: caClient.setCryptoSuite(cryptoSuite);");
			// Create a wallet for managing identities
			Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));
			System.out.println("Passed: Wallet wallet = Wallet.createFileSystemWallet(Paths.get(\"wallet\"));");
			boolean adminExists = wallet.exists("admin");
	        if (adminExists) {
	            String ret = "An identity for the admin user \"admin\" already exists in the wallet";
	            return ret;
	        }
	     // Enroll the admin user, and import the new identity into the wallet.
	        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
	        System.out.println("Passed: Enrollment req");
	        enrollmentRequestTLS.addHost("localhost");
	        System.out.println("Passed: enrollmentRequestTLS.addHost(\"localhost\");");
	        enrollmentRequestTLS.setProfile("tls");
	       
	        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
	        System.out.println("Fails this");
	        System.out.println("Passed: Enrollment enrollment = caClient.enroll(\"admin\", \"adminpw\", enrollmentRequestTLS);");
	        Identity user = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
	        System.out.println("Identity user = Identity.createIdentity(\"Org1MSP\", enrollment.getCert(), enrollment.getKey());");
	        wallet.put("admin", user);
	        System.out.println("Passed:  wallet.put(\"admin\", user);");
			System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "Admin Enrollment Success!";
	}

}
