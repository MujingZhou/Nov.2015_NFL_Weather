
package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/*
 * -- Lab2 18-655 --
 * Name: Mujing Zhou
 * AndrewID: mujingz
 * 
 */

/* 
 * This class is used to interact with database using request information sent from the ReadXMLFile class.
 */
public class DataBaseConnector {
	private Connection myConnection;
	private Statement myStatement;

	public Connection getMyConnection() {
		return myConnection;
	}

	public Statement getMyStatement() {
		return myStatement;
	}

	// connect to database using username: root and no password
	public void connectDataBase() {

		String url = "jdbc:mysql://localhost";
		try

		{
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = DriverManager.getConnection(url, "root", "");
			myStatement = myConnection.createStatement();
		} catch (

		ClassNotFoundException e)

		{
			e.printStackTrace();
		} catch (

		SQLException e1)

		{
			e1.printStackTrace();
		}
	}

	/*
	 * createDataBase -- create the database from the given text file..
	 */
	public void createDataBase(Statement myStatement) {
		try {
//		String s ="DROP DATABASE IF EXISTS RESTfulService;";
//		myStatement.executeUpdate(s);
		String s="CREATE DATABASE IF NOT EXISTS RESTfulHistory;";
		myStatement.executeUpdate(s);
		s= "USE RESTfulHistory;";
		myStatement.executeUpdate(s);
		s= "CREATE TABLE IF NOT EXISTS RESTfulHistory.Search(searchId INTEGER not NULL AUTO_INCREMENT,"
				+ "cityName VARCHAR(255),Date VARCHAR(255),Temp VARCHAR(255),Wind VARCHAR(255),Home VARCHAR(255),"
				+ "Guest VARCHAR(255),Location VARCHAR(255),Weather VARCHAR(255),WeatherDes VARCHAR(255), PRIMARY KEY (searchId));";
		
			myStatement.executeUpdate(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createCache(Statement myStatement){
		try {
			String s="CREATE DATABASE IF NOT EXISTS RESTfulCache;";
			myStatement.executeUpdate(s);
			s= "USE RESTfulCache;";
			myStatement.executeUpdate(s);
			s= "CREATE TABLE IF NOT EXISTS RESTfulCache.Cache(searchId INTEGER not NULL AUTO_INCREMENT,"
					+ "cityName VARCHAR(255),Date VARCHAR(255),Weather VARCHAR(255),WeatherDes VARCHAR(255),Temp VARCHAR(255),TempMin VARCHAR(255),TempMax VARCHAR(255),"
					+ "WindSpeed VARCHAR(255),WindDir VARCHAR(255),Home VARCHAR(255),"
					+ "Guest VARCHAR(255),Location VARCHAR(255),Address VARCHAR(255),Network VARCHAR(255), PRIMARY KEY (searchId));";
			
				myStatement.executeUpdate(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void inserIntoCacheTable(String cityName, String date,String weather, String weather_des, String temp,
			String tempMin, String tempMax, String windSpeed,String windDir, String home, String guest,
			String location,String address,String network) {
		try {
			String subCommand = "insert into RESTfulCache.Cache(cityName,Date,Weather,WeatherDes,Temp,TempMin,TempMax,WindSpeed,"
					+ "WindDir,Home,Guest,Location,Address,Network) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps1 = myConnection.prepareStatement(subCommand);
			ps1.setString(1, cityName);
			ps1.setString(2, date);
			ps1.setString(3, weather);
			ps1.setString(4, weather_des);
			ps1.setString(5, temp);
			ps1.setString(6, tempMin);
			ps1.setString(7, tempMax);
			ps1.setString(8, windSpeed);
			ps1.setString(9, windDir);
			ps1.setString(10, home);
			ps1.setString(11, guest);
			ps1.setString(12, location);
			ps1.setString(13, address);
			ps1.setString(14, network);
			

			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void inserIntoSearchTable(String cityName, String date, String temp, String wind, String home, String guest,
			String location,String weather, String weather_des) {
		try {
			String subCommand = "insert into RESTfulHistory.Search(cityName,Date,Temp,Wind,Home,Guest,Location,Weather,WeatherDes) "
					+ "values(?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps1 = myConnection.prepareStatement(subCommand);
			ps1.setString(1, cityName);
			ps1.setString(2, date);
			ps1.setString(3, temp);
			ps1.setString(4, wind);
			ps1.setString(5, home);
			ps1.setString(6, guest);
			ps1.setString(7, location);
			ps1.setString(8, weather);
			ps1.setString(9, weather_des);

			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<HashMap<String, String>> retrieveInfo() {
		ArrayList<HashMap<String, String>> list = new ArrayList<>();
		ResultSet result1;
		try {
		myStatement.executeUpdate("USE RESTfulHistory;");
			result1 = myStatement.executeQuery("select * from RESTfulHistory.Search");
			while (result1.next()) {
				HashMap<String,String> map=new HashMap<>();
				String cityName = result1.getString("cityName");
				String date = result1.getString("Date");
				String temp = result1.getString("Temp");
				String wind = result1.getString("Wind");
				String home = result1.getString("Home");
				String guest = result1.getString("Guest");
				String location = result1.getString("Location");
				String weather=result1.getString("Weather");
				String weather_des=result1.getString("WeatherDes");
				map.put("cityName", cityName);
				map.put("Date", date);
				map.put("Temp", temp);
				map.put("Wind", wind);
				map.put("Home", home);
				map.put("Guest", guest);
				map.put("Location", location);
				map.put("Weather", weather);
				map.put("WeatherDes", weather_des);
				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<HashMap<String, String>> retrieveCache(String date,String cityName) {
		ArrayList<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String,String> map=new HashMap<>();
		HashMap<String,String> map1=new HashMap<>();
		ResultSet result1;
		try {
		myStatement.executeUpdate("USE RESTfulCache;");
			result1 = myStatement.executeQuery("select * from RESTfulCache.Cache where cityName= '"+cityName+"' AND Date='"+date+"'");
			while (result1.next()) {
//				String cityName = result1.getString("cityName");
//				String date = result1.getString("Date");
				String weather=result1.getString("Weather");
				String weather_des=result1.getString("WeatherDes");
				String temp = result1.getString("Temp");
				String tempMin=result1.getString("TempMin");
				String tempMax=result1.getString("TempMax");
				String windSpeed = result1.getString("WindSpeed");
				String windDir = result1.getString("WindDir");
				String home = result1.getString("Home");
				String guest = result1.getString("Guest");
				String location = result1.getString("Location")+result1.getString("Address");
				String address = result1.getString("address");
				String network= result1.getString("network");
				map.put("cityName", cityName);
				map.put("date", date);
				map.put("weather", weather);
				map.put("weather_des", weather_des);
				map.put("curTemp", temp);
				map.put("minTemp", tempMin);
				map.put("maxTemp", tempMax);
				map.put("windSpeed", windSpeed);
				map.put("windDir", windDir);
				map1.put("homeName", home);
				map1.put("awayName", guest);
				map1.put("fieldName", location);
				map1.put("address", address);
				map1.put("network", network);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(map);
		list.add(map1);
		return list;
	}
	

}