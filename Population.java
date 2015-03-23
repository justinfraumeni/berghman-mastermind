//Population.java
//Justin Fraumeni
//jfraumen@u.rochester.edu

import java.util.*;


//This class contains the main genetic algorithm, as well as all relavant methods
public class Population{

	int POPULATIONSIZE = 200;
	Individual [] individuals = new Individual[POPULATIONSIZE];
	String [] individualColors;
	int [][] response;
	String [][] pastGeuss;
	int geussNumber;
	int positions;
        int MAXGEN = 700;
        int MAXSIZE = 300;

	
	//constructor, initializes population with random individuals
	public Population(String [] possibleColors, int positionNumber){

		individualColors = possibleColors;
		positions = positionNumber;
			
		for(int i = 0; i < POPULATIONSIZE; i++ ){//fills the population with random permutations of colors
				
			addRandomIndividual(i);
		}
					
	}

	//calculates the best geuss, using a main loop as described in "Efficent Solutions to MasterMind using Genetic Algorithims
	//Main loop executes depending on constants MAXGEN and MAXSIZE, which can be manipulated to optimize run time and accuracy
	//Every loop represents one generation of individuals, out of every generation, eligable members are drawn for geusses
	public String [] bestGeuss(int geussCount, int [][] responseArray, String geussArray[][]){

		response = responseArray;
                geussNumber = geussCount;
                pastGeuss = geussArray;

                int h = 1;
		ArrayList<Individual> E = new ArrayList<Individual>(); //This list will store our eligable geusses
                int CONSTANTA = 1;
                int CONSTANTB = 2;

		//MAIN LOOP               
		 while(h <= MAXGEN && E.size() <= MAXSIZE){

                      	breedPopulation();
		
			for(int i = 0; i < POPULATIONSIZE; i++){
 
               			int diffBlack = 0;
				int diffWhite = 0;

               			for(int q = 1; q < geussNumber; q++){
                       			diffBlack += CONSTANTA * Math.abs(blackPinsGeuss(individuals[i], q) - response[q-1][0]);

				}			

                               	for(int q = 1; q < geussNumber; q++){

                               		diffWhite += Math.abs(whitePinsGeuss(individuals[i], q) - response[q-1][1]);
                               	}  

				if(diffBlack == 0 || diffWhite == 0){

					boolean exists = false;

					for(Individual I : E){

						if(Arrays.equals(I.getGeuss(), individuals[i].getGeuss())) exists = true;

					}

					if(!exists) E.add(individuals[i]);
					}
				}
                        	h++;
                	}
		
	
		if(E.size() == 0){
			System.out.println("You must have messed up!");
			return null;
		}
		Individual bestGeuss;
		bestGeuss = E.get(0);
		int mostSimilarity = 0;
		int similarity = 0;
			
		for(Individual c : E){

			for(Individual cprime : E){

				if(cprime == c) continue;

				similarity += blackPinCounter(c, cprime) + whitePinCounter(c, cprime);

			}

			if(similarity >= mostSimilarity){
		
				mostSimilarity = similarity;
				bestGeuss = c;	
			}
			
		}
	
		 return bestGeuss.getGeuss(); 
		
	}

	//main fitness function as described in "efficent solutions for mastermind using genetic algorithims"
	//https://lirias.kuleuven.be/bitstream/123456789/184247/2/Mastermind
        public int fitness(Individual c){

		int CONSTANTA = 1;
		int CONSTANTB = 2;
		int fitness = 0;

		for(int q = 1; q < geussNumber; q++){

			fitness += CONSTANTA *( Math.abs(blackPinsGeuss(c, q) - response[q-1][0]) + Math.abs(whitePinsGeuss(c,q) - response[q-1][1]));
		} 
		
		fitness = fitness + CONSTANTB * positions * (geussNumber - 1);	
		
		return fitness;
			
        }


	public int blackPinCounter(Individual c, Individual secret){

             int blackCounter = 0;    

                for(int i = 0; i < positions; i++){

                        if(c.getColor(i).equals(secret.getColor(i))) blackCounter++;
                   
                }    
                return blackCounter;
	}



	//counts white pins given a individual as test and one as secret
	public int whitePinCounter(Individual c, Individual secret){


                int whiteCounter = 0;
                int repeatCount = 1;
                for(int i = 0; i < positions; i++){

                        boolean checkRepeats = false;


                        //dont handle if color has already appeared
                        for(int j = 0; j < i; j++){

                                if(c.getColor(i).equals(c.getColor(j))){
                                        checkRepeats = true;
                                }
                        }

                        if(checkRepeats == true) continue;


                        //count future repeats of colors not handled
                        repeatCount = 1;


                        for(int j = i+1; j < positions; j++){

                                if(c.getColor(i).equals(c.getColor(j))){
                                        repeatCount++;
                                }
                        }


                        for(int j = 0; j < positions; j++){

                                if(repeatCount == 0) break;

                                        if(i != j && c.getColor(i).equals(secret.getColor(j))){

                                                whiteCounter++;
                                                repeatCount--;
                                        }

                                        if(i == j && c.getColor(i).equals(secret.getColor(j))){
                                                repeatCount--;
                                        }


                        }

                }


                return whiteCounter;
	}


