
# The code has two ways of finding the determinant:

### The CalculateDet presents Big O notation of $O(N!)$ in time complexity and it is based on the Laplace Expansion to find determinants, in this case with recursion; yeah, I know, not the best.

### The CalculateDet2 presents Big O notation of $O(N^3)$ in time complexity, which is better, and it is based on the Gauss Elimination method to find determinants.

## Reducing any matrix

### You can row reduce any $m$ x $n$ matrix, just choose the option at the begining to reduce or find the determinant (using CalculateDet2).

## Finding the inverse of a matrix

### You can also find the inverse of any square matrix using the Gauss-Jordan elimination method (using CalculateDet2).

## Run

If you to run it without downloading anything, run online with [Online GDB](https://onlinegdb.com/J68Y8BGEhO).
To run on your machine, go to the directory where you want to clone the repository and run the following commands:

```javac -d bin src/*.java```

```java -cp bin/. Linearity```

