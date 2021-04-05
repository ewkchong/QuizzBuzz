
<p align="center">
<img height="350" src="https://puu.sh/Hd5Wa/e2d402c682.png">
</p>

# QuizzBuzz
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
- getDeck()
- generateStudyList()
- generateShuffleSequence()
- begin()
- incrementCorrectReviews()
- showPerformanceDialog()
The only overriding method that is in all three sub-classes is:
- generateStudyList()
