#!/usr/bin/env python

import random
import copy
import math

class FWrapper(object):
    def __init__(self, function, childcount, name):
        self.function = function
        self.childcount = childcount
        self.name = name


class Node(object):
    def __init__(self, fw, children):
        self.function = fw.function
        self.name = fw.name
        self.children = children

    def evaluate(self, in_data):
        results = [n.evaluate(in_data) for n in self.children]
        return self.function(results)

    def display(self, indent=0):
        print (' ' * indent) + self.name
        for c in self.children:
            c.display(indent+1)


class ParamNode(object):
    def __init__(self, idx):
        self.idx = idx

    def evaluate(self, inp):
        return inp[self.idx]

    def display(self,indent=0):
        print '%sp%d' % (' '*indent,self.idx)


class ConstNode(object):
    def __init__(self, v):
        self.v = v

    def evaluate(self, inp):
        return self.v

    def display(self,indent=0):
        print '%s%d' % (' '*indent,self.v)


addw=FWrapper(lambda l:l[0]+l[1],2,'add')
subw=FWrapper(lambda l:l[0]-l[1],2,'subtract')
mulw=FWrapper(lambda l:l[0]*l[1],2,'multiply')

def iffunc(l):
    if l[0]>0: return l[1]
    else: return l[2]

ifw=FWrapper(iffunc,3,'if')

def isgreater(l):
    if l[0]>l[1]: return 1
    else: return 0

gtw=FWrapper(isgreater,2,'isgreater')

flist=[addw,mulw,ifw,gtw,subw]

# if X > 3: True => Y + 5, False => Y - 2
def example_tree():
    return Node(ifw, [ # (if
                       Node(gtw, [ParamNode(0), ConstNode(3)]), # (> ParamNode(0) 3)
                       Node(addw, [ParamNode(1), ConstNode(5)]), # (+ ParamNode(1) 5)
                       Node(subw, [ParamNode(1), ConstNode(2)])  # (- ParamNode(1) 2)
                     ] # )
               )

def example_tree2():
    return Node(ifw, [ # (if
                        Node(gtw, [ParamNode(0), ParamNode(1)]), # (> ParamNode(0) ParamNode(1))
                        Node(mulw, [ParamNode(0), ParamNode(1)]), # (* ParamNode(0) ParamNode(1))
                        Node(addw, [ParamNode(0), ParamNode(1)]) # (+ ParamNode(0) ParamNode(1))
                     ] # )
               )

def make_random_tree(pc, maxdepth=4, fpr=0.5, ppr=0.6):
    if random.random() < fpr and maxdepth > 0:
        f = random.choice(flist)
        children=[make_random_tree(pc, maxdepth-1, fpr, ppr)
                    for i in range(f.childcount)]
        return Node(f, children)
    elif random.random() < ppr:
        return ParamNode(random.randint(0, pc-1))
    else:
        return ConstNode(random.randint(0, 10))

def hidden_function(x, y):
    return x**2 + 2 * y + 3 * x + 5

def buildhiddenset():
    rows = []
    for i in range(200):
        x = random.randint(0, 40)
        y = random.randint(0, 40)
        rows.append([x, y, hidden_function(x, y)])
    return rows

def score_function(tree, s):
    dif = 0
    for data in s:
        v = tree.evaluate([data[0], data[1]])
        dif += abs(v-data[2])
    return dif

def mutate(tree, pc, prob_change=0.1):
    if random.random() < prob_change:
        return make_random_tree(pc)
    else:
        result = copy.deepcopy(tree)
        if isinstance(tree, Node):
            result.children = [mutate(c, pc, prob_change) for c in tree.children]
        return result

def crossover(t1, t2, prob_swap=0.7, top=True):
    if random.random() < prob_swap and not top:
        return copy.deepcopy(t2)
    else:
        result = copy.deepcopy(t1)
        if isinstance(t1, Node) and isinstance(t2, Node):
            result.children = [crossover(c, random.choice(t2.children), prob_swap, False) for c in t1.children]
        return result

