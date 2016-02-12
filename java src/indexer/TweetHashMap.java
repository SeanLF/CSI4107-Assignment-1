package indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TweetHashMap{

	private HashMap<String,List<Tweet>> tweetMap;
	
	public TweetHashMap(){
		
		tweetMap = new HashMap<String,List<Tweet>>();
		
	}
	
	//counts a word in a hash map
	public void addTweet(String _w,Tweet _t){
		
		//get the tweets that have word _w in it 
		List<Tweet> _fetchedTweets = tweetMap.get(_w);
		
		//no tweets?
		if(_fetchedTweets == null){
			
			//create list of tweets
			List<Tweet> _list = new ArrayList<Tweet>(64);
			_list.add(_t);
			
			//add new list to map
			
			tweetMap.put(_w, _list);
			
		}else{
			
			//add tweet to list of tweets
			_fetchedTweets.add(_t);
			
    	}
		
	}
	
		//retreive tweets that have word _w in them
	public List<Tweet> getTweets(String _w){
		
		List<Tweet> _res = null;
		//fetch  list
		_res= tweetMap.get(_w);
		
		//no tweets for given word
		if(_res == null){
			
			//empty list
			return new ArrayList<Tweet>(0);
			
		}else{
			
			return _res;
		}
		
		
	}
	
	public Iterator getWordIterator(){return tweetMap.keySet().iterator();}
	public String toString(){
		
		Set _keySet = tweetMap.keySet();
		Iterator _it = _keySet.iterator();
		
		String _res = "";
		
		//iterate all words
		while(_it.hasNext()){
			
			String _w = (String)_it.next();
			//get tweets for each word
			List<Tweet> _ts = tweetMap.get(_w);
			_res += "("+_w+"): [";
			//add tweets to string reault
			for(Tweet _t : _ts){
				_res+= _t.toString() +", ";
			}
			_res += "]\n";
		}
		
		return _res;
		
	}
}
