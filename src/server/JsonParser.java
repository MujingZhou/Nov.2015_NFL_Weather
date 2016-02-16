package server;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JsonParser {
	public HashMap<String, String> parseWeatherJson(String input, int cnt) {
		// HashMap<String,Double> map=new HashMap<>();
		// try{
		// JSONObject obj = new JSONObject(input);
		// JSONObject a=obj.getJSONObject("coord");
		// System.out.println(a.getInt("lon"));
		// JSONObject temperature=obj.getJSONObject("main");
		// double currenTemp=temperature.getDouble("temp");
		// double highTemp=temperature.getDouble("temp_max");
		// double lowTemp=temperature.getDouble("temp_min");
		// JSONObject wind=obj.getJSONObject("wind");
		// double windSpeed=wind.getDouble("speed");
		// double winddirection=(double)wind.getInt("deg");
		// map.put("curTemp",currenTemp);
		// map.put("minTemp",lowTemp);
		// map.put("maxTemp",highTemp);
		// map.put("windSpeed", windSpeed);
		// map.put("windDirection", winddirection);
		// }catch (Exception e){
		//// e.printStackTrace();
		// }
		// return map;

		HashMap<String, String> map = new HashMap<>();
		try {
			// JSONArray arr_weeks=obj.getJSONArray("weeks");
			JSONObject obj = new JSONObject(input);
			if (obj.has("city")){
				JSONObject city=obj.getJSONObject("city");
				String cityName=city.getString("name");
				map.put("cityName", cityName);
			}
			if (obj.has("list")) {
				JSONArray arr_list = obj.getJSONArray("list");
				JSONObject singleDay = arr_list.getJSONObject(cnt - 1);
				// System.out.println("dt is "+singleDay.getInt("dt"));
				// String weatherCondi=singleDay.getString("main");
				JSONObject temperature = singleDay.getJSONObject("temp");
				double currenTemp = temperature.getDouble("day");
				double highTemp = temperature.getDouble("max");
				double lowTemp = temperature.getDouble("min");

				JSONArray arr_weather = singleDay.getJSONArray("weather");
				// JSONObject obj_weather=singleDay.getJSONObject("weather");
				String weather = arr_weather.getJSONObject(0).getString("main");
				String weather_des = arr_weather.getJSONObject(0).getString("description");
				double windSpeed = singleDay.getDouble("speed");
				double winddirection = (double) singleDay.getInt("deg");
				map.put("curTemp", String.valueOf(currenTemp));
				map.put("minTemp", String.valueOf(lowTemp));
				map.put("maxTemp", String.valueOf(highTemp));
				map.put("windSpeed", String.valueOf(windSpeed));
				map.put("windDir", String.valueOf(winddirection));
				map.put("weather", weather);
				map.put("weather_des", weather_des);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	public HashMap<String, String> parseNFLJson(String input, String cityChosen, String date) {
		HashMap<String, String> map = new HashMap<>();
		JSONObject obj = new JSONObject(input);
		JSONArray arr_weeks = obj.getJSONArray("weeks");
		System.out.println("parsing  " + cityChosen + date);
		for (int i = 0; i < arr_weeks.length(); i++) {
			JSONArray arr_game = arr_weeks.getJSONObject(i).getJSONArray("games");
			for (int j = 0; j < arr_game.length(); j++) {
				String match_date = arr_game.getJSONObject(j).getString("scheduled");
				JSONObject obj_venue = arr_game.getJSONObject(j).getJSONObject("venue");
				String match_place = obj_venue.getString("city");
				// System.out.println(match_place);
				if (match_date.startsWith(date) && match_place.equals(cityChosen)) {
					String fieldName = obj_venue.getString("name");
					String address = obj_venue.getString("address");
					JSONObject obj_home = arr_game.getJSONObject(j).getJSONObject("home");
					String homeName = obj_home.getString("name");
					JSONObject obj_away = arr_game.getJSONObject(j).getJSONObject("away");
					String awayName = obj_away.getString("name");
					JSONObject obj_broadcast = arr_game.getJSONObject(j).getJSONObject("broadcast");
					String networkName = obj_broadcast.getString("network");

					map.put("homeName", homeName);
					map.put("awayName", awayName);
					map.put("fieldName", fieldName);
					map.put("address", address);
					map.put("network", networkName);
					// System.out.println(homeName);
					// System.out.println(awayName);
					// String location=
					// map.put(key, value)
				}
			}
		}

		// JSONObject obj2 = obj.getJSONObject("week");
		// JSONArray arr=obj2.getJSONArray("games");

		// for (int i=0;i<arr.length();i++){
		// JSONObject ob3=arr.getJSONObject(i).getJSONObject("venue");
		// String city=ob3.getString("city");
		//// String
		// System.out.println(city);
		// }

		// JSONObject a=obj.getJSONObject("coord");
		// System.out.println(a.getInt("lon"));
		// JSONObject temperature=obj.getJSONObject("main");
		// double currenTemp=temperature.getDouble("temp");
		// double highTemp=temperature.getDouble("temp_max");
		// double lowTemp=temperature.getDouble("temp_min");
		// JSONObject wind=obj.getJSONObject("wind");
		// double windSpeed=wind.getDouble("speed");
		// double winddirection=(double)wind.getInt("deg");
		// map.put("curTemp",currenTemp);
		// map.put("minTemp",lowTemp);
		// map.put("maxTemp",highTemp);
		// map.put("windSpeed", windSpeed);
		// map.put("windDirection", winddirection);
		return map;

	}

	// private String getResponse(WebResource resource) {
	// return resource.accept(MediaType.TEXT_XML).get(String.class);
	// }

	// public void main(String[] args){
	// JsonParser x1=new JsonParser();
	// String BASE_URI = "http://api.sportradar.us/";
	// String PATH_NAME = "nfl-ot1/games/2015/reg/";
	// String resourcePath =
	// String.valueOf(3)+"/schedule.json?api_key=n87wq3bxutuzz8d6k4hfaz4r";
	// ClientConfig config = new DefaultClientConfig();
	// Client client = Client.create(config);
	// WebResource resource = client.resource(BASE_URI+PATH_NAME+resourcePath);
	//// WebResource nameResource4 = resource4.path(PATH_NAME2 + resourcePath2);
	//// System.out.println("Client Response \n" +
	// getClientResponse(nameResource4));
	// System.out.println("Response \n" + getResponse(resource) + "\n\n");
	// String result=getResponse(resource);
	// try {
	// x1.parseNFLJson(result);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// private String getResponse(WebResource resource) {
	// return resource.accept(MediaType.TEXT_XML).get(String.class);
	// }
}
