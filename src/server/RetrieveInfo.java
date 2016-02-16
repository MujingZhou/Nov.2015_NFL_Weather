package server;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.xml.internal.txw2.Document;

public class RetrieveInfo {

	// public static final String BASE_URI = "http://api.openweathermap.org";
	// public static final String PATH_NAME = "/data/2.5/weather?q=";

	public HashMap<String, String> retrieveWeather(String cityName, int cnt) {
		HashMap<String, String> map = new HashMap<>();
		if (!cityName.isEmpty()) {

			try {
				cityName = cityName.replace(' ', '+');

				// HashMap<String, Double> map = new HashMap<>();
				String BASE_URI = "http://api.openweathermap.org";
				String PATH_NAME = "/data/2.5/forecast/daily?q=" + cityName + "&units=metric&cnt=7";
				System.out.println("here it is");
				ClientConfig config = new DefaultClientConfig();
				Client client = Client.create(config);
				WebResource resource = client.resource(BASE_URI + PATH_NAME);
				String result = getResponse(resource);
				System.out.println("Response \n" + getResponse(resource) + "\n\n");
				JsonParser parser1 = new JsonParser();

				map = parser1.parseWeatherJson(result, cnt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}

	public HashMap<String, String> retrieveNFLGame(String cityChosen, String date) {
		HashMap<String, String> map = new HashMap<>();
		if (!cityChosen.isEmpty()) {

			String BASE_URI = "http://api.sportradar.us/";
			String PATH_NAME = "nfl-ot1/games/2015/reg/";
			String resourcePath = "/schedule.json?api_key=n87wq3bxutuzz8d6k4hfaz4r";
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource resource = client.resource(BASE_URI + PATH_NAME + resourcePath);
			System.out.println("output in nfl");
			// String result = getResponse(resource);
			String result = resource.get(String.class);
			JsonParser parser1 = new JsonParser();
			map = parser1.parseNFLJson(result, cityChosen, date);
			try {
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;

	}

	private String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
	}

	private String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(String.class);
	}
}