	//counts black pins given individual and geuss number
	public int blackPinsGeuss(Individual c, int geuss){

		int blackCounter = 0;
		

		for(int i = 0; i < positions; i++){
			
			if(c.getColor(i).equals(pastGeuss[geuss -1][i])) blackCounter++;					
		}		

		return blackCounter;
	}


	//Counts white pins given and individual and geuss number
	public int whitePinsGeuss(Individual c, int geuss){

		int whiteCounter = 0;
		int repeatCount = 1;
		for(int i = 0; i < positions; i++){

			boolean checkRepeats = false;


			//dont handle if color has already appeared
			for(int j = 0; j < i; j++){

				if(c.getColor(i).equals(c.getColor(j))){
					checkRepeats = true;
				}
			}

			if(checkRepeats == true) continue;

	
			//count future repeats of colors not handled
			repeatCount = 1;

			for(int j = i+1; j < positions; j++){

				if(c.getColor(i).equals(c.getColor(j))){
					repeatCount++;
				}	
			}
			

			for(int j = 0; j < positions; j++){
							
				if(repeatCount == 0) break;
			
					if(i != j && c.getColor(i).equals(pastGeuss[geuss -1][j])){
		
						whiteCounter++;
						repeatCount--;	
					}
				
					if(i == j && c.getColor(i).equals(pastGeuss[geuss-1][j])){
						repeatCount--;
					}
					
			}
		
		}		
		

		return whiteCounter;
	}


	//this function creates the next generation of codes with crossover, mutation, permutation, and inversion methods
	//it utilizes roulette wheel selection with a normalized bubble sort to choose fittest parents for the crossover
	public void breedPopulation(){


		Individual[] nextGen = new Individual[POPULATIONSIZE];
		double randomNum;		
		Individual mommy = null;
		Individual daddy = null;
		Individual  children;
		double [] fitnessArray = new double[POPULATIONSIZE];
		double totalFitness = 0;

		//Roulette wheel selection
		for(int i = 0; i < POPULATIONSIZE; i++){
			fitnessArray[i] = (double) fitness(individuals[i]);
			totalFitness = totalFitness + fitnessArray[i]; 
		}
			
		for(int i = 0; i < POPULATIONSIZE; i++){
			fitnessArray[i] = fitnessArray[i]/totalFitness;
		}

		//bubble sort in least to greatest order
		boolean swap;
		Individual tempInd;
		double tempFit;

		do{		
			swap = false;

			for(int i = 1; i < POPULATIONSIZE; i++){
				
				if(fitnessArray[i] > fitnessArray[i-1]){
				
					tempInd = individuals[i];
					tempFit = fitnessArray[i];
					individuals[i] = individuals[i-1];
					fitnessArray[i] = fitnessArray[i-1];
					individuals[i-1] = tempInd;
				        fitnessArray[i-1] = tempFit;
					swap = true;
				}

			}

		}while(swap);
		
		
		//normalize
		
		totalFitness = 0;

		for(int i = 0; i<POPULATIONSIZE; i++){

			totalFitness+=fitnessArray[i];
			fitnessArray[i] = totalFitness;

		}

		fitnessArray[POPULATIONSIZE-1] = 1;

		for(int i = 0; i < POPULATIONSIZE; i++){

		
			randomNum = Math.random();
			

			for(int j = 0; j < POPULATIONSIZE; j++){

				if(fitnessArray[j] >= randomNum){
					mommy = individuals[j];
					break;
				}
			}

			randomNum = Math.random();

			for(int j = 0; j < POPULATIONSIZE; j++){
				
				if(fitnessArray[j] >= randomNum){
					daddy = individuals[j];
					break;
				}
			}

			
			if(Math.random() >= .5){

				children = onePointCrossover(mommy, daddy);
				nextGen[i] = children;

			}else{

                                children = twoPointCrossover(mommy, daddy);
                                nextGen[i] = children;
			}		
		}
		
		individuals = nextGen;
		
		for(int i = 0; i < POPULATIONSIZE; i++){
			
			if(individualExists(individuals[i])) addRandomIndividual(i); 
		}		

		scramblePopulation();
		mutate();
		permutation();
		inversion();
	}


	//one point crossover with two parents chosen via roulette
	public Individual onePointCrossover(Individual mom, Individual dad){

		int random;
		String [] colors = new String[positions];
		Individual child;

		random = (int)(Math.random() *(positions));
		

                for(int i = 0; i < positions; i++){

                        if(i < random) colors[i] = mom.getColor(i);
                        if(i >= random) colors[i] = dad.getColor(i);
                }

         
                child = new Individual(colors);

		return child;

	}


