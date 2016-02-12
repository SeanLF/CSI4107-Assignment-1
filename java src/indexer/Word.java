/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/


package indexer;
// line 2 "model.ump"
// line 15 "model.ump"
public class Word
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Word Attributes
  private String value;
  private double frequency;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Word(String aValue, double aFrequency)
  {
    value = aValue;
    frequency = aFrequency;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setValue(String aValue)
  {
    boolean wasSet = false;
    value = aValue;
    wasSet = true;
    return wasSet;
  }

  public boolean setFrequency(double aFrequency)
  {
    boolean wasSet = false;
    frequency = aFrequency;
    wasSet = true;
    return wasSet;
  }

  public String getValue()
  {
    return value;
  }

  public double getFrequency()
  {
    return frequency;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return "["+ getValue()+ "," +
            getFrequency()+ "]"
     + outputString;
  }
}