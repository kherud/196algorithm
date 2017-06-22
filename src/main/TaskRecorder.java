package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum TaskRecorder {
    instance;

    private Connection connection;
    private String driverName = "jdbc:hsqldb:";
    private String username = "sa";
    private String password = "";

    public void startup() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = driverName + Configuration.instance.databaseFile;
            connection = DriverManager.getConnection(databaseURL,username,password);
            if (Configuration.instance.isDebug)
                System.out.println("TaskRecorder - connection : " + connection);
        } catch (Exception e) {
            if (Configuration.instance.isDebug)
            System.out.println(e.getMessage());
        }
    }

    public void init() {
        dropTable();
        createTable();
    }

    public synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);
            if (result == -1)
                System.out.println("error executing " + sqlStatement);
            statement.close();
        } catch (SQLException sqle) {
            if (Configuration.instance.isDebug)
            System.out.println(sqle.getMessage());
        }
    }

    public void dropTable() {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE algorithm_analysis");
        if (Configuration.instance.isDebug) {
            System.out.println("--- dropTable");
            System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        }

        update(sqlStringBuilder.toString());
    }

    public void createTable() {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE algorithm_analysis ").append(" ( ");
        sqlStringBuilder.append("id BIGINT NOT NULL").append(",");
        sqlStringBuilder.append("number VARCHAR(63) NOT NULL").append(",");
        sqlStringBuilder.append("solution VARCHAR(4095) NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (id)");
        sqlStringBuilder.append(" )");
        update(sqlStringBuilder.toString());
    }

    public String buildSQLStatement(long id, String number, String solution) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO algorithm_analysis (id,number,solution) VALUES (");
        stringBuilder.append(id).append(",");
        stringBuilder.append("'").append(number).append("'").append(",");
        stringBuilder.append("'").append(solution).append("'");
        stringBuilder.append(")");
        if (Configuration.instance.isDebug)
            System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public void insert(String number, String solution) {
        update(buildSQLStatement(System.nanoTime(), number, solution));
    }

    public void shutdown() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
            if (Configuration.instance.isDebug)
                System.out.println("TaskRecorder - isClosed : " + connection.isClosed());
        } catch (SQLException sqle) {
            if (Configuration.instance.isDebug)
            System.out.println(sqle.getMessage());
        }
    }
}
