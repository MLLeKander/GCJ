#include <stdio.h>
#define MAX 100000

void solve() {
  int n;
  int in[MAX];
  scanf("%d", &n);
  
  for (int i = 0; i < n; i++) {
    scanf("%d", in+i);
  }
  int flag, tmp;
  do {
    flag = 0;
    for (int i = 0; i < n-2; i++) {
      if (in[i] > in[i+2]) {
        tmp = in[i];
        in[i] = in[i+2];
        in[i+2] = tmp;
        flag = 1;
      }
    }
  } while (flag);
  for (int i = 0; i < n-1; i++) {
    if (in[i] > in[i+1]) {
      printf("%d\n", i);
      return;
    }
  }
  puts("OK");
}

int main() {
  int t;
  scanf("%d", &t);
  for (int i = 1; i <= t; i++) {
    printf("Case #%d: ", i);
    solve();
  }
}
