#!/usr/bin/env python
import time

class Counter(object):
    """
    This class can handle 1000s of calls per second for counting time based
    events. Counts number of increments in the last hour, minute and second.

    """
    HOUR_SECONDS = 3600
    MINUTE_SECONDS = 60
    def __init__(self):
        self._slots = [0] * Counter.HOUR_SECONDS
        self._itime = lambda: int(time.time())
        self._current_second = lambda s: s % Counter.HOUR_SECONDS
        self._last_ts = self._itime()

    def _reset_passed_seconds(self, update_last=False):
        """
        Reset individual second counts.

        @param update_last - flag to update the last write time stamp

        """
        ts = self._itime()
        diff = ts - self._last_ts
        # Last invoked longer than hour ago, reset everything
        if diff > Counter.HOUR_SECONDS:
            self._slots = [0] * Counter.HOUR_SECONDS
        elif diff > 1:
            cur_sec = self._current_second(ts)
            # add 1 to cover for the last second
            last_sec = cur_sec - diff + 1
            def set_to_zero(i): self._slots[i] = 0
            _ = [set_to_zero(i) for i in range(last_sec, cur_sec)]
        if update_last:
            self._last_ts = ts

    def increment(self):
        """
        Register a new event.

        """
        self._reset_passed_seconds(update_last=True)
        slot = self._current_second(self._itime())
        self._slots[slot] += 1

    def get_prev_second(self):
        """
        Return number of events that happened in the last second.

        """
        self._reset_passed_seconds()
        slot = self._current_second(self._itime()) - 1
        return self._slots[slot]

    def get_prev_minute(self):
        """
        Return number of events that happened in the last minute.

        """
        self._reset_passed_seconds()
        slot = self._current_second(self._itime())
        start = slot - Counter.MINUTE_SECONDS
        return sum(self._slots[i] for i in xrange(start, slot))

    def get_prev_hour(self):
        """
        Return number of events that happened in the last hour.

        """
        self._reset_passed_seconds()
        return sum(self._slots)


def main():
    counter = Counter()

    for _ in xrange(10):
        counter.increment()
    time.sleep(2)

    second = counter.get_prev_second()
    minute = counter.get_prev_minute()
    hour = counter.get_prev_hour()
    print "Last second:", second
    print "Last minute:", minute
    print "Last hour:", hour


if __name__ == "__main__":
    main()
