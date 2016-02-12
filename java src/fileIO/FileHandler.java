/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.19.0.3290 modeling language!*/


package fileIO;
import java.io.*;
import java.util.ArrayList;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.net.URL;

import javax.swing.JOptionPane;

import java.lang.ClassNotFoundException;
// line 390 "model.ump"
public class FileHandler
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //FileHandler Attributes
  private File file;
  
  private static boolean allowGUI = false;
  //FileHandler Associations
  //private FileParser fileParser;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /*public FileHandler(File aFile, FileParser aFileParser)
  {
    file = aFile;
    boolean didAddFileParser = setFileParser(aFileParser);
    if (!didAddFileParser)
    {
      throw new RuntimeException("Unable to create fileHandler due to fileParser");
    }
  }*/

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFile(File aFile)
  {
    boolean wasSet = false;
    file = aFile;
    wasSet = true;
    return wasSet;
  }

  /**
   * attributes
   * file to be handled
   */
  public File getFile()
  {
    return file;
  }

  /*public FileParser getFileParser()
  {
    return fileParser;
  }*/

  /*public boolean setFileParser(FileParser aFileParser)
  {
    boolean wasSet = false;
    if (aFileParser == null)
    {
      return wasSet;
    }

    FileParser existingFileParser = fileParser;
    fileParser = aFileParser;
    if (existingFileParser != null && !existingFileParser.equals(aFileParser))
    {
      existingFileParser.removeFileHandler(this);
    }
    fileParser.addFileHandler(this);
    wasSet = true;
    return wasSet;
  }
*/
  public void delete()
  {
    //FileParser placeholderFileParser = fileParser;
    //this.fileParser = null;
    //p//laceholderFileParser.removeFileHandler(this);
  }

  @umplesourcefile(line={427},file={"model.ump"},javaline={90},length={3})
  public String getPath(){
    return file.getPath();
  }

  @umplesourcefile(line={429},file={"model.ump"},javaline={95},length={3})
  public String getName(){
    return file.getName();
  }

  @umplesourcefile(line={431},file={"model.ump"},javaline={100},length={3})
  public String getParent(){
    return file.getParent();
  }


  /**
   * return number of bytes in this file
   */
  @umplesourcefile(line={435},file={"model.ump"},javaline={105},length={3})
  public long fileLength(){
    return file.length();
  }


  /**
   * get the string representation of a file's directory
   */
  @umplesourcefile(line={540},file={"model.ump"},javaline={114},length={23})
   static  String getClassDirectory(String _className){
    URL main = FileHandler.class.getResource(_className+".class");
		if (!"file".equalsIgnoreCase(main.getProtocol())){
		  	throw new IllegalStateException("Main class is not stored in a file.");
		}
		//create new path of this directory
		File _file = new File(main.getPath());

		String _result="";
		String [] _tmp = _file.getParent().split("%20");
		//loop and add spaces instead of %20's
		for(int i=0;i<_tmp.length;i++){
			//only if not last word
			if(i<_tmp.length-1){
				_result+=_tmp[i]+" ";
			}else{
				_result+=_tmp[i];
			}

		}
		//return paretn path
		return _result;
  }

  @umplesourcefile(line={811},file={"model.ump"},javaline={143},length={3})
  public String toString(){
    return "FileHandler: "+file.getPath()+", "+file.length()+" bytes.";
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  //  @umplesourcefile(line={396},file={"model.ump"},javaline={150},length={385})
  public FileHandler (File _file)throws FileHandlerException 
  {
    //not a directory, then throw exception
		if(_file.isDirectory()){
			throw new FileHandlerException("Cannot create instance of FileHandler, "+_file.getName()+" is not a file.");
		}
		this.file=_file;
  }

//  @umplesourcefile(line={405},file={"model.ump"},javaline={160},length={375})
  public FileHandler (String _path)throws FileHandlerException 
  {
    File _file = new File(_path);
		//not a directory, then throw exception
		if(_file.isDirectory()){
			throw new FileHandlerException("Cannot create instance of FileHandler, "+_file.getName()+" is not a file.");
		}
		file = _file;
  }

//  @umplesourcefile(line={415},file={"model.ump"},javaline={171},length={364})
  public FileHandler (String _path, byte [] _bytes) throws IOException 
  {
    File _file = new File(_path);
		//not a directory, then throw exception
		if(_file.isDirectory()){
			throw new FileHandlerException("Cannot create instance of FileHandler, "+_file.getName()+" is not a file.");
		}
		file = _file;
		createNewFile(_bytes);
  }

//  @umplesourcefile(line={437},file={"model.ump"},javaline={183},length={352})
  public void setFileSecurely (File _file)throws FileHandlerException 
  {
    if(_file.isDirectory()){
			throw new FileHandlerException("Cannot set file of "+this+" "+_file.getName()+" is not a file.");
		}
  }

//  @umplesourcefile(line={444},file={"model.ump"},javaline={191},length={344})
  public void setFileSecurely (String _path)throws FileHandlerException 
  {
    File _file = new File(_path);
		if(_file.isDirectory()){
			throw new FileHandlerException("Cannot set file of "+this+" "+_file.getName()+" is not file.");
		}
		this.file=_file;
  }

//  @umplesourcefile(line={456},file={"model.ump"},javaline={201},length={334})
  public String getText () throws IOException 
  {
    String result="";
    	BufferedReader input=null;
    	FileInputStream f = new FileInputStream( file );
		InputStreamReader is = new InputStreamReader( f );
		input = new BufferedReader( is );

	    String tmp;

	    //get first line
	    tmp =input.readLine();

	    //llop through each line and copy to array
	    while(tmp!=null){
	       	result+=""+tmp+"\n";
	       	//get string array of line
	       	tmp =input.readLine();
	    }
	    //always close file even when exception is thrown
    	input.close();

        return result;
  }

//  @umplesourcefile(line={482},file={"model.ump"},javaline={227},length={308})
  public String getLine () throws IOException 
  {
    String result="";
    	BufferedReader input=null;
    	FileInputStream f = new FileInputStream( file );
		InputStreamReader is = new InputStreamReader( f );
		input = new BufferedReader( is );

	    String tmp;

	    tmp =input.readLine();

	    while(tmp!=null){
	      	result+=tmp;
	       	//get string array of line
	       	tmp =input.readLine();
	    }

	    //always close file even when exception is thrown
    	input.close();

        return result;
  }

//  @umplesourcefile(line={506},file={"model.ump"},javaline={252},length={283})
  public String [] getLines ()throws IOException 
  {
    String [] _result=null;

		ArrayList<String> _list = new ArrayList<String>((int)file.length());
		
    	BufferedReader _input=null;
    	FileInputStream _f = new FileInputStream( file );
		InputStreamReader _is = new InputStreamReader( _f );
		_input = new BufferedReader( _is );

	    String _tmp;

	    while((_tmp =_input.readLine())!=null){
	       	//get string array of line
	       	_list.add(_tmp);
	    }

	    //always close file even when exception is thrown
    	_input.close();

		Object [] _tmpArr = _list.toArray();

		_result = new String[_tmpArr.length];

		for(int i=0;i<_result.length;i++){
			_result[i]=(String)_tmpArr[i];
		}

        return _result;
  }

//  @umplesourcefile(line={563},file={"model.ump"},javaline={285},length={250})
  public boolean writeBytes (byte [] _arr)throws IOException 
  {
	
    //output stream
		FileOutputStream fos = new FileOutputStream(file);
		//wrtie bytes from byte array
		fos.write(_arr);

		//close stream
		fos.close();
		return true;

		//return success;
  }

//  @umplesourcefile(line={577},file={"model.ump"},javaline={299},length={236})
  public byte [] readBytes () throws IOException 
  {
    //amount of bytes
    	int _bufferSize = (int) file.length();

		//input stream
    	FileInputStream _input = new FileInputStream(file);

		//byte buffer
    	byte[] _buf = new byte[_bufferSize];

    	//flag for when finished reading
    	int _bytesRead = _input.read(_buf);
    	//read bytes
    	while (_bytesRead != -1) {
      		_bytesRead = _input.read(_buf);
    	}
    	//close the strea,
    	_input.close();

    	return _buf;
  }

//  @umplesourcefile(line={601},file={"model.ump"},javaline={323},length={212})
  public boolean appendFiles (FileHandler [] _handlers)throws IOException 
  {
    //get byte arrays
		byte [][] _byteArrays = new byte[_handlers.length][];

		for(int i=0;i<_handlers.length;i++){
			_byteArrays[i]=_handlers[i].readBytes();
		}

		//append each byte array to this handler's file
		for(int i=0;i<_handlers.length;i++){
			appendStringToFile("\r\n");
			appendBytesToFile(_byteArrays[i]);
		}
		return true;
  }

//  @umplesourcefile(line={619},file={"model.ump"},javaline={341},length={194})
  public boolean appendBytesToFile (byte [] _bytes)throws IOException 
  {
    //get bytes from file
		byte [] _bytesFromFile = readBytes();

		//create buffer with sze of file, and the size of appending byte array
		ByteBuffer _buf = ByteBuffer.allocateDirect(_bytesFromFile.length+_bytes.length);

		FileOutputStream _out = new FileOutputStream(file);
		//get output channel from file
		FileChannel _fileChannel = _out.getChannel();

		//clear buffer, set position to 0
		_buf.clear();

		//write bytes from file into buffer starting from buffer position
		_buf.put(_bytesFromFile,0,_bytesFromFile.length);
		//set position to end of bytes from file
		_buf.position(_bytesFromFile.length);
		//write bytes to append into buffer
		_buf.put(_bytes,0,_bytes.length);

		//flip to be ready to write
		_buf.flip();

         while (_buf.hasRemaining()) { // loop until no bytes remain in buffer
            _fileChannel.write(_buf);  // Write data from ByteBuffer to file
         }

        //close channel, and input stream
		_fileChannel.close();
		_out.close();

		return true;
  }

//  @umplesourcefile(line={656},file={"model.ump"},javaline={378},length={157})
  public boolean appendStringToFile (String _str)throws IOException 
  {
    appendBytesToFile(_str.getBytes());
		return true;
  }

//  @umplesourcefile(line={664},file={"model.ump"},javaline={385},length={150})
  public boolean writeToAllFiles (File [] _files)throws IOException 
  {
    //new array of handlers for each file
		FileHandler [] _handlers = new FileHandler[_files.length];

		//loop through each handler and fill array
		for(int i=0;i<_handlers.length;i++){

			//create new file handlers
			_handlers[i]=new FileHandler(_files[i]);
		}

		//call method for to write to all files
		writeToAllFiles(_handlers);
		return true;
  }

//  @umplesourcefile(line={681},file={"model.ump"},javaline={403},length={132})
  public boolean writeToAllFiles (FileHandler [] _handlers)throws IOException 
  {
    //get byte from this handler's file
		byte [] _bytes = readBytes();

		//loop through each handler and write the bytes to each of their files
		for(int i=0;i<_handlers.length;i++){
			_handlers[i].writeBytes(_bytes);
		}//end loop
		return true;
  }

//  @umplesourcefile(line={694},file={"model.ump"},javaline={416},length={119})
  public boolean createNewFile (byte [] _bytes)throws IOException 
  {
    //only if doesnt exist
		if(!file.exists()){
			//write bytes
			writeBytes(_bytes);
			//create new file after writting bytes to file
			file.createNewFile();
			return true;
		}else{
			return replaceFilePrompt(this,_bytes);
		}//end if file already exists
  }

//  @umplesourcefile(line={709},file={"model.ump"},javaline={431},length={104})
  public boolean createNewFile (File _path, byte[] _bytes) throws IOException 
  {
    if(!_path.exists()){
			//create new file handler
			FileHandler _fH = new FileHandler(_path);
			//write bytes
			_fH.writeBytes(_bytes);
			//create new file after writting bytes to file
			_fH.getFile().createNewFile();
			return true;
		}else{

			return replaceFilePrompt(new FileHandler(_path),_bytes);
		}//end if file already exist
  }

//  @umplesourcefile(line={726},file={"model.ump"},javaline={448},length={87})
  public boolean createNewFile (FileHandler _handler, byte[] _bytes)throws IOException 
  {
    File _file =_handler.getFile();

		if(!_file.exists()){
			//write bytes
			_handler.writeBytes(_bytes);
			//create new file after writting bytes to file
			_handler.getFile().createNewFile();
			return true;
		}else{
			return replaceFilePrompt(_handler,_bytes);
		}
  }

//  @umplesourcefile(line={741},file={"model.ump"},javaline={464},length={71})
  public boolean replaceFilePrompt (FileHandler _newFileHandler, byte[] _bytes)throws IOException 
  {
    //check if allow gui
		if(allowGUI){

			int _choice = JOptionPane.showConfirmDialog(null, "File "+_newFileHandler.getName()+" already exists, are you sure you want to replace it?");

			//exists and user wants to replace
			if(_choice == 0){
				File _file = _newFileHandler.getFile();
				//delete file
				boolean success = _file.delete();
				//write bytes
				_newFileHandler.writeBytes(_bytes);
				//create new file after writting bytes to file
				_file.createNewFile();
				return success;
			}else{
				//do nothing, no replacement
				return false;
			}
		}else{
			//do nothing, already exists, no gui
			//return false;
			File _file = _newFileHandler.getFile();
			//delete file
			boolean success = _file.delete();
			//write bytes
			_newFileHandler.writeBytes(_bytes);
			//create new file after writting bytes to file
			_file.createNewFile();
			return success;
		}//end if gui allowed
  }

//  @umplesourcefile(line={768},file={"model.ump"},javaline={492},length={43})
  public boolean copyTo (File _path)throws IOException 
  {
    //check if _path is not a directory
    	if(!_path.isDirectory()){
    		System.out.println("Cannot copy file. File "+_path.getPath()+" is not a directory.");
    		//cannot copy a file to a file
    		return false;
    	}

    	//new file to be copied, the directory+ the name of this file
    	File _newFile = new File(_path.getPath()+"\\"+file.getName());

    	//only copy if doesnt already exist
    	if(!_newFile.exists()){
	    	//stream
	    	FileOutputStream _out = new FileOutputStream(_newFile);
			FileInputStream  _in = new FileInputStream(file);

	    	FileChannel _thisChannel = _in.getChannel();
	    	FileChannel _toCopyChannel = _out.getChannel();

			//transfer file.length bytes to filechannel of _path
			_thisChannel.transferTo(0,file.length(),_toCopyChannel);

			//close streams
	    	_toCopyChannel.close();
	    	_thisChannel.close();
	    	_in.close();
	    	_out.close();

			_newFile.createNewFile();
			return true;
    	}else{
    		return replaceFilePrompt(new FileHandler(_newFile),readBytes());
    	}
  }

//  @umplesourcefile(line={805},file={"model.ump"},javaline={530},length={5})
  public boolean copyTo (String _path)throws IOException 
  {
    return copyTo(new File(_path));
  }

  
}