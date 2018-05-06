import itertools, math
import sys

def val(a):
    maxW = max(map(lambda x: x[0], a))
    out = math.pi * maxW * maxW
    for tmp in a:
        out += 2*tmp[0] * tmp[1] * math.pi
    return out
def solve(t):
    n, k = map(int, raw_input().split())
    pancakes = []
    for i in range(n):
        pancakes.append(map(int, raw_input().split()))
    m = -1
    for perm in itertools.permutations(pancakes, k):
        tmp = val(perm)
        if tmp > m:
            print sorted(perm, key=lambda x: x[0])
            m = tmp
    return m
    
t = int(raw_input())
for i in range(t):
    print 'Case #%d: %s' % (i+1, solve(t))
    sys.stdout.flush()
