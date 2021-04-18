
public abstract class Ship {
	
	int hp; 
	int length; 
	int Xpos; 
	int Ypos; 
	String Name;
	
	public Ship (int Shiphp,int Shiplength,int ShipXpos, int ShipYpos,String Shipname) {
		hp=Shiphp;
		length= Shiplength;
		Xpos=ShipXpos;
		Ypos = ShipYpos;
		Name=Shipname;
	}
	
	public void PrintShipName () {
		System.out.println(Name);
	}

}
