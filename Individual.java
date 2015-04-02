//Individual.java
//Justin Fraumeni
//jfraumen@u.rochester.edu



//This class represents a single code in the population
public class Individual{

	String [] geussArray;

	public Individual(String [] geuss){

		geussArray = geuss;
	}

	public void changeColor(int indice, String newColor){

		geussArray[indice] = newColor;
	}

	public String getColor(int indice){

		return geussArray[indice];
	}

	public String[] getGeuss(){

		return geussArray;
	}
}
