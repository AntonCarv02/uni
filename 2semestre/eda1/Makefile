
ap5/main: ap5/main.o ap5/list.o
	gcc -o $@ $^

ap5/main.o: ap5/main.c 
	gcc -c -o $@ $^

ap5/list.o: ap5/list.c 
	gcc -c -o $@ $^


clean:
	rm -rf ap5/main 