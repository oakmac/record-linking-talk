# Talk Notes

1. What is Record Linkage? ie: how to recognize the problem

2. What is the space of possible solutions? Different kinds of data require
   different solution approaches. How to recognize which methods will work best
   with your dataset.

3. Creating a probabilistic algorithm using the Felligi-Sunter method. I will
   walk the audience through the creation of a real-world probabilistic algorithm
   using Clojure.

## Outline

- hook: "the nurses are unhappy"
  - mashup emoji of unhappy face + nurse
- Introduction
  - "you may know me from such projects as..." (good idea?)
  - name, background, why I worked on this problem
- Explain the problem in real terms:
  - "the nurses say the census is missing patients..."
  - database A, database B, same data, different schema + systems
  - "you got yourself a Record Linking problem"
- Review other names for record linking
- Step #1: normalize the data
  - "data cleaning"
  - this is literally the most important step
  - it takes the most time
  - Clojure + spec would make a great combination
  - test, test, test
  - do not skimp on this step; it can make a world of difference for your outcome
- attempt a deterministic solution
  - try a few things, have it fail every time
  - explain that this is a viable approach
  - success will depend on your data
  - think "secret foreign key"
- let's try something else: probabilistic approach
  - explain the Felligi-Sunter method
  - show the code
  - how to determine the weights? guess!
  - run it --> success!
- explain the context for this problem within Luminare
  - quick explanation of sepsis and what our product does
  - the problem we experienced (wrong census)
  - show sepsis mortality graph when our product is being applied
  
