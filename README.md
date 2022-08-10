# Testing with Reflection: Part 3 - Custom ClassLoaders

This is a simple program that uses a custom `ClassLoader` to load the test classes 
from an external "tests" folder.

You load the compiled test classes at runtime. 
Then, the test runner can just instantiate them by name and run them that way. 
Because you will be loading already-compiled test classes, 
this also means the unit tests won't have to live in the same directory as the framework code.


## Compiling and Running

    javac -cp . *.java tests/*.java
    java -ea -cp . TestRunner tests/ tests.CalculatorTest

*Note:* In addition to the `-ea` JVM option, 
you might notice the dubious `*` file expansions given to the `javac` command. 
If you ever catch yourself writing a command like this in the real world, 
you should seriously consider switching to a modern tool such as [Maven](https://maven.apache.org/).
For the purposes of this exercise, 
this command is the simplest way to compile all `.java` files in the current folder and all subfolders.

## The program output should be the same as before:

    Passed tests: [CalculatorTest#testAddition]
    FAILED tests: [CalculatorTest#testSubtraction]

