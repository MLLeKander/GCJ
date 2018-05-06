def solve():
    n = int(input())
    ws = map(int, input().split())
    dp = [0]
    for w in ws:
        prevend = dp[-1]
        for o in range(len(dp)-1,0,-1):
            if w*6 >= dp[o-1]:
                dp[o] = min(dp[o], w+dp[o-1])
        if w*6 >= prevend:
            dp.append(w + prevend)
    return len(dp)-1


t = int(input())
for case in range(1,t+1):
    print('Case #%d: %s'%(case,solve()))
