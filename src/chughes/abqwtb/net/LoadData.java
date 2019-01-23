package chughes.abqwtb.net;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;
import redis.clients.jedis.Jedis;

public class LoadData {

  public static void main(String[] args) throws IOException {
    List<Vehicle> vehicles = new HTTPGetter().get();

    GtfsReader reader = new GtfsReader();
    reader.setInputLocation(new File("C:/Users/chris/gtfs.zip"));

    GtfsDaoImpl store = new GtfsDaoImpl();
    reader.setEntityStore(store);

    reader.run();

    // Access entities through the store
    for (Vehicle vehicle : vehicles) {
      Trip t = store.getTripForId(new AgencyAndId("1",vehicle.getTripId().trim()));
      Stop s = store.getStopForId(new AgencyAndId("1",vehicle.getNextStopId()));
      
      System.out.println(t.getRoute().getShortName() + " " + t.getServiceId().getId() + " " + vehicle.getRouteShortName() + " " + vehicle.getNextStopId() + " " + vehicle.getMsgTime());

    }

    /*Jedis j = new Jedis("127.0.0.1");
    j.set("test","hi");
    System.out.println(j.get("test"));*/
  }

}
