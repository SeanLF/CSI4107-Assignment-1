package indexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import fileIO.FileHandler;
import fileIO.PerformanceLogger;

public class ReverseIndexer {
	private String dataPath;
	//private WordVector wordFreqList;
	private SearchMap map;
	
	public static final boolean REMOVE_STOP_WORDS=true;
	
	public static final boolean STEM_WORDS=true;
	
	public static final boolean REMOVE_LINKS=true;
	
	private	TweetTokenizer tokenizer;
	
	public ReverseIndexer(String
			_dataPath)throws IOException{
        map = new SearchMap("C:\\Users\\OWNER\\Documents\\edu\\uni\\hiver2016\\CSI4107\\ass\\1\\dev\\stopwords.txt");		
		dataPath=_dataPath;
		tokenizer = new TweetTokenizer(_dataPath,map);
		//wordFreqList = new WordVector();
	}
	
	public void init()throws IOException{
	
		List<Tweet> _table = createFrequencyTable();
		 
		 createInverseFreqTable(_table );
	}
	/*
	public void tokenizeDocuments(int _num)throws IOException{
		
		List<List<String>> _docs = parseDocuments(_num);
		for(int i=0;i<_docs.size();i++){
			
			List<String> _tokens = _docs.get(i);
			
			FileHandler _fH = new FileHandler(dataPath+(i+1)+".parsed");
			String _tmp = "";
			for(String _token: _tokens){
				
				_tmp+=_token+" ";
			}
			
			_fH.writeBytes(_tmp.getBytes());
			
			
		}
	}
	public List<List<String>> parseDocuments(int _numDocs) throws IOException{
		
		List<List<String>> _docs = new ArrayList<List<String>>(_numDocs);
		
		List<TokenizerWorker> _workers = new ArrayList<TokenizerWorker>(_numDocs);
		for(int i=0;i<_numDocs;i++){
			
			FileHandler _fH = new FileHandler(dataPath+(i+1)+"");
			
			String _html = _fH.getText();
			
			TokenizerWorker _tw = new TokenizerWorker(_html);
			_tw.run();
		//	Thread _t = new Thread(_tw);
			
		//	_t.start();
			
			_workers.add(_tw);
			
		}
		
		//create doc token list
		for(TokenizerWorker _t: _workers){
			//_t.waitForTokens();
			_docs.add(_t.getTokens());
			
		}
		
		return _docs;
	}
	*/
	//creates the frequency table and tweet lookup table, and return list of tweets tokenized
	public List<Tweet> createFrequencyTable()throws IOException{
		
		System.out.println("in createFreq table");
		//tokenizer = new TweetTokenizer(this.dataPath, this.map);
		
		//tokenize tweets from file into list (this may fail)
		List<Tweet> _table = tokenizer.tokenize();
		
		System.out.println("finished tokenizing tweets");
		//iterate tweets
		for(int i=0;i<_table.size();i++){
			
			Tweet _t = _table.get(i);
			
			//get words in tweet
			WordVector _wordList = _t.getWordList();
			
			//get the iterator to iterate words
			Iterator _it =_wordList.getWordIterator();
		
			//add words to freqency table, saving the tweet the word is found in as well
			while(_it.hasNext()){
				
				String _w = (String) _it.next();
				
				//count words found in a hash lookup table
				//wordFreqList.addWord(_w.getValue());
				map.addToWordFrequency(_w, _t);
				
				//create an inverse lookup table to find list of 
				//tweetMap.put(_w.getValue(),_t);
				//throw new IllegalStateException("Not implemented add a document to word found thats added to list");
				
			}//end iterate words
			
			
		}//end iterate tweets
		
		return _table;
		
	}//end create freq table
	
	public static double computeWeight(double _wordFreq, double _numDocs, double _numDocsWordIsIn){
		
		double _log2 = Math.log10(2);
		
		double _res = _wordFreq *( Math.log10(_numDocs/_numDocsWordIsIn)/_log2);
		
		return _res;   
		
	}
	
