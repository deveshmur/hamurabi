package com.hamurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Hamurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    int year;
    int population;
    int acres;
    int bushels;
    int landValue;

    public Hamurabi() {
        year = 1;
        population = 100;
        bushels = 2800;
        acres = 1000;
        landValue = 19;
    }

    public static void main(String[] args) {
        new Hamurabi().playGame();
    }


    void playGame() {
        printSummary(1, 0, 5, population, 3000, 200, bushels, acres, landValue);

        int totalStarved = 0; 

        for (year = 1; year <= 10; year++) {
            System.out.println();
            System.out.println("O great Hammurabi!");
            System.out.println("You are in year " + year + " of your total 10 years.");


            int buy = askHowManyAcresToBuy(landValue, bushels);
            if (buy > 0) {
                int cost = buy * landValue;
                bushels -= cost;
                acres += buy;
            } else {
                int sell = askHowManyAcresToSell(acres);
                if (sell > 0) {
                    acres -= sell;
                    bushels += sell * landValue;
                }
            }


            int feed = askHowMuchGrainToFeedPeople(bushels);
            bushels -= feed;

            int plant = askHowManyAcresToPlant(acres, population, bushels);
            int seedRequired = plant * 2;
            bushels -= seedRequired;

            int plagueDeaths = plagueDeaths(population);
            population -= plagueDeaths;

            int starved = starvationDeaths(population, feed);
            
            if (starved < 0) starved = 0;
            population -= starved;
            totalStarved += starved;

            if (uprising(population + starved, starved)) { 
                System.out.println("You let " + starved + " people starve in year " + year + "!");
                System.out.println("You have been overthrown!");
                finalSummary(year, totalStarved, population, acres);
                return;
            }

            int newcomers = 0;
            if (starved == 0) {
                newcomers = immigrants(population, acres, bushels);
                population += newcomers;
            }


            int yieldPerAcre = harvest(plant);
            int harvestBushels = plant * yieldPerAcre;
            bushels += harvestBushels;

            int ratsPercent = grainEatenByRats(bushels);
            int ratsAte = 0;
            if (ratsPercent > 0) {
                ratsAte = (bushels * ratsPercent) / 100;
                bushels -= ratsAte;
            }

            int newPrice = newCostOfLand();
            landValue = newPrice;

            printSummary(year + 1, starved, newcomers, population, harvestBushels, ratsAte, bushels, acres, landValue);
        }


        finalSummary(10, totalStarved, population, acres);
    }



    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }



//INPUT / OUTPUT 
    int askHowManyAcresToBuy(int price, int bushels) {
        while (true) {
            int acresToBuy = getNumber("O great Hammurabi, how many acres of land do you want to buy? ");
            if (acresToBuy < 0) {
                System.out.println("O great Hammurabi, you cannot purchase less than 0 acres!");
                continue;
            }
            long cost = (long) acresToBuy * price;
            if (cost <= bushels) {
                return acresToBuy;
            } else {
                System.out.println("O great Hammurabi, you don't have enough! You only have " + bushels + " bushels.");
            }
        }
    }

    int askHowManyAcresToSell(int acresOwned) {
        while (true) {
            int acresToSell = getNumber("O great Hammurabi, how many acres of land do you want to sell? ");
            if (acresToSell < 0) {
                System.out.println("O great Hammurabi, you cannot sell less than 0 acres!");
                continue;
            }
            if (acresToSell <= acresOwned) {
                return acresToSell;
            } else {
                System.out.println("O great Hammurabi, you only have " + acresOwned + " acres to sell!");
            }
        }
    }

    int askHowMuchGrainToFeedPeople(int bushelsAvailable) {
        while (true) {
            int grainToFeed = getNumber("O great Hammurabi, how many bushels do you want to feed your people? ");
            if (grainToFeed < 0) {
                System.out.println("O great Hammurabi, you cannot feed less than 0 bushels!");
                continue;
            }
            if (grainToFeed <= bushelsAvailable) {
                return grainToFeed;
            } else {
                System.out.println("O great Hammurabi, you only have " + bushelsAvailable + " bushels available!");
            }
        }
    }

    int askHowManyAcresToPlant(int acresOwned, int populationAvailable, int bushelsAvailable) {
        while (true) {
            int acresToPlant = getNumber("O great Hammurabi, how many acres do you want to plant with grain? ");
            if (acresToPlant < 0) {
                System.out.println("O great Hammurabi, you cannot plant less than 0 acres!");
                continue;
            }

            if (acresToPlant > acresOwned) {
                System.out.println("O great Hammurabi, you do not have enough land! We own only " + acresOwned + " acres!");
                continue;
            }

            int maxAcresTended = populationAvailable * 10;
            if (acresToPlant > maxAcresTended) {
                System.out.println("O great Hammurabi, we have only " + populationAvailable + " people to tend the fields!");
                continue;
            }
       
            if (acresToPlant * 2 > bushelsAvailable) {
                System.out.println("O great Hammurabi, we have only " + bushelsAvailable + " bushels left for seed!");
                continue;
            }

            return acresToPlant;
        }
    }



//PURE
    int plagueDeaths(int population) {
        if (rand.nextInt(100) < 15) {
            return population / 2;
        } else {
            return 0;
        }
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        int peopleFed = bushelsFedToPeople / 20;
        int deaths = population - peopleFed;
        return deaths > 0 ? deaths : 0;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        return howManyPeopleStarved > (population * 45) / 100;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        if (population <= 0) return 0; 
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
    }

    int harvest(int acresPlanted) {
        return rand.nextInt(6) + 1;
    }

    int grainEatenByRats(int bushels) {
        if (rand.nextInt(100) < 40) {
            return rand.nextInt(21) + 10;
        } else {
            return 0;
        }
    }

    int newCostOfLand() {
        return rand.nextInt(7) + 17;
    }


