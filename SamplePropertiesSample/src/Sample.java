import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class Sample {

	public static void main(String[] args) throws Exception {
		FileReader fileReader=new FileReader("C:\\Workspace\\Chandigarh\\OBPS-Chandigarh_Impl1\\SamplePropertiesSample\\src\\sample.properties");
		Properties properties=new Properties();
		properties.load(fileReader);
		
		System.out.println(properties.get("abc.test1"));
		
	}
}
