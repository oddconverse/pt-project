import java.util.ArrayList;

public class Suburb {
    private String name;
    private ArrayList<Route> routes;
    public Suburb(String name, ArrayList<Route> routes) {
        this.name = name;
        this.routes = routes;
    }
    public String getName() {
        return name;
    }
    public boolean containsRoute(String input) {
        for (Route route : routes) {
            if (route.getShortName().equals(input))
                return true;
        }
        return false;
    }
    public String toString() {
        return name + "[" + routes + "]";
    }
}