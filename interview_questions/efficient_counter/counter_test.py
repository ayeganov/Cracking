#!/usr/bin/env python

import mock
import time
import unittest

import efficient_counter

class CounterTest(unittest.TestCase):
    def setUp(self):
        self.cur_time = 0
        self.mock_time = lambda: self.cur_time
        efficient_counter.Counter.HOUR_SECONDS = 36
        efficient_counter.Counter.MINUTE_SECONDS = 6

    def test_seconds(self):
        counter = efficient_counter.Counter()
        with mock.patch('time.time', self.mock_time):
            # register one count at beginning of time = 0
            counter.increment()
            # previous second must be 0
            self.assertEqual(0, counter.get_prev_second())
            self.cur_time = 1
            # one second passed, count must be equal to 1
            self.assertEqual(1, counter.get_prev_second())
            self.cur_time = 2
            # another second - count back to 0
            self.assertEqual(0, counter.get_prev_second())

    def test_minutes(self):
        counter = efficient_counter.Counter()
        def new_second_count():
            counter.increment()
            self.cur_time += 1

        with mock.patch('time.time', self.mock_time):
            _ = [new_second_count() for _ in range(counter.MINUTE_SECONDS)]
            for i in range(counter.MINUTE_SECONDS+1):
                self.cur_time += 1
                self.assertEqual(max(0, counter.MINUTE_SECONDS-i-1), counter.get_prev_minute())

    def test_hour(self):
        with mock.patch('time.time', self.mock_time):
            counter = efficient_counter.Counter()

            def new_second_count():
                counter.increment()
                self.cur_time += 1

            _ = [new_second_count() for _ in range(counter.HOUR_SECONDS)]

            for i in range(counter.HOUR_SECONDS+1):
                prev_time = counter.get_prev_hour()
                self.assertEqual(counter.HOUR_SECONDS - i, prev_time)
                self.cur_time += 1

    def test_non_zero_start(self):
        with mock.patch('time.time', self.mock_time):
            counter = efficient_counter.Counter()

            cur_time = 5

            def new_second_count():
                counter.increment()
                self.cur_time += 1

            _ = [new_second_count() for _ in range(counter.HOUR_SECONDS)]

            for i in range(counter.HOUR_SECONDS+1):
                prev_time = counter.get_prev_hour()
                self.assertEqual(counter.HOUR_SECONDS-i, prev_time)
                self.cur_time += 1

    def test_minute_run_out(self):
        with mock.patch('time.time', self.mock_time):
            counter = efficient_counter.Counter()

            self.cur_time = 5
            def new_second_count():
                counter.increment()
                self.cur_time += 1

            _ = [new_second_count() for _ in range(counter.MINUTE_SECONDS)]

            for i in range(counter.MINUTE_SECONDS+1):
                prev_time = counter.get_prev_minute()
                self.assertEqual(counter.MINUTE_SECONDS-i, prev_time)
                self.cur_time += 1

    def test_increment(self):
        with mock.patch('time.time', self.mock_time):
            counter = efficient_counter.Counter()

            self.assertEqual(0, counter._last_ts)
            self.assertEqual(0, counter._slots[0])

            counter.increment()

            self.assertEqual(0, counter._last_ts)
            self.assertEqual(1, counter._slots[0])

            self.cur_time += 1
            counter.increment()

            self.assertEqual(1, counter._last_ts)
            self.assertEqual(1, counter._slots[0])
            self.assertEqual(1, counter._slots[1])

            counter.increment()

            self.assertEqual(1, counter._last_ts)
            self.assertEqual(1, counter._slots[0])
            self.assertEqual(2, counter._slots[1])

    def test_passed_seconds(self):
        with mock.patch('time.time', self.mock_time):
            counter = efficient_counter.Counter()
            self.assertEqual(0, counter._last_ts)

            self.cur_time = 4
            counter._reset_passed_seconds()
            self.assertEqual(0, counter._last_ts)

            counter._reset_passed_seconds(update_last=True)
            self.assertEqual(4, counter._last_ts)

            _ = [counter.increment() for _ in xrange(10)]
            self.assertEqual(10, counter.get_prev_hour())
            # more than hour worth of seconds has passed
            self.cur_time += counter.HOUR_SECONDS + 1
            self.assertEqual(0, counter.get_prev_hour())



if __name__ == "__main__":
    unittest.main()
