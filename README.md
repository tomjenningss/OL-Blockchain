# Open Liberty Blockchain Client

## Prerequisites:

* Java
* Git
* Maven

## Description

Experience using Open Liberty to commmunicate to a Blockchain Network. 

Where you can:

* Enroll Users.

* Add Cars to the ledger.

* Query all items from the ledger.

* Query specific items from the ledger


# Now for the fun part

## Start interacting with the Blockchain Network

Head to the `finish` directory in the `Blockchain-restful-service` directory:

`cd ../../blockchain-restful-service/finish/`

## Start up the Open Liberty Server:

`mvn liberty:run`

## Test that the server is running:

Open up a browser of your choice eg Chrome

Hit the RESTful endpoint of `Hello world` which will return 

`Hello World`

`http://localhost:9080/LibertyProject/System/helloworld`


## Query what is already on the ledger:

The blockchain network has cars already on the network and you can view them by hitting the `QueryCar/AllCars` endpoint:

`http://localhost:9080/LibertyProject/System/QueryCar/AllCars`

and you can check the OL terminal with the returned cars and it will also display on the webserver:

```json
Queried all Cars Successfully.
Cars:
[{"Key":"CAR0","Record":{"make":"Toyota","model":"Prius","colour":"blue","owner":"Tomoko"}},{"Key":"CAR1","Record":{"make":"Ford","model":"Mustang","colour":"red","owner":"Brad"}},{"Key":"CAR10","Record":{"make":"VW","model":"Polo","colour":"Grey","owner":"Mary"}},{"Key":"CAR11","Record":{"make":"VW","model":"Polo","colour":"P!ink","owner":"Mary"}},{"Key":"CAR2","Record":{"make":"Hyundai","model":"Tucson","colour":"green","owner":"Jin Soo"}},{"Key":"CAR3","Record":{"make":"Volkswagen","model":"Passat","colour":"yellow","owner":"Max"}},{"Key":"CAR4","Record":{"make":"Tesla","model":"S","colour":"black","owner":"Adriana"}},{"Key":"CAR5","Record":{"make":"Peugeot","model":"205","colour":"purple","owner":"Michel"}},{"Key":"CAR6","Record":{"make":"Chery","model":"S22L","colour":"white","owner":"Aarav"}},{"Key":"CAR7","Record":{"make":"Fiat","model":"Punto","colour":"violet","owner":"Pari"}},{"Key":"CAR8","Record":{"make":"Tata","model":"Nano","colour":"indigo","owner":"Valeria"}},{"Key":"CAR9","Record":{"make":"Holden","model":"Barina","colour":"brown","owner":"Shotaro"}}]
```

## Query specific items on the ledger:

There is added functionality to Query specific cars on the ledger. Once all the cars are displayed on the ledger you can query by `CarByKeyID`.

For example query `CAR5` and see the details of it:

`http://localhost:9080/LibertyProject/System/QueryCar/CarByKeyID?Key=CAR5`

```json

Queried car Successfully. 
Key = CAR5
Details = {"make":"Peugeot","model":"205","colour":"purple","owner":"Michel"}

```

Query any car on the ledger by:

`http://localhost:9080/LibertyProject/System/QueryCar/CarByKeyID?Key=<ID>`


## Add a car to the ledger:

Open up a new terminal window

Create a POST request to add to the ledger:

`curl -X POST "http://localhost:9080/LibertyProject/System/AddCar?make=Volkswagon&model=Golf&colour=white&owner=Tom"`

<br>
<br>

<img src="images/built-on-openliberty.png" alt="drawing" width="200" align="right"> 
<img src="images/hyperledger_image.png" alt="drawing" width="200" align="left">

