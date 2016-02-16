package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DataBaseConnector;

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String cityChosen = request.getParameter("cityChosen");
		String date = request.getParameter("date");
		// response.setIntHeader("Refresh", 5);
		int cnt = getDateCount(date);
//		System.out.println("it count to" + cnt);
//		System.out.println("city chosen:" + cityChosen);
//		System.out.println("date chosen:" + date);
		HashMap<String, String> map = new HashMap<>();
		HashMap<String, String> map2 = new HashMap<>();
		RetrieveInfo rf1 = new RetrieveInfo();
		DataBaseConnector db1 = new DataBaseConnector();
		db1.connectDataBase();
		Statement myStatement = db1.getMyStatement();
		db1.createCache(myStatement);
		ArrayList<HashMap<String, String>> list = db1.retrieveCache(date, cityChosen);
		map = list.get(0);
		map2 = list.get(1);
		boolean isCached = false;
		if (map.isEmpty() && map2.isEmpty()) {
			map = rf1.retrieveWeather(cityChosen, cnt);
			map2 = rf1.retrieveNFLGame(cityChosen, date);

			db1.inserIntoCacheTable(cityChosen, date, map.get("weather"), map.get("weather_des"), map.get("curTemp"),
					map.get("minTemp"), map.get("maxTemp"), map.get("windSpeed"), map.get("windDir"),
					map2.get("homeName"), map2.get("awayName"), map2.get("fieldName"), map2.get("address"),
					map2.get("network"));

			System.out.println("insert into cache");

		} else {
			isCached = true;
			System.out.println("already cached");
		}

		// map = rf1.retrieveWeather(cityChosen,cnt);
		// map2 = rf1.retrieveNFLGame(cityChosen,date);

		// System.out.println(result);
		response.setContentType("text/html");
		PrintWriter out1;
		try {
			out1 = response.getWriter();

			String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
			out1.println(docType);
			out1.println("<HTML>\n" + "<HEAD><TITLE>Welcome!</TITLE></HEAD>\n" + "<BODY BGCOLOR=\"#FDF5E6\">\n"
					+ "<H1 ALIGN=\"CENTER\">" + "Searching result:" + "</H1>");
			out1.println("<h1 ALIGN=\"CENTER\">" + "You are searching "+cityChosen);
			out1.println("<h1 ALIGN=\"CENTER\">" + map.get("cityName") + "(" + date + ")");
			if (isCached) {
				out1.println("(" + "retrieved from Cache" + ")");
			} else {
				out1.println("(" + "retrieved from API" + ")");
			}
			out1.println("</h1>");
			out1.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" + "<TR><TD style=\"width:300px;\">");
			// out1.println("<h1>" + cityChosen + "</h1>");
			out1.println("<h3>Weather:</h3>");
			out1.println("<p>" + "Weather: " + map.get("weather") + "</p>");
			out1.println("<p>" + "Descritption: " + map.get("weather_des") + "</p>");
			out1.println("<h3>Temperature:</h3>");
			out1.println("<p>" + "Day: " + map.get("curTemp") + " &#8451</p>");
			out1.println("<p>" + "Minimum: " + map.get("minTemp") + " &#8451</p>");
			out1.println("<p>" + "Maxmum: " + map.get("maxTemp") + " &#8451</p>");
			out1.println("<h3>Wind:</h3>");
			out1.println("<p>" + "Speed: " + map.get("windSpeed") + "</p>");
			out1.println("<p>" + "Degree: " + map.get("windDir") + "</p></TD>");
			out1.println("<TD style=\"width:300px;\">" + "<h3>NFL Info:</h3>");
			out1.println("<p>" + "Date: " + date + "</p>");
			out1.println("<p>" + "Home: " + map2.get("homeName") + "</p>");
			out1.println("<p>" + "Guest: " + map2.get("awayName") + "</p>");
			out1.println("<p>" + "Location: " + map2.get("fieldName") + "(" + map2.get("address") + ")" + "</p>");
			out1.println("<p>" + "Network: " + map2.get("network") + "</p></TD></TR></TABLE>");

			if (map.isEmpty()) {
				out1.println("<p ALIGN=\"CENTER\">No weather information!" + "</p>");
			} else if (!map2.isEmpty()) {
				if (map.get("weather") != null&&(map2.get("homeName")!=null)) {
					if (map.get("weather").contains("Rain")) {
						out1.println("<p ALIGN=\"CENTER\">There will be rain on" + date
								+ ", you would better not go watching the NFL game!" + "</p>");
					}

					else if (map.get("weather").equals("Clear") || map.get("weather").equals("Clouds")) {
						out1.println("<p ALIGN=\"CENTER\">Good weather for watching the NFL game!" + "</p>");
					}
				}
				else if (map.get("weather") != null){
					out1.println("<p ALIGN=\"CENTER\">No weather information!" + "</p>");
				}
				else if (map2.get("homeName")!=null){
					out1.println("<p ALIGN=\"CENTER\">No NFL match!" + "</p>");
				}
			}

			else {
				out1.println("<p ALIGN=\"CENTER\">There is no NFL match!" + "</p>");
			}

			// out1.println("<p>" + "Home: "+ map2.get("homeName") + "</p>");

			out1.println("<form action=\"ChoiceServlet\" method=\"GET\"><input type=\"submit\" value=\"Another Search\""
					+ "style=\"margin-top:0px;margin-left:250px;\">" + "</form>");

			// out1.println("<form action=\"DisplayServlet\"
			// method=\"GET\"><input type=\"submit\" value=\"Another Search\">"
			// + "</form>");
			out1.println("<form action=\"HistoryServlet\" method=\"GET\"><input type=\"submit\" value=\"History Info\""
					+ "style=\"margin-left:250px;\">" + "</form>");
			out1.println("</BODY></HTML>");

			// DataBaseConnector db1=new DataBaseConnector();
			// db1.connectDataBase();
			//
			// Statement myStatement = db1.getMyStatement();
			db1.createDataBase(myStatement);
			db1.inserIntoSearchTable(cityChosen, date, String.valueOf(map.get("curTemp")),
					String.valueOf(map.get("windSpeed")), map2.get("homeName"), map2.get("awayName"),
					map2.get("fieldName"), map.get("weather"), map.get("weather_des"));

		} catch (

		IOException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getDateCount(String queryDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		System.out.println(currentDate);
		System.out.println(currentDate.substring(8, 10));
		String[] dateArr = new String[7];
		int cnt = 0;
		dateArr[0] = currentDate;
		int MAX_DAY = 0;
		// int newDay=0;
		for (int i = 1; i < 7; i++) {
			int currentDay = Integer.valueOf(dateArr[i - 1].substring(8, 10));
			int currentMonth = Integer.valueOf(currentDate.substring(5, 7));
			if (currentMonth == 1 || currentMonth == 3 || currentMonth == 5 || currentMonth == 7 || currentMonth == 8
					|| currentMonth == 10 || currentMonth == 12) {
				MAX_DAY = 31;
			} else {
				MAX_DAY = 30;
				if (currentDay == MAX_DAY) {

					currentMonth++;
					int newDay = 0;
					for (int k = i; k < 7; k++) {
						newDay++;
						dateArr[k] = currentDate.substring(0, 5) + String.valueOf(currentMonth) + "-0"
								+ String.valueOf(newDay);
					}
					break;

				} else {
					dateArr[i] = dateArr[i - 1].substring(0, 8) + String.valueOf(currentDay + 1);
				}
			}
		}

		for (int i = 0; i < 7; i++) {
			if (dateArr[i].equals(queryDate)) {
				cnt = i + 1;
			}
		}

		return cnt;

	}

}
