import javax.swing.*;

public class Player {
	private String name;
	private Ship[] ships = new Ship[5];
	private boolean placedShips = false;
		
		public Player(){
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
	}
		
		public Ship[] getShips() {
			return ships;
		}
		
		
		public boolean isPlacedShips() {
			return placedShips;
		}

		
		public void setPlacedShips(boolean placedShips) {
			this.placedShips = placedShips;
		}

		
		public boolean hasShips() {
			int counter = 0;
			for(Ship ship: ships) {
				if(ship.isDead()==true)
					counter++;
				if(counter == 5)
					return false;
				
			}
			return true;
		}

}
