package sample.hyperledger.blockchain.communication;

import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

@javax.ws.rs.Path("RegisterUser")
public class RegisterUser {
	
	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String RegisterUsr() {
		
		try {
			// Create a CA client for interacting with the CA.
			Properties props = new Properties();
			props.put("pemFile",
				"first-network/crypto-config/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
			props.put("allowAllHostNames", "true");
			HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);
			// Create a wallet for managing identities
			Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

			// Check to see if we've already enrolled the user.
			boolean userExists = wallet.exists("user2");
			if (userExists) {
				String ifUserExists = "An identity for the user \"user2\" already exists in the wallet";
				return ifUserExists;
			}

			userExists = wallet.exists("admin");
			if (!userExists) {
				String ifuserExists = "\"admin\" needs to be enrolled and added to the wallet first";
				return ifuserExists;
			}

			Identity adminIdentity = wallet.get("admin");
			User admin = new User() {

				@Override
				public String getName() {
					return "admin";
				}
				@Override
				public Set<String> getRoles() {
					return null;
				}

				@Override
				public String getAccount() {
					return null;
				}

				@Override
				public String getAffiliation() {
					return "org1.department1";
				}
				@Override
				public Enrollment getEnrollment() {
					return new Enrollment() {

						@Override
						public PrivateKey getKey() {
							return adminIdentity.getPrivateKey();
						}

						@Override
						public String getCert() {
							return adminIdentity.getCertificate();
						}
					};
				}

				@Override
				public String getMspId() {
					return "Org1MSP";
				}
			};
			
			// Register the user, enroll the user, and import the new identity into the wallet.
			RegistrationRequest registrationRequest = new RegistrationRequest("user2");
			registrationRequest.setAffiliation("org1.department1");
			registrationRequest.setEnrollmentID("user2");
			String enrollmentSecret = caClient.register(registrationRequest, admin);
			Enrollment enrollment = caClient.enroll("user2", enrollmentSecret);
			Identity user = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
			wallet.put("user2", user);
			System.out.println("Successfully enrolled user \"user2\" and imported it into the wallet");

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}

}
