package main;

import java.math.BigInteger;
import java.util.ArrayList;

public class Task implements Runnable {

    private int taskNumber;
    private Application app;
    private BigInteger lowerBorder;
    private BigInteger upperBorder;
    private BigInteger interval;
    private ArrayList<BigInteger> currentNumbers;
    private long currentIterations;
    private long totalIterations;
    private long solutionsFound;
    private long numbersStalled;

    public Task(int taskNumber, Application app, BigInteger lowerBorder, BigInteger upperBorder) {
        this.taskNumber = taskNumber;
        this.app = app;
        currentNumbers = new ArrayList<>();
        this.lowerBorder = lowerBorder;
        this.upperBorder = upperBorder;
        interval = upperBorder.subtract(lowerBorder);
        System.out.println("Thread" + taskNumber + " [" + lowerBorder + ";" + (upperBorder.subtract(BigInteger.ONE)) + "]");
    }

    /**
     * Iteration over the whole interval
     * Skips iteration-steps that were already solved by a lower number
     * Adds solutions to the applications HashMap
     * Adds stalled numbers to the applications stalledNumbers ArrayList
     */
    private void calculate196() {
        for (BigInteger i = lowerBorder; i.compareTo(upperBorder) < 0; i = i.add(BigInteger.ONE)) {
            if (totalIterations % 1000 == 0) {
                System.out.println("Thread " + taskNumber + ":");
                System.out.println("Solutions so far: " + solutionsFound);
                System.out.println("Numbers stalled so far: " + numbersStalled + "\n");
            }
            if (!app.containsSolutions(i)) {
                try {
                    BigInteger solution = getNumber(i);
                    if (solution.equals(BigInteger.ZERO)) {
                        // System.out.println("Stalling number " + i + " due to too many iterations.");
                        app.addStalledNumber(i);
                        numbersStalled++;
                    } else {
                        app.addSolutions(currentNumbers, solution);
                        solutionsFound += currentNumbers.size();
                        currentNumbers.clear();
                    }
                    currentIterations = 0;
                } catch (Exception e) {
                    System.out.println("Error (probably big integer stack overflow):");
                    System.out.println(e.toString());
                }
            }
        }
    }

    /**
     * Executes the 196-Algorithm for a single number and stores every found solution in currentNumbers
     *
     * @param number BigInteger to solve
     * @return Palindrome or if not solved next step of iteration
     */
    public BigInteger getNumber(BigInteger number) {
        if (currentIterations >= Configuration.instance.maximalIterationsPerNumber
                && Configuration.instance.maximalIterationsPerNumber != 0 ) {
            return BigInteger.ZERO;
        }
        /* Check whether the current number is relevant for this or a higher interval
           -> number < lowerBorder + (number of cores - task number + 1) * interval
           <-> amount of threads should fit amount of cores */
        if (number.compareTo(lowerBorder.add(interval.multiply(BigInteger.valueOf(Configuration.instance.numberOfCores - taskNumber + 1)))) < 0) {
            currentNumbers.add(number);
        }
        BigInteger q = invertNumber(number);
        BigInteger r = number.add(q);
        currentIterations++;
        totalIterations++;
        return (isPalindrome(r)) ? r : getNumber(r);
    }

    /**
     * Checks whether the given number is a palindrom
     *
     * @param number number to check
     * @return boolean whether palindrom or not
     */
    public boolean isPalindrome(BigInteger number) {
        BigInteger reverseNumber = invertNumber(number);
        return number.equals(reverseNumber);
    }

    /**
     * inverts a BigInteger
     *
     * @param number BigInteger to invert
     * @return inverted BigInteger
     */
    public BigInteger invertNumber(BigInteger number) {
        BigInteger temp = number;
        BigInteger reverseNumber = BigInteger.ZERO;

        while (!temp.equals(BigInteger.ZERO)) {
            reverseNumber = reverseNumber.multiply(BigInteger.TEN).add(temp.mod(BigInteger.TEN));
            temp = temp.divide(BigInteger.TEN);
        }

        return reverseNumber;
    }

    @Override
    public void run() {
        calculate196();
    }
}
