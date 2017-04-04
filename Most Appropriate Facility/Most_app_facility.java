	//Author: Nishigandha Rane
	//Author: Aditi Ghamandi

	import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.Scanner;

	import javax.swing.ScrollPaneConstants;


public class Most_app_facility {

	

		ArrayList<ArrayList<String>> facility;
		ArrayList<ArrayList<String>> customerExclude;

		Most_app_facility()
		{
			facility = new ArrayList<>();
			customerExclude = new ArrayList<>();
		}


		public static void main(String[] args) {
			// TODO Auto-generated method stub

			/*
			 * load the database of copart facilities from csv file
			 * format of file: facility name, facility zip code 
			 */

			Most_app_facility fp = new Most_app_facility();
			try {
				Scanner sc = new Scanner(new File("facilities.csv"));

				//skip heading
				sc.nextLine();
				while(sc.hasNext())
				{
					String dataLine = sc.nextLine();

					String[] dataSplit = dataLine.split(",");

					ArrayList<String> tempList =  new ArrayList<>();

					tempList.add(dataSplit[0]); //facility name
					tempList.add(dataSplit[1]); //facility zip
					fp.facility.add(tempList);
				}

				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * load customer exclusion rules from csv file
			 * file format:CustomerId,Zip
			 */
			try {
				Scanner sc = new Scanner(new File("rules.csv"));

				//skip heading
				sc.nextLine();
				while(sc.hasNext())
				{
					String dataLine = sc.nextLine();

					String[] dataSplit = dataLine.split(",");

					ArrayList<String> tempList =  new ArrayList<>();

					tempList.add(dataSplit[0]); //facility name
					tempList.add(dataSplit[1]); //facility zip
					fp.customerExclude.add(tempList);
				}

				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * get customer id and zip code from stdin
			 * input: 1
			 * 		  75080
			 */

			System.out.println("Please enter customer id and zip code :");
			Scanner sc = new Scanner(System.in);
			int custId = sc.nextInt();
			int zip    = sc.nextInt();
			sc.close();

			/*
			 * call webservice to check distance of customer from each copart facility 
			 */

			ArrayList<String> zipCodes = new ArrayList<>();
			ArrayList<Double> distances = new ArrayList<>();
			for (int i = 0; i < fp.facility.size(); i++) {

				if(!fp.available(fp.facility.get(i).get(1),custId))
				{
					Double distance = getDistance(zip,fp.facility.get(i).get(1));
					zipCodes.add(fp.facility.get(i).get(1));
					distances.add(distance);
				}
			}

			String nearZip = getNearZip(zipCodes,distances);
			System.out.println("For customer " + custId + " with a provided zip code " + zip + ", closest copart facility is " + fp.getFacilityName(nearZip));
		}

		private String getFacilityName(String nearZip) {
			// TODO Auto-generated method stub

			for (int i = 0; i < facility.size(); i++) {

				if(facility.get(i).get(1).equalsIgnoreCase(nearZip))
					return facility.get(i).get(0);
			}
			return "";
		}

		private static String getNearZip(ArrayList<String> zipCodes, ArrayList<Double> distances) {
			// TODO Auto-generated method stub

			Double min = distances.get(0);
			int minLoc = 0;
			for (int i = 1; i < distances.size(); i++) {

				if(distances.get(i)< min)
				{
					min = distances.get(i);
					minLoc = i;
				}
			}

			return zipCodes.get(minLoc);
		}

		private boolean available(String zip, int custId) {
			// TODO Auto-generated method stub
			boolean op = false;
			String id = Integer.toString(custId);

			for (int i = 0; i < customerExclude.size(); i++) {
				if(customerExclude.get(i).get(0).equalsIgnoreCase(id) && 
						customerExclude.get(i).get(1).equalsIgnoreCase(zip))
				{
					op = true;
					break;
				}
			}
			return op;
		}

		private static Double getDistance(int zip, String facilityZip) {
			// TODO Auto-generated method stub
			Double dist = 0.0;
			try {

				URL url = new URL("https://www.zipcodeapi.com/rest"
						+ "/wuBC8LO1XFIu4BX8FLXGYvTvF6XVr7cYBNAKzpLw4t195YhLKesIKldqsbmUiySU/distance.json/" +zip + "/"+ facilityZip + "/km");
				
				//System.out.println(url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				output =  br.readLine();

				String[] op = output.split(":");

				dist = Double.parseDouble(op[1].substring(0, op[1].length()-1));
				conn.disconnect();

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			}

			return dist;
		}


}
