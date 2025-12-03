import java.io.*;
import java.util.*;

public class GtfsConversion {
    private BinarySearchTree<Stop> stops;
    private BinarySearchTree<Route> routes;
    public GtfsConversion() {
        this.stops = new BinarySearchTree<>();
        this.routes = new BinarySearchTree<>();
    }
    public void saveStops() {
        // converts all stop data to my stop type
        try {
            ArrayList<File> fileList = new ArrayList<>();
            File vlineTrainFile = new File("gtfs//stops.txt");
            File metroTrainFile = new File("gtfs/2 - Metro Train/stops.txt");
            File tramFile = new File("gtfs/3 - Tram/stops.txt");
            File busFile = new File("gtfs/4 - Local buses/stops.txt");
            fileList.add(vlineTrainFile);
            fileList.add(metroTrainFile);
            fileList.add(tramFile);
            fileList.add(busFile);
            FileWriter outfile = new FileWriter("stops.txt", true);
            for (File file : fileList) {
                Scanner infile = new Scanner(file);
                infile.nextLine();
                while (infile.hasNextLine()) {
                    String line = infile.nextLine();
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String idTemp = stk.nextToken().trim();
                    String id = idTemp.substring(1, idTemp.length() - 1);

                    if (file.getPath().equals("gtfs\\4 - Local buses\\stops.txt")) {
                        stk.nextToken();
                    }
                    String nameTemp = stk.nextToken().trim();
                    String name = nameTemp.substring(1, nameTemp.length() - 1);
                    String latStr = stk.nextToken().trim();
                    float lat = Float.parseFloat(latStr.substring(1, latStr.length() - 1));
                    String lonStr = stk.nextToken().trim();
                    float lon = Float.parseFloat(lonStr.substring(1, lonStr.length() - 1));
                    String typeStr = stk.nextToken().trim();
                    int type;
                    if (typeStr.equals("\"\"")) {
                        type = 0;
                    }
                    else {
                        type = Integer.parseInt(typeStr.substring(1, typeStr.length() - 1));
                    }
                    String parentID = stk.nextToken().trim();
                    stk.nextToken();
                    stk.nextToken();
                    // checking for railway stations type (1 is station as a whole) or if it is bus or tram stop (bus and tram stops have no extra features)
                    // proper stop type check should be implemented later
                    if ((!id.contains(":rail:") && !name.contains("Replacement Bus Stop")) || file.getPath().equals("gtfs\\3 - Trams\\stops.txt") || file.getPath().equals("gtfs\\4 - Local buses\\stops.txt")) {
                        Stop stop = new Stop(name, id, lat, lon);
                        outfile.append(stop.saveString() + "\n");
                    }
                }
                infile.close();
            }
            outfile.close();
        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {

        }

    }
    public void loadStops() {
        try {
            File file = new File("stops.txt");
            Scanner infile = new Scanner(file);
            while (infile.hasNextLine()) {
                String line = infile.nextLine();
                StringTokenizer stk = new StringTokenizer(line, ",");
                String nameTemp = stk.nextToken().trim();
                String name = nameTemp.substring(1, nameTemp.length() - 1);
                String idTemp = stk.nextToken().trim();
                String id = idTemp.substring(1, idTemp.length() - 1);
                String lonTemp = stk.nextToken().trim();
                float lon = Float.parseFloat(lonTemp.substring(1, lonTemp.length() - 1));
                String latTemp = stk.nextToken().trim();
                float lat = Float.parseFloat(latTemp.substring(1, latTemp.length() - 1));
                Stop stop = new Stop(name, id, lon, lat);
                stops.add(stop);
            }
            infile.close();
        } 
        catch (Exception e) {

        }
    }
    public void saveRoutes() {
        try {
            ArrayList<File> routeFileList = new ArrayList<>();
            File vlineTrainRouteFile = new File("gtfs/1 - V-Line Train/routes.txt");
            File metroTrainRouteFile = new File("gtfs/2 - Metro Train/routes.txt");
            File tramRouteFile = new File("gtfs/3 - Tram/routes.txt");
            File busRouteFile = new File("gtfs/4 - Local buses/routes.txt");
            routeFileList.add(vlineTrainRouteFile);
            routeFileList.add(metroTrainRouteFile);
            routeFileList.add(tramRouteFile);
            routeFileList.add(busRouteFile);
            FileWriter routeOutfile = new FileWriter("routes.txt", true);
            for (File file : routeFileList) {
                Scanner routeInfile = new Scanner(file);
                routeInfile.nextLine();
                System.out.println(file.getPath());
                while (routeInfile.hasNextLine()) {
                    String line = routeInfile.nextLine();
                    //System.out.println(line);
                    if (line.contains("Replacement Bus")) {
                        continue;
                    }
                    if (!isNumber(line.substring(4, 7)) && file.getPath().equals("gtfs\\4 - Local buses\\routes.txt")) {
                        break;
                    }
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String idTemp = stk.nextToken().trim();
                    String id = idTemp.substring(1, idTemp.length() - 1);
                    stk.nextToken();
                    String shortNameTemp = stk.nextToken().trim();
                    String shortName = shortNameTemp.substring(1, shortNameTemp.length() - 1);
                    String longNameTemp = stk.nextToken().trim();
                    String longName = longNameTemp.substring(1, longNameTemp.length() - 1);
                    Route route = new Route(id, shortName, longName);
                    routes.add(route);
                    while (stk.hasMoreTokens()) {
                        stk.nextToken();
                    }
                }
                routeInfile.close();
            }

            routeOutfile.append(routes.printAll());
            routeOutfile.close();
            ArrayList<File> tripFileList = new ArrayList<>();
            File vlineTrainTripFile = new File("gtfs/1 - V-Line Train/trips.txt");
            File metroTrainTripFile = new File("gtfs/2 - Metro Train/trips.txt");
            File tramTripFile = new File("gtfs/3 - Tram/trips.txt");
            File busTripFile = new File("gtfs/4 - Local Buses/trips.txt");
            tripFileList.add(vlineTrainTripFile);
            tripFileList.add(metroTrainTripFile);
            tripFileList.add(tramTripFile);
            tripFileList.add(busTripFile);
            for (File file : tripFileList) {
                Scanner tripInfile = new Scanner(file);
                tripInfile.nextLine();
                while (tripInfile.hasNextLine()) {
                    String line = tripInfile.nextLine();
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String routeIDTemp = stk.nextToken().trim();
                    String routeID = routeIDTemp.substring(1, routeIDTemp.length() - 1);
                    // make bst accept string for search
                    Route targetRoute = routes.search(routeID);
                    
                    if (targetRoute == null) {
                        continue;
                    }
                    stk.nextToken();
                    String tripIDTemp = stk.nextToken().trim();
                    String tripID = tripIDTemp.substring(1, tripIDTemp.length() - 1);
                    targetRoute.addTrip(tripID);
                }
                tripInfile.close();
            }
            ArrayList<File> stopTimeFileList = new ArrayList<>();
            File vlineTrainStopTimeFile = new File("gtfs/1 - V-Line Train/stop_times.txt");
            File metroTrainStopTimeFile = new File("gtfs/2 - Metro Train/stop_times.txt");
            File tramStopTimeFile = new File("gtfs/3 - Tram/stop_times.txt");
            File busStopTimeFile = new File("gtfs/4 - Local Buses/stop_times.txt");
            stopTimeFileList.add(vlineTrainStopTimeFile);
            stopTimeFileList.add(metroTrainStopTimeFile);
            stopTimeFileList.add(tramStopTimeFile);
            stopTimeFileList.add(busStopTimeFile);
            for (File file : stopTimeFileList) {
                Scanner stopTimeInfile = new Scanner(file);
                stopTimeInfile.nextLine();
                while (stopTimeInfile.hasNextLine()) {
                    String line = stopTimeInfile.nextLine();
                    StringTokenizer stk = new StringTokenizer(line, ",");
                    String tripIDTemp = stk.nextToken().trim();
                    String tripID = tripIDTemp.substring(1, tripIDTemp.length() - 1);
                    Route targetRoute;
                }
                stopTimeInfile.close();
            }
            
        } 
        catch (Exception e) {

        }
    }
    public boolean isNumber(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } 
        catch (Exception e) {
            return false;
        }
    }
    public static void main(String[] args) {
        GtfsConversion convert = new GtfsConversion();
        convert.saveRoutes();

    }
}