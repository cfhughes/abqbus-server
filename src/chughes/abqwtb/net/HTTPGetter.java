package chughes.abqwtb.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HTTPGetter {

  public static void main(String[] args) {
    System.out.println(new HTTPGetter().get().size());
  }

  public List<Vehicle> get() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalTime.class, new JsonDeserializer<LocalTime>() {
          @Override
          public LocalTime deserialize(JsonElement json, Type type,
              JsonDeserializationContext jsonDeserializationContext) {
            return LocalTime.parse(json.getAsString());
          }
        })
        .create();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://data.cabq.gov/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    AbqDataService service = retrofit.create(AbqDataService.class);

    try {
      return service.listVehicles().execute().body().getAllroutes();
    } catch (IOException e) {
      throw new RuntimeException("Error getting data");
    }
  }

}
