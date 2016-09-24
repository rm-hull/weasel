# Weasel

Demonstrations of the process that drives evolutionary systems.

## The Blind Watchmaker

In his 1986 book _The Blind Watchmaker_, Richard Dawkins ponders the infinite
monkey problem: The scenario is staged to produce a string of gibberish
letters, assuming that the selection of each letter in a sequence of 28
characters will be random. The number of possible combinations in this random
sequence is 27^28, or about 10^40, so the probability that the monkey will
produce a given sequence is extremely low. Any particular sequence of 28
characters could be selected as a "target" phrase, all equally as improbable as
Dawkins's chosen target, "METHINKS IT IS LIKE A WEASEL". Dawkins then goes on
to show that a process of cumulative selection can take far fewer steps to
reach any given target. It duplicates repeatedly, but with a certain chance of
random error – 'mutation' – in the copying. By repeating the procedure, a
randomly generated sequence of 28 letters and spaces will be gradually changed
each generation, and will converge to the target phrase in a hundred-or-so
generations.

http://programming-enchiladas.destructuring-bind.org/rm-hull/8347c8fd72570d7a8b32

## Travelling Salesman

The TSP ('travelling salesman problem') is a popular demonstration of an
NP-hard problem in Computer Science: Given a list of cities and the distances
between each pair of cities, what is the shortest possible route that visits
each city exactly once and returns to the origin city? This implementation uses
an evolutionary cumulative-selection approach where on each iteration the five
shortest candidate paths are reproduced a number of times and mutated. The
shortest path is then displayed, and the resulting five candidate paths are
used to evolve the next generation.

http://programming-enchiladas.destructuring-bind.org/rm-hull/106becfb921c78dea949

## Mona Lisa

Roger Johansson's [blog](https://rogeralsing.com/2008/12/07/genetic-programming-evolution-of-mona-lisa/)
demonstrates how genetic programming can conjure an image of the Mona Lisa. This
project also aims to acheive the same, but programmed in clojure.

### Attribution

* Sydney Opera House - http://cameralabs.org/4289-nevoobrazimye-pejzazhi-avstralii

## License

### The MIT License (MIT)

Copyright (c) 2016 Richard Hull

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
