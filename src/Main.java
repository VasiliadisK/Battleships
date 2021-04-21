
public class Main {

	public static void main(String[] args) {
		Ship[] ships = new Ship[5];
		AircraftCarrier a = new AircraftCarrier(0,0,true,"AircraftCarrier",5);
		Battleship b = new Battleship(0,1,true,"Battleship",4);
		Cruiser c = new Cruiser(0,2,true,"Cruiser",3);
		Submarine s = new Submarine(0,3,true,"Submarine",3);
		Destroyer d = new Destroyer(0,4,true,"Destroyer",2);
		
		ships[0] = a;
		ships[1] = b;
		ships[2] = c;
		ships[3] = s;
		ships[4] = d;
		
		new GUI(ships);
	}

}
