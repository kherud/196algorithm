package main;

public enum Configuration
{
    instance;

    int numberOfCores = Runtime.getRuntime().availableProcessors();
    int maximalIterationsPerNumber = 1000; // 0 for unlimited

    boolean persistData = true;
    boolean isDebug = false;

    String userDirectory = System.getProperty("user.dir");
    String fileSeparator = System.getProperty("file.separator");

    String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    String databaseFile = dataDirectory + "196Solutions.db";
}
