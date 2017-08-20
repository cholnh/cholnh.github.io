---
layout: post
title:  "Eclipse 에 github 연동하기"
categories: Git
tags: Github Git Eclipse IDE Java
comments: true
---

### How to

우선 Github에 진행할 프로젝트가 저장 될 Repository(저장소)를 하나 만든다.  
(만드는 방법 : [Git Hub 시작]({{ site.url }}/Dev/Git-start.html))


진행할 자바 프로젝트를 하나 생성한다.  
![ 1 ]({{ site.url }}/assets/newRepos/1.png)
![ 2 ]({{ site.url }}/assets/newRepos/2.png)


**프로젝트 우클릭 - Team - Share Project...** 선택  
![ 3 ]({{ site.url }}/assets/newRepos/3.png)


첫번째 옵션을 선택한다.  
![ 4 ]({{ site.url }}/assets/newRepos/4.png)


가운데 자신의 프로젝트를 누르고 **Create Repository**를 선택 후 Finish.  
![ 5 ]({{ site.url }}/assets/newRepos/5.png)


새로 생긴 **Git Repositories** 탭에 **자신의 프로젝트 우클릭 - Pull...** 선택  
(Git Repositories탭이 없다면 Window - Perspective - Open Perspective - Other... 선택 후 Git을 찾아 선택)
![ 6 ]({{ site.url }}/assets/newRepos/6.png)
![ persp ]({{ site.url }}/assets/newRepos/persp.png)


URI에 나의 github 저장소 주소를 적고, User에 깃허브 아이디와 패스워드를 적는다.  
**ex) https://github.com/깃아이디/프로젝트이름**
![ 7 ]({{ site.url }}/assets/newRepos/7.png)


New Remote... 선택.
![ 8 ]({{ site.url }}/assets/newRepos/8.png)


위와 똑같이 URI를 적고 깃허브 아이디와 패스워드를 적고 Finish.  
![ 9 ]({{ site.url }}/assets/newRepos/9.png)


Reference 칸에 `m`을 적으면 `master branch`가 나올것이다.
![ 10 ]({{ site.url }}/assets/newRepos/10.png)


선택 후 Finish를 누르면 다음과 같이 연결 된 모습을 볼 수 있다.
![ 11 ]({{ site.url }}/assets/newRepos/11.png)


깃허브에도 해당 프로젝트를 등록 시켜보자.  
**프로젝트 우클릭 - Team - Commit 선택**
![ 12 ]({{ site.url }}/assets/newRepos/12.png)


Git Staging 탭이 만들어 지고, 수정사항 들이 stage에 올라와 있을 것이다.  
노란 네모 박스안에 있는 Unstaged Changes 내용들을 모두 클릭하여 Staged Changes 칸으로 드래그 시킨다.  
![ 13 ]({{ site.url }}/assets/newRepos/13.png)


Commit에 대한 설명을 적는다.  
설명은 명료하고 이해하기 쉽게 남겨야 하고 git에서 권장하는 형식이 있다. 
``` 
1번째 줄 : 커밋 내의 변경 내용을 요약
2번째 줄 : 빈 칸
3번째 줄 : 변경한 이유
```

다음으로 **Commit and Push**를 누른다.  
Commit 단계에서 **이전 커밋부터 현재 상태까지의 변경 이력이 기록**되고  
Push 단계에서 원격 저장소로의 전송이 이루어 진다.  
![ 14 ]({{ site.url }}/assets/newRepos/14.png)


Branch에 `master`를 적고 Next -  Finish - Ok
![ 15 ]({{ site.url }}/assets/newRepos/15.png)
![ 16 ]({{ site.url }}/assets/newRepos/16.png)
![ 17 ]({{ site.url }}/assets/newRepos/17.png)


github와 ide간의 연동도 하였고, github에 새 프로젝트도 올려놓았다.  
이제 프로젝트에 소스를 추가시켜 보자.  


아래와 같이 패키지와 클래스를 만든다.
![ 18 ]({{ site.url }}/assets/newRepos/18.png)
![ 19 ]({{ site.url }}/assets/newRepos/19.png)


위와 같이 Unstaged Changes 내용을 Staged Changes 칸으로 드래그 시킨다.
![ 20 ]({{ site.url }}/assets/newRepos/20.png)


Commit 설명을 적고 **Commit and Push 선택** (위와 같다..)
![ 21 ]({{ site.url }}/assets/newRepos/21.png)


### Result

Push가 완료되었으면 **자신의 github 페이지**로 가서 정상적으로 소스가 등록되었는지 확인한다.  
![ 22 ]({{ site.url }}/assets/newRepos/22.png)
![ 23 ]({{ site.url }}/assets/newRepos/23.png)
 

