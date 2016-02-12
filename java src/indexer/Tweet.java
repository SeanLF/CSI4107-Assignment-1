/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.23.0-599796a modeling language!*/

package indexer;

import java.util.Iterator;

// line 9 "model.ump"
// line 20 "model.ump"
public class Tweet
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Document Attributes
  private String id;

  private WordVector wordList;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tweet(String aid)
  {
    id = aid;
    wordList = new WordVector(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aid)
  {
    boolean wasSet = false;
    id = aid;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public void delete()
  {}


	public void addWord(String _w){
		
		wordList.addWord(_w);
	}
	
	public void addWordAndSetFrequency(String _w, double _freq){
		
		wordList.addWordAndSetFrequency(_w,_freq);
	}
	
	public Double getWordCount(String _w){
		
		return wordList.getWordCount(_w);
		
	}
	public WordVector getWordList(){return this.wordList;}
	public Iterator getWordIterator(){return wordList.getWordIterator();}
	
	//Depends only on account number
    @Override
    public int hashCode() {
        return id.hashCode();
    }
 
    //Compare only account numbers
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tweet other = (Tweet) obj;
        if (!id.equals(other.id))
            return false;
        return true;
    }
    
  public String toString()
  {
	  
	  String outputString = "";
    return super.toString() + "["+
            "id" + ":" + getId()+ "]"
            + wordList.toString()+"\n"
     + outputString;
  }
}