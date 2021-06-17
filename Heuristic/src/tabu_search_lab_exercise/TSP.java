package tabu_search_lab_exercise;

/*

    Implemented by Abdussamed KACI

 */

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class TSP {

    static int seed = 0;
    static Random rnd = new Random(seed);

    public static Solution tabuSearch(List<City> cities) throws CloneNotSupportedException {
        final int MAX_TABU_SIZE = 6;
        final int iter = 1000;
        Solution bestCandidate = new Solution(cities.size());
        bestCandidate.createRndVisitOrder(cities, 0, cities.size() - 1);
        bestCandidate.calculateObjective();

        Solution bestSolution = new Solution(cities.size());
        bestSolution.createRndVisitOrder(cities, 0, cities.size()-1);
        bestSolution.calculateObjective();
        Queue<Solution> tabuList = new LinkedList<>();
        tabuList.add(bestCandidate);
        int counter = 0;
        while (counter < iter) {
            List<Solution> neighbors = Utils.getRndNeighbors(bestCandidate);
            Collections.shuffle(neighbors);
            bestCandidate = neighbors.get(0);
            for (Solution neighbor : neighbors) {
                if (!Utils.isTabuListContain(tabuList, neighbor.getRndSolution()) && neighbor.getObj() < bestCandidate.getObj()) {
                    bestCandidate = neighbor;
                }
            }
            if(bestCandidate.getObj() < bestSolution.getObj())
                bestSolution = bestCandidate;
            if(!Utils.isTabuListContain(tabuList, bestCandidate.getRndSolution()))
                tabuList.add(bestCandidate);
            if (tabuList.size() > MAX_TABU_SIZE) {
                tabuList.remove();
            }
            counter++;
        }

        return bestSolution; // return best solution
    }

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {

        List<City> cities = Utils.readFile("C:\\Users\\ribat\\OneDrive\\Masaüstü\\2020-2021 Bahar\\Heuristics Opt. Alg\\Odev1\\data\\tsp\\tsp_70_1");

        Solution bestSolution = tabuSearch(cities);
        // Print best solution and objective value of this solution
        System.out.println("Objective value of the best solution:" + bestSolution.getObj());
        bestSolution.printVisitingOrder();

    }

}
