README.txt
Justin Fraumeni
jfraumen@u.rochester.edu


The enclosed java files constitute a fun implementation of an artificial intelligence to solve the classic MasterMind guessing game using a genetic algorithm. The algorithm implemented is based on the one described in "Efficient solutions for Mastermind using genetic algorithms" by Lotte Berghman, Dries Goossens and Roel Leus of K.U.Leuven. The algorithm proposed in that paper has a slightly greater number of guesses than Knuth's famous solution for the classic 4 position 6 color game, but the run time scales up slowly compared to his algorithm, enabling it to be applied to more game situations.

The algorithm never has to generate a exponential number of combinations, and hence sacrifices absolute logic and guess efficiency for efficient run time, and providing a non brute force option to cracking MasterMind.


"Efficient solutions for Mastermind using genetic algorithms":
https://lirias.kuleuven.be/bitstream/123456789/184247/2/Mastermind  
