# ttc-matching

allocation using the TTC (top trading cycle) algorithm

## Housing Allocation Problem

The basic TTC algorithm is illustrated by the following housing market situation. There are n students living in the student dormitories. Each student lives in a single house. Each student has a preference relation on the houses, and some students prefer the houses assigned to other students. This may lead to mutually-beneficial exchanges. For example, if student 1 prefers the house allocated to student 2 and vice versa, both of them will benefit by exchanging their houses. The goal is to find a core-stable allocation – a re-allocation of houses to students, such that all mutually-beneficial exchanges have been realized (i.e., no group of students can together improve their situation by exchanging their houses).

## Top Trading Cycle Algorithm

1. Ask each agent to indicate his "top" (most preferred) house.
2. Draw an arrow from each agent i to the agent, denoted Top(i), who holds the top house of i.
3. Note that there must be at least one cycle in the graph (this might be a cycle of length 1, if some agent i currently holds his own top house). Implement the trade indicated by this cycle (i.e., reallocate each house to the agent pointing to it), and remove all the involved agents from the graph.
4. If there are remaining agents, go back to step 1.

The algorithm must terminate, since in each iteration we remove at least one agent.
