/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.19.0.3289 modeling language!*/
package fileIO;
//package fileManaging;
import java.io.*;
// line 10 "model.ump"
public class FileHandlerException extends IOException
{
  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  public @interface umplesourcefile{int[] line();String[] file();int[] javaline();int[] length();}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public FileHandlerException(String aMessage)
  {
    super(aMessage);
  }


}