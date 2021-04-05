
<p align="center">
<img height="350" src="https://puu.sh/Hd5Wa/e2d402c682.png">
</p>

# QuizzBuzz
<p align="center">
<img src="https://media.giphy.com/media/cLYVKNPHfQFjMM8iPe/giphy.gif">
</p>

## Introduction
**QuizzBuzz** is a flash-card tool that uses a spaced-repetition algorithm to aid the user's memorization.
It is the perfect tool for students, language learners, and anyone wanting to learn anything quickly.

## Features
With QuizzBuzz, you can:
- Create and edit your own flash cards
- Sort cards into decks
- Add tags to individual cards for further categorization

What the tool will do for you:
- Compile flash-cards into review sessions
- Prescribe review times for optimal knowledge retention
- Display review performance

## Why?
Being a student in school for the vast majority of my life, it is an understatement to say that
learning has been a significant aspect of it. Since I realized that learning will not become any
less prevalent in my life, I might as well do it as efficiently as possible, right? That's when I
discovered the two best 
<a href="https://www.apa.org/science/about/psa/2016/06/learning-memory">
proven study methods:
</a>
spaced repetition and active retrieval. I found that the programs that were already out there 
that could automatically space out repetition were quite unwieldy and not simplistic enough 
in their user experience, so I set out to create **QuizzBuzz**, a project that anyone can use
to help memorize anything in the most effective manner.

## User Stories
- As a user, I want to be able to add, remove, and edit flash cards in a deck
- As a user, I want to be able to view all flash cards in a selected deck
- As a user, I want to be able to add, remove, and rename decks
- As a user, I want to have automatically scheduled review dates
- As a user, I want to be able to review my cards in a random order
- As a user, I want to be able to review my cards by tag
- As a user, I want to be able to review all my cards, ignoring the schedule
- As a user, I want to be able to see my performance each session
- As a user, I want to be able to save my decks to file
- As a user, I want my decks to be automatically loaded from file
- As a user, when I quit the application, I want to be able to choose whether I want to save my deck/its changes
or not

## Phase 4: Task 2
I have chosen to implement a type hierarchy in my code. Since I have two different type hierarchies, I will be
examining the hierarchy involving the abstract class "StudySession". There are three sub-classes that extend the
abstract class StudySession: AllStudySession, NormalStudySession, and TagStudySession. The methods that are inherited
but not overridden are:
<ul>
<li>getDeck()</li> 
<li>generateStudyList()</li> 
<li>generateShuffleSequence()</li> 
<li>begin()</li> 
<li>incrementCorrectReviews()</li> 
<li>showPerformanceDialog()</li> 
</ul>

The only overriding method that is in all three sub-classes is:
- generateStudyList()

## Phase 4: Task 3
If I had more time, I would re-factor the following:
#### Merge MainMenu and QuizApp classes
Since MainMenu uses all of QuizApp's two fields (the ArrayList of Deck and JFrame), all the functionality of MainMenu
could be merged into QuizApp, keeping the class diagram more compact and simplistic. I found that there isn't a lot of
functionality inside the current QuizApp class, and so it would be beneficial to merge the two classes into a new
"QuizApp" class. 
#### QuizApp, Card, and Deck implement an interface Save-able
All of these classes have a method that changes them into a JSON-like format. The only issue with doing this
refactoring is that the method in QuizApp has a different return value: a JSONArray as opposed to a JSONObject. So 
there would have to be a lot of adjustment in terms of how the object data in this program is serialized.
#### Split Card class to abide by Single Responsibility Principle and improve cohesion
The card class seems to be able to be split into another class, as we can see two different categories of behaviour:
one category which deals with the information of the card (like the text on the front, back, tags, etc.), and one 
category which deals with the study/review data. Methods like processReview() and calculateEase() would be methods that
are taken away and put into the new class which deals with study/review data.
#### Reduce coupling in all the UI classes
A significant portion of the UI classes involve moving from menu to menu. I believe that changing the content of the
frame is quite repetitive, and there should definitely be a method abstracted out of the code that perhaps takes a 
panel as a parameter and sets the content of the frame, revalidates, and repaints. In addition, I believe it would have
also been a good idea to abstract out the creation of buttons in many of the classes, just so that there can be less 
coupling between all the different classes and their similar JButton implementations in terms of formatting.
