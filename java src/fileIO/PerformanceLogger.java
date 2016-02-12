package fileIO;

import java.io.IOException;

public class PerformanceLogger {
	private static long BEGIN_TIME=0;
	private static long STOP_TIME=0;
	private static String LOG_PATH="C:\\Windows\\Temp\\TweetLog.txt";
	public static void initiateLogger(){
		
		BEGIN_TIME = System.currentTimeMillis();
		
	}
	
	public static void log(String _msg){
		
		STOP_TIME = System.currentTimeMillis();
		try{
			FileHandler _fh = new FileHandler(LOG_PATH);
			
			//not checking time
			if(BEGIN_TIME==0){
				
				_fh.appendStringToFile(_msg+"\n");
				
			}else{
				
				long _elapsed = STOP_TIME - BEGIN_TIME ;
				_fh.appendStringToFile(_msg+". elapsed: "+_elapsed+"ms.\n");
			}

		}catch(IOException e){e.printStackTrace();}
		
		BEGIN_TIME=0;
		
	}
}
