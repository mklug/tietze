### Tietze ###


This simple program prompts the user for a presentaion of a group and then allows the 
user to modify this presentation using Tietze moves (adding/removing additional redundant 
relations and adding/removing additional redundant generators).  Tietze observed that 
any two presentations finite for the same group differ from one another by some sequence 
of Tietze moves (although it is undecidable in general if two groups, say given
by presentations, are isomorphic so such sequences of Tietze moves can be arbitrarily complicated).

## setup ##

The program is  written in java and thus requires a java virtual machine to run. The program
can be compiled with the following command run from within the directory containing the java files

```
> javac . -d *.java
``` 


## usage ##

The command
```
> java tietze/Tietze
```
starts an interactive session.  The user is the prompted to input a group by first inputing generators 
(of the form `a,b,c` or `a1,b1,a2,b2` -- so lower case letters possibly followed by positive integers and
separated by commas) and then relations (of the form `abAbcab^{12}A, abc^{3}, abAB` for example -- where
here upper-case letters denote the inverse of the respective lower-case generators).  

When the initial group has been successfully initiated, the user is then prompted to either apply Tietze 
moves of type 1 (adding a redundant relation is type 1 and removing a redundant relation which is 
type 1') or type 2 (adding a redundant generator is type 2 and removing a redundant generator is type 2').

For type 1 moves, the user is prompted to input a word of the form `r1^{bab} R2` where `r1` and `r2` are
the two relations of the group presentation and where `r^{w}` denotes `Wrw` (the usual convention
for exponential notation for conjugation) and `R2` denotes the inverse of `r2`.  Type 1' moves are the inverse 
of this process.

For type 2 moves, the user is propted to enter a new letter, say `a3`, and a word in the generators, 
say `a1B2a1b2` and then the relation `a3a1B2a1b2` is added to the group presentation.  Type 2' moves are 
the inverse of this process.


## copyright ##

This program is released under the terms of the GNU GPL version 3.0. See the attached license file.

