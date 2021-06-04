import javax.swing.*;

public class Player {
	private String name;
	private Ship[] ships = new Ship[5];
	private boolean placedShips = false;
	boolean hasDouble,hasHeal,hasTorpedo,hasKamikaze;
	
		public Player(){
			AircraftCarrier a = new AircraftCarrier(0,0,true,"AircraftCarrier",5);
			Battleship b = new Battleship(0,1,true,"Battleship",4);
			Cruiser c = new Cruiser(0,2,true,"Cruiser",3);
			Submarine s = new Submarine(0,3,true,"Submarine",3);
			Destroyer d = new Destroyer(0,4,true,"Destroyer",2);
			hasDouble = hasHeal = hasTorpedo = hasKamikaze = true;
			
			ships[0] = a;
			ships[1] = b;
			ships[2] = c;
			ships[3] = s;
			ships[4] = d;
	}
		
		public Ship[] getShips() {
			return ships;
		}
		
		public Ship getShip(int number) {
			return ships[number];
		}
		
		
		public boolean isPlacedShips() {
			return placedShips;
		}

		
		public void setPlacedShips(boolean placedShips) {
			this.placedShips = placedShips;
		}
		
		public void setHasDouble(boolean hasDouble) {
			this.hasDouble = hasDouble;
		}

		public void setHasHeal(boolean hasHeal) {
			this.hasHeal = hasHeal;
		}

		public void setHasTorpedo(boolean hasTorpedo) {
			this.hasTorpedo = hasTorpedo;
		}

		public void setHasKamikaze(boolean hasKamikaze) {
			this.hasKamikaze = hasKamikaze;
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
