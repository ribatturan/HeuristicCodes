/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Odev1.week_2_tsp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*

a. 
7. 
 */
public class TSP {

    public static Solution probabilisticIterativeImp(List<City> cities) {

        final double CONSTANT_PROB = 0.5; // Constant Probability

        Solution s = new Solution(cities.size());
        s.createRndVisitOrder(cities, 0, cities.size() - 1); //Create random TSP cycle (Solution ‘s’).
        s.calculateObjective(); //Calculate objective value of solution ‘s’.
        Solution bestSolution = new Solution(cities.size());
        bestSolution.createRndVisitOrder(cities, 0, cities.size() - 1);
        bestSolution.calculateObjective();
        int count = 0;

        while (count < 100) { //Repeat 1-6 steps until the number of maximum iterations
            Solution s_prime = new Solution(cities.size());
            List<City> neighbor = randomNeighbor(cities); //Create random neighbor of solution ‘s’;
            s_prime.createRndVisitOrder(neighbor, 0, cities.size() - 1); //Select random two cities and swap this city's order and name this solution as ‘s_prime’
            s_prime.calculateObjective();
            float delta = s_prime.getObj() - s.getObj(); //Calculate delta value (Objective value of ‘s_prime’ – Objective value of ‘s’).
            if (delta < 0) { //If new solution (‘s_prime’) better than current (‘s’) (Attention: TSP is a minimization problem)
                s = s_prime; //Accept solution ‘s_prime’.

                if (s_prime.getObj() < bestSolution.getObj()) { //Compare objective value of new solution and best solution.
                    bestSolution = s_prime; //If the new solution is better than the best, change it to the best.
                }
            } else { //If new solution worse than current (‘s’)
                if (Math.random() > CONSTANT_PROB) { //Accept new solution with 0.5 probability
                    s = s_prime;
                }
            }
            count++;
        }

        return bestSolution;
    }

    public static <E> void swap(List<E> list, int i, int j) {
        E e = list.get(i);
        list.set(i, list.get(j));
        list.set(j, e);
    }

    public static List<City> randomNeighbor(List<City> cities) {
        Random random = new Random();
        List<City> rndCities = new ArrayList<>(cities);
        int first = random.nextInt(rndCities.size() / 2) + 1;
        int second = random.nextInt(rndCities.size() / 2) + 1;
        swap(rndCities, first, second);

        return rndCities;

    }

    public static void main(String[] args) throws FileNotFoundException {

        List<City> cities = Utils.readFile("C:\\Users\\ribat\\OneDrive\\Masaüstü\\2020-2021 Bahar\\Heuristics Opt. Alg\\Odev1\\data\\tsp\\tsp_5_1");
        int startPoint = 0;
        int endPoint = cities.size() - 1;

        //Solution solution = new Solution(cities.size());
        //solution.createRndVisitOrder(cities, startPoint, endPoint);
        //solution.calculateObjective();
        Solution solution = probabilisticIterativeImp(cities);

        // Print Solution Visiting Order and Objective Value
        System.out.println("Objective: " + solution.getObj());
        solution.printVisitingOrder();
    }

}
