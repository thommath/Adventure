

class Location{
  Location north, south, east, west, up, down;
  Object[] items;
  String name;
  String description;
  Enemy enemy;
  Player p;
  boolean firstVisit = true;

  //Setter de nodvendige variablene
  Location(Object[] items, String description, Player p){
    this.items = items;
    this.description = description;
    this.p = p;
  }
  Location(String name, Player p){
    this.p = p;
	this.name = name;
  }
  
  //Oppdaterer feks fiende
  public void update(){
	  if(enemy != null && !firstVisit){
		  if(enemy.isDead()){
			  addItem("The corpse of an " + enemy.name);
			  enemy = null;
		  }else{
			  enemy.hit(p);
		  }
	  }
	  if(firstVisit){
		  firstVisit = false;
	  }
  }

  //Legger til en kobling til et annet sted
  public void addLocation(String rettning, Location location){
    if(rettning.equalsIgnoreCase("north")){
      north = location;
    }if(rettning.equalsIgnoreCase("south")){
      south = location;
    }if(rettning.equalsIgnoreCase("east")){
      east = location;
    }if(rettning.equalsIgnoreCase("west")){
      west = location;
    }if(rettning.equalsIgnoreCase("up")){
      up = location;
    }if(rettning.equalsIgnoreCase("down")){
      down = location;
    }
  }

  //Legger til et item
  public void addItem(String item){
	if(items == null){
		items = new Object[1];
		items[0] = new Object(item);
		return;
	}
    Object[] temp = new Object[items.length +1];
    for(int n = 0; n < items.length; n++){
      temp[n] = items[n];
    }
    temp[temp.length-1] = new Object(item);
    items = temp;
  }
  public void addItem(Object item){
	if(items == null){
		items = new Object[1];
		items[0] = item;
		return;
	}
    Object[] temp = new Object[items.length +1];
    for(int n = 0; n < items.length; n++){
      temp[n] = items[n];
    }
    temp[temp.length-1] = item;
    items = temp;
  }

  //Fjerner et item
  public void removeItem(String item){
    if(hasItem(item)){
		if(items.length == 1){
			items = null;
			return;
		}
		
		Object[] temp = items;
		items = null;
      
		for(int n = 0; n < temp.length; n++){
			if(!temp[n].name.equalsIgnoreCase(item)){
				addItem(temp[n]);
			}
		}
    }
  }

  //Sjekker om et item er her
  public boolean hasItem(String item){
    if(items == null){
      return false;
    }
    for(int n = 0; n < items.length; n++){
      if(item.equalsIgnoreCase(items[n].name)){
        return true;
      }
    }
    return false;
  }

  
  
  public void addDescription(String s){
	  description = s;
  }

  //Skriv ut velkomsmelding
  public void greet(){
    System.out.println("\n" + description);
    if(enemy != null){
		System.out.println("There is an " + enemy.name + " here");
	}
    if(items != null){
      for(Object o : items){
        System.out.println("You see " + o.name);
      }
    }else{
		System.out.println("There is northing here");
	}
  }

}