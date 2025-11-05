package com.hamurabi;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

public class Hamurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    public static void main (String[] args) {
        new Hamurabi().playGame();
    }

    void playGame() {
        //instance variables
        int year = 1;
        int population = 100;
        int bushels = 2800;
        int acres = 1000;
        int landValue = 19;


        // initial state
        System.out.println("O great Hammurabi!");
        printSummary(year - 1, 0, 5, population, 3000, 200, bushels, acres, landValue); 
        


        //years 1-10, for loop bc known time-frame
        for (;year <=10 ;year++);

        //place holder for methods 
        System.out.println("Year " + year + " begins...");
        System.out.println("Population: " + population);
        System.out.println("Bushels: " + bushels);
        System.out.println("Land: " + acres);
        System.out.println("Price: " + landValue);




        //for getNumber
        int getNumber(String message) {
            while (true) {
                System.out.print(message);
            try {
                return scanner.nextInt();
            } 
                catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
                }
            }
        }         




        //METHOD 1: ask player how many acres of land to buy and returns that number.
        int askHowManyAcresToBuy(int price, int bushels) {
            while (true) {
                int acresToBuy = getNumber ("O great Hammurabi, how many acres of land do you want to buy?");


                if (acresToBuy < 0) {
                    System.out. println("O great Hammurabi, you cannot purchase less than 0 acres!"); 
                continue; 
                }

                int cost = acresToBuy * price; 
                if (cost <= bushels) {
                    return acresToBuy; 
                System.out. println("O great Hammurabi, congrats on your purchase."); 
                }
                else {
                    System.out.println("O great Hammurabi, you don't have enough! You only have" + bushels + "bushels to give");
                }
            }
        }
    

        //METHOD 2: ask player how many acres of land to sell and reutrn that number. 
        int askHowManyAcresToSell(int acresOwned) { 
            while (true) {
                int acresToSell = getNumber ("O great Hammurabi, how many acres of land do you want to sell?");


                if (acresToSell < 0) {
                        System.out. println("O great Hammurabi, you cannot sell less than 0 acres!"); 
                    continue;  
                }

                if (acresToSell <= acresOwned) {
                    return acresToSell; 
                } 
                else {
                    System.out.println("O Great Hammurabi, You only have " + acresOwned + "acres to sell!");
                }   
            }
        }
        

        //Method 3: ask player how much grain to feed people, and returns that number. 
        int askHowMuchGrainToFeedPeople(int bushels) {
            while (true) {
                int grainToFeed = getNumber(" O great Hammurabi, how many bushels do you want to feed people? ");


                if (grainToFeed < 0) {
                    System.out.println("O Great Hammurabi, you cannot feed less than 0 grain to people!");
                    continue;
                }

                if (grainToFeed <= bushels) {
                    return grainToFeed; 
                }
                else {
                System.out.println("O Great Hammurabi, you have only " + bushels + " to feed!");
                }
            }
        }

        
        //Method 4: ask player how many acres to plant with grain, and return that number.
        int askHowManyAcresToPlant(int acresOwned, int population, int bushels) { 
            while (true) { 
                int acresToPlant = getNumber("O great Hammurabi, how many acres do you want to plant with grains?");

                
                if (acresToPlant < 0) {
                    System.out.println("O great Hammurabi, you cannot plant less than 0 acres!");
                    continue; 
                }
                else if (acresToPlant > acresOwned) {
                    System.out.println("O Great Hammurabi, you do not have enough! We own only " + acresOwned + " acres!");
                }
                else if (acresToPlant > population * 10) {
                    System.out.println("O Great Hammurabi, we have only " + population + " people to tend the fields!");
                } 
                else if (acresToPlant * 2 > bushels) {
                    System.out.println("O Great Hammurabi, we have only " + bushels + " bushels left!");
                } 
                else {
                    return acresToPlant;
                }
            }   

        }

