---
layout: post
title:  "프로그래머스 - N으로 표현"
categories: Algorithm
tags: Algorithm dp
comments: true
---

### 문제

아래와 같이 5와 사칙연산만으로 12를 표현할 수 있습니다.

```
12 = 5 + 5 + (5 / 5) + (5 / 5)
12 = 55 / 5 + 5 / 5
12 = (55 + 5) / 5
```

5를 사용한 횟수는 각각 6,5,4 입니다. 그리고 이중 가장 작은 경우는 4입니다.  
이처럼 숫자 N과 number가 주어질 때, N과 사칙연산만 사용해서 표현 할 수 있는 방법 중 N 사용횟수의 최솟값을 return 하도록 solution 함수를 작성하세요.

<div class="br"/>

**제한사항**
- N은 1 이상 9 이하입니다.
- number는 1 이상 32,000 이하입니다.
- 수식에는 괄호와 사칙연산만 가능하며 나누기 연산에서 나머지는 무시합니다.
- 최솟값이 8보다 크면 -1을 return 합니다.

<div class="br"/>

### 풀이

```java
public class Dp1 {
    public int solution(int N, int number) {
        List<Set<Integer>> dp = new ArrayList<>();
        Set<Integer> first = new HashSet<>();
        if (N == number) return 1;
        first.add(N);
        dp.add(0, new HashSet<>());
        dp.add(1, first);
    
        for (int depth = 2; depth <= 8; depth++) {
            Set<Integer> newSet = new HashSet<>();
            for (int lengthOfN = 1; lengthOfN <= depth; lengthOfN++) {
                int value = 0;
                if (lengthOfN == depth) {
                    value = convert(N, depth);
                    if (value == number) return depth;
                    else newSet.add(value);
                }
                else
                    for (Integer preNode : dp.get(lengthOfN))
                        for (Integer postNode : dp.get(depth - lengthOfN))
                            for (int type = 1; type <= 4; type++) {
                                switch (type) {
                                    case 1: value = preNode + postNode; break;
                                    case 2: value = preNode - postNode; break;
                                    case 3: value = preNode * postNode; break;
                                    case 4: value = postNode != 0 ? preNode / postNode : -1; break;
                                }
                                if (type == 4 && postNode == 0) continue;
                                if (value == number) return depth;
                                else newSet.add(value);
                            }
            }
            dp.add(depth, newSet);
        }
        return -1;
    }
    
    private int convert(int N, int repeat) {
        StringBuilder repeatedN = new StringBuilder();
        for (int i = 1; i <= repeat; i++)
            repeatedN.append(N);
        return Integer.parseInt(repeatedN.toString());
    }
}
```
