package fileIO;

import java.io.File;

public class FileTraverser {
	
	public void handleFile(File _file){}
	
	public void handleDirectoryBeforeRecursion(File _file){}
	
	public void handleDirectoryAfterRecursion(File _file){}
	
	public void traversePath(File _file)throws Exception{
		
		//is the path a file?
		if(_file.isFile()){
			
			//is file
			handleFile(_file);
			
		}else{
			
			//is directory
			handleDirectoryBeforeRecursion(_file);
			
			File [] _filesInDirectory = _file.listFiles();
			
			//iterate every file in directory, and begin recursion
			for(int i=0;i<_filesInDirectory.length;i++){
				
				traversePath(_filesInDirectory[i]);
				//handle the directory as recursion goes up the execution tree
				//handleDirectoryAfterRecursion(_file);
				
			}//end iterate files
			
			//handle the directory as recursion goes up the execution tree
			handleDirectoryAfterRecursion(_file);
			
			
		}//end is file a file
		
		
	}//end traverse path
	
	public static void main(String [] args)throws Exception	{
		
		System.out.println("In main");
		if(args.length!=1){
			
			System.out.println("Invalid number of arguments.");
			
		}else{
			
			FileTraverser _fTrav = new FileTraverser(){
				
				public void handleFile(File _file){
					
					System.out.println(_file.getPath());
					
				}
				public void handleDirectoryBeforeRecursion(File _file){
					
					System.out.println("in folder: "+_file.getName() );
					
				}
				
				public void handleDirectoryAfterRecursion(File _file){
					
					System.out.println("/..");
					
				}
				
				
			};
			
			_fTrav.traversePath(new File(args[0]));
			
			
		}
		System.out.println("Out  main");
		
	}
	
	

}
