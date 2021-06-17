package tabu_search_lab_exercise;
/*

    Implemented by Abdussamed KACI

*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Utils {
    
    
    
    // We need to write a function that will read the file.
    // We will read the given file and store data in City List.
    public static List<City> readFile(String fileName) throws FileNotFoundException{
        Scanner reader = new Scanner(new File(fileName));
        
        int nbrLine = 0;
        int solutionNumber = 0;
        List<City> cityList = new ArrayList<>();
        
        // The loop will run until the lines in the file are finished.
        while (reader.hasNext()) {
            String nextLine = reader.nextLine(); // Get Next Line
            
            if(nbrLine == 0){ 
                nbrLine++;
                continue;
            } // Skip First Row
            
            // Split Line
            String[] coordinates = nextLine.replace("\n", "").split(" ");
            float xCoord = Float.parseFloat(coordinates[0]); // Get X Coordinate
            float yCoord = Float.parseFloat(coordinates[1]); // Get Y Coordinate
            
            // Create the city based on the information read from the file.
            cityList.add(new City("" + solutionNumber++, xCoord, yCoord)); // Add city
        }
        
        return cityList; // Return read cities from given tsp file
    }
    
    public static float euclideanDistance(City cityOne, City cityTwo){
        // Calculate Euclidean Distance
        float xDistance = (float) Math.pow(cityOne.getX_coord() - cityTwo.getX_coord(), 2);
        float yDistance = (float) Math.pow(cityOne.getY_coord() - cityTwo.getY_coord(), 2);
        return (float) Math.sqrt(xDistance + yDistance);
    }
    
    // get random neighbors of given Solution
    public static List<Solution> getRndNeighbors(Solution givenSolution) throws CloneNotSupportedException{
        List<Solution> neighbors = new ArrayList<>();
        
        int startPoint = 0; // first city
        int endPoint = givenSolution.getRndSolution().size() - 1;   // last city
        for (int i = 0; i < givenSolution.getRndSolution().size(); i++) {
           
            Solution temp = (Solution) givenSolution.clone(); // get clone of given solution, because we dont want change original solution
            // get random index of city without start index and end index
            // because we dont want to change start and end indexes
            int rndCity1 = (startPoint + 1) + (int) (TSP.rnd.nextDouble() * (endPoint - startPoint - 1));
            int rndCity2 = (startPoint + 1) + (int) (TSP.rnd.nextDouble() * (endPoint - startPoint - 1));
            // swap two cities
            Collections.swap(temp.getRndSolution(), rndCity1, rndCity2);
            temp.calculateObjective(); // calculate objective value of this temp solution
            neighbors.add(temp);    // add solution to neighbors
        }
        
        return neighbors; // return neighbors
    }
    public static boolean isStrictlyEqual(List<City> solution1,List<City> solution2){
        for (int i = 0; i < solution1.size(); i++) {
            if(solution1.get(i).getX_coord() != solution2.get(i).getX_coord() 
                    && solution1.get(i).getY_coord() != solution2.get(i).getY_coord()
                    && solution1.get(i).getName() != solution2.get(i).getName()){
                return false;           
            }            
        }
        return true;    
    }
    public static boolean isTabuListContain(Queue<Solution> tabuList, List<City> solution){
        for(Solution tSolution: tabuList){
            if(isStrictlyEqual(tSolution.getRndSolution(), solution))
                return true;
        }
        return false;
    }
}
