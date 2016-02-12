package indexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fileIO.FileHandler;

public class SearchMap {

	private TweetHashMap tweetInverseTable;
	
	private WordVector wordFrequencyTable;
	
	private HashMap<Tweet,Tweet> inverseFrequencyTable;
	
	private HashMap<Tweet,Tweet> tweetMap;
	
	private String[] stopWords;
	 
	private String stopWordPath;
	
	public SearchMap(String _stopWordPath){
		
		this.wordFrequencyTable = new WordVector();
		
		this.inverseFrequencyTable = new HashMap<Tweet,Tweet>();
		this.tweetMap = new HashMap<Tweet,Tweet>();
		this.tweetInverseTable = new TweetHashMap();
		
		//initialize stop words
		this.stopWordPath = _stopWordPath;
		
		//only read the stop words if we desire to remove them
		if(ReverseIndexer.REMOVE_STOP_WORDS ){
		
			this.readStopWordList();
			
		}else{
			
			//we wish to not remove stop words
		}
	}
	
	/*
	 * Frequency of word methods
	 */
	public void addToWordFrequency(String _w, Tweet _t){
		
		//count the word
		this.wordFrequencyTable.addWord(_w);
		
		//add the tweet to the word lookup table
		this.tweetInverseTable.addTweet(_w, _t);
		
	}
	public void addToInverseTable(Tweet _trevFreq,Tweet _tFreq){
		
		this.inverseFrequencyTable.put(_trevFreq,_trevFreq);
		this.tweetMap.put(_trevFreq,_tFreq);
		
	}
	
	public Tweet lookUpFreqTweet(Tweet _t){return this.tweetMap.get(_t);}
	
	/*
	//find word and set he value
	public void addWordAndSetFrequency(String _w, double _freq){

		this.inverseFrequencyTable.addWordAndSetFrequency(_w, _freq);
		
	}*/

	//retreive value from word-value pair
	public Double getWordCount(String _w){
		return this.wordFrequencyTable.getWordCount(_w);
	}
	
	/*
	//retreive value from word-value pair
	public Double getWordInverseFrequency(String _w){
		return this.inverseFrequencyTable.getWordCount(_w);
	}*/
	
	
	
	
	private List<Tweet> getTweetsWithWords(List<String> _words){
		
		//this is used to store tweets and remove duplicates from result
		HashMap<Tweet,Tweet> _buffer = new HashMap<Tweet,Tweet>();
		
		//iterate words
		for(String _w : _words){
			
			//get tweets that has word _w in it
			List<Tweet> _tweets = this.tweetInverseTable.getTweets(_w);
			
			//iterate tweetts
			for(Tweet _t : _tweets){
			
				//save tweets only if found once
				Tweet _fetchResult = _buffer.get(_t);
				
				//first time found tweet?
				if(_fetchResult == null){
				
					///save 
					_buffer.put(_t,_t);
					
				}else{
					
					//dont save tweet, already found in reault (union dont have doubles)
					
				}
				
			}//end save tweets that are unique
				
		}//end iterate each word for their tweets
		
		List<Tweet> _res = new ArrayList<Tweet>(_buffer.size());
		
		//iterator of unique tweets found
		Iterator _it = _buffer.keySet().iterator();
		
		//add tweets to result
		while(_it.hasNext()){
			
			_res.add((Tweet)_it.next());
			
		}
		
		return _res; 
		
	}
	
	
	public List<WordVector> getInverseFrequencyVectors(List<String> _words){
		
		//find tweets the words are in
		List<Tweet> _tweets = this.getTweetsWithWords(_words);
		
		
		List<WordVector> _res = new ArrayList<WordVector>(_tweets.size());
		
		//iterate tweeets and use as key to find the actualy tweet with invers word vec
		for(Tweet _t: _tweets){
			
			//fetch tweet from inverse table
			Tweet _tactual = this.inverseFrequencyTable.get(_t);
			
			//add its inverse word vector
			_res.add(_tactual.getWordList());
			
		}
		
		//find the vectors representing word inverse frequency for given tweets
		return _res;
	}
	
	public boolean isStopWord(String _w){
		
		//faield to read stopword file
		if(stopWords == null){
			
			//did we wish to populate this array?
			if(ReverseIndexer.REMOVE_STOP_WORDS){
				
				//this shouldn't happen
				throw new RuntimeException("no stop word list");
				
			}
			return false;
			
		
		}
		
		//return this.binarySearch(_w, 0, this.stopWords.length-1);
		return  Arrays.binarySearch(stopWords, _w)>=0;
		
	}
	
	private boolean binarySearch(String _w, int _l, int _r){
	
		int _pivot = (int) (_l+_r)/2;
		
		if(_r < _l){
			return false;
		}
		//general case
		//_w before pivot?
		if(_w.compareTo(stopWords[_pivot])<0){
			
			//_w before pivot so no need to search at index pivot or more for word
			return this.binarySearch(_w, _l, _pivot-1);
			
		}else if(_w.compareTo(stopWords[_pivot])>0){//w after pivot?

			//_w after pivot so no need to search at index pivot or fewer for word
			return this.binarySearch(_w, _pivot+1, _r);
			
		}else{//founmd word
			
			return true;
			
		}
			
			
		
	}
	
	private void readStopWordList(){
		try{
			
			FileHandler _fh = new FileHandler(this.stopWordPath);
		
			String _stopWords = _fh.getText();
			
			this.stopWords = _stopWords.split("\\s");
			//for(String _s : stopWords){System.out.println(_s);}
			
		}catch(IOException _e){
			
			_e.printStackTrace();
			
		}
		
	}
	
	

}
