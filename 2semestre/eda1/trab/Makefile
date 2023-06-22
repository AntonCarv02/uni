exe/boogle: bin/boogle.o bin/hashtable.o
	gcc -o $@ $^

bin/boogle.o: boogle_main.c 
	gcc -c -o $@ $^

bin/hashtable.o: hashtable.c 
	gcc -c -o $@ $^


clean:
	rm -rf boogle 