
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class FetchFPLData {
	
	private static final String dataURL ="https://fantasy.premierleague.com/api/";
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMDDHHMMSS");
	private static final LocalDateTime date = LocalDateTime.now();
	
	//main JSON object from boot strap static API
	private static String bootstrap = null;
	private static JSONObject bootStrapStatic = null;
	//JSON arrays within boot strap static JSON object
	private static JSONArray elementsPlayerData = null; 
	private static JSONArray elementsPlayerStats = null;
	private static JSONArray elementsPlayerTypes = null;
	private static JSONArray teams = null;
	private static JSONArray phases = null;
	private static JSONArray events = null;
	private static String elementPlayerID [] = null; 
	//main JSON Array from fixtures API
	private static JSONArray fixture = null;
	//get player history from API
	private static JSONArray playerHistory = null; 
	
	

	public FetchFPLData() {
		// TODO Auto-generated constructor stub

	}
	
	public FetchFPLData(String bs) {
		try {
			setBootStrapStatic(bs);
			setBootStrapJObject();
			setElementsPlayerData();
			setElementsPlayerStats();
			setElementsPlayerTypes();
			setEvents();
			setTeams();
			setPhases();
			setElementPlayerID();
			
			setPlayerData();
		}
		catch(MalformedURLException mue){
			mue.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public FetchFPLData(String bs, String fx) {
		try {
			setBootStrapStatic(bs);
			
			setBootStrapJObject();
			System.out.println("setBootStrap done");
			setElementsPlayerData();
			System.out.println("setElementsPlayerData done");
			setElementsPlayerStats();
			System.out.println("setElementsPlayerStats done");
			setElementsPlayerTypes();
			System.out.println("setElementsPlayerTypes done");
			setEvents();
			System.out.println("setEvents done");
			setTeams();
			System.out.println("setTeams done");
			setPhases();
			System.out.println("setPhases done");
			setElementPlayerID();
			System.out.println("setElementPlayerID done");
			
			setFixtures(fx);
			System.out.println("setFixtures done");
			
			setPlayerData();
			System.out.println("setPlayerData done");
		}
		catch(MalformedURLException mue){
			mue.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void setBootStrapStatic(String endpoint) throws MalformedURLException, IOException{
		String end = endpoint;
		String output = null;
		
		try {
			URL url = new URL(dataURL + end); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setReadTimeout(5000);
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				
				output = br.readLine();
				
				conn.disconnect();
				br.close();
			}
			else {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
		}
		catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
			
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			
		}
		bootstrap = output;
		
	}
			
	public void setBootStrapJObject() {
		
		String jsonText = getBootstrap();
		
		Object Ob = JSONValue.parse(jsonText);
		JSONObject JObject = (JSONObject) Ob;
		
		bootStrapStatic = JObject;
		
	}
	
	public void setElementsPlayerData() {
		
		JSONArray elementsJArray = (JSONArray) bootStrapStatic.get("elements");
		elementsPlayerData = elementsJArray;
		
	}
	
	public void setElementsPlayerStats() {
		
		JSONArray elementsJArray = (JSONArray) bootStrapStatic.get("element_stats");
		elementsPlayerStats = elementsJArray;
	}
	
	public void setElementsPlayerTypes() {
		
		JSONArray elementsJArray = (JSONArray) bootStrapStatic.get("element_types");
		elementsPlayerTypes = elementsJArray;
	}
	
	public void setElementPlayerID() {
		
		JSONArray array = elementsPlayerData;
		int size = array.size();
		String id[] = new String [size];
		
		ArrayList<String> ListData = new ArrayList<String>();     
		//populate array list with data from JSON Array
		if (array != null) { 
		   for (int i=0;i<size;i++){ 
			   ListData.add(array.get(i).toString());
		   } 
		}
		//get the element "player" id by doing a substring. would need to change if the layout of the JSON changes
		for (int i =0; i < size; i ++) {
			 id[i] = ListData.get(i).substring(ListData.get(i).indexOf("\"id\":")+5, ListData.get(i).indexOf(",\"creativity\":"));
		}
		//populating global variable
		elementPlayerID = new String[size];
		for (int i =0; i < size; i ++) {
			elementPlayerID[i] = id[i];
		}
		array = null; 
		size = 0; 
		id = new String[0];
	}
	
	public void setEvents() {
		JSONArray elementsJArray = (JSONArray) bootStrapStatic.get("events");
		events = elementsJArray;
	}
	
	public void setTeams() {
		JSONArray elementsJArray = (JSONArray) bootStrapStatic.get("teams");
		teams = elementsJArray;
	}
	
	public void setPhases() {
		JSONArray elementsJArray = (JSONArray) bootStrapStatic.get("phases");
		phases = elementsJArray;
	}
	
	private static String getBootstrap() {
		return bootstrap;
	}
	
	private static JSONObject getBootStrapJObject() {
		return bootStrapStatic;
	}
	
	public static JSONArray getElementsPlayerData() {
		return elementsPlayerData;
	}
	
	private static JSONArray getElementsPlayerStats() {
		return elementsPlayerStats;
		
	}
	
	private static JSONArray getElementsPlayerTypes() {
		return elementsPlayerTypes;
	}
	
	private static String [] getElementPlayerID() {
		return elementPlayerID;
	}
	
	private static JSONArray getEvents() {
		return events; 
	}
	
	private static JSONArray getTeams() { 
		return teams; 
	}
	
	private static JSONArray getPhases() {
		return phases;
	}
	
	/*
	 * private void printElementPlayerID() {
	 * 
	 * String jArr [] = getElementPlayerID();
	 * 
	 * for (int i = 0; i < jArr.length; i ++) { System.out.println(jArr[i]); } }
	 */

	public void printBootStrapStatic() {
		System.out.println(getBootStrapJObject());
	}
	
	public void printElementsPlayerData() {
		System.out.println(getElementsPlayerData());
	}
	
	public void printElementsPlayerStats() {
		System.out.println(getElementsPlayerStats());
	}
	
	public void printElementsPlayerTypes() {
		System.out.println(getElementsPlayerTypes());
	}
	
	public void printEvents() {
		System.out.println(getEvents());
	}
	
	public void printTeams() {
		System.out.println(getTeams());
	}
	
	public void printPhases() {
		System.out.println(getPhases());
	}
	
	public void setFixtures(String endpoint) throws MalformedURLException, IOException{
		String end = endpoint;
		String output = null;
		
		try {
			URL url = new URL(dataURL + end); 
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setReadTimeout(5000);
			
			if(conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				
				output = br.readLine();
				
				conn.disconnect();
				br.close();
			}
			else {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
		}
		catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
			
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			
		}
		
		Object Ob = JSONValue.parse(output);
		JSONArray JArray = (JSONArray) Ob;
		
		fixture = JArray;
	}
		
	private static JSONArray getFixtures() {
		return fixture;
	}
	
	public void printFixtures() {
		System.out.println(getFixtures());
	}
	
	@SuppressWarnings("unchecked")
	public void setPlayerData() throws MalformedURLException, IOException {
		String end = "element-summary/";
		String output = null;
		String playerID [] = getElementPlayerID();
		playerHistory = new JSONArray();
		JSONArray jArr;
		try {
			for(int i = 0; i < playerID.length; i ++) {
				
				URL url = new URL(dataURL + end + playerID[i] + "/"); 
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setReadTimeout(5000);
				
				if(conn.getResponseCode() == 200) {
					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					
					output = br.readLine();
					br.close();
					
					Object ob = (Object) JSONValue.parse(output);
					JSONObject jOb = (JSONObject)ob;
					jArr = (JSONArray) jOb.get("history");
					
					playerHistory.add(jArr);
				}
				else {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}
				
				conn.disconnect();
			}
			
			
		}
		catch(MalformedURLException mue) {
			System.out.println(mue.getMessage());
			
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			
		}
	}
	
	private static JSONArray getPlayerData() {
		return playerHistory;
	}
	
	public void printPlayerData() {
		System.out.println(getPlayerData());
	}
	
	public void generateFileBootStrapStatic(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if (JSONFileType == true) {
				filePath = Location + "BootStrapStatic_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "BootStrapStatic_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getBootStrapJObject().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generateElementsPlayerData(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if (JSONFileType == true) {
				filePath = Location + "ElementsPlayerData_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "ElementsPlayerData_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getElementsPlayerData().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generateElementsPlayerStats(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if(JSONFileType == true) {
				filePath = Location + "ElementsPlayerStats_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "ElementsPlayerStats_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getElementsPlayerStats().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generateElementsPlayerTypes(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if(JSONFileType = true) {
				filePath = Location + "ElementsPlayerTypes_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "ElementsPlayerTypes_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getElementsPlayerTypes().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generateEvents(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if(JSONFileType == true) {
				filePath = Location + "Events_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "Events_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getEvents().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generateTeams(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if(JSONFileType == true) {
				filePath = Location + "Teams_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "Teams_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getTeams().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generatePhases(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath ="";
			
			if(JSONFileType == true) {
				filePath = Location + "Phases_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "Phases_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getPhases().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generateFixtures(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath ="";
			
			if(JSONFileType == true) {
				filePath = Location + "Fixtures_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "Fixtures_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getFixtures().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void generatePlayerData(String Location, Boolean JSONFileType) throws IOException{
		try {
			String filePath = "";
			
			if(JSONFileType == true) {
				filePath = Location + "PlayerData_" + dtf.format(date) + ".json";
			}
			else {
				filePath = Location + "PlayerData_" + dtf.format(date) + ".txt";
			}
			
			FileWriter myWriter = new FileWriter(filePath);
		    myWriter.write(getPlayerData().toString());
		    myWriter.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
