package main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    private HashMap<BigInteger, BigInteger> solutions;
    private ArrayList<BigInteger> stalledNumbers;
    private BigInteger lowerBorder;
    private BigInteger upperBorder;
    private BigInteger interval;

    public Application(BigInteger lowerBorder, BigInteger upperBorder) {
        solutions = new HashMap<>();
        stalledNumbers = new ArrayList<>();
        this.lowerBorder = lowerBorder;
        this.upperBorder = upperBorder;
        interval = upperBorder.subtract(lowerBorder).divide(BigInteger.valueOf(4));
    }

    /**
     * Starts the application for [1; 100000]
     *
     * @param args system arguments
     */
    public static void main(String... args) {
        Application app = new Application(BigInteger.valueOf(1), BigInteger.valueOf(100000));
        app.execute();
    }

    private void execute() {
        Thread task01 = new Thread(new Task(1, this, lowerBorder, lowerBorder.add(interval)));
        Thread task02 = new Thread(new Task(2, this, lowerBorder.add(interval), lowerBorder.add(interval.multiply(BigInteger.valueOf(2)))));
        Thread task03 = new Thread(new Task(3, this, lowerBorder.add(interval.multiply(BigInteger.valueOf(2))), lowerBorder.add(interval.multiply(BigInteger.valueOf(3)))));
        Thread task04 = new Thread(new Task(4, this, lowerBorder.add(interval.multiply(BigInteger.valueOf(3))), upperBorder.add(BigInteger.ONE)));

        System.out.println("Maximal iterations per value: " + Configuration.instance.maximalIterationsPerNumber);

        long runtimeStart = System.currentTimeMillis();

        TaskRecorder.instance.startup();
        TaskRecorder.instance.init();

        task01.start();
        task02.start();
        task03.start();
        task04.start();

        try {
            task01.join();
            task02.join();
            task03.join();
            task04.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        TaskRecorder.instance.shutdown();

        System.out.println(solutions);

        System.out.println("runtime (s)        : " + (System.currentTimeMillis() - runtimeStart) / 1000);
    }

    void addSolutions(ArrayList<BigInteger> keys, BigInteger value) {
        keys.forEach(e -> solutions.put(e, value));
        if (Configuration.instance.persistData) {
            keys.forEach(e -> TaskRecorder.instance.insert(e.toString(), value.toString()));
        }
    }

    boolean containsSolutions(BigInteger solution) {
        return solutions.containsKey(solution);
    }

    HashMap<BigInteger, BigInteger> getSolutions() {
        return solutions;
    }

    void addStalledNumber(BigInteger number) {
        stalledNumbers.add(number);
    }
}
