package main;

import fileIO.FileHandler;
import indexer.QueryResult;
import indexer.ReverseIndexer;
import indexer.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	//reads file and create  object from fil for testing queries
	public static List<TestQuery> parseTestQueries(String _testQueryPath)throws IOException{

		List<TestQuery> _res = new ArrayList<TestQuery>(49);
		
		FileHandler _fh = new FileHandler(_testQueryPath); 
		
		/*
		 *sample format 
<top>
<num> Number: MB049 </num>
<title> carbon monoxide law </title>
<querytime> Tue Feb 01 22:44:23 +0000 2011 </querytime>
<querytweettime> 32569981321347074 </querytweettime>
</top>
		 */
		
		//read lines from file
		String [] _lines = _fh.getLines();
		int _numParsed = -1;
		String _q = "";
		//iterate the lines
		for(String _l : _lines){
			
			//we want to extract the data from <num> ... </num> and <title> ... </title>
			
			//extract number: (Number: MB049 == 49)
			if(_l.startsWith("<num>")){
				
				//find the MB index
				int _i = _l.indexOf("MB");
				
				//extarct num from MB000
				//				   |    \
				//                 _i   _i+5
				String _num = _l.substring(_i+2, _i+5);
				
				_numParsed = Integer.parseInt(_num);
				
				
				
			}else if(_l.startsWith("<title>")){
				
				//find the </title> index
				int _i = _l.indexOf("</title>");
				
				//extarct query
				//	<title> ... </title>
				//  0      6 ..._i          
				 _q = _l.substring( 7, _i);
				
			}else if(_l.startsWith("</top>")){
				
				String _numParsedStr = ""+_numParsed;
				
				//end of test query, create a query test and add to result
				_res.add(new TestQuery(_numParsedStr,_q));
				
				//reset vars
				_numParsed=-1;
				_q="";
			}else{
				
				//irrelevant line, do nothing
				
			}
			
		}//end iterate lines
		
		return _res;
		
	}
	
	//outputs the query result in the format for the output file
	public static String outputQueryResult(String _testQueryNum , List<QueryResult> _res) throws IOException{
		
		
		//testquery number	0		tweetid			rank
		String _out = "";
		
		int i=1;
		//go through each tweet retreived
		for(QueryResult _qr : _res){
			
			if(i<=1000){
			//extract id of tweet
			Tweet _t = _qr.getTweet();
			String _id = _t.getId();
			
			//get rank of tweet
			double _r = _qr.getRank();
			
			//build output line
			_out+=_testQueryNum+"\tQ0\t"+_id+"\t"+i+"\t"+_r+" javaRun\r\n";
			
			i++;
			
			}else{
				
				//top 1000 reults output
				break;
				
			}
			
		}
		
		return _out;
		
	}
	
	public static void testSystem(String _tweetDataPath, String _outputPath, String _testQueryPath)throws IOException{
		
		FileHandler _fh = new FileHandler(_outputPath);
		
		
		//start the indexing
		ReverseIndexer _ri = new ReverseIndexer(_tweetDataPath);
		 
		_ri.init();
		
		//get all the test queries from the file
		List<TestQuery> _queriesToTest =  parseTestQueries(_testQueryPath);
		
		String _out = "";
		
		
		//build output by exsecuting queries one by one
		for(TestQuery _q: _queriesToTest){
			
			//extract query from test query
			String _query = _q.getQuery();
			
			//execute query 
			List<QueryResult> _res = _ri.query(_query);
			
			//add the formate query output to the final output result 
			_out+=outputQueryResult(_q.getNumber(),_res);
			
		}
		
		//write all test query ouput to output file
		_fh.createNewFile(_out.getBytes());
		
	}
	public static void main(String[] args) throws IOException{
		if(args.length!=3){
			
			System.out.println("Invalid arguments... Usage: <tweet data path> <result output path> <test query path>");
			System.exit(1);
		}
		
		System.out.println("..running");
		// TODO Auto-generated method stub
		testSystem(args[0],args[1],args[2]);
		System.out.println("..done");
	}

}
