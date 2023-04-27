# A Gratitude Journal
_____________________________________________________________________________________
## Short description of this project:

Many scientific studies have shown that the practice of gratitude journaling
help to boost one's happiness as well as overall physical and mental health.
This app is designed to allow users to reflect on the things in their life
that they express gratitude for. Additionally, this app asks users to input
on a scale of 1-10 how their day went. If the user inputs a score lower than 5,
the app will display a group of previous journal entries in which the user has
had a great day. This will hopefully allow the user to focus more on all the
amazing things in their life and feel at least some gratitude and joy even on a
bad day. The app will also have search features where one can search through
their journal based on a variety of criteria, acting as a diary where one can
store their memories.

This project is of special interest to me because I found that by simply
diverting my attention to the many little things I am grateful for, I feel
much happier, fulfilled and less anxious. I hope that this app can
bring those that use it a similar sort of calm and joy.

_____________________________________________________________________________________
## User stories:
- As a user, I want to be able to add a journal entry to my journal.
- As a user, I want to be able to view a list of my past journal entries.
- As a user, I want to be able to give a score to every journal entry (of how well the day went).
- As a user, I want to be able to remove a journal entry.
- As a user, I want to be able to see the number of journal entries I have.
- As a user, I want to be able to search for a journal entry on a given date.
- As a user, I want to be able to save my journal to a file (if I so choose)
- As a user, I want to be able to be able to load my journal from a file (if I so choose)

_____________________________________________________________________________________
*“Gratitude is the fairest blossom which springs from the soul.” – Henry Ward Beecher*

-------------------------------------------------------------------------------------
# Instructions for Grader

- You can generate the first required action related to adding journal entries to a journal by
  clicking on the add button in the home page.
- You can generate the second required action related to searching for journal entries in a journal
  by clicking on the search button in the home page.
- You can locate my visual component by clicking on the home button and going to the home page.
- You can save the state of my application by clicking on the save button on the home page.
- You can reload the state of my application by clicking on the load button on the home page.

-------------------------------------------------------------------------------------
# Phase 4 Task 2

Wed Apr 12 10:37:13 PDT 2023
Journal entry added.
Wed Apr 12 10:37:21 PDT 2023
Found journal entry that was written on 04/12/2023.

# Phase 4: Task 3

One way I would refactor this program would be to separate the JournalApp class into two classes.
One class would be responsible for displaying the different JPanels and another would handle 
interacting with the Journal class to add/delete/search journal entries. This increases
cohesion as each class now has only one specific purpose instead of handling multiple functions.

Another potential way to refactor this program is to change ui package so that instead of using a
singleton (AppState) to keep track of the state of the app, the methods in different classes simply
call each other. The advantage of doing so is that it eliminates the need to call a singleton 
class that serves as the "middleman" and results in cleaner code that is less prone to error. 
However, the disadvantage would be that by making the methods in the class call other methods in
other classes we increase the coupling between classes. This makes it much more challenging
to change the aspects of one class without affecting another class.