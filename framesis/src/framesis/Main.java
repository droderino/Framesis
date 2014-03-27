package framesis;

public class Main {

	public static void main(String[] args)
	{
		System.out.println("Starting framesis....");
		
		try {
			org.apache.felix.main.Main.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
