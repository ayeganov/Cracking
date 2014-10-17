#!/usr/bin/env python
# -*- coding: utf-8 -*-
import argparse
import collections
import os
import random
import time

Interval = collections.namedtuple("Inteval", ['letter', 'start', 'end'])

def read_dict_file(path):
    """
    Reads the dictionary file and returns a list of words.

    @param path - path to the dictionary file
    @return a list of words

    """
    if path is None:
        raise ValueError("Must specify a valid path.")
    if not os.path.isfile(path):
        raise ValueError("Path must point to a regular file.")

    with open(path) as f:
        whole_file = f.read()

    return whole_file.split("\n")

class LanguageLearner(object):
    """
    Learns words from a dictionary an generates new words based on the letter
    transition probabilities.

    """
    NULL_TO_CHAR = '['
    CHAR_TO_NULL = ']'
    def __init__(self, words):
        """
        Initializes the language learner instance.

        @param words - a list of words to learn

        """
        self._trans_counts = collections.defaultdict(dict)
        self.process_words(words)

    def process_word(self, word):
        """
        Update transition counts for this language.

        @param word - word belonging to this learners language.

        """
        if word is None or len(word) == 0:
            return

        def transition(word, idx):
            if idx == -1:
                return self.NULL_TO_CHAR, word[0]
            elif idx >= (len(word)-1):
                return word[-1], self.CHAR_TO_NULL
            else:
                return word[idx], word[idx+1]

        for i in xrange(-1, len(word)):
            from_, to = transition(word, i)
            trans_from = self._trans_counts[from_]
            trans_from[to] = trans_from.get(to, 0) + 1

    def process_words(self, words):
        """
        Updates transition counts for this language.

        @param words - a list of words belonging to this language.

        """
        _ = [self.process_word(word) for word in words]

    def generate_word(self):
        """
        Generates a new word based on the already processed information.

        @returns generated word string

        """
        def make_intervals(counts):
            start = 0
            total = float(sum(counts.values()))
            intervals = []
            for k, v in counts.items():
                v /= total
                intervals.append(Interval(letter=k, start=start, end=(start+v)))
                start += v
            return intervals

        def letter_from_intervals(intervals):
            value = random.random()
            for inter in intervals:
                if inter.start <= value < inter.end:
                    return inter.letter
            raise RuntimeError("Ooops, this should never happen. Random value = %s" % value)

        new_word = ""
        from_ = self.NULL_TO_CHAR
        while from_ != self.CHAR_TO_NULL:
            counts = self._trans_counts[from_]
            intervals = make_intervals(counts)
            from_ = letter_from_intervals(intervals)
            new_word += "" if from_ == self.CHAR_TO_NULL else from_

        return new_word

def main():
    parser = argparse.ArgumentParser(description="Learns the words from the given "
                                     "dictionary and generates new words.")

    parser.add_argument("-c",
                        "--count",
                        help="Number of new words to generate.",
                        default=10,
                        type=int)
    parser.add_argument("-d",
                        "--dict",
                        help="Path to the dictionary file.",
                        type=str,
                        required=True)
    args = parser.parse_args()

    words = read_dict_file(args.dict)
    start = time.time()
    lang_learner = LanguageLearner(words)
    print "Learning time:", time.time() - start

    start = time.time()
    words = [lang_learner.generate_word() for _ in xrange(args.count)]
    print "Generated new words in %s seconds: %s" % (time.time() - start, words)

if __name__ == "__main__":
    main()
