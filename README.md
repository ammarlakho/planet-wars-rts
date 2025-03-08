# Planet Wars RTS

This repo contains the code and instructions for a series
of Planet Wars Real-Time Strategy (RTS) games.

## The core idea

Planet Wars is a simultaneuous move RTS game where players 
aim to gain control of planets and destroy enemy units.

The software supports a family of games where key
details can be varied to affect the difficulty
of the game.  This includes:

* The number of planets
* The battle rules
* In-transit collisions
* Transit speed
* The time allowed per decision
* The winning conditions:
  - the number of planets controlled
  - the difference in the number of units
  - whether units in transit count towards the total
* The game duration


## Agent API



## Game Runners

There are two ways to run games: synchronous and asynchronous.

### Synchronous

In synchronous mode, the game runner runs in
a single thread.  At each game tick, it calls the getAction method
of each agent, waits for the response, applies the actions of 
each agent to generate the next state using the
forward model, and the repeats until the game is over.

This mode is useful for debugging and testing:
it is often faster than asynchronous mode as it
avoids any overhead due to coroutines and timeouts
(though in theory asynchronous mode could be
faster on a multi-core machine as agents could run
in their own threads - the actual speed depends on
a number of factors).

###

In asynchronous mode, the game runner runs in multiple threads
or coroutines.  The key point is that every game tick it sends
the current state observation to each agent, and then waits for
for a specified number of milliseconds for a response.  
If the agent does not respond in time, the game runner assumes
a doNothing action, and proceeds to step the game forward.

Hence this mode is truly real-time.

## The codebase and philosophy

## The evaluation




The games
Planet wars RTS game for AI agent evaluation
