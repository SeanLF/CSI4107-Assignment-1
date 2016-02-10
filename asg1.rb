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
        weights: Hash.new(BigDecimal.new(0)),
        idf: BigDecimal.new(0)
      }
    }
  end

  # Build the index.
  def build_index(corpus_file)
    # For each tweet
    IO.readlines(corpus_file).each do |tweet|
      # find and store the tweet id
      matches = tweet.match(/(\d{17})/)
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
    frequencies = Hash.new(BigDecimal.new(0))

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
    frequencies.each do |word, frequency|
      frequencies[word] = frequency / maximum_frequency
    end
    return frequencies
  end

  # Calculate the document frequencies (df) and idf and tf_idf weights.
  def calculate_tweet_weights
    number_of_tweets = BigDecimal.new(@tweets.size)
    @index.each do | word, hash |
      document_frequency = BigDecimal.new(hash[:weights].size)
      hash[:idf] = Math.log2(number_of_tweets / document_frequency)
      puts "#{word}, #{number_of_tweets} / #{document_frequency} = #{hash[:idf]}" if hash[:idf] == 0
      puts "log2(#{number_of_tweets / document_frequency}) = #{hash[:idf]}" if hash[:idf] == 0

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
    cosineSimilarities = Hash.new(BigDecimal.new(0))
    term_frequencies = calculate_term_frequencies(string)
    query_term_weights = calculate_query_weights(term_frequencies)
    relevant_tweets = get_relevant_tweets(term_frequencies.keys)
    relevant_tweets.each do |tweet|
      cosineSimilarities[tweet] = calculate_cosine_similarity(tweet, query_term_weights)
    end

    return cosineSimilarities.sort_by { |tweet, value| value }.reverse
  end

  def calculate_query_weights(term_frequencies)
    weights = Hash.new(BigDecimal.new(0))
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
    word_weights = BigDecimal.new(0)
    word_squared_weights = BigDecimal.new(0)
    tweet_squared_weights = BigDecimal.new(0)

    # The sum of weights for query words and sum of weights^2
    query_term_weights.each do |word, query_word_weight|
      word_weights += query_word_weight * @index[word][:weights][tweet]
      word_squared_weights += query_word_weight**2
    end

    # Calculate weights squared for words in tweets
    @tweets[tweet].each do |word|
      tweet_squared_weights += @index[word][:weights][tweet]**2
    end

    word_weights / Math.sqrt(word_squared_weights * tweet_squared_weights)
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
        linesToWrite << "#{question} Q0 #{r[0]} #{i} #{r[1]} rubyRun\n"
      end
    end

    # Write to file
    File.open('Output.TREC', 'w') do |f|
      linesToWrite.each do |line|
        f << line
      end
    end
  end
end
