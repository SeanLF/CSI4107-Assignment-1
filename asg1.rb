require 'active_support/inflector'
require 'nokogiri'
require 'active_support/all'

class Assignment
  attr_accessor :index
  attr_reader :stopwords
  attr_accessor :tweets
  attr_accessor :questions

  def initialize
    # Fetch stopwords & set up data structures.
    fetch_stopwords('StopWords.txt')

    # keep a record of all tweet IDs.
    @tweets = Hash.new

    # initialize the index data structure.
    initialize_index_stucture()

    # Get a hash of question numbers and questions.
    parse_questions('topics_MB1-49.txt')

    # Build the index from the corpus of tweets.
    build_index('Trec_microblog11.txt')
  end

  #### Index logic

  # Fetches an array of stopword symbols.
  def fetch_stopwords(file_name)
    @stopwords = File.open(file_name).read.split.map! {|stopword| stopword.to_sym}
  end

  # Build index data structure.
  def initialize_index_stucture
    @index = Hash.new { |hash, word|
      hash[word] =
      {
        weights: Hash.new(0.0),
        idf: 0.0
      }
    }
  end

  # Build the index.
  def build_index(corpus_file)
    # For each tweet
    IO.readlines(corpus_file).each do |tweet|
      # find and store the tweet id
      matches = tweet.match(/^(\d{17})/)
      tweet_id = matches[1]

      # Get term frequencies. (hash[word symbol] = term frequency)
      term_frequencies = calculate_term_frequencies(tweet)

      # Store the term frequencies in the index.
      term_frequencies.each do |word, term_frequency|
        @index[word][:weights][tweet_id] = term_frequency
      end

      @tweets[tweet_id] = term_frequencies.keys
    end

    # Store inverse document frequencies in the index
    calculate_tweet_weights()
  end

  def calculate_term_frequencies(string)
    frequencies = Hash.new(0.0)

    # Get each word of length greater than 2.
    string.downcase.scan(/[[:alpha:]]{2,}/).each do |word|
      # Singularize the word and convert it to a symbol.
      word = ActiveSupport::Inflector.singularize(word).to_sym

      # If the word isn't a stopword, increase its frequency.
      unless @stopwords.include?(word)
        frequencies[word] += 1
      end
    end

    # get the maximum frequency
    maximum_frequency = frequencies.values.max

    # Calculate term frequencies
    frequencies.each { |word, frequency| frequencies[word] = frequency / maximum_frequency }

    return frequencies
  end

  # Calculate the document frequencies (df) and idf and tf_idf weights.
  def calculate_tweet_weights
    number_of_tweets = @tweets.size
    @index.each_value do | hash |
      document_frequency = hash[:weights].size
      hash[:idf] = Math.log2(number_of_tweets / document_frequency)

      # calculate weights for each document
      hash[:weights].each_pair do |tweet_id, term_frequency|
        hash[:weights][tweet_id] = term_frequency / hash[:idf]
      end
    end
  end

  #### Query logic

  def parse_questions(question_file)
    @questions = {}
    Nokogiri::XML("<xml>"+IO.readlines(question_file).join.gsub("\n","")+"</xml>").xpath('//top').each { |node| questions[node.xpath('num').text.match(/\sNumber\: MB(\d{3})\s/)[1].to_i] = node.xpath('title').text.strip }
  end

  def query(string)
    cosineSimilarities = Hash.new(0.0)
    term_frequencies = calculate_term_frequencies(string)
    query_term_weights = calculate_query_weights(term_frequencies)
    relevant_tweets = get_relevant_tweets(term_frequencies.keys)
    relevant_tweets.each do |tweet|
      cosineSimilarities[tweet] = calculate_cosine_similarity(tweet, query_term_weights)
    end

    return cosineSimilarities.sort_by { |tweet, value| value }.reverse
  end

  def calculate_query_weights(term_frequencies)
    weights = Hash.new(0.0)
    term_frequencies.each do |word, term_frequency|
      weights[word] = (0.5 + 0.5 * term_frequency) * (@index[word][:idf])
    end
    return weights
  end

  def get_relevant_tweets(words)
    documents = Array.new
    words.each do |word|
      documents += @index[word][:weights].keys
    end
    return documents.uniq
  end

  def calculate_cosine_similarity(tweet, query_term_weights)
    # Query words, and their weights
    query_words = query_term_weights.keys
    query_term_weights = query_term_weights.values

    # The sum of weights for query words and sum of weights^2
    query_word_sum = query_term_weights.inject(0, :+)
    query_word_squared_sum = query_term_weights.inject(0) { |sum, item| sum + item*item }

    # Take the words present in the query and get their weights for the current document.
    values = []
    query_words.each do |word|
      values << @index[word][:weights][tweet]
    end

    # Calculate the sum of weights, and the sum of weights^2
    document_word_sum = values.inject(0, :+)
    document_word_squared_sum = values.inject(0) { |sum, item| sum + item*item }

    # Calculate the cosine similarity
    cosSim = (query_word_sum + document_word_sum) / (Math.sqrt(query_word_squared_sum + document_word_squared_sum))

    return cosSim
  end

  #### Run all queries and output to file

  def run_queries
    results = Hash.new
    # Run all queries and store results
    @questions.each do |question_number, question|
      results[question_number] = query(question).first(1000)
    end

    linesToWrite = Array.new
    results.each do |question, result|
      result.to_enum.with_index(1).each do |r, i|
        linesToWrite << "MB1 Q#{question} #{r[0]} #{i} #{r[1]} rubyRun\n"
      end
    end

    # Write to file
    File.open('Output', 'w') do |f|
      linesToWrite.each do |line|
        f << line
      end
    end
  end
end
