## CSI4107, Winter 2016
# Assignment 1
### Due Friday 12, 22:00
**Microblog information retrieval system**

|Student Number|Student Name|
|---|---|
|6778524|Sean Louis Alan Floyd|
|6797308|Joseph Patrick Ronald Killeen|

#### Division of tasks

We decided that we would both implement a retrieval system of our own. After completing our own solution, we discussed it in detail, and the changes that led to increasing the accuracy of the IR system.
To compare each solution, we used the [trec_eval](http://trec.nist.gov/trec_eval/) script.

#### Functionality of the programs
This document details our implementation of an Information Retrieval system. This system will be used for tweets and will return 1000 answers for any given query. The trec_eval script will evaluate the first 1000 answers for 49 seperate queries.

The IR system is implemented using the Ruby programming language.

Our system expects you to have the following files, which it will use as inputs: the TREC tweet corpus file (`Trec_microblog11.txt`), the question topics (`topics_MB1-49.txt`), and a list of stopwords(`StopWords.txt`). These files were provided to us and can be found in the [instructions document](Instructions.md). The program outputs a retrieval and ranking results file (`Output.TREC`) to use for the trec_eval script.

#### How to run

To run the ruby solution:

+ ensure that ruby is installed
+ download the files containing the tweets, stopwords, and questions.
+ head over to your terminal, and type: `ruby ./asg1.rb`

##### To run the Java version
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

#### Explanation
+ _Ruby_ version
  + **algorithms**  
    Each line in the tweet corpus represents a tweet. It uses the following format: `tweet_id    tweet_content`. Therefore, we analyze the corpus by lines. 
    1. Find, store and remove the 17 digit integer tweed ID from the line.
    2. We then use a regular expression to find and remove URLs.
    3. Whatever is left is downcased and scanned with a regular expression matching at words formed by at least 2 latin characters; thus removing most of the non-english words.
    4. We count the frequency of the words in the tweet. Each frequency is then divided by the maximum word frequency in the tweet. We also store the unique words for each tweet. The term frequencies are stored in our inverted index.
    5. Calculate the inverse document frequency using the inverted index (the unique number of documents that each word appears in) and calculate the weights for each word in every tweet. We replace the term frequencies by the tf_idf weights in our inverted index.  
  + **data structures**  
  We use the hash and array objects provided by Ruby. The array provides methods such as sorting, array merging, finding unique elements and reversing itself. It also provides a `.each` method to iterate through its elements.  
  The hash object proved to be very useful when parsing the XML, properly building the index. It provides some useful methods such as returning an array of its keys or values. The hash also provides a `.each` method that allows iteration with the key and/or value.
  + **optimizations that we used**  
  We noticed that URLs within tweets were decomposed into words and would in turn increase the quantity of words per tweet. This generally lowered the precision of our IR system. Considering that it was not designed to search for links or users, we simply removed them from the tweet before its analysis.

#### Vocabulary size
The vocabulary size in the Ruby solution is _59874_ words.

#### Sample tokens from our vocabulary.
10 sample tokens from the ruby solution: `[bbc, world, service, savage, cuts, lot, people, fun, question, rethink]`

#### First 10 answers to queries 1 and 25.
- Ruby solution:
  - Query 1: `[30260724248870912, 33823403328671744, 30198105513140224, 34952194402811904, 32504175552102401, 32158658863304705, 30236884051435520, 30244402504929280, 29993695927336960, 30303184207478784]`
  - Query 25: `[31773184512495616, 31286354960715777, 31550836899323904, 31320463862931456, 33207460051292160, 32955753920733184, 32685391781830656, 32609015158542336, 32527914905894912, 31847718259269632]`

#### Discussion of our final results.
Suprising findings from the ruby version:
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
