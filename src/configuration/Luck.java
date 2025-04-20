package configuration;

public interface Luck {
    // Experience Gained On Events
    int EXPERIENCE_EVENT_UPPER_BOUND = 10;
    int EXPERIENCE_EVENT_LOWER_BOUND = 1;
    double EXPERIENCE_EVENT_MULTIPLIER = 1.0;

    // Experience Gained On Messages
    int EXPERIENCE_MESSAGE_UPPER_BOUND = 100;
    int EXPERIENCE_MESSAGE_LOWER_BOUND = 10;
    double EXPERIENCE_MESSAGE_MULTIPLIER = 1.0;
}
