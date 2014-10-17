#!/usr/bin/env python
import argparse
import copy
import operator
import random
import time

import cv2
import numpy

MAX_SIZE = {numpy.uint8:255}

def show_image(title, image):
    cv2.imshow(title, image)
    cv2.waitKey(0)

def mutate_row(row, i, dtype):
    var = random.randint(0, 50)
    op = random.choice((operator.add, operator.sub))
    val = int(row[i])
    row[i] = dtype.type(max(min(op(val, var), MAX_SIZE[dtype.type]), 0))

def mutate(img, mut_prob=0.2):
    new = img.copy()
    for row in new:
        _ = [mutate_row(row, i, img.dtype) for i in xrange(len(row)) if random.random() < mut_prob]
    return new

def genetic_draw(image, popsize=50, mut_prob=0.2, elite=0.2, luck=0.3, maxiter=1000):
    def cost_function(candidate):
        return numpy.abs(image-candidate).sum()

    def mutate_row(row, i, dtype):
        var = random.randint(0, 50)
        op = random.choice((operator.add, operator.sub))
        val = int(row[i])
        row[i] = dtype.type(max(min(op(val, var), MAX_SIZE[dtype.type]), 0))

    def mutate(img):
        new = img.copy()
        for row in new:
            _ = [mutate_row(row, i, img.dtype) for i in xrange(len(row)) if random.random() < mut_prob]
        return new

    def crossover(img1, img2):
        height, width = img1.shape
        i = random.randint(0, height-1)
        j = random.randint(0, height-1)
        i, j = min(i,j), max(i,j)
        stack = numpy.vstack((img2[0:i], img1[i:j], img2[j:]))
        return stack

    population = [numpy.random.random(image.shape).astype(image.dtype) for _ in xrange(popsize)]

    top_elite = int(elite * popsize)

#    show_image("Original", image)
    for i in xrange(maxiter):
        scores = [(cost_function(img), img) for img in population]
        scores.sort(key=lambda t: t[0])
        ranked = [v for s, v in scores]

        # Start with pure the winners
        population = ranked[0:top_elite]
        if random.random() <= luck:
            population.pop()
            population.append(random.choice(ranked[top_elite:]))

        # Add mutated and bred forms of the winners
        while len(population) < popsize:
            if random.random() < mut_prob:
#                print "Mutating"
                c = random.randint(0, top_elite-1)
                population.append(mutate(population[c]))
            else:
#                print "crossing over"
                c1 = random.randint(0, top_elite-1)
                c2 = random.randint(0, top_elite-1)
                population.append(crossover(population[c1], population[c2]))


        print scores[0][0], '======', id(scores[0][1]), '======'
        diffs = []
        for i in xrange(1, popsize):
            diffs.append(diff(population[i-1],population[i]))
        if numpy.sum(population[:top_elite-2]) == 0:
            population[1:top_elite] = population[top_elite:top_elite + 5]

        print diffs
#        show_image("Image", scores[0][1])
    return scores[0][1]

def diff(m1, m2):
    return (m1-m2).sum()

def main():
    parser = argparse.ArgumentParser(description="Attempts to genetically draw provided image.")
    parser.add_argument("-i",
                        "--image",
                        help="Image to draw.",
                        required=True)

    args = parser.parse_args()
    img = cv2.imread(args.image, cv2.cv.CV_LOAD_IMAGE_GRAYSCALE)
    result = genetic_draw(img, popsize=40, elite=0.1, mut_prob=0.35, luck=0.25, maxiter=20000)

    cv2.imwrite("result.jpg", result)

if __name__ == "__main__":
    main()
