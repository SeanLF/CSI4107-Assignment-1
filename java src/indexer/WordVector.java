package indexer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class WordVector {

	private HashMap<String,Word> wordMap;
	
	//tweet with this vector
	private Tweet tweetParent;
	
	public WordVector(){
		
		wordMap = new HashMap<String,Word>();
		this.tweetParent = null;
	}
	
	public WordVector(Tweet _t){
		
		wordMap = new HashMap<String,Word>();
		this.tweetParent = _t;
	}
	
	public Tweet getTweetParent(){return this.tweetParent;}
	
	//counts a word in a hash map
	public void addWord(String _w){
		
		Word _fetchedWord = wordMap.get(_w);
		
		//no word?
		if(_fetchedWord == null){
			
			//new word (1),
			wordMap.put(_w, new Word(_w,1));
			
		}else{
			
			double _freq = _fetchedWord.getFrequency();
			_fetchedWord.setFrequency(++_freq);
			
    	}
		
	}
	
	//find word and set he value
	public void addWordAndSetFrequency(String _w, double _freq){
		

		Word _fetchedWord = wordMap.get(_w);
		
		//no word?
		if(_fetchedWord == null){
			
			//new word (1),
			wordMap.put(_w, new Word(_w,_freq));
			
		}else{
			
			
			_fetchedWord.setFrequency(_freq);
			
    	}
		
	}
	//retreive value from word-value pair
	public Double getWordCount(String _w){
		
		Word _res = null;
		
		//return null if word not found
		_res= wordMap.get(_w);
		
		if(_res == null){
			
			return 0.0;
			
		}else{
			
			return _res.getFrequency();
		}
		
		
	}
	public Iterator getWordIterator(){return wordMap.keySet().iterator();}
	public String toString(){
		
		Set _keySet = wordMap.keySet();
		Iterator _it = _keySet.iterator();
		
		String _res = "";
		
		while(_it.hasNext()){
			
			Word _w = wordMap.get(_it.next());
			
			_res+= _w.toString() +", ";
		}
		
		return _res;
		
	}
}
