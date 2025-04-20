package configuration;

public interface Settings {
    // Runtime parameters
    boolean isDebugEnabled = System.getenv("DEBUG") != null && System.getenv("DEBUG").equals("true");

    // Credentials
    String token = System.getenv("TOKEN");
    String databaseUrl = System.getenv("DATABASE_URL");
    String databaseUsername = System.getenv("DATABASE_USERNAME");
    String databasePassword = System.getenv("DATABASE_PASSWORD");

    // Database Connector
    int minimumIdleConnections = 5;
    int maximumIdleConnections = 10;
    int maximumTotalConnections = 25;

}
