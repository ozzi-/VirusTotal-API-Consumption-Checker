package virusTotalApiConsumption;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class URLConnectionReader {
    public static String getURL(String urlToGet, String proxyName, int proxyPort) throws Exception {    	
    	URL url = new URL(urlToGet);
    	HttpURLConnection uc;

    	if(proxyName!=null && proxyPort!=0){
    		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyName, proxyPort));
    		uc = (HttpURLConnection)url.openConnection(proxy);
    	}else{
    		uc = (HttpURLConnection)url.openConnection();
    	}
    	uc.connect();
    	String content="";
    	BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
    	String line;
		while ((line = in.readLine()) != null){
    	   content+=line + "\n";
    	}
		return content;
    }
}
