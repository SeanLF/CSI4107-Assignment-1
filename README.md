## CSI4107, Winter 2016
# Assignment 1
### Due Friday 12, 22:00
**Microblog information retrieval system**

|Student Number|Student Name|
|---|---|
|6778524|Sean Louis Alan Floyd|
|6797308|Joseph Patrick Ronald Killeen|

#### Division of tasks

We decided that we would each implement our own retrieval system. After completing our own solutions, we discussed them in detail and the changes that would lead to increasing the accuracy of the IR system.
To compare each solution, we used the [trec_eval](http://trec.nist.gov/trec_eval/) script.

#### Functionality of the programs
This document details our implementation of an Information Retrieval (IR) system. This system can be used for tweets and will return 1000 answers for any given query. The trec_eval script evaluates the first 1000 answers for 49 separate queries.

The IR system is implemented using the Ruby programming language.

Our system expects you to have the following files, which it will use as inputs: the TREC tweet corpus file (`Trec_microblog11.txt`), the question topics (`topics_MB1-49.txt`), and a list of stopwords (`StopWords.txt`). These files were provided to us by professor Diana Inkpen and can be found in the [instructions document](Instructions.md). The program outputs a retrieval and ranking results file (`Output.TREC`) to use for the trec_eval script.

#### How to run

To run the ruby solution:

+ ensure that Ruby is installed
+ download the files containing the tweets, stop words, and questions.
+ type: `ruby ./asg1.rb` at your terminal

#### Explanation
+ _Ruby_ solution

  + **Algorithms**  
    Each line in the tweet corpus represents a tweet. It uses the following format: `tweet_id    tweet_content`. Therefore,  the corpus is analyzed line by line (described below in sequential order). 
    1. Find, store and remove the 17 digit integer tweed ID from the line.
    2. Use a regular expression to find and remove URLs.
    3. Downcase whatever is left and scan with a regular expression matching words formed by at least 2 Latin characters; thus removing most of the non-English words.
    4. Count the frequency of the words in the tweet. Each frequency is then divided by the maximum word frequency in the tweet. Also store the unique words for each tweet. The term frequencies are stored in the inverted index.
    5. Calculate the inverse document frequency using the inverted index (the unique number of documents in which each word appears) and calculate the weights for each word in every tweet. Replace the term frequencies by the tf_idf weights in the inverted index.

  + **Data structures**  
  The hash and array objects used are provided by Ruby. The array provides methods such as sorting, array merging, finding unique elements and reversing itself. It also provides a `.each` method to iterate through its elements.  
  The hash object proved to be very useful when parsing the XML, properly building the index. It provides some useful methods such as returning an array of its keys or values. The hash also provides a `.each` method that allows iteration with the key and/or value.

  + **Optimizations that we used**  
  Because URLs within tweets were decomposed into words, the quantity of words per tweet increased, generally lowering the precision of our IR system. Considering that the system was not designed to search for links, we simply removed them from the tweet before we conducted an analysis.

+ _Java_ solution

  + **Algorithms**  
    Our tokenization and indexing method follows:
      1.	To find words, we read the tweets file line by line, splitting each line by white space to initially tokenize words to process them further individually. 
      2.	Lowercase each word.
      3.	Remove any URLs by using a regular expression to identify them.
      4.	Remove any symbols from the words.
      5.	Stem each word adding the word and its stem to the index only if they aren’t a stop word.  

    The query processing is done as follows:
      1.	convert the query into a tweet, so it can be tokenized into a word vector with an inverse frequency
      2.	For each word found, retrieve the tweets with those words
      3.	Use the cosine vector comparison to compare the query vector and each tweet sharing words with the query
      4.	Order the tweets by rank

  + **Data structures**  
  Java Hash tables, Java Lists, and Java Arrays were used as data structures for the robust indexing of words in the tweets.
    + Arrays: Stopwords were added in lexicographical order to a string array. Looking up a stop word could be done using a binary search, which is efficient. Hence, this is an effective data structure for identifying stop words. 
    + Hash tables were used to hold words found in each tweet. The word (string) is the key, and the number of words occurring in the tweet is stored as a value. Hence, tweets had their word vectors. The total word frequencies for all the tweets were stored in a hash table, with the word (string) as the key and the number of total occurrences as the value. To lookup the tweets that have a given word, we used a hash table. The word (string) was the key, and the list (java.util.ArrayList) of tweets containing the word was the value. Thus looking up tweets that have a given word was done efficiently and looking up words found in a tweet were both done efficiently. 
    + The inverse frequency table: was a hash table of tweets (the hash key was the twitter id, a string). The values stored in the inverse frequency table were the word vectors (hash table of words) for the tweet. The word vector had the inverse frequency of each word found in the tweet.

  + **Optimizations that we used**  
  URLs added to the index lowered the precision of our results. We decided to remove URLs from the index. We also decided to stem words and add the stem along with the word to the index, to allow general queries to find the stem of a word and it can find a more specific word.


#### Vocabulary size
The vocabulary size in the Ruby solution is _59874_ words.

#### Sample tokens from our vocabulary.
The following are 10 sample tokens from the ruby solution: `bbc, world, service, savage, cuts, lot, people, fun, question, rethink`

#### First 10 answers to queries 1 and 25.
- Ruby solution:
  - Query 1: `[30260724248870912, 33823403328671744, 30198105513140224, 34952194402811904, 32504175552102401, 32158658863304705, 30236884051435520, 30244402504929280, 29993695927336960, 30303184207478784]`
  - Query 25: `[31773184512495616, 31286354960715777, 31550836899323904, 31320463862931456, 33207460051292160, 32955753920733184, 32685391781830656, 32609015158542336, 32527914905894912, 31847718259269632]`

#### Discussion of our final results.
There were some surprising findings from the ruby version:
+ Removing twitter users (ie. @BBCWitness) reduced our _precision at 5_ by 2.4%.
+ Stemming words reduced our _precision at 5_ by 6.12%.
+ Singularizing words reduced our _precision at 5_ by 2.85%.
+ Removing links increased our _precision at 5_ from 10.61% to 32.65%.

Final results:
```bash
$ ./trec_eval Trec_microblog11-qrels.txt Output.TREC 
num_q          	all	49
num_ret        	all	36733
num_rel        	all	2640
num_rel_ret    	all	2152
map            	all	0.1967
gm_ap          	all	0.1213
R-prec         	all	0.2184
bpref          	all	0.2040
recip_rank     	all	0.5253
ircl_prn.0.00  	all	0.6015
ircl_prn.0.10  	all	0.4119
ircl_prn.0.20  	all	0.3001
ircl_prn.0.30  	all	0.2469
ircl_prn.0.40  	all	0.2264
ircl_prn.0.50  	all	0.2146
ircl_prn.0.60  	all	0.1605
ircl_prn.0.70  	all	0.1274
ircl_prn.0.80  	all	0.0976
ircl_prn.0.90  	all	0.0546
ircl_prn.1.00  	all	0.0129
P5             	all	0.3265
P10            	all	0.3082
P15            	all	0.2816
P20            	all	0.2612
P30            	all	0.2306
P100           	all	0.1520
P200           	all	0.1156
P500           	all	0.0727
P1000          	all	0.0439
```
