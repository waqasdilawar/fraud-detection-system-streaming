# Problem statement
A client will produce an event to a profanity_check topic, so our system will split the input of the event string into words, create new key based on words and then join with profanity_words table and if matching records are found then will perform further checks and at the end aggregate the whole event string back into a complete tet and then add if it's safe to be processed further or not.

