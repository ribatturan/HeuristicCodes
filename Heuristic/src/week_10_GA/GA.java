/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week_10_GA;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zekikus
 */
public class GA {
        static int POP_SIZE = 10; // Store the population size
        static int MAX_GENERATION = 50; // Store the maximum generation count
        static int CHROMOSOME_LENGTH = 11; // Store the chromosome length or number of gene count
        static double MUTATION_RATE = 0.5;
        static Population currentPopulation = new Population(POP_SIZE, CHROMOSOME_LENGTH);
    public static void main(String[] args) {
        
        
        
        
        currentPopulation.initializePopulation(); // Generate the initial population
        currentPopulation.printPopulation();
        
        int generation = 0;
        
        
        while(generation < MAX_GENERATION){
            
            Population newPopulation = new Population(POP_SIZE, CHROMOSOME_LENGTH);
            int counter = 0;
            while(counter<POP_SIZE){
                // Selection
                Individual p1 = TournamentSelection();
                Individual p2 = TournamentSelection();    
                // Crossover operation to the selected pairs  
                Individual child = Crossover(p1, p2);
                // Mutate this child
                bitwiseMutation(child);
                child.evaluate();
                newPopulation.getIndividuals()[counter] = child;
                counter ++;
            }
            int bestIndividualIdx = getFittestSolution();
            int worstIndividualIdx = getWorstSolution(newPopulation);
            
            // Elitism - elite individuals replace worst indivual in the generated new population if they are better
            if(currentPopulation.getIndividuals()[bestIndividualIdx].getFitness() > newPopulation.getIndividuals()[worstIndividualIdx].getFitness()){
                newPopulation.getIndividuals()[worstIndividualIdx] = currentPopulation.getIndividuals()[bestIndividualIdx];
            }
            
            // Used to generation model. Pass every child produced to the next generation;
            currentPopulation = newPopulation;
            generation++;
            
            System.out.println("\n GENERATION " + generation);
            currentPopulation.printPopulation();
            
        }
        
        
        
    }
    public static Individual TournamentSelection(){
        Individual[] pool = Utils.createMatingPool(currentPopulation.getIndividuals(), 5);        
        List<Individual> liste = Arrays.asList(pool);
	Collections.shuffle(liste);
	liste.toArray(pool);
        Individual best = pool[0];
            for (Individual pool1 : pool) {
                if (pool1.getFitness() > best.getFitness()) {
                    best = pool1;
                }
            }
        return best;
        
        
    }
    public static Individual Crossover(Individual p1,Individual p2){
        double pc = 0.5;
        Individual child = new Individual(CHROMOSOME_LENGTH, false);
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if(Math.random()>pc)
                child.getChromosome()[i] = p1.getChromosome()[i];    
            else
                child.getChromosome()[i] = p2.getChromosome()[i];
        }
        return child;
              
    }
    public static void bitwiseMutation(Individual child){
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if(Math.random() > MUTATION_RATE)
                child.getChromosome()[i] = child.getChromosome()[i] ^ 1;
        }
    }
    public static int getFittestSolution(){
        int idx = 0;
        
        Individual bestIndividual = currentPopulation.getIndividuals()[0];
        for(int i = 0; i < POP_SIZE; i++){
            if(currentPopulation.getIndividuals()[i].getFitness() > bestIndividual.getFitness()){
                bestIndividual = currentPopulation.getIndividuals()[i];
                idx = i;
            }
                
        }
        
        return idx;
    }
    
    // Get worst solution in the given population as parameter
    public static int getWorstSolution(Population pop){
        int idx = 0;
        Individual worstIndividual = currentPopulation.getIndividuals()[0];
        for(int i = 0; i < POP_SIZE; i++){
            if(currentPopulation.getIndividuals()[i].getFitness() < worstIndividual.getFitness()){
                worstIndividual = currentPopulation.getIndividuals()[i];
                idx = i;
            }
                
        }
        
        return idx;
    }
}
