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
+ head over to `irb` and type
```ruby
# Load the required file into the environment
require './asg1.rb'

# Instantiate an assignment object.
# It will fetch the stopwords, store the queries, build the index and assign weights.
assignment = Assignment.new

# This will run all of the queries and write the results to 'Output'
assignment.run_queries
```

##### To run the Java version
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

#### Explanation
+ algorithms  
 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
+ data structures  
 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
+ optimizations that we used  
 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.

#### Vocabulary size
The vocabulary size in the Ruby version is _76773_ words.

#### Sample tokens from our vocabulary.
Ruby sample tokens: `[bbc, world, service, savage, cut, http, www, petitionbuzz, com, petition]`

#### First 10 answers to queries 1 and 25.
- Ruby version:
  - Query 1: `[30244402504929280, 32504175552102401, 33823403328671744, 32415024995631105, 29997693933719553, 30236884051435520, 30299217419304960, 30051954050727936, 30554037510213632, 30162553262841857]`
  - Query 25: `[31286354960715777, 31550836899323904, 31738694356434944, 32864814447460352, 33198317529735169, 33174905985957888, 32955753920733184, 30767638397321217, 30704222135652352, 29993056316948480]`

#### Discussion of our final results.
 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
