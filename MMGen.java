//Justin Fraumeni
//jfraumen@u.rochester.edu

import java.util.Scanner;
public class MMGen {

	public static void main(String args []){
		
		Scanner gameScan = new Scanner(System.in);
		String [] colors;
		System.out.println("WELCOME TO MASTERMIND");
		
		while(true){
		
			int positions = 0;
			System.out.println("How many positions?: (2-6)");	
			positions = gameScan.nextInt();
			while(positions > 6 || positions <=2){
				System.out.println("Number must be between 2 and 6!");
				positions = gameScan.nextInt();
			
			}

			String line;
			gameScan.nextLine();
			System.out.println("Enter 2-8 different colors, seperated by spaces!:");
			line = gameScan.nextLine();
			colors = line.split(" ");
			while(colors.length > 8 || colors.length < 2){
				System.out.println("Enter between 2 and 8, seperate with a SINGLE space:");
				line = gameScan.nextLine();
				colors = line.split(" ");
			}

			MasterMindCodebreaker gameBreaker = new MasterMindCodebreaker(colors, positions);
			
			String playAgain;
			while(true){

			
				int blacks = 0;
				int whites = 0;
				while(positions != blacks){
					
					System.out.print("Geuss:");
					for(String s : gameBreaker.nextMove()){
						System.out.print(" " + s); 
					}
					System.out.println();
					System.out.println("How many are the right color, right position?");
					blacks = gameScan.nextInt();
					gameScan.nextLine();
					System.out.println("How many are the right color, but in the wrong spot?");
					whites = gameScan.nextInt();
					gameScan.nextLine();
					gameBreaker.response(blacks, whites);
					if(gameBreaker.nextMove() == null) break;

				}
			
				System.out.println("Do you want to play again? (Y/N)");
				playAgain = gameScan.nextLine();
				if(!playAgain.equals("Y")) break;
				gameBreaker.newGame();			
			}
			System.out.println("Do you want to play again with new parameters? (Y/N)");
			playAgain = gameScan.nextLine();
			if(!playAgain.equals("Y")) break;
		}
		System.out.println("Goodbye!");
	}

}
