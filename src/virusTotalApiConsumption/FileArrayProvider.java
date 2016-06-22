package virusTotalApiConsumption;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Read file to String Array
 */
public class FileArrayProvider {

    public String[] readLines(String filename, boolean ignoreHashtagComments) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
        	if((ignoreHashtagComments && !line.substring(0, 1).equals("#")||!ignoreHashtagComments)){
            	lines.add(line);            	
            } 
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
}
