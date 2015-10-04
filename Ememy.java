
class Enemy extends Object{

  public Enemy(String name, int hp, int damage, int armor){
    this.name = name;
    this.hp = hp;
	breakable = true;
    this.damage = damage;
    this.bonusArmor = armor;
  }
  
  public Enemy(String name, int hp){
    this.name = name;
    this.hp = hp;
	breakable = true;
  }

  //Prov a sla til spilleren
  public void hit(Player s){
    int power = (int)(Math.random()*21)-1;
    if(power == 20){
      System.out.println("The " + name + " in the head. You recieve double damage and a headache");
      s.damage(damage*2);
    }else if(power == 0){
      System.out.println("The " + name + " hits himself in the foot and recieve " + damage + " damage");
      damage(damage);
    }else if(power <= s.armor){
      System.out.println("Your armor deflects the hit");
    }else{
      s.damage(damage);
      System.out.println("The " + name + " hits and does " + damage + " damage");
    }
  }
  
  //Ta skade og skriv ut melding hvis jeg dor
  public void damage(int damage){
    hp-= damage;
    if(hp < 0){
    	System.out.println("You deal a final blow to the " + name + " and i falls to the ground");
    }
  }

  //Er jeg dod?
  public boolean isDead(){
    return hp<0;
  }
}