
public class Server
{
	private static int portNumber;

	private static Thread thread;
	private static RunnableThread runnableThread;

	public static void main(String[] args)
	{
		initializeThread();
		thread.start();
	}

	private static void initializeThread()
	{
		portNumber = 6969;
		runnableThread = new RunnableThread(portNumber);
		thread = new Thread(runnableThread);
	}

}