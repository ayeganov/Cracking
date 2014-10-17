#!/usr/bin/env python
import argparse
import os
import sys
import time


RED = "R"
BLACK = "B"
EMPTY = "_"


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

def _is_surrounded(grid, position, piece_type, visited, blob):
    """
    Performs a recursive search through the grid and determines whether the
    piece under the given position is surrounded.

    @param grid - a 2d array containing a either R, B, or _.
    @param position - starting position of the search
    @param piece_type - piece type that is being checked for surroundness
    @param blob - used to collect all adjacent pieces of same type as piece_type
    @return True if surrounded, False otherwise

    """
    piece = grid[position[0]][position[1]]

    if piece == piece_type and blob is not None:
        blob.add(position)

    if piece == EMPTY:
        return False

    if piece != piece_type:
        return True

    if position in visited:
        return True

    visited.add(position)
    return all(_is_surrounded(grid, n, piece_type, visited, blob) for n in get_neighbors(position, grid))

def is_surrounded(grid, position, blob=None):
    """
    Determines whether the piece under the given position is surrounded.

    @param grid - a 2d array containing a either R, B, or _.
    @param position - starting position of the search
    @param blob - all pieces of the same type near the original position
    @return True if surrounded, False otherwise

    """
    visited = set([])
    piece = grid[position[0]][position[1]]
    return _is_surrounded(grid, position, piece, visited, blob)

def clear_surrounded(surrounded, grid):
    """
    Removes all surrounded pieces from the grid.

    @param surrounded - surrounded pieces
    @param grid - the GO board

    """
    def clear(piece):
        grid[piece[0]][piece[1]] = EMPTY

    _ = [clear(p) for p in surrounded]

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
#            grid.append(line.strip())
    return grid

def convert_position(position):
    """
    Converts the given position from command line format to integer format.

    @param position - a string containing two numbers separated by a comma.
                      ie: (12,23)
    @return a list of two numbers
    """
    try:
        position = tuple([int(v.strip()) for v in position.split(',')])
        if len(position) != 2:
            raise ValueError("Position must be 2d")
        return position
    except ValueError as ve:
        raise argparse.ArgumentTypeError("Invalid position argument: %s" % position)

def print_error(message):
    """
    Prints error message to stderr.

    @param message - message to print.

    """
    sys.stderr.write("Error: " + message + '\n')

def print_board(grid):
    for i, row in enumerate(grid):
        print "%2d:" % i, row


def main():
    parser = argparse.ArgumentParser(description="Solves the grid boggle puzzle.")

    parser.add_argument("-g",
                        "--grid",
                        help="File representing the GO board. R-red, B-black, "
                        "_-empty.",
                        type=str,
                        required=True)
    parser.add_argument("-p",
                        "--position",
                        help="Search position",
                        type=convert_position,
                        required=True)
    args = parser.parse_args()

    try:
        print "Starting position is", args.position
        grid = read_grid_file(args.grid)
        print_board(grid)
        piece = grid[args.position[0]][args.position[1]]
        blob = set()
        surrounded = is_surrounded(grid, args.position, blob)
        print "Piece", piece, "at given position", args.position, "is surrounded", surrounded
        if surrounded:
            clear_surrounded(blob, grid)
            print_board(grid)

    except Exception as e:
        print_error(e.message)
        sys.exit(1)


if __name__ == "__main__":
    main()
