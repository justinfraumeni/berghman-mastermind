//MasterMindCodeBreaker.java
//Justin Fraumeni
//jfraumen@u.rochester.edu

import java.util.*;

public class MasterMindCodebreaker implements MasterMindMethods{

	private String [] colors;
	private int positionNumber;
	private String [] currentGeuss;
	private int geussCounter;
	private int [][] responseArray;
	private String [][] geussArray;


	public MasterMindCodebreaker(String [] tokencolors, int positions){

		responseArray = new int[40][2];
		geussCounter = 0;
		colors = tokencolors;
		positionNumber = positions;
		geussArray = new String[40][positions]; 


		geussCounter++;
		geussArray [geussCounter-1] = firstGeuss();
		currentGeuss = firstGeuss();

	}

	public void response(int colorsRightPositionWrong, int colorsAndPositionRight){


		responseArray[geussCounter-1][0] = colorsRightPositionWrong;
		responseArray[geussCounter-1][1] = colorsAndPositionRight;

		geussCounter++;	
		Population testPop = new Population(colors, positionNumber);
		
		geussArray[geussCounter - 1] = testPop.bestGeuss(geussCounter, responseArray, geussArray);
		
		currentGeuss = geussArray[geussCounter -1];	
	}

	
	public void newGame(){

                responseArray = new int[30][2];
                geussCounter = 0;
                geussArray = new String[30][positionNumber]; 

		
		geussCounter++;
                geussArray [geussCounter-1] = firstGeuss();
                currentGeuss = firstGeuss();


	}

	public String [] nextMove(){
		
		return currentGeuss;

	}

	public String [] firstGeuss(){


               String[] firstGeuss = new String[positionNumber];

	       for( int i = 0; i < positionNumber; i++){
	
			if(i < 2) firstGeuss[i] = colors[0];
			if(i >= 2 && i < 4) firstGeuss[i] = colors[1];
			if(i >= 4 && i <6 ) firstGeuss[i] = colors[2];
			if(i == 6) firstGeuss[i] = colors[3];
		}

		return firstGeuss;
	}

}
