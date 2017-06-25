**Project Overview**

This project provides the Domain driven design and implementation for the _Branch Logic Survey_  where the next questions are chosen 
based on the answer for the current answer.

**How to run**

This is a standard Scala project with sbt. The main class is located in `com.mysurvey.Main`.
Please use the `sbt run` command, to start the application

**Deviations from Spec** 

It makes it easier and to go with natural surveying flow to prompt answer for all the questions in the survey.
So I had implemented the application to take user input for all the questions.

**Assumptions**

1) We need to collect the surveyed items with the user supplied answer so that it can be stored in a DB or file.
 So the application collects the answers with corresponding question and return the output. For a console survey 
 the output is printed as below, with Question Id, Question, Answer format and Answer 
 (question 3 is skipped based on previous answer):
 ``` 
 Results:
 (1) What's your name? <Free Text> Answer: 'username'
 (2) How old are you? <Free Text> Answer: '23'
 (4) Do you have a driving license? <Boolean> Answer: 'false'
 ```

**Improvements**

There is a scope for the below improvements:

1) the `com.mysurvey.rules` package holds all the rules. It currently has one class which return `Boolean` as result, 
 which is sufficient for the current assignment containing only two _results_ (`yesBlock` and `noBlock`). 
 However for complicated branching there will be more than 2 _result_ flows. The rules need to be more generalised.
2) Currently the Answers (`BooleanAnswer`, `FreeTextAnswer`) are not strictly typed. BooleanAnswer should only
 contain `Boolean` value, FreeTextAnswer should only contain `String` value, not an `Int` value represented as `String` 
 e.g: Age.
 There should be `NumberAnswer` which take the age of the user (again this will be a slight deviation from the spec 
 as it is expecting 'Free Text')
 
**Things to consider**
1) When implementing the `Scanner` and `Printer`  traits, care should be take to open, flush and close the Input/Output
 streams.

**Test cases**

Since part of the code is console interacting, it is hard to test using test cases. However this is tested using 
manual testing.

It has a combined test coverage of 87% calculated from unit test cases + manual console test.  

The test class `com.mysurvey.flow.InMemoryFlowManagerTest` uses `InMemoryScanner` and `InMemoryPrinter` for testing purposes