import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SourceCodeFetcher {
	
	private String address;
	private String fileName;
	
	public SourceCodeFetcher(String address, String fileName) {
		this.address = address;
		this.fileName = fileName;
	}
	public void saveSourceCodeToFile() throws Exception{
		URL url = new URL(address);
		URLConnection spoof = url.openConnection();
		spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
		BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
		String strLine = "";
		BufferedWriter writer = null;
		StringBuilder ans = new StringBuilder();
		while ((strLine = in.readLine()) != null) {
			ans.append(strLine + "\n");
		}
		try {
			writer = new BufferedWriter( new FileWriter(fileName));
			writer.write(ans.toString());
		}
		catch (IOException e) {}
		finally {
			try {
				if ( writer != null)
					writer.close( );
			}
			catch (IOException e) {}
		}
		System.out.println("End of page.");
	}
}
