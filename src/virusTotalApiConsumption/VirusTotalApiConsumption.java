package virusTotalApiConsumption;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VirusTotalApiConsumption {

	static String workingDirectory = System.getProperty("user.dir") + File.separator;
	static String keyPath = workingDirectory + "apikeys.txt";
	static String proxyPath = workingDirectory + "proxy.txt";
	static String baseAPIURL = "https://www.virustotal.com/en/apikey/";
	static String consumptionAPICall ="/consumption/";
	static int apiKeyLength = 64;
	static int apiDailyLimit = 5760;
	static int apiDailyLimitLength = String.valueOf(apiDailyLimit).length();
	static FileArrayProvider faProvider = new FileArrayProvider();
	static String proxyName = null;
	static int proxyPort = 0;

	public static void main(String[] args) throws IOException {
		ArrayList<String> keys = getKeys();
		getProxySettings();
		String today = getToday();
		for (String key : keys) {
			try {
				String consumptionHTML = (URLConnectionReader
						.getURL(baseAPIURL + key + consumptionAPICall
						, proxyName, proxyPort));
				int requests = parseRequestCountFromHTML(consumptionHTML,today);
				String requestsPadded = String.format("%0"+apiDailyLimitLength+"d", Integer.valueOf(requests));
				System.out.println(requestsPadded + " / "+apiDailyLimit+" - " + key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int parseRequestCountFromHTML(String consumptionHTML, String forDate) {
		int locationToday = consumptionHTML.indexOf(forDate);
		int locationToggleCount = consumptionHTML.indexOf("total-count", locationToday);
		int locationBracketClosing = consumptionHTML.indexOf(">", locationToggleCount);
		int locationBracketOpening = consumptionHTML.indexOf("<", locationBracketClosing);
		return Integer.valueOf(consumptionHTML.substring(locationBracketClosing + 1, locationBracketOpening));
	}

	private static String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	/*
	 * Load Proxy Settings, else null / 0
	 */
	private static void getProxySettings() {
		File f = new File(proxyPath);
		if (f.exists() && !f.isDirectory()) {
			String[] proxyCFG;
			try {
				proxyCFG = faProvider.readLines(proxyPath,true);
				if(proxyCFG.length>0 && proxyCFG[0].split(":").length>1){
					proxyCFG = proxyCFG[0].split(":");
					proxyName = proxyCFG[0];
					proxyPort = Integer.valueOf(proxyCFG[1]);					
					System.out.println("Loaded Proxy Config: " + proxyName + ":" + proxyPort);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Load Keys from File
	 */
	static ArrayList<String> getKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		String[] potentialKeys = null;
		try {
			potentialKeys = faProvider.readLines(keyPath,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String potentialKey : potentialKeys) {
			if (potentialKey.length() == apiKeyLength) {
				keys.add(potentialKey);
			}
		}
		System.out.println("Loaded " + keys.size() + " x keys!");
		return keys;
	}

}
