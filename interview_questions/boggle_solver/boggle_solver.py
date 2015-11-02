#!/usr/bin/env python
import argparse
import os
import time


class TrieNode(object):
    """
    Very simplistic trie implementation.

    """
    def __init__(self, is_word=False):
        """
        Initialize the trie node.

        @param is_word - does this constitute an end of a word

        """
        self.children = {}
        self._is_word = is_word

    @property
    def is_word(self):
        return self._is_word

    def __repr__(self):
        return "word: %s, children: %s" % (self._is_word, self.children.keys())

    def add(self, word):
        """
        Add a new word into the trie.

        @param word - new word to add to the trie

        """
        if word is None:
            return
        if len(word) < 1:
            return
        first, rest = word[0], word[1:]
        is_word = (rest == "")
        if first in self.children:
            self._is_word |= is_word
            self.children[first].add(rest)
        else:
            node = TrieNode(is_word=is_word)
            self.children[first] = node
            node.add(rest)

    def lookup(self, word):
        """
        Determine if the given word is a part of the trie, and whether it is a
        prefix, and a word

        @param word - word to check
        @return tuple (prefix=boolean, word=boolean)

        """
        if word is None: return False, False
        first, rest = word[0], word[1:]
        if first in self.children:
            if rest == "":
                last_node = self.children[first]
                return True, last_node.is_word
            return self.children[first].lookup(rest)
        else:
            return False, False

    @classmethod
    def createTrie(cls, words):
        """
        Creates an instance of a trie.

        @param cls - TrieNode class
        @param words - words to be contained in this trie

        """
        root = cls()
        _ = [root.add(word) for word in words]
        return root

def find_words(grid, trie):
    """
    Finds all words in the grid not shorter than 3 letters long.

    @param grid - grid containing the letters
    @param trie - trie dictionary for looking up words
    @return a list of all found words

    """
    if grid is None:
        raise ValueError("Grid must be 2d array.")

    if trie is None:
        raise ValueError("Trie can not be None.")

    words = []
    for i in range(len(grid)):
        for j in range(len(grid[0])):
            words.extend(dfs(grid=grid, trie=trie, start=(i, j)))

    return words

def to_word(points, grid):
    """
    Converts the given list of points into a words by mapping each into a
    corresponding letter in the grid.

    @param points - a list of 2d points
    @param grid - a 2d array containing a single character in each cell
    @return a string representing a word

    """
    return "".join(grid[p[0]][p[1]] for p in points)

def validate_word(word, trie):
    """
    Checks whether the given word is a known word, or a prefix.

    @param word - word string
    @param trie - dictionary containig all known words
    @return a tuple of the form (prefix=bool,is_word=bool)

    """
    is_pref, is_word = trie.lookup(word)
    return is_pref, (is_word and len(word) > 1)

def get_neighbors(point, grid):
    """
    Find all neighbords in the grid around the given point.

    @param point - point of interest
    @param grid - a 2d array containing a single character in each cell
    @return consecutive neighbors of point

    """
    height = len(grid)
    width = len(grid[0])
    for i in (-1,0, 1):
        for j in (-1,0,1):
            if i == 0 and j == 0: continue
            n = (point[0]+i,point[1]+j)
            if n[0] >= 0 and n[1] >= 0 and n[0] < height and n[1] < width:
                yield n

def dfs(grid, trie, start):
    """
    Performs a depth first search for words in the given grid from the starting
    position in the grid.

    @param grid - a 2d array containing a single character in each cell
    @param trie - dictionary containig all known words
    @param start - starting position of the search
    @return a list of found words

    """
    words = []
    fringe = [[start]]
    visited = set([start])
    while len(fringe):
        top = fringe.pop()
        last = top[-1]
        for n in get_neighbors(last, grid):
            if n in top:
                continue
            points = list(top)
            points.extend((n,))
            word = to_word(points, grid)
            is_pref, is_word = validate_word(word, trie)
            if is_word:
                words.append(word)
            if is_pref:
                fringe.append(points)
    return words

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

    with open(path, encoding="ISO-8859-1") as f:
        whole_file = f.read()

    return whole_file.split("\n")

def read_grid_file(path):
    """
    Reads the file containing the boggle grid.

    @param path - to the boggle grid file
    @return a 2d array with a single letter in each cell

    """
    if path is None:
        raise ValueError("Must specify a valid path.")
    if not os.path.isfile(path):
        raise ValueError("Path must point to a regular file.")

    grid = []
    with open(path) as f:
        for line in f:
            grid.append([letter.strip() for letter in line if letter != "\n"])
    return grid


def main():
    parser = argparse.ArgumentParser(description="Solves the grid boggle puzzle.")

    parser.add_argument("-g",
                        "--grid",
                        help="File containing the grid to be solved.",
                        type=str,
                        required=True)
    parser.add_argument("-d",
                        "--dict",
                        help="Path to the dictionary file.",
                        type=str)
    args = parser.parse_args()

    words = read_dict_file(args.dict)
    start = time.time()
    trie = TrieNode.createTrie(words=words)
    print("Trie creation time:", time.time() - start)

    grid = read_grid_file(args.grid)
    start = time.time()
    words = find_words(grid, trie)
    print("Found %s words in %s seconds %s" % (len(words), time.time() - start, words))


if __name__ == "__main__":
    main()
