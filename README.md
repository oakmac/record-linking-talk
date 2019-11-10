# Probabilistic Record Linkage of Hospital Patients

Slides and code for talk on [Record Linking] given at [Clojure/conj 2019].

[Record Linking]:https://en.wikipedia.org/wiki/Record_linkage
[Clojure/conj 2019]:http://2019.clojure-conj.org/speaker-chris-oakman/

## Abstract

How can you tell if a patient is the same person across all the different
electronic systems used in a hospital?

Can you be confident with messy data when lives are on the line?

Medical startup Luminare faced this challenge in a hospital setting and used
Clojure to save the day and make the nurses happy again.

This talk will explore the challenge of record linking: dealing with dirty data
sets, the pros and cons of different solution approaches, and using the
Felligi-Sunter method to create a probabilistic algorithm to match records.

## Speaker Bio

Chris Oakman is a software developer, designer, and educator from Houston, TX.

He works at Luminare - a medical startup based out of the Texas Medical Center -
and teaches software development at DigitalCrafts - a coding bootcamp school.

He is the author of several open source projects, including the cljs.info
cheatsheet, the CLJS logo, and several Parinfer ports and editor plugins.


## References

- [Record Linking on Wikipedia](https://en.wikipedia.org/wiki/Record_linkage)
- [Video overview of the problem space and includes a walkthough of using the Felligi-Sunter method](https://www.youtube.com/watch?v=5AlCf1w0T4w)
- [Slide deck demonstrating the Felligi-Sunter method](http://www.bristol.ac.uk/media-library/sites/cmm/migrated/documents/problinkage.pdf)
