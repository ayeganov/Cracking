#ifndef GARBAGE_COLLECT_H
#define GARBAGE_COLLECT_H

template <class T>
class Reference
{
public:
    Reference(T* const pointer)
    {
        m_pointer = pointer;
        m_count = new int;
        *m_count = 1;
    }

    Reference(const Reference& other)
    {
        m_pointer = other.m_pointer;
        m_count = other.m_count;
        (*m_count)++;
    }

    Reference operator=(const Reference& other)
    {
        m_pointer = other.m_pointer;
        m_count = other.m_count;
        (*m_count)++;
        return *this;
    }

    ~Reference()
    {
        if(*m_count > 0)
        {
            (*m_count)--;
        }
        else
        {
            delete m_pointer;
        }
    }

private:
    int* m_count;
    T* m_pointer;
};

#endif
