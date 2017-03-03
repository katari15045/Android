import java.util.HashMap;

public class Server
{
	private static HashMap<String,String> hashmap;
	private static int portNumber;

	private static Thread thread;
	private static RunnableThread runnableThread;

	public static void main(String[] args)
	{
		initializeHashMap();
		initializeThread();
		thread.start();
	}

	private static void initializeHashMap()
	{
		hashmap = new HashMap<String, String>(3);

		hashmap.put("Dravid", "India");
		hashmap.put("Kevin", "England");
		hashmap.put("Steyn", "SA");
	}

	private static void initializeThread()
	{
		portNumber = 6969;
		runnableThread = new RunnableThread(hashmap, portNumber);
		thread = new Thread(runnableThread);
	}

}