all: nullify

nullify: nullify.o
	g++ -std=c++0x nullify.o -o null

nullify.o: nullify.cpp
	g++ -std=c++0x -c nullify.cpp

clean:
	rm -rf *.o
