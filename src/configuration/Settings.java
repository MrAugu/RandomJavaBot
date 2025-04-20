package configuration;

public interface Settings {
    boolean isDebugEnabled = System.getenv("DEBUG") != null && System.getenv("DEBUG").equals("true");
}
