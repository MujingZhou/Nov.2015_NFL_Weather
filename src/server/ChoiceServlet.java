
package server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DataBaseConnector;

@WebServlet("/ChoiceServlet")
public class ChoiceServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");

		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date date = new Date();
		// String currentDate=dateFormat.format(date);
		// System.out.println(currentDate.substring(8, 9));
		// System.out.println(dateFormat.format(date));
		// String[] dateArr=new String[7];
		// for (int i=1;i<7;i++){
		// if (!dateArr[i-1].isEmpty()){
		// dateArr[i-1].substring(8, 9)
		//// if (dateArr[i-1].substring(8, 9))
		// }
		// }

		PrintWriter out1;
		try {
			out1 = response.getWriter();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			System.out.println(currentDate);
			System.out.println(currentDate.substring(8, 10));
			String[] dateArr = new String[7];
			dateArr[0] = currentDate;
			int MAX_DAY = 0;
			// int newDay=0;
			for (int i = 1; i < 7; i++) {
				int currentDay = Integer.valueOf(dateArr[i - 1].substring(8, 10));
				int currentMonth = Integer.valueOf(currentDate.substring(5, 7));
				if (currentMonth == 1 || currentMonth == 3 || currentMonth == 5 || currentMonth == 7
						|| currentMonth == 8 || currentMonth == 10 || currentMonth == 12) {
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
				System.out.println(dateArr[i]);
			}

			String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">\n";
			out1.println(docType);
			out1.println("<HTML>\n" + "<HEAD><TITLE>Welcome!</TITLE></HEAD>\n" + "<BODY BGCOLOR=\"#99CCFF\">"
					+ "<H1 ALIGN=\"CENTER\">" + "Weather & NFL Gaming" + "</H1>"
					+ "<form ALIGN=\"CENTER\" name=\"info\"  action=\"DisplayServlet\" method=\"GET\"></br>");
			out1.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" + "<TR>\n");
			out1.println("<TR>" + "<TD style=\"font-size:30px;\"> City: </TD>" + "<TD style=\"font-size:20px;\">"
					+ "<input type=\"text\" name=\"cityChosen\"></TD> "+ "</TR>\n");
			out1.println("<TR>" + "<TD style=\"font-size:30px;\"> Date: </TD>" + "<TD style=\"font-size:25px;\">"
					+ "<select name=\"date\" style=\"font-size:25px;\"> ");
			for (int i = 0; i < 7; i++) {
				out1.println("<option value=" + dateArr[i] + " style=\"font-size:20px;\">" + dateArr[i] + "</option>");
			}
			out1.println("</select></TD> "+ "</TR>\n");
			out1.println("</select>" + "</TABLE>");

			for (int i = 0; i < 7; i++) {
				out1.println("<option value=" + dateArr[i] + ">" + dateArr[i] + "</option>");
			}
//			out1.println();
			out1.println("</select>");
			out1.println("<input type=\"submit\" value=\"Done\">" + "</form>" + "</BODY></HTML>");
//			File here = new File(".");
//			System.out.println(here.getAbsolutePath());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
