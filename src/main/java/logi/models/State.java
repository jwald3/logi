package logi.models;

public class State {
    private final String stateName;
    private final String stateAbbrev;

    public State(String stateName, String stateAbbrev) {
        this.stateName = stateName;
        this.stateAbbrev = stateAbbrev;
    }

    public String getStateName() {
        return stateName;
    }

    public String getStateAbbrev() {
        return stateAbbrev;
    }
}
