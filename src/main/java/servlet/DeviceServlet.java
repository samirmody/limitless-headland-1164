package main.java.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

import java.util.Map;
import java.util.Vector;
import org.json.JSONObject;
import org.json.JSONException;

@WebServlet(
	name = "DeviceServlet",
	urlPatterns = {"/devices"}
)
public class DeviceServlet extends HttpServlet {

	private static Boolean checkRequiredParameters(Vector requiredParameterList, Map parameterMap) {
		for (Object parameter : requiredParameterList) {
			if (!parameterMap.containsKey((String)parameter)) {
				return false;
			}
		}
		return true;
	}

	private static void returnError(HttpServletResponse response, String exceptionErrorMsg)
	throws IOException {
		System.out.println(exceptionErrorMsg);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"status\":\"FAILED\"}");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("getUserDevicesStatus")) {
			try {
				JSONObject responseJson = new JSONObject();
				Connection conn = DbManager.getConnection();
				Statement stmt = conn.createStatement();

				ResultSet rs1 = stmt.executeQuery("SELECT * FROM test_lamps WHERE node_address=1 ORDER BY time DESC LIMIT 1");
				rs1.next();
				responseJson.put("lamp1", rs1.getString("data_value"));
				ResultSet rs2 = stmt.executeQuery("SELECT * FROM test_lamps WHERE node_address=2 ORDER BY time DESC LIMIT 1");
				rs2.next();
				responseJson.put("lamp2", rs2.getString("data_value"));

				responseJson.put("status", "SUCCESS");

				conn.close();

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(responseJson.toString());
			}
			catch (SQLException e) {
				returnError(response, "SQLException:\n" + e.getMessage());
			}
			catch (URISyntaxException e) {
				returnError(response, "URISyntaxException:\n" + e.getMessage());
			}
			catch (JSONException e) {
				returnError(response, "JSONException:\n" + e.getMessage());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("getStatus")) {
			try {
				JSONObject responseJson = new JSONObject();
				Connection conn = DbManager.getConnection();
				Statement stmt = conn.createStatement();

				ResultSet rs1 = stmt.executeQuery("SELECT * FROM test_lamps WHERE node_address=1 ORDER BY time DESC LIMIT 1");
				rs1.next();
				responseJson.put("lamp1", rs1.getString("data_value"));
				ResultSet rs2 = stmt.executeQuery("SELECT * FROM test_lamps WHERE node_address=2 ORDER BY time DESC LIMIT 1");
				rs2.next();
				responseJson.put("lamp2", rs2.getString("data_value"));

				responseJson.put("status", "SUCCESS");

				conn.close();

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(responseJson.toString());
			}
			catch (SQLException e) {
				returnError(response, "SQLException:\n" + e.getMessage());
			}
			catch (URISyntaxException e) {
				returnError(response, "URISyntaxException:\n" + e.getMessage());
			}
			catch (JSONException e) {
				returnError(response, "JSONException:\n" + e.getMessage());
			}
		}
		else if (action.equals("updateNode")) {
			// try {
			// 	JSONObject responseJson = new JSONObject();

			// 	Vector requiredParameterList = new Vector();
			// 	requiredParameterList.addElement("node_address");
			// 	requiredParameterList.addElement("data_value");
			// 	if (!checkRequiredParameters(requiredParameterList, request.getParameterMap()))
			// 	{
			// 		responseJson.put("status", "FAILED");
			// 		// response.sendError(400, "Client did not request a node_address");
			// 	}

			// }
		}
		else {
			Vector requiredParameterList = new Vector();
			requiredParameterList.addElement("node_address");
			requiredParameterList.addElement("data_value");

			if (!checkRequiredParameters(requiredParameterList, request.getParameterMap()))
			{
				request.setAttribute("error", "Client is missing parameters was not given");
				request.getRequestDispatcher("/testlamp-post.jsp").forward(request, response);
				return;
				// response.sendError(400, "Client did not request a node_address");
			}

			String requestedNodeAddress = request.getParameter("node_address");
			String requestedDataValue = request.getParameter("data_value").toLowerCase();

			if (!requestedDataValue.equals("0") && !requestedDataValue.equals("1")) {
				request.setAttribute("error", "Client has provided invalid data_value");
				request.getRequestDispatcher("/testlamp-post.jsp").forward(request, response);
				return;
				// response.sendError(400, "Client did not request a node_address");
			}

			String node_address = "";
			String time = "";
			String data_value = "";

			try {
				Connection conn = DbManager.getConnection();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO test_lamps VALUES (" + requestedNodeAddress + ", now(), " + requestedDataValue + ")");

				// Return the latest status of the test lamp
				ResultSet rs = stmt.executeQuery("SELECT * FROM test_lamps WHERE node_address=" + requestedNodeAddress + " ORDER BY time DESC LIMIT 1");
				rs.next();

				node_address = rs.getString("node_address");
				time = rs.getString("time");
				data_value = rs.getString("data_value");

				conn.close();
			}
			catch (SQLException e) {
				request.setAttribute("SQLException", e.getMessage());
			}
			catch (URISyntaxException e) {
				request.setAttribute("URISyntaxException", e.getMessage());
			}

			request.setAttribute("lampAddress", node_address);
			request.setAttribute("lampStatusTime", time);
			request.setAttribute("lampStatus", data_value);

			request.getRequestDispatcher("/testlamp-post.jsp").forward(request, response);

		}
	}

};

