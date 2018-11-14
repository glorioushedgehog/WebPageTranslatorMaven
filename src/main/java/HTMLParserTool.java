import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;
import java.net.URI;
public class HTMLParserTool {

	private String url;
	private String tempFile;
	private String destFile;
	private String sourceCode;
	
	public HTMLParserTool(String url,
			String tempFile,
			String destFile) {
		this.url = url;
		this.tempFile = tempFile;
		this.destFile = destFile;
	}

	public void convert() throws Exception {
		Translator translator = new Translator();
		PrintWriter writer = new PrintWriter(destFile);
		int ind = 1;
		StringBuilder toTrans = new StringBuilder();
		StringBuilder code = new StringBuilder("<");
		int state = 1;
		while(ind < sourceCode.length()) {
			char current = sourceCode.charAt(ind);
			if(current == '<') {
				String textToTranslate = toTrans.toString();
				if(!textToTranslate.trim().equals("")) {
					String translatedString = translator.translate(textToTranslate);
					code.append(translatedString);
				}
				code.append("\n<");
				toTrans = new StringBuilder();
				state++;
			} else if (current == '>') {
				code.append(current);
				state--;
			} else {
				if (state == 0) {
					toTrans.append(current);
				} else {
					code.append(current);
				}
			}
			ind++;
		}
		writer.print(code.toString());
		writer.close();
	}

	public Queue<String>[] convertToTranslatableFormat() throws Exception {
		
		Queue<String> htmlCodeList = new LinkedList<>();
		Queue<String> wordsList = new LinkedList<>();
		
		Translator translator = new Translator();
		PrintWriter writer = new PrintWriter(destFile);
		int ind = 1;
		
		StringBuilder toTrans = new StringBuilder();
		StringBuilder htmlCode = new StringBuilder();
		
		htmlCode.append("<");

		int state = 1;
		
		
		while(ind < sourceCode.length()) {
			char current = sourceCode.charAt(ind);
			if(current == '<') {
				String textToTranslate = toTrans.toString();
				if(!textToTranslate.trim().equals("")) {
					wordsList.add(textToTranslate);
					toTrans = new StringBuilder();
				}
				htmlCode.append("\n<");
				state++;
			} else if (current == '>') {
				htmlCode.append(current);
				if(ind < sourceCode.length() - 1) {
					if(sourceCode.charAt(ind + 1) != '<') {
						String code = htmlCode.toString();
						htmlCodeList.add(code);
						htmlCode = new StringBuilder();
					}
				}
				state--;
			} else {
				if (state == 0) {
					toTrans.append(current);
				} else {
					htmlCode.append(current);
				}
			}
			ind++;
		}
		GoogleAPI googleAPI = new GoogleAPI("en");
		Queue<String> translation = googleAPI.getTranslations(wordsList);
		while(!htmlCodeList.isEmpty() && !translation.isEmpty()) {
			writer.print(htmlCodeList.poll());
			writer.print(translation.poll());
		}
		while(!htmlCodeList.isEmpty()) {
			writer.print(htmlCodeList.poll());
		}
		writer.close();
		Queue[] ret = new Queue[2];
		ret[0] = htmlCodeList;
		ret[1] = wordsList;
		return ret;
	}
	
	public void run() throws Exception {
		FileExtractor extractor = new FileExtractor(tempFile);
		SourceCodeFetcher fetcher = new SourceCodeFetcher(url, tempFile);
		
		fetcher.saveSourceCodeToFile();
		sourceCode = extractor.readFile(tempFile);
		convertToTranslatableFormat();
	}

	public static void main(String[] args) throws Exception {
		new HTMLParserTool("https://projecteuler.net/archives", "temp", "new4.html").run();
	}
}