	private void createInverseFreqTable(List<Tweet> _freqTable){
		
		//List<Tweet> _table = new ArrayList<Tweet>(_freqTable.size());
		
		//iterate the tweets
		for(Tweet _t: _freqTable){
			
			Iterator _it = _t.getWordIterator();
			//create a new tweet that will have a vecote of inverse frequencyes for its words
			Tweet _newTweet = new Tweet(_t.getId());
			
			//iterate all words of the tweet
			while(_it.hasNext()){
				
				String _w = (String)_it.next();
				
				//double _revFreq = computeWeight(_t.getWordCount(_w),_freqTable.size(),this.wordFreqList.getWordCount(_w));
				double _revFreq = computeWeight(_t.getWordCount(_w),_freqTable.size(),this.map.getWordCount(_w));
				
				_newTweet.addWordAndSetFrequency(_w,_revFreq);
			}
			
			//_table.add(_newTweet);
			map.addToInverseTable(_newTweet,_t);
		}
		
		//return _table;
		
	}
	
	public List<String> getQueryWords(String _q){
		
		
		
		//_q = _q.toLowerCase();
		
		String [] _words = _q.split("\\s+");
		
		List<String> _res = new ArrayList<String>(_words.length);
		
		for(String _w : _words){
			
			_res.add(_w);
			
		}
		
		return _res;
	
	}
	

		private void doMergeSort(int lowerIndex, int higherIndex, List<QueryResult> _res,List<QueryResult> _tmp) {
			
			if (lowerIndex < higherIndex) {
				int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
				// Below step sorts the left side of the array
				doMergeSort(lowerIndex, middle,_res,_tmp);
				// Below step sorts the right side of the array
				doMergeSort(middle + 1, higherIndex,_res,_tmp);
				// Now merge both sides
				mergeParts(lowerIndex, middle, higherIndex,_res,_tmp);
			}
		}

		private void mergeParts(int lowerIndex, int middle, int higherIndex,List<QueryResult> _res,List<QueryResult> _tmp) {

			for (int i = lowerIndex; i <= higherIndex; i++) {
				_tmp.set(i, _res.get(i));
			}
			int i = lowerIndex;
			int j = middle + 1;
			int k = lowerIndex;
			while (i <= middle && j <= higherIndex) {
				if (_tmp.get(i).getRank() > _tmp.get(j).getRank()) {
					//array[k] = tempMergArr[i];
					_res.set(k, _tmp.get(i));
					i++;
				} else {
					//array[k] = tempMergArr[j];
					_res.set(k, _tmp.get(j));
					j++;
				}
				k++;
			}
			while (i <= middle) {
				//array[k] = tempMergArr[i];
				_res.set(k, _tmp.get(i));
				k++;
				i++;
			}

		}


	private List<QueryResult> sortQueryResults(List<QueryResult> _res){
		
		List<QueryResult> _tmp = new ArrayList<QueryResult>(_res.size());
		for(int i=0;i<_res.size();i++){
			
			_tmp.add(null);
			
		}
		doMergeSort(0, _res.size() - 1,_res,_tmp);
		
		return _res;
		
	}
	public List<QueryResult> query(String _q)throws IOException{
		
	/*	String [] _words = _q.split("\\s+");
		
		WordVector _wl = new WordVector();
		
		for(String _w : _words){
			
			_wl.addWord(_w);
			
		}
		*/;
		
		//create a temporary tweet, we just need its vecotr (the query vector)
		Tweet _tmpTweet = tokenizer.tokenizeLine("", _q);
			
		//create query vectore
		WordVector _queryVec = _tmpTweet.getWordList();
			

		//tokenize query
		List<String> _qwords = new ArrayList<String>(_q.length()); 
				
		Iterator _it = _queryVec.getWordIterator();
		
		while(_it.hasNext()){
			
			_qwords.add((String)_it.next());
			
		}
		//retrreive relevant wordvectors from tweets that have atleast one word in the list of query words
		List<WordVector> _inverseFreqVecs = map.getInverseFrequencyVectors( _qwords);
	
		 
		//list of rankings for each tweet found
		List<Double> _rankings = new ArrayList<Double>(_inverseFreqVecs.size());
		
		//compare the query vector to all tweets that had a word in query
		for(int i=0;i<_inverseFreqVecs.size();i++){
			
			WordVector _revFreqVec  = _inverseFreqVecs.get(i);
			
			Double _rank = this.compareVectors(_queryVec,_revFreqVec);
			
			_rankings.add(_rank);
			
		}
		
		List<QueryResult> _res = new ArrayList<QueryResult>(_rankings.size());
		
		for(int i=0;i<_inverseFreqVecs.size();i++){
			
			WordVector _revFreqVec  = _inverseFreqVecs.get(i);
			Tweet _t = map.lookUpFreqTweet(_revFreqVec.getTweetParent());
			Double _rank = _rankings.get(i);
			
			//System.out.println(_t.toString()+"***********"+_rank);
			_res.add(new QueryResult(_t,_rank));
		}
		
		return this.sortQueryResults(_res);
	}
	
