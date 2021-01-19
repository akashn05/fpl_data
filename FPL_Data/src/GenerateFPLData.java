
public class GenerateFPLData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String filepath = System.getProperty("user.home")+"/eclipse/output/";
			Boolean fileOutput = true;
			
			
			  FetchFPLData obj = new FetchFPLData("bootstrap-static/","fixtures/");
			  
			  obj.generateFileBootStrapStatic(filepath,fileOutput);
			  System.out.println("generateFileBootStrapStatic done");
			  obj.generateElementsPlayerData(filepath, fileOutput);
			  System.out.println("generateElementsPlayerData done");
			  obj.generateElementsPlayerStats(filepath, fileOutput);
			  System.out.println("generateElementsPlayerStats done");
			  obj.generateElementsPlayerTypes(filepath, fileOutput);
			  System.out.println("generateElementsPlayerTypes done");
			  obj.generateEvents(filepath, fileOutput);
			  System.out.println("generateEvents done"); obj.generatePhases(filepath,
			  fileOutput); System.out.println("generatePhases done");
			  obj.generateTeams(filepath, fileOutput);
			  System.out.println("generateTeams done"); obj.generateFixtures(filepath,
			  fileOutput); System.out.println("generateFixtures done");
			  obj.generatePlayerData(filepath, fileOutput);
			  System.out.println("generatePlayerData done");
			 			


		}
			
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
