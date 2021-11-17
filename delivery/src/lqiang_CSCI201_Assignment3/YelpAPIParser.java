//package PA3.util;
package lqiang_CSCI201_Assignment3;
import java.io.*;
import com.google.gson.*;


//import PA3.models.Location;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

public class YelpAPIParser {
    public static Location getLocation(String restaurant, Location location) {
//    	System.out.println("Restaurant: "+restaurant);
//        System.out.println("HeadQuarter: "+location.getLatitude()+" "+location.getLongitude());
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String builder = "https://api.yelp.com/v3/businesses/search" + "?term=" + restaurant +"&latitude="+location.getLatitude()+"&longitude="+location.getLongitude();
//                    
            Request request = new Request.Builder().url(builder).method("GET", null).addHeader(
                    "Authorization",
                    
                    "Bearer xy3yvPueJp3iIFRetoFZ0ZAVe00PuKpu2xakfNNqx9BOheXC7kEylZc6tYdweWaZjrV_3jIF2nfjMLWx14M9QNxOecC96KdXOOqjsonMsXyfMpccYvzcOVvhwdV4YXYx")
                    .build();
            Response response = client.newCall(request).execute();
            String responseString=Objects.requireNonNull(response.body().string());
            if(responseString.contains("error"))
            {
            	System.out.println("Yelp API Failure. ");
            	return null;
            }
            Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new MyDeserializer()).create();
            Location val=gson.fromJson(responseString, Location.class);
            return val;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}

// Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
class MyDeserializer implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonElement content = je.getAsJsonObject().getAsJsonArray("businesses").get(0).getAsJsonObject()
                .get("coordinates");

        return new Gson().fromJson(content, Location.class);
    }
}
