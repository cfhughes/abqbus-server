package chughes.abqwtb.net;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AbqDataService {

  @GET("transit/realtime/route/allroutes.json")
  Call<AbqDataPayload> listVehicles();


}
