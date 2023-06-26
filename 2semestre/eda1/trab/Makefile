boogle: boogle.o hashtable.o lists.o
	gcc -o $@ $^

boogle.o: boogle_main.c 
	gcc -c -o $@ $^

hashtable.o: hashtable.c 
	gcc -c -o $@ $^

lists.o: lists.c 
	gcc -c -o $@ $^


clean:
	rm -rf boogle boogle.o