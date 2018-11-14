import java.util.Random;

public class Translator {
	public String translate(String x) { 
		Random rand = new Random();
		StringBuilder b = new StringBuilder();
		for(int i=0; i<x.length(); i++) {
			b.append(Integer.toString(rand.nextInt(10)));
		}
		return b.toString();
	}
}
