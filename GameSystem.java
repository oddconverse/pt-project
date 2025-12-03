import java.util.*;
import java.io.*;

public class GameSystem {
    private ArrayList<Suburb> suburbs;
    private ArrayList<Route> routes;
    private Suburb selectedSuburb;
    public GameSystem() {
        this.suburbs = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.selectedSuburb = null;
    }
    public Suburb getSelectedSuburb() {
        return selectedSuburb;
    }
    public ArrayList<Suburb> getSuburbs() {
        return suburbs;
    }
    public ArrayList<Route> getRoutes() {
        return routes;
    }
    public boolean containsRoute(String routeName) {
        for (Route route : getRoutes()) {
            if (route.getShortName().toLowerCase().equals(routeName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public void addRoute (Route route) {
        routes.add(route);
    }
    public Route getRoute (String routeNumberName) {
        for (Route route : routes) {
            if (route.getShortName().toLowerCase().equals(routeNumberName.toLowerCase()))
                return route;
        }
        return null;
    }
    public boolean hasSuburb(String suburbName) {
        for (Suburb suburb : suburbs) {
            if (suburb.getName().toLowerCase().equals(suburbName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public boolean hasRoute(String routeName) {
        for (Route route : routes) {
            if (route.getShortName().equals(routeName)) {
                return true;
            }
        }
        return false;
    }
    public void selectSuburb() {
        Random random = new Random(System.currentTimeMillis());
        int suburbIndex = Math.abs(random.nextInt()) % getSuburbs().size();
        selectedSuburb = suburbs.get(suburbIndex);
    }
    public ArrayList<Suburb> loadData(String filename) {
        try {
            File file = new File(filename);
            Scanner infile = new Scanner(file);
            while (infile.hasNextLine()) {
                String s = infile.nextLine();
                StringTokenizer stk = new StringTokenizer(s, ";");
                String suburbName = stk.nextToken();
                ArrayList<Route> tempRoutes = new ArrayList<>();
                while (stk.hasMoreTokens()) {
                    String routeName = stk.nextToken().trim();
                    if (!containsRoute(routeName)){
                        Route route = new Route(routeName);
                        addRoute(route);
                    }
                    Route route = getRoute(routeName);
                    tempRoutes.add(route);
                }
                Suburb suburb = new Suburb(suburbName, tempRoutes);
                suburbs.add(suburb);
            }
            infile.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void main (String[] args) {
        /*  TO DO:
            Game system to choose random suburb from list
            Input should be able to tell between suburb guess and route guess
            Find reliable data to use
         */
        GameSystem system = new GameSystem();
        system.loadData("data.txt");
        system.selectSuburb();
        Scanner input = new Scanner(System.in);
        boolean endCondition = false;
        while (!endCondition) {
            String next = input.nextLine();
            // route is in suburb
            if (system.getSelectedSuburb().containsRoute(next)) {
                System.out.println("This route is contained in this suburb");
            }
            else if (system.hasRoute(next)) {
                System.out.println("This route is not contained in this suburb");
            }
            else if (system.getSelectedSuburb().getName().toLowerCase().equals(next.toLowerCase())) {
                System.out.println("Correct! The suburb was: " + system.getSelectedSuburb().getName());
                endCondition = true;
            }
            else if (system.hasSuburb(next)) {
                System.out.println("Suburb is incorrect.");
            }
            else {
                System.out.println("Not a route or suburb!");
            }
        }
        input.close();
    }
}