	private void printQueryResult(List<QueryResult> _res ){
		
		for(QueryResult _qr : _res){
			
			System.out.println(_qr);
			
		}
		
	}
	
	public double compareVectors(WordVector _wl1, WordVector _wl2){
		
		//dot prod / length*length
		double _len1 = this.vectorLength(_wl1);
		double _len2 = this.vectorLength(_wl2);
		
		double _tmp = 0;
		Iterator _it = _wl1.getWordIterator();
		
		while(_it.hasNext()){
			
			String _w = (String) _it.next();
			
			double _f1 = _wl1.getWordCount(_w);
			double _f2 = _wl2.getWordCount(_w);
			
			_tmp += (_f1*_f2);
			
		}
		
		return Math.abs(_tmp/(_len1*_len2));
		
	}
	
	public double vectorLength(WordVector _wl){
		
		double _tmp = 0;
		
		Iterator _it = _wl.getWordIterator();
		
		while(_it.hasNext()){
			
			String _w = (String) _it.next();
			
			double _freq = _wl.getWordCount(_w);
			
			_tmp+= Math.pow(_freq, 2);
			
		}
		
		return Math.sqrt(_tmp);
	}

	private void outputQuery(String _path,String _q) throws IOException{
		
		String _out = "********************************************\n\n\nQuery: "+_q;
		long _before = System.currentTimeMillis();
		List<QueryResult> _res = query(_q);
		long _after = System.currentTimeMillis();
		_out+="\nResults found in "+(_after-_before)+"ms:\n";
		Tweet _t = this.tokenizer.tokenizeLine("", _q);
		_out+="tokenized query: "+_t.getWordList().toString()+"\n";
		for(QueryResult _qr: _res){
			
			 
			_out+=_qr.toString()+"\n";
		}
		
		FileHandler _fh = new FileHandler(_path);
		_fh.appendBytesToFile(_out.getBytes());
	}
	public static void main(String [] args)throws Exception{
	
	  //  System.out.println(removeSymboles("some, dat with ! symbolws liek % http://www.google.ca"));


		PerformanceLogger.initiateLogger();

		ReverseIndexer _ri = new ReverseIndexer("C:\\Users\\OWNER\\Documents\\edu\\uni\\hiver2016\\CSI4107\\ass\\1\\dev\\Trec_microblog11.txt");
		
		//make sur the is in stop words
	//	System.out.println("the is stop word?: "+_ri.map.isStopWord("the"));
		//System.out.println("the compare to these: "+ "the".compareTo("these")); 
		_ri.init();
		PerformanceLogger.log("Time for indexing firs 3144 tweets file, removing stop words and stem, reading all tweets, line by line");

		//PerformanceLogger.initiateLogger();
		String _q = "experimental drug";
	//	_ri.printQueryResult(_ri.query(_q));
		//PerformanceLogger.log("Time for querying '"+_q+"', removing stop words and stem");
		// create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner(System.in);

		do{
		
	    //  prompt for the user's name
	    System.out.print("query> ");

	    // get their input as a String
	    _q = scanner.nextLine();
	    if(_q.equals("exit")){
	    	System.exit(0);
	    }
	    _ri.outputQuery("C:\\Windows\\temp\\tweetQueries.txt",_q);
		}   while(true);
	
	
	}
}