	//two point crossover with two parents selected by roulette
	public Individual twoPointCrossover(Individual mom, Individual dad){

		int random1;
		int random2;
		int temp;
		String [] colors = new String[positions];
		Individual child;	

		random1 = (int)(Math.random() * (positions));

		do{
			random2 = (int)(Math.random() *(positions));	
	
		}while(random2 == random1);

		if(random1 > random2){

			temp = random1;
			random1 = random2;
			random2 = temp;
		}
	
		for(int i = 0; i < positions; i++){

			if(i <= random1 && i < random2) colors[i] = mom.getColor(i);
			if(i > random1 && i <= random2) colors[i] = dad.getColor(i);
			if(i > random1 && i > random2) colors[i] = mom.getColor(i);
				
		}

		child = new Individual(colors);

		return child;	
	}

	//scrambles the population
	public void scramblePopulation(){

		int index1;
		int index2;
		Individual temp;
		
		for(int i = 0; i < 500; i++){
			index1 = (int)(Math.random() * POPULATIONSIZE);
			
			do{
				index2 = (int)(Math.random() * POPULATIONSIZE);
			}while(index1 == index2);
			
			temp = individuals[index1];
			individuals[index1] = individuals[index2];
			individuals[index2] = temp;
		}

	}

		
	//mutates an individual with probability .03
	public void mutate(){
		
		int random;
		int randomIndex;
		Individual test;

		String randomColor;

		for(int i = 0; i < POPULATIONSIZE; i++){

			random = (int)(Math.random()*(100+1));
		
			if(random <= 3){ //mutation acts with probability .03
			
				randomIndex = (int)(Math.random() * (positions));			
				randomColor = individualColors[(int)(Math.random() * individualColors.length)];
				test = new Individual(individuals[i].getGeuss());
				test.changeColor(randomIndex, randomColor);
	
				if(individualExists(test)){
					 addRandomIndividual(i);						
				}else{
					individuals[i].changeColor(randomIndex, randomColor);
				}
			}
			
		}
		

	}

		
	//reverses order of colors between two randomly selected points in a individual with probability .02
	public void inversion(){

                int random;
		int tempo;
                int randomIndex1;
                int randomIndex2;
                String temp;
 		Individual added;  
                for(int i = 0; i < POPULATIONSIZE; i++){

                        random = (int)(Math.random()*(100+1));
    
                        if(random <= 2){ //inversion acts with probability .02
    				

				added = new Individual(individuals[i].getGeuss());
                                randomIndex1 = (int)(Math.random()*(positions));

                                do{
				
					randomIndex2 = (int)(Math.random()*(positions));

                                }while(randomIndex2 == randomIndex1);

                         	if(randomIndex1 < randomIndex2){
					tempo = randomIndex1;
					randomIndex1 = randomIndex2;
					randomIndex2 = tempo;
				}	  
                             
				while(randomIndex1 < randomIndex2){
					
					temp = added.getColor(randomIndex1);
					added.changeColor(randomIndex1, added.getColor(randomIndex2));
					added.changeColor(randomIndex2, temp);

					randomIndex1++;
					randomIndex2--;
				}

	                        if(individualExists(added)){

                        	         addRandomIndividual(i);
                	        }else{

        	                        individuals[i] = added;
	                        }

                        }   

		}     
	}

	//checks to see if the individual exists in the population
	public boolean individualExists(Individual testSubject){

		boolean alreadyExists;

		for(int i = 0; i < POPULATIONSIZE; i++){

			if(testSubject != individuals[i] && individuals[i] != null && Arrays.equals(testSubject.getGeuss(), individuals[i].getGeuss())){
		
				alreadyExists = true;
      				break;
			}
		
		}

		alreadyExists = false;
		return alreadyExists;
	 	
	}
    	
	//adds a ranom individual at the specified indice
	public void addRandomIndividual(int indice){

		int randomIndex;
		Individual newIndividual;

                String [] random = new String[positions];

		do{
	                for(int j = 0; j < positions; j++){

                                randomIndex = (int)(Math.random() * (individualColors.length));
                                random[j] = individualColors[randomIndex];
	                  }

	                newIndividual = new Individual(random);

		}while(individualExists(newIndividual));

		individuals[indice] = newIndividual;	
		
	}

	//interchanges two random color indices of an individual in the populaion with probability .03
	public void permutation(){

      	        int random;
		int randomIndex1;
		int randomIndex2;
      		Individual added;
		String tempColor;
   
                for(int i = 0; i < POPULATIONSIZE; i++){

                        random = (int)(Math.random()*(100+1));
    
                        if(random <= 3){ //permutation acts with probability .03
    
	
				randomIndex1 = (int)(Math.random()*(positions));
	
				do{
					randomIndex2 = (int)(Math.random()*(positions));

				}while(randomIndex2 == randomIndex1);
        
				added = new Individual(individuals[i].getGeuss());
				tempColor = added.getColor(randomIndex1);
				added.changeColor(randomIndex1, added.getColor(randomIndex2));
				added.changeColor(randomIndex2, tempColor);
			
	
				if(individualExists(added)) addRandomIndividual(i);
				 individuals[i] = added;				
                        }   
                            

                }   
      

	}
   

}
