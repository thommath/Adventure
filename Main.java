
/*
  Lage et enkelt rollespill med:
    En karakter
      ga til andre location
      plukke opp "objekter"
    Ett eller flere monstere
    To eller flere locations
    
	
    Det ble litt stort, men jeg har ikke brukt noen vanskelige eller merkelige ting. 
    Jeg fikk lyst til a lage noe morsomt sa da ble det litt.... stort, beklager, men haper du har det litt goy nar du spiller ^^
    Du trenger ikke sette deg inn i det siden det er litt mye
*/

import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.lang.Exception;

/*
 * Lager playeren
 * Lager kartet
 * Har kjorelokke
 * 	hent command
 * 	utfor command
 * 	oppdater locationmet
 */

class Main{
	
  static Queue<Location> location = new LinkedList<Location>();
	
  public static void main(String[] args){
	  //Lager variabler og sier velkommen osv... 
    Scanner in = new Scanner(System.in);

    System.out.println("Welcome to adventure!\nPlease enter a name");

    Player player = new Player(in.nextLine());
    
	try{
		createLocation(player);
	}catch(Exception e){
		System.out.println("Error reading from file");
		return;
	}
    System.out.println("Welcome " + player.name + "!");
    player.move(findLocationByName("Start"));

    
    //Kjorelokke
    while(player.isDead() != true){
    	
      String[] command = in.nextLine().split(" ");

      //Sjekker inputen mot de forskjellige mulighetene og utforer en funksjon
      if(command[0].equalsIgnoreCase("go")){
        if(command.length >= 1){
          player.go(command[1]);
        }else{
          System.out.println("Wrong arguments");
        }
      }else if(command[0].equalsIgnoreCase("hit")){
		if(command.length >= 4){
			if(player.location.enemy != null){
				player.hit(player.location.enemy, player.getItem(command[3]));
			}
		}else{
			System.out.println("hit what with what?");
		}
      }else if(command[0].equalsIgnoreCase("take")){
        if(command.length >= 1){
          player.take(command[1]);
        }else{
          System.out.println("Wrong arguments");
        }
      }else if(command[0].equalsIgnoreCase("status")){
    	  player.status();
      }else if(command[0].equalsIgnoreCase("drop")){
    	  if(command.length >= 1){
              player.removeItem(command[1]);
            }else{
              System.out.println("Wrong arguments");
            }
      }else if(command[0].equalsIgnoreCase("message")){
    	  player.location.greet();
      }else if(command[0].equalsIgnoreCase("help") || command[0].equalsIgnoreCase("?")){
    	  help();
      }else if(command[0].equalsIgnoreCase("exit") || command[0].equalsIgnoreCase("quit")){
    	  System.exit(0);
      }else{
    	  System.out.println("I don't understand " + command[0] + ", enter help eller ? for help");
      }
      //Oppdaterer stedet du er pa
      player.location.update();
    }
    
    //Spilleren dode og far en trist melding
    System.out.println("You took one blow too many and lay down on the floor to sleep...");
  }
  
  //Skriver ut hjelpetekst
  static void help(){
	  System.out.println("Write command og arguments");
	  System.out.println();
	  System.out.println("'help' or '?' for help");
	  System.out.println("'go' 'direction' to move");
	  System.out.println("'hit' [enemy] to kill someone");
	  System.out.println("'take' 'objekt' to pick something up");
	  System.out.println("'drop' 'objekt' to drop something");
	  System.out.println("'status' How do I do?");
	  System.out.println("'message' how does this place look again?");
	  System.out.println("'exit' or 'quit' I'm tired");
  }

  //Lager mange instanser av Location og setter verdier i dem
  static void createLocation(Player p) throws Exception{
	  Scanner file = new Scanner(new File("../map.txt"));
	  
	  Location loc = new Location("", p);
	  loc = null;
	  
	  String[][] s = new String[1000][3];
	  int sc = 0;
	  
	  while(file.hasNextLine()){
		  String in = file.nextLine();
		  String[] splittet = in.split(" ");
		  if(splittet[0].equalsIgnoreCase("new")){
			  if(loc != null){
				  location.add(loc);
				  loc = new Location(splittet[1], p);
				  
			  }else{
				  loc = new Location(splittet[1], p);
			  }
		  }else if(splittet[0].equalsIgnoreCase("items")){
			  for(int n = 1; n < splittet.length; n++){
				loc.addItem(splittet[n]);
			  }
		  }else if(splittet[0].equalsIgnoreCase("desc")){
			  loc.addDescription(in.substring(5, in.length()));
		  }else if(	splittet[0].equalsIgnoreCase("north") || 
					splittet[0].equalsIgnoreCase("south") ||
					splittet[0].equalsIgnoreCase("east") ||
					splittet[0].equalsIgnoreCase("west") || 
					splittet[0].equalsIgnoreCase("up") || 
					splittet[0].equalsIgnoreCase("down")){
						s[sc][0] = splittet[0];
						s[sc][1] = loc.name;
						s[sc][2] = splittet[1];
						sc++;
		  }
	  }
	  location.add(loc);
	  file.close();
	  
	  for(int n = 0; n < sc; n++){
		  if(s[n][0].equalsIgnoreCase("north")){
			  findLocationByName(s[n][1]).addLocation("north", findLocationByName(s[n][2]));
		  }else if(s[n][0].equalsIgnoreCase("south")){
			  findLocationByName(s[n][1]).addLocation("south", findLocationByName(s[n][2]));
		  }else if(s[n][0].equalsIgnoreCase("east")){
			  findLocationByName(s[n][1]).addLocation("east", findLocationByName(s[n][2]));
		  }else if(s[n][0].equalsIgnoreCase("west")){
			  findLocationByName(s[n][1]).addLocation("west", findLocationByName(s[n][2]));
		  }else if(s[n][0].equalsIgnoreCase("up")){
			  findLocationByName(s[n][1]).addLocation("up", findLocationByName(s[n][2]));
		  }else if(s[n][0].equalsIgnoreCase("down")){
			  findLocationByName(s[n][1]).addLocation("down", findLocationByName(s[n][2]));
		  }
	  }
  }
  
  private static Location findLocationByName(String name){
	  for(Location loc : location){
		  if(loc.name.equalsIgnoreCase(name)){
			  return loc;
		  }
	  }
	  return null;
  }
  
}


