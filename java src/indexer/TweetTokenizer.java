package indexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fileIO.FileHandler;

public class TweetTokenizer {

		//private List<String> tokens;
		
		private String tweetDataPath;
		
		//private String tweetData;
		private String [] tweetData;
		
		//map used to serach for stop words
		private SearchMap map;
		
		private Stemmer stemmer;
		
		public TweetTokenizer(String _tweetDataPath,SearchMap _map) throws IOException{
			
			tweetDataPath = _tweetDataPath;
			map = _map;
			stemmer = new Stemmer();
			
			//tokens = new ArrayList<String>();
			
			//create handle to file of tweets
			FileHandler _fh = new FileHandler(this.tweetDataPath);
			
			//read tweets
			//tweetData = _fh.getText();
			tweetData = _fh.getLines();
			//System.out.println("file read: "+this.tweetData.length()+" bytes");
			System.out.println("done reading tweets");
		}
		
		
		/**
		 * Parses the tweet data and tokenizes each line and creates a list of Tweet objects
		 */
		public List<Tweet> tokenize(){
			
			//error checking
			if(tweetData == null || tweetData.length==0){
				
				throw new IllegalStateException("A problem occured whil tokenizing. The data wasn't initiated properly."
						+ "Make sure the file "+this.tweetDataPath+" exists and has the tweet data");
				
			}
			
			//create an array of tweets (1 tweet per new line)
			//String [] _lines = this.tweetData.split("\n");
			String [] _lines = this.tweetData;
			 
			//init the list of tokenized tweets
			List<Tweet> _tokenizedTweets = new ArrayList<Tweet>(_lines.length);
			
			//iterate lines/tweets and populate tokenized tweets list
			for(String _dataLine : _lines){
				
				String _id = this.getTweetId(_dataLine);
				String _data = _dataLine.substring(18);
				Tweet _tokenizedTweed = this.tokenizeLine(_id,_data);
				
				_tokenizedTweets.add(_tokenizedTweed);
				
			}
			
			return _tokenizedTweets;
			
		}
		
		
		/**
		 * Removes all specials symboles (. , ! ...) but keeps hyper links
		 * @param _data
		 * @return
		 */
		public static String removeSymboles(String _data){
			
			String [] _words = _data.split("\\s+");
			String _linksToKeep ="";
			//url regex
			String regex ="^(https?://)?[-a-zA-Z0-9+&@#/%?=~_|!:,\\.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			//String regex ="(https?://)?[\\da-z\\.-]+\\.[a-z\\.]{2,6}[/\\w\\s\\.-]*/?";
			String _res = "";
			int _index = 0;
			boolean _wwwflag=false;
			//find links and save them
			for(String _w : _words){
				
				_wwwflag=true;
				
				//found aurl, extract it
				if(_w.matches(regex)){
				
					//only manage the links if were adding them to index
					if(!ReverseIndexer.REMOVE_LINKS){
						_linksToKeep+=" "+_w;
						
						//we dont want the generic 'http://www' in the word vector, it serves little purpose
						_index = _w.indexOf("://www");
						
						//no www? so search just for index of ://
						if(_index==-1){
						
							_index = _w.indexOf("://");
							_wwwflag=false;
						}
						
						//found ://www (offset of 6 from index found)
						if(_wwwflag){
							//size of ://www is 6, so thats the offset of index to remove begining of link
							_w = _w.substring(_index+6);//dont include http/file/ftp in the link that wiil have its
						//	symboles erased
						}else{//no need to add offset, the :// will be erased anyway
							_w = _w.substring(_index);
							
						}
						
						//add link
						_res += " "+_w;// add word backt to result
						
					}else{
						
					
						//dont add link
						
					}//end if remove links
					
				}else{
					
					//dont add users to index
					if(!_w.startsWith("@")){
						_res += " "+_w;// add word backt to result
					}
					
					
				}	//end if found link
				
					
					
				
			}
			
			
			//keeping links?
			if(!ReverseIndexer.REMOVE_LINKS){
					
				//replace special chars with empty space and add the link back
				_res = _res.replaceAll("[\\[\\]{}<>^'\";!\\-\\+&#/%?,=~_|\\\\/:\\(\\)\\.\\*@]", " ") + _linksToKeep;
				
			}else{
				
				//dont add links
				_res = _res.replaceAll("[\\[\\]{}<>^'\";!\\-\\+&#/%?,=~_|\\\\/:\\(\\)\\.\\*@]", " ");
				
			}
			
			return _res;
			
		}
		
		
		private String getTweetId(String _dataLine){return _dataLine.substring(0,17);}
		/**
		 * Creates a Tweet object by tokenizing a provided data line. Note that
		 * this method will only correctly tokenize english tweets. We ignore
		 * the format of tweets of languages that have the id at the end of the
		 * tweet. This won't affect the search/indexing of english tweets 
		 * @param _dataLine The line of data representing a tweet.
		 * @return The tokenized Tweet object.
		 */
		public Tweet tokenizeLine(String _id,String _dataLine){
			
			//set all words to lower case
			_dataLine = _dataLine.toLowerCase();
			
			//get/prase the id of string
			//String _id =_dataLine.substring(0,17);
			
			Tweet _t = new Tweet(_id);
			
			//remove all the special character and symboles from data (keep the urls intact)
			//String _data = removeSymboles(_dataLine.substring(18));
			String _data = removeSymboles(_dataLine);
			
			//get the data and split by white space
			String [] _words = _data.split("\\s+");
			 
			//iterate words and add to tweet object
			for(String _w : _words){
				
				//make sure word is atleast 2 chars. its fine if we stem a word into smaller than 2,
				//since its probably a more meaninful word than a normal 2 character word
				if(_w.length()<2){
					continue;
				}
				
				String _stemmedW = "";
				//do we stem the word?
				if(ReverseIndexer.STEM_WORDS){
					
					//stem the word
					_stemmedW = stemmer.stemWord(_w);
					
					//_w += " "+_stemmedW;
					//add th sstemmed word as well to words
					
				}
			
				//only consider removing stop words if the global const flag
				//REMOVE_STOP_WORDS is true  and the word is a stop word do we 
				//not add it to vector
				if(!ReverseIndexer.REMOVE_STOP_WORDS){
			
					
					//add word no matter what
					_t.addWord(_w);
					
				} else if(!map.isStopWord(_w)){//we are remove stop words, only add if its not a stop word
				
					
					//if(_w.equals("the")){
						
						//System.out.println("were adding 'the' for some reason: removestopwords: "+ReverseIndexer.REMOVE_STOP_WORDS+"."+_w+" is stopword: "+map.isStopWord(_w));
						
					//}
					_t.addWord(_w);
			
					
				}
					
			}
			
			return _t;
		}//end tokenize data line into tweet
		
		
}//end class
