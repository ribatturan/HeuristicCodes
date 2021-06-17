package week_6_lab_exercise;

import java.io.FileNotFoundException;
import java.util.List;
/* 

7. If new solution worse than current (‘s’);
a. Accept new solution with �
!∆
#$ probability
8. Repeat 4-7 steps until the number of maximum inner iterations
9. Reduce the temperature T by cooling_rate
10. Repeat 4-9 steps until the T > 1 condition satisfied.
*/


public class TSP {
   
    public static Solution simulatedAnnealing(List<City> cities){
        double T = 10; // Initial T value
        final int MIN_T_VALUE = 1; // Minimum Temperature value
        final int MAX_INNER_LOOP = 10; // Maximum inner loop count
        final double cooling_rate = 0.95; // Cooling Rate
        Solution s = new Solution(cities.size());        
        s.createRndVisitOrder(cities, 0, cities.size()-1);
        s.calculateObjective();
        
        Solution bestSolution = new Solution(cities.size());
        bestSolution.createRndVisitOrder(cities, 0, cities.size() - 1);
        bestSolution.calculateObjective();
        
        while (T > MIN_T_VALUE) {  
            
            int inner_loop = 0;     
            
            while(inner_loop < MAX_INNER_LOOP){  
                Solution s_prime = new Solution(cities.size());
                List<City> neighbors = s.createRandomNeighbour();
                s_prime.createRndVisitOrder(neighbors, 0, cities.size()-1);
                s_prime.calculateObjective();
                float delta = s_prime.getObj() - s.getObj();
                
                if(delta < 0){
                    s = s_prime;
                    if(s_prime.getObj() < bestSolution.getObj()){
                        bestSolution = s_prime;
                    }
                } else{
                    if(Math.random() > Math.exp(-(delta / T))){
                        s = s_prime;
                    }
                }
                inner_loop++;
            }
            T = T * cooling_rate;
        }
        
        return bestSolution;
                
    }

    public static void main(String[] args) throws FileNotFoundException {
        
        List<City> cities = Utils.readFile("C:\\Users\\ribat\\OneDrive\\Masaüstü\\2020-2021 Bahar\\Heuristics Opt. Alg\\Odev1\\data\\tsp\\tsp_5_1");
        int startPoint = 0;
        int endPoint = cities.size() - 1;
        
        Solution solution = simulatedAnnealing(cities);
        //solution.createRndVisitOrder(cities, startPoint, endPoint);
        //solution.calculateObjective();
        
        System.out.println("Objective: " + solution.getObj());
        solution.printVisitingOrder();
    }
    
}
