def solve():
    raw_input()
    lst = map(int, raw_input().split(' '))
    l1, l2 = lst[::2], lst[1::2]
    l1.sort()
    l2.sort()
    lst[::2], lst[1::2] = l1, l2
    for i in range(len(lst)-1):
        if lst[i] > lst[i+1]:
            return i
    return 'OK'

t = int(raw_input())
for i in range(1,t+1):
    print 'Case #%d: %s'%(i,solve())