def evolve(pc,
           popsize,
           rankfunction,
           maxgen=500,
           mutationrate=0.1,
           breedingrate=0.4,
           pexp=0.7,
           pnew=0.05):
    # Returns a random number, tending towards lower numbers. The lower pexp is
    # the lower numbers you will get
    def selectindex():
        return int(math.log(random.random()) / math.log(pexp))

    # Create a random initial population
    population = [make_random_tree(pc) for _ in xrange(popsize)]
    for i in xrange(maxgen):
        scores = rankfunction(population)
        print scores[0][0]
        if scores[0][0] == 0: break

        # The two best always make it
        newpop = [scores[0][1], scores[1][1]]
        # Build the next generation
        while len(newpop) < popsize:
            if random.random() > pnew:
                newpop.append(mutate(
                                crossover(
                                          scores[selectindex()][1],
                                          scores[selectindex()][1],
                                          prob_swap=breedingrate
                                         ),
                                pc,
                                prob_change=mutationrate
                             ))
            else:
                # Add a random node to mix things up
                newpop.append(make_random_tree(pc))
        population = newpop

    scores[0][1].display()
    return scores[0][1]

def make_rank_function(data_set):
    def rank_function(population):
        scores = [(score_function(t, data_set), t) for t in population]
        scores.sort()
        return scores
    return rank_function

def grid_game(players):
    # Board size
    max = (3, 3)

    # Remember the last move for each player
    lastmove = [-1, -1]

    # Remember the players locations
    location = [[random.randint(0, max[0]), random.randint(0, max[1])]]

    # Put the second player a sufficient distance from the first
    location.append([(location[0][0]+2)%4, (location[0][1]+2)%4])

    # Maximum of 50 moves before a tie
    for o in range(50):
        # for each player
        for i in xrange(2):
            locs = location[i][:] + location[1-i][:]
            locs.append(lastmove[i])
            move = players[i].evaluate(locs) % 4

            # You lose if you move the same direction twince in a row
            if lastmove[i] == move: return 1-i
            lastmove[i] = move
            if move == 0:
                location[i][0] -= 1
                # Board limits
                if location[i][0] < 0: location[i][0] = 0
            if move == 1:
                location[i][0] += 1
                # Board limits
                if location[i][0] > max[0]: location[i][0] = max[0]
            if move == 2:
                location[i][1] -= 1
                # Board limits
                if location[i][1] < 0: location[i][1] = 0
            if move == 3:
                location[i][1] += 1
                # Board limits
                if location[i][1] > max[1]: location[i][1] = max[1]

            if location[i] == location[1-i]: return i
    return -1

def tournament(players):
    # Every player lost 0 times so far...
    losses = [0] * len(players)

    # Every player plays every other player
    for i in xrange(len(players)):
        for j in xrange(len(players)):
            if i == j: continue

            # Who is the winner?
            winner = grid_game([players[i], players[j]])

            # Two points for a loss, one point for a tie
            if winner == 0:
                losses[j] += 2
            elif winner == 1:
                losses[i] += 2
            elif winner == -1:
                losses[i] += 1
                losses[j] += 1
    # Sort and return the results
    z = zip(losses, players)
    z.sort()
    return z

class HumanPlayer(object):
    def evaluate(self, board):
        # Get my location and the location of other players
        me = tuple(board[0:2])
        others = [tuple(board[x:x+2]) for x in range(2, len(board)-1, 2)]

        # Display the board
        for i in range(4):
            for j in range(4):
                if (i, j) == me:
                    print '$',
                elif (i, j) in others:
                    print 'X',
                else:
                    print '.',
            print

        # show moves for reference
        print "Your last move was %d" % board[len(board)-1]
        print " 0"
        print "2 3"
        print " 1"

        move = int(raw_input("Enter move:"))
        return move
