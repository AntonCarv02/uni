main: main.o doubleList.o BigInt.o
    gcc -o $@ $^

doubleList.o: doubleList.c
    gcc -c -o $@ $^

BigInt.o: BigInt.c
    gcc -c -o $@ $^

main.o: main.c
    gcc -c -o $@ $^

bin: 
    mkdir $@

clean:
    rm -rf main bin