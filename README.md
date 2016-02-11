## CSI4107, Winter 2016
# Assignment 1
### Due Friday 12, 22:00
**Microblog information retrieval system**

|Student Number|Student Name|
|---|---|
|6778524|Sean Louis Alan Floyd|
|6797308|Joseph Patrick Ronald Killeen|

#### Division of tasks

We decided that we would both implement a retrieval system of our own. We would then meet up to determine what could be improved, and provide feedback to each other. Whichever solution was better would be submitted.

#### Functionality of the programs
a detailed note about the functionality of your programs
_Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
tempor incididunt ut labore et dolore magna aliqua._

#### How to run

##### To run the ruby version

+ ensure that ruby is installed
+ download the files containing the tweets, stopwords, and questions.
+ head over to your terminal, and type: `ruby ./asg1.rb`

##### To run the Java version
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

#### Explanation
+ _Ruby_ version
  + **algorithms**  
  To find words, we downcase each word, then remove stopwords. We also add words to stopwords to remove common parts of URLs.
  + **data structures**  
  We use the hash and array objects provided by Ruby. The array provides a few methods out of the box such as sorting and grouping. The hash is very useful when parsing the XML, properly building the index and also provides some useful methods.
  + **optimizations that we used**  
  We noticed that the URL would be decomposed into words when possible and would increase the quantity of words per tweet. This could allow for lower scores considering that our information retrieval system is not meant to search for links, hashtags or users. Removing the links before calculating the weights increased the accuracy by almost twofold.
  + Suprising findings  
  Removing twitter users (ie. @BBCWitness) reduced our _precision at 5_ by 2.4%.
  Stemming words reduced our _precision at 5_ by 6.12%.
  Singularizing words reduced our _precision at 5_ by 2.85%.
  Removing links increased our _precision at 5_ from 10.61% to 32.65%.

#### Vocabulary size
The vocabulary size in the Ruby version is _59874_ words.

#### Sample tokens from our vocabulary.
Ruby sample tokens: `[bbc, world, service, savage, cuts, lot, people, fun, question, rethink]`

#### First 10 answers to queries 1 and 25.
- Ruby version:
  - Query 1: `[30260724248870912, 33823403328671744, 30198105513140224, 34952194402811904, 32504175552102401, 32158658863304705, 30236884051435520, 30244402504929280, 29993695927336960, 30303184207478784]`
  - Query 25: `[31773184512495616, 31286354960715777, 31550836899323904, 31320463862931456, 33207460051292160, 32955753920733184, 32685391781830656, 32609015158542336, 32527914905894912, 31847718259269632]`

#### Discussion of our final results.
 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
