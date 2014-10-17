#!/usr/bin/env python

import argparse

def is_rotated(s1, s2):
    if len(s1) != len(s2):
        return False

    return "".join(sorted(s1)).find("".join(sorted(s2))) == 0

def is_rotated_fast(s1, s2):
    if len(s1) != len(s2):
        return False
    s3 = s1 + s1
    return s3.find(s2) >= 0

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Determines whether two strings are rotations.")

    parser.add_argument('-s1', type=str, required=True)
    parser.add_argument('-s2', type=str, required=True)

    args = parser.parse_args()

    print "Strings %s and %s are rotations of each other: %s" % (args.s1, args.s2, is_rotated_fast(args.s1, args.s2))
