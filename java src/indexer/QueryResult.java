/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-b005246 modeling language!*/


package indexer;
// line 2 "model.ump"
// line 9 "model.ump"
public class QueryResult
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //QueryResult Attributes
  private Tweet tweet;
  private double rank;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public QueryResult(Tweet aTweet, double aRank)
  {
    tweet = aTweet;
    rank = aRank;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTweet(Tweet aTweet)
  {
    boolean wasSet = false;
    tweet = aTweet;
    wasSet = true;
    return wasSet;
  }

  public boolean setRank(double aRank)
  {
    boolean wasSet = false;
    rank = aRank;
    wasSet = true;
    return wasSet;
  }

  public Tweet getTweet()
  {
    return tweet;
  }

  public double getRank()
  {
    return rank;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "rank" + ":" + getRank()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tweet" + "=" + (getTweet() != null ? !getTweet().equals(this)  ? getTweet().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}