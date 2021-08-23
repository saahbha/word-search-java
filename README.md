This program solves word search puzzles with the Boyer-Moore String matching algorithm.

Demo this program by running main() from WordSearch.java in your IDE. To change the problem, change the `'problemName'` variable at the beginning of main(). The program reads 3 input files which should be placed in `\testFiles`:

- `'problemName'\_puzzle.txt`
- `'problemName'\_words.txt`
- `'problemName'\_solution.txt`

In `\testFiles` there are two sample problems found from [http://www.whenwewordsearch.com/](http://www.whenwewordsearch.com/): Find 30 Famous Artists in a 24x24 grid, and Find 50 Famous Books in a 32x32 grid. Each of these files start with a number of lines for the program to read in. Words in the `'problemName'\_words.txt` file are seperated by a new line. To add more problems, follow the format of these files and put them in this folder. I haven't tested this program on grids that aren't square.

It outputs to the console the list of words it searched for along with where it found each word in `'word' 'row':'column'` format. It also outputs whether or not it matches the solution file and how long it took to run in miliseconds.
