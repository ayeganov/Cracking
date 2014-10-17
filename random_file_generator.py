#!/usr/bin/env python
import argparse
import concurrent.futures
import os
import psutil
import random
import tempfile


CHARSET = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'

def generate_random_string(max_length, min_length=1):
    """
    Generates a random string within provided length range.

    @param max_length - maximum length of string.

    """
    string_length = random.randint(min_length, max_length)
    return "".join(random.choice(CHARSET) for _ in xrange(string_length))

def generate_random_strings_file(path, size, max_string_size=20):
    """
    Generates a file with random strings in it.

    @param path - path to the file
    @param size - size of the file
    @param max_string_size - maximum length of a string in the generated file

    """
    if path is None:
        raise ValueError("Must specify proper file path.")

    if size is None or size <= 0:
        raise ValueError("Must specify a positive file size.")

    with open(path, 'w') as f:
        while size > 0:
            string_size = min(max_string_size, size)
            s = generate_random_string(string_size) + '\n'
            length = len(s)
            f.write(s)
            size -= length

def main():
    parser = argparse.ArgumentParser(description="Creates files of the "
                   "specified size populated by randomly generated strings.")

    parser.add_argument("-s",
                        "--size",
                        help="File size to be generated.",
                        type=int,
                        default=1000)
    parser.add_argument("-p",
                        "--path",
                        help="Path to the file to be generated.",
                        type=str,
                        required=True)
    args = parser.parse_args()

    print "Populating file {f}...".format(f=args.path)

    tmpfiles = [tempfile.mktemp(dir="/var/tmp") for _ in range(psutil.NUM_CPUS)]
    sizes = [args.size / psutil.NUM_CPUS] * psutil.NUM_CPUS
    sizes[0] += args.size % psutil.NUM_CPUS

    try:
        with concurrent.futures.ProcessPoolExecutor(max_workers=psutil.NUM_CPUS) as executor:
            futures = [executor.submit(generate_random_strings_file, tmp_f, size)
                        for tmp_f, size in zip(tmpfiles, sizes)]
            concurrent.futures.wait(futures)

        with open(args.path, 'w') as outfile:
            for tmpfile in tmpfiles:
                with open(tmpfile, 'r') as infile:
                    for line in infile:
                        outfile.write(line)
    except Exception as e:
        print e.message
    finally:
        _ = [os.remove(tmpfile) for tmpfile in tmpfiles]

if __name__ == "__main__":
    main()
