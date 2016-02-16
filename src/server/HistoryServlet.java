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

@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		DataBaseConnector db1 = new DataBaseConnector();
		db1.connectDataBase();
		Statement myStatement = db1.getMyStatement();
//		db1.createDataBase(myStatement);
		ArrayList<HashMap<String, String>> list = db1.retrieveInfo();
		
		String cssLocation = request.getContextPath();
		System.out.println(cssLocation);
		response.setContentType("text/html");
		PrintWriter out1;
		try {
			out1 = response.getWriter();

			String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
			out1.println(docType);
			out1.println("<HTML>\n" + "<HEAD><TITLE>Welcome!</TITLE></HEAD>\n" + "<BODY BGCOLOR=\"#FDF5E6\">\n"
					+ "<H1 ALIGN=\"CENTER\">" + "History Info:" + "</H1>");
			out1.println("<TABLE BORDER=1 WIDTH=900px ALIGN=\"CENTER\">\n");
			out1.println("<TR><TH>Num.</TH><TH>City</TH><TH>Date</TH><TH>Weather</TH><TH>Temp"+"(&#8451)"
					+ "</TH><TH>Wind Speed</TH><TH>Home Team"
					+ "</TH><TH>Guest Team</TH><TH>Location</TH></TR>");
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, String> map = list.get(i);
				out1.println("<TR><TD>" + (i+1) + "</TD><TD>" + map.get("cityName")
						+ "</TD>" + "<TD>" + map.get("Date") + "</TD>" +"<TD>" + map.get("Weather")+"("+map.get("WeatherDes")+")" + "</TD>"+ "<TD>" + map.get("Temp")
						+ "</TD><TD>" + map.get("Wind") + "</TD><TD>" + map.get("Home")
						+ "</TD><TD>" + map.get("Guest") + "</TD><TD>"
						+ map.get("Location") + "</TD></TR>");

			}
			out1.println("</TABLE></BODY></HTML>");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
