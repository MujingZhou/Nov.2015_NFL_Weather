package server;
//package com.eviac.blog.restclient;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//
//import javax.ws.rs.core.MediaType;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//
//public class UserInfoClient {
//	public static final String BASE_URI = "http://api.openweathermap.org";
//	public static final String PATH_NAME = "/data/2.5/weather?q=";
//	// public static final String PATH_AGE = "/UserInfoService/age/";
//	// http://api.sportradar.us/nba-t3/games/2014/05/18/schedule.xml?api_key=8vsmvb8qdxxefppvy9yeffax
//	public static final String BASE_URI2 = "http://api.sportradar.us";
//	public static final String PATH_NAME2 = "/nba-t3/games/2014/05/18/schedule.xml" + "\u003F" + "api_key=";
//
//	// http://api.sportradar.us/nba-t3/schema/schedule-v2.0.xsd?api_key=8vsmvb8qdxxefppvy9yeffax
//	// http://api.sportradar.us/nba-t3/games/2014/05/18/schedule.xml?api_key=8vsmvb8qdxxefppvy9yeffax
//	public static void main(String[] args) {
//		String name = "Pittsburgh";
//		String name2 = "8vsmvb8qdxxefppvy9yeffax";
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		ClientConfig config2 = new DefaultClientConfig();
//		Client client2 = Client.create(config2);
//		WebResource resource = client.resource(BASE_URI);
//		WebResource nameResource = resource.path(PATH_NAME + name+"");
//		System.out.println("Client Response \n" + getClientResponse(nameResource));
//		System.out.println("Response \n" + getResponse(nameResource) + "\n\n");
//		WebResource resource2 = client2.resource(BASE_URI2);
//		WebResource resource3 = client2.resource(
//				"http://api.sportradar.us/nba-t3/games/2014/05/18/schedule.xml?api_key=8vsmvb8qdxxefppvy9yeffax");
//
//		// WebResource nameResource2 = resource2.path(PATH_NAME2 + name2);
//		WebResource nameResource2 = resource2
//				.path("/nba-t3/games/2014/05/17/schedule.xml?api_key=8vsmvb8qdxxefppvy9yeffax");
//		System.out.println(getResponse(resource3));
//
//		System.out.println("Client Response \n" + getClientResponse(nameResource2));
////		System.out.println("Response \n" + getResponse(nameResource2) + "\n\n");
//		int week=5;
//		String BASE_URI2 = "http://api.sportradar.us/";
//		String PATH_NAME2 = "nfl-ot1/games/2015/reg/";
//		String resourcePath2 = String.valueOf(week)+"/schedule.json?api_key=n87wq3bxutuzz8d6k4hfaz4r";
//		ClientConfig config3 = new DefaultClientConfig();
//		Client client3 = Client.create(config3);
//		WebResource resource4 = client.resource(BASE_URI2+PATH_NAME2+resourcePath2);
////		WebResource nameResource4 = resource4.path(PATH_NAME2 + resourcePath2);
////		System.out.println("Client Response \n" + getClientResponse(nameResource4));
//		System.out.println("Response \n" + getResponse(resource4) + "\n\n");
//		
////		Week Game NFL
////		http://api.sportradar.us/nfl-ot1/games/2015/reg/4/schedule.json?api_key=n87wq3bxutuzz8d6k4hfaz4r
//		
//		
//		
//		// String
//		// url="http://api.sportradar.us/nba-t3/games/2014/05/18/schedule.xml";
//		//// http://api.sportradar.us/nba-t3/games/2014/05/18/schedule.xml?api_key=8vsmvb8qdxxefppvy9yeffax
//		// String query="api_key=8vsmvb8qdxxefppvy9yeffax";
//		// String charset = "UTF-8";
//		// URLConnection connection = new URL(url + "?" +
//		// query).openConnection();
//		// connection.setRequestProperty("Accept-Charset", charset);
//		// InputStream response = connection.getInputStream();
//		//// String line="";
//		//
//		// BufferedReader in = new BufferedReader(new
//		// InputStreamReader(response));
//		// String line = null;
//		//
//		// StringBuilder responseData = new StringBuilder();
//		// while((line = in.readLine()) != null) {
//		// responseData.append(line);
//		// }
//		//
//		// System.out.println(responseData.toString());
//
//		// WebResource ageResource = resource.path("rest").path(PATH_AGE + age);
//		// System.out.println("Client Response \n" +
//		// getClientResponse(ageResource));
//		// System.out.println("Response \n" + getResponse(ageResource));
//	}
//
//	private static String getClientResponse(WebResource resource) {
//		return resource.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
//	}
//
//	private static String getResponse(WebResource resource) {
//		return resource.accept(MediaType.TEXT_XML).get(String.class);
//	}
//}
