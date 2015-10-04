

class Player{
  int hp = 10;
  int armor = 0;
  Object[] bag = new Object[10];
  String name;
  Location location;

  Player(String name){
    this.name = name;
  }

  //Prov a skade fienden
	public void hit(Object target, Object weapon){
		if(weapon.damage <= 0){
			System.out.println("You can't use a " + weapon.name + " as a weapon");
		}else{
			int power = (int)(Math.random()*21)-1;
    
			if(power == 20){
				System.out.println("You hope for the best and lay all your strength in your hit. You deal double damage");
				target.damage(weapon.damage*2);
				
			}else if(power == 0){
				System.out.println("You get distracted by a thought and get hit by the " + target.name);
			
				damage(target.damage);
			}else if(power <= target.bonusArmor){
				System.out.println("You missed, but it was awsome!");
		
			}else{
				target.damage(weapon.damage);
				System.out.println("You hit and did " + weapon.damage + " damage");
			}
		}
	}

  //Ta skade
  public void damage(int damagee){
    hp-=damagee;
  }

  //Er jeg dod?
  public boolean isDead(){
    return hp<0;
  }

  //Flytt i rettningen s hvis mulig
  public void go(String s){
    if(s.equalsIgnoreCase("north")){
      if(location.north != null){
        move(location.north);
      }else{
        System.out.println("You can't go that way");
      }
    }else if(s.equalsIgnoreCase("east")){
      if(location.east != null){
        move(location.east);
      }else{
        System.out.println("You can't go that way");
      }

    }else if(s.equalsIgnoreCase("west")){
      if(location.west != null){
        move(location.west);
      }else{
        System.out.println("You can't go that way");
      }

    }else if(s.equalsIgnoreCase("south")){
      if(location.south != null){
        move(location.south);
      }else{
        System.out.println("You can't go that way");
      }

    }
  }

  //Endre posisjon og fa velkomstmelding
  public void move(Location location){
    this.location = location;
    location.greet();
  }

  //Legg et element pa stedet i sekken(eller vapen eller armor) og fjern det fra stedet
  public void take(String ting){
      if(location.hasItem(ting)){
    	  if(addItem(ting)){
    		  location.removeItem(ting);
    	  }
      }else{
    	  System.out.println("You see no such thing");
      }
  }
  
  //Legg til en ting i baggen
  public boolean addItem(String item){
	  for(int n = 0; n < bag.length; n++){
		  if(bag[n] == null){
			  bag[n] = new Object(item);
			  System.out.println("Added " + item + " to inventory");
			  return true;
		  }
	  }
	  System.out.println("Your bag is full");
	  return false;
  }
  
  //Fjern noe fra baggen
  public void removeItem(String item){
	  if(bag.length == 1){
		  bag = null;
		  return;
	  }
	  
	  for(int n = 0; n < bag.length; n++){
		  if(bag[n] == null){
			  continue;
		  }
		  if(bag[n].name.equalsIgnoreCase(item)){
			  location.addItem(bag[n]);
			  bag[n] = null;
			  System.out.println("You drop " + item);
			  return;
		  }
	  }
	  System.out.println("You don't possess that item");
  }

  public Object getItem(String item){
	  for(Object o : bag){
		  if(o.name.equalsIgnoreCase(item)){
			  return o;
		  }
	  }
	  return null;
  }
  
  
  //Skriv ut hvordan jeg har det
  public void status(){
	  System.out.println("Name: " + name);
	  System.out.println("Hp: " + hp);
	  System.out.println("Armor: " + calculateArmor());
	  if(bag != null){
		  System.out.println("Items: ");
		  for(Object o : bag){
			  if(o != null){
				  System.out.println(o.name + " - " + o.description);
			  }
		  }
	  }
  }
  
  public int calculateArmor(){
	  int sum = 0;
	  for(Object o : bag){
		  if(o != null){
			  sum += o.bonusArmor;
		  }
	  }
	  return sum;
  }
  
}