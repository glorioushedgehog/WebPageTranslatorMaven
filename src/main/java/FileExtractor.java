import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileExtractor {
	
	private String filePath;
	
	public FileExtractor(String filePath) {
		this.filePath = filePath;
	}
	
	public String readFile(String filePath) {
		FileReader f;
		BufferedReader b;
		StringBuilder accumulator = new StringBuilder();
		try {
			f = new FileReader(filePath);
			b = new BufferedReader(f);
			String line = "";
			while((line = b.readLine()) != null) {
				accumulator.append(line);
			}
		} catch (IOException e) {
			System.out.println("Faced file reading error!");
		}
		return accumulator.toString();
	}
}
