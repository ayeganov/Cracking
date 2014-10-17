#!/usr/bin/env python

import argparse


def is_anagrams(s, t):
    """
    Determines whether the two given strings are anagrams.

    @param s - first string
    @param t - second string

    @returns True if anagrams, False otherwise
    """
    equal = len(s) == len(t)
    return equal and (sorted(s) == sorted(t))


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Anagram verifier")
    parser.add_argument("-s", type=str, required=True)
    parser.add_argument("-t", type=str, required=True)

    args = parser.parse_args()

    print "String", args.s, "is anagram" if is_anagrams(args.s, args.t) else "is not anagram", "of", args.t
