# Problem statement
A client will produce an event to a profanity_check topic, so our system will split the input of the event string into words, create new key based on words and then join with profanity_words table and if matching records are found then will perform further checks and at the end aggregate the whole event string back into a complete tet and then add if it's safe to be processed further or not.

* **TODO-1**: ~~Create proper model for all the topics~~
* **TODO-2**: ~~Split the profanity_check event into multiple words based on space~~
* **TODO-3**: ~~Join with profanity_words~~
* **TODO-4**: ~~Allow further filtering based on the profanity_words metadata~~
* **TODO-5**: ~~After filtering, if profanity words is still considered bad then aggregate all the words back into whole text message (see TODO-2)~~ 
* **TODO-6**: ~~Produce to the profanity_result for to be consumed by the client~~ 

Completed all the TODOs for this `demo` branch.