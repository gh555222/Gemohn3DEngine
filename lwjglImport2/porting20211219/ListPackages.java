
import javax.json.Json;

public class ListPackages{
	public static void main(String[] argv){
		String strClassPath = System.getProperty("java.class.path");
		System.out.println(strClassPath);
	}
}
