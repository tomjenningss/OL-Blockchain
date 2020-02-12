# Open Liberty Blockchain Client

## Description

Experience using Open Liberty to commmunicate to a Blockchain Network. 

Where you can:

* Enroll Users.

* Add Cars to the ledger.

* Query all items from the ledger.

* Query specific items from the ledger


The most difficult bit is setting up the Hyperledger Network. 

## Set up the Network:

`cd fabric-samples/` 

Install the Hyperledger Fabric platform-specific binaries and config files for the version specified into the /bin and /config directories of fabric-samples

`curl -sSL https://bit.ly/2ysbOFE | bash -s`

The command above downloads and executes a bash script that will download and extract all of the platform-specific binaries you will need to set up your network and place them into the cloned repo you created above. It retrieves the following platform-specific binaries:

`configtxgen`,
`configtxlator`,
`cryptogen`,
`discover`,
`idemixgen`
`orderer`,
`peer`,
`fabric-ca-client`

and places them in the bin sub-directory of the current working directory.

Export the path you have just downloaded

`export PATH=<path to download location>/bin:$PATH`

EG: `export /Users/thomas.jennings@ibm.com/Documents/blockchain-network-new/fabric-samples/bin:$PATH`

## Bring up the test network

`cd fabric-samples/test-network`

Run the following command to remove any containers or artifacts from any previous runs:

`./network.sh down`

You can then bring up the network by issuing the following command. You will experience problems if you try to run the script from another directory:

`./network.sh up`

When running the network locally, it runs in Docker containers, so you can prove that it is up by running:

`docker ps`

## Create Channel

Use the network.sh script to create a channel between Org1 and Org2 and join their peers to the channel. Run the following command to create a channel with the default name of mychannel:

`./network.sh createChannel`

## Deploy Chaincode

After you have created a channel, you can start using smart contracts to interact with the channel ledger. Smart contracts contain the business logic that governs assets on the blockchain ledger.

After you have used the network.sh to create a channel, you can start a chaincode on the channel using the following command:

`./network.sh deployCC`

If you have had any problems with building the network refer to:

`https://github.com/hyperledger/fabric-samples/tree/master`


# Now for the fun part

## Start interacting with the Blockchain Network

Head to the `finish` directory in the Blockchain RESTful service

`cd ../../blockchain-restful-service/finish/`

## Start up the Open Liberty Server

`mvn liberty:run`

## Test that the server is running

Open up a browser of your choice eg Chrome

Hit the RESTful endpoint of `Hello world` which will return 

`Hello World`

`http://localhost:9080/LibertyProject/System/helloworld`


## Query what is already on the Ledger:

The blockchain network has cars already on the network and you can view them by hitting the `QueryCar/AllCars` endpoint:

`http://localhost:9080/LibertyProject/System/QueryCar/AllCars`

and you can check the OL terminal with the returned cars and it will also display on the webserver:

```json
Queried all Cars Successfully.
Cars:
[{"Key":"CAR0","Record":{"make":"Toyota","model":"Prius","colour":"blue","owner":"Tomoko"}},{"Key":"CAR1","Record":{"make":"Ford","model":"Mustang","colour":"red","owner":"Brad"}},{"Key":"CAR10","Record":{"make":"VW","model":"Polo","colour":"Grey","owner":"Mary"}},{"Key":"CAR11","Record":{"make":"VW","model":"Polo","colour":"P!ink","owner":"Mary"}},{"Key":"CAR2","Record":{"make":"Hyundai","model":"Tucson","colour":"green","owner":"Jin Soo"}},{"Key":"CAR3","Record":{"make":"Volkswagen","model":"Passat","colour":"yellow","owner":"Max"}},{"Key":"CAR4","Record":{"make":"Tesla","model":"S","colour":"black","owner":"Adriana"}},{"Key":"CAR5","Record":{"make":"Peugeot","model":"205","colour":"purple","owner":"Michel"}},{"Key":"CAR6","Record":{"make":"Chery","model":"S22L","colour":"white","owner":"Aarav"}},{"Key":"CAR7","Record":{"make":"Fiat","model":"Punto","colour":"violet","owner":"Pari"}},{"Key":"CAR8","Record":{"make":"Tata","model":"Nano","colour":"indigo","owner":"Valeria"}},{"Key":"CAR9","Record":{"make":"Holden","model":"Barina","colour":"brown","owner":"Shotaro"}}]
```

## Add a car to the Ledger

Open up a new terminal window

Create a POST request to add to the ledger:

`curl -X POST "http://localhost:9080/LibertyProject/System/AddCar?make=Volkswagon&model=Golf&colour=white&owner=Tom"`


<img src="images/built-on-openliberty.png" alt="drawing" width="200" align="right">
<img src="images/hyperledger_image.png" alt="drawing" width="200" align="left">

