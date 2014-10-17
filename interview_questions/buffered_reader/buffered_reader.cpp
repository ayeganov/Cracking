#include <algorithm>
#include <iostream>
#include <iterator>
#include <stdexcept>

#include <fcntl.h>

const int PAGE_SIZE = 4096;

int read4k(int fd, char* buf, size_t offset)
{
    if(!buf)
    {
        throw std::invalid_argument("Buffer can not be null.");
    }
    ssize_t to_read = PAGE_SIZE;
    ssize_t total_read = 0;
    char* pos = buf + offset;
    while(to_read > 0)
    {
        ssize_t bytes_read = TEMP_FAILURE_RETRY(read(fd, pos, PAGE_SIZE));
        if(bytes_read < 0)
        {
            return -1;
        }
        to_read -= bytes_read;
        pos += bytes_read;
        total_read += bytes_read;

        if(bytes_read < PAGE_SIZE)
            break;
    }
    return total_read;
}

class ReadArbitrary
{
private:
    std::string m_filename;
    char* m_buffer;
    int m_fd;
    int m_head;
    int m_tail;

    void copy_into_buffer(char* buffer, int offset, int num_bytes)
    {
        std::copy(&m_buffer[m_head],
                  &m_buffer[m_head + num_bytes],
                  buffer + offset);
        m_head += num_bytes;
    }

public:
    ReadArbitrary(std::string filename) :
    m_filename(filename),
    m_buffer(nullptr),
    m_fd(-1),
    m_head(0),
    m_tail(0)
    {
        m_buffer = new char[PAGE_SIZE];
        m_fd = TEMP_FAILURE_RETRY(open(filename.c_str(), O_RDONLY));
    }

    ~ReadArbitrary()
    {
        if(m_buffer)
        {
            delete [] m_buffer;
        }
        if(m_fd >= 0)
        {
            TEMP_FAILURE_RETRY(close(m_fd));
        }
    }

    /**
     * Returns number of bytes in the local buffer.
     */
    long size() const
    {
        return std::max(0, m_tail - m_head);
    }

    void refill_buffer()
    {
        m_head = 0;
        m_tail = read4k(m_fd, m_buffer, 0);
    }

    int read(char* buffer, long num_bytes)
    {
        if(!buffer)
        {
            throw std::invalid_argument("Buffer can not be null.");
        }

        if(num_bytes <= 0) return 0;
        long bytes_read = 0;
        int offset = 0;
        while(num_bytes)
        {
            // buffer contains enough data - simply copy it over.
            if(size())
            {
                int to_read = std::min(num_bytes, size());
                copy_into_buffer(buffer, offset, to_read);
                bytes_read += to_read;
                num_bytes -= to_read;
                offset += to_read;
                if(num_bytes)
                {
                    continue;
                }
                break;
            }
            // buffer is empty
            else
            {
                // More than 1 page requested - read directly into the provided buffer
                if(num_bytes > PAGE_SIZE)
                {
                    int page_read = read4k(m_fd, buffer, offset);
                    offset += page_read;
                    num_bytes -= page_read;
                    bytes_read += page_read;
                    if(page_read < PAGE_SIZE)
                    {
                        std::cout << "No more data to read.\n";
                        break;
                    }
                }
                // less than one page left - buffer up the data
                else
                {
                    refill_buffer();
                    if(!size())
                    {
                        break;
                    }
                }
            }
        }
        return bytes_read;
    }
};

const char* USAGE = "br <file_name>";
int main(int argc, const char* argv[])
{
    if(argc < 2)
    {
        std::cout << USAGE << std::endl;
        return 0;
    }
    const char* filename = argv[1];
    ReadArbitrary ra(filename);
    char to_print[2] = {'\0'};
    for(int i = 0; i < 10; i++)
    {
        int read_bytes = ra.read(to_print, 1);
        std::cout << "Read " << read_bytes << " bytes: " << to_print << std::endl;
    }

    return 0;
}
