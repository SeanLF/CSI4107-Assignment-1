/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-b005246 modeling language!*/


package main;
// line 2 "model.ump"
public class TestQuery
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TestQuery Attributes
  private String number;
  private String query;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TestQuery(String aNumber, String aQuery)
  {
    number = aNumber;
    query = aQuery;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(String aNumber)
  {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuery(String aQuery)
  {
    boolean wasSet = false;
    query = aQuery;
    wasSet = true;
    return wasSet;
  }

  public String getNumber()
  {
    return number;
  }

  public String getQuery()
  {
    return query;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "query" + ":" + getQuery()+ "]"
     + outputString;
  }
}