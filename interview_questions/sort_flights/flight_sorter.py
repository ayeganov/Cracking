#!/usr/bin/env python
import argparse
import os
import sys
import traceback


def read_flight_file(path):
    """
    Reads the file containing the flight schedule.

    @param path - to the flight schedule file
    @return a list of tuples in the form [(start, dest), ...]

    """
    if path is None:
        raise ValueError("Must specify a valid path.")
    if not os.path.isfile(path):
        raise ValueError("Path must point to a regular file.")

    grid = []
    with open(path) as f:
        for line in f:
            if line[0] != ' ' and line[0] != '#' and line[0] != '\n':
                grid.append([v.strip().upper() for v in line.split(',') ])
    return grid

def print_error(message):
    """
    Prints error message to stderr.

    @param message - message to print.

    """
    sys.stderr.write("Error: " + message + '\n')

def sort_flights(flights):
    """
    Takes a list of flight tuples and sorts it in the order of start to finish.

    @param flights - a list of flight tuples (start, end)
    @returns sorted list

    """
    starts = {flight[0]:flight for flight in flights}
    ends = {flight[1]:flight for flight in flights}
    sorted_flights = [flights[0]]
    head, tail = flights[0], flights[0]
    while head is not None or tail is not None:
        if head is not None and head[1] in starts:
            head = starts[head[1]]
            sorted_flights.append(head)
        else:
            head = None
        if tail is not None and tail[0] in ends:
            tail = ends[tail[0]]
            sorted_flights.insert(0, tail)
        else:
            tail = None
    return sorted_flights


def main():
    parser = argparse.ArgumentParser(description="Sorts the list of flights.")

    parser.add_argument("-f",
                        "--flight_file",
                        help="File containing flight schedule, not sorted.",
                        type=str,
                        required=True)
    args = parser.parse_args()

    try:
        flights = read_flight_file(args.flight_file)
        sorted_flights = sort_flights(flights)
        print "Proper order of flights:", sorted_flights

    except Exception as e:
        print_error(e.message)
        traceback.print_exc()
        sys.exit(1)


if __name__ == "__main__":
    main()
