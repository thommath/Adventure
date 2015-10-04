

class Object{
	String name;
	String description = "It is mine! Just mine! Mine forever! My precious!";
	int hp = 0;
	int damage = 0;
	int bonusDamage = 0;
	int bonusArmor = 0;
	boolean breakable = false;
	
	public Object(String name){
		this.name = name;
	}
	public Object(){
	}
	
	public void use(){
		System.out.println("You don't understand how to use a " + name);
	}
	
	public void damage(int dmg){
		if(breakable){
			hp -= damage;
			if(hp < 0){
				System.out.println("You destroyed " + name);
			}
		}else{
			System.out.println("You hit it, but you can't break it");
		}
	}
	
	public void wield(){
		if(damage > 0){
			System.out.println("You are wielding the " + name);
		}else{
			System.out.println("You can't wield a " + name);
		}
	}
	
	public void setDamage(int n){
		damage = n;
	}
	
	public void inspect(){
		System.out.println(description);
	}
	
	public void destroy(){
		System.out.println("Well done! Now it is broken");
		name = "broken " + name;
	}
	
	public void setBonusArmor(int amount){
		bonusArmor = amount;
	}
	public void setBonusDamage(int amount){
		bonusDamage = amount;
	}
	
	public void setDescription(String desc){
		description = desc;
	}
	
	public void setHp(int hp){
		this.hp = hp;
		breakable = true;
	}
}