---
layout: post
title:  "Git Hub 시작"
categories: Git
tags: Git Github vcs
comments: true
---

### Intro

**[깃(Git)이란](https://git-scm.com/downloads)** 형상 관리(버전 관리)를 해주는 시스템이다.  
진행하는 프로젝트의 규모가 커져 여러 사람들이 협력하여 진행한다고 가정 해보자.   
많은 수의 개발 인원이 진행함에 따라 **수많은 변경을 제어할 수 있는 시스템**이 없다면 프로젝트는 꼬이고 꼬일것이다.  
형상 관리는 이러한 상황을 제어할 수 있도록 도와주는 도구로서, 프로젝트의 소스 변경 이력을 파악하여 보여주거나 과거 시점으로 되돌리기 등 프로젝트 개발을 쉽게 진행하도록 돕는다.  

<br/>
형상 관리 시스템은 여러 종류가 있다.  
- CVS(Concurrent Versions System)
- SVN(Sub Version)
- RCS(Revision Control System)
- Git

<br/>
깃을 설치하면 다음과 같은 터미널(bash)창에서 작업이 이루어 진다.  
![ gitbash ]({{ site.url }}/assets/gitstart/gitbash.png)

<br/>
**[Github](https://github.com/)**는 깃 작업 저장소를 호스팅해주는 페이지이다.  
진행하는 프로젝트를 public(공개범위)으로 올릴 경우, 오픈소스처럼 모두에게 공개되며 무료로 깃허브를 이용할 수 있다.  
하지만 private 프로젝트의 경우 돈을 지불해야 한다.  

<br/>
이제 깃허브를 시작하여 친구들 또는 나아가 전세계 사람들과 프로젝트를 제작하는 법을 알아보자.  

<br/>
<br/>
### How to

**[Github 홈페이지](https://github.com/)**에 접속하여 회원가입을 진행한다.  
![ 1 ]({{ site.url }}/assets/gitstart/1.png)


Your repositories메뉴에 있는 **New repository** 클릭  
(repository는 프로젝트가 저장되는 저장소의 단위이다)  
![ 2 ]({{ site.url }}/assets/gitstart/2.png)


프로젝트 이름과 간단한 설명을 적고 위에서 말한 공개범위(public/private)를 설정한다.  
(README는 이 저장소의 설명, 사용법 등를 적는 곳이다)  
![ 3 ]({{ site.url }}/assets/gitstart/3.png)


하나의 프로젝트 저장소가 만들어졌다.  
![ 4 ]({{ site.url }}/assets/gitstart/4.png)


**Create new file**을 눌러 텍스트파일을 하나 추가시켜 보자.  
![ 5 ]({{ site.url }}/assets/gitstart/5.png)


파일 이름을 적고, 아래 텍스트 창에 내용을 적는다.  
![ 6 ]({{ site.url }}/assets/gitstart/6.png)


위 텍스트 파일에 대한 설명(내가 무엇을 진행하였는지를 적음)을 적고 Commit을 하여, 텍스트 파일을 저장소에 등록시킨다.  
(Commit이란 완성된 작업물에 대한 작업이력 기록이다)
![ 7 ]({{ site.url }}/assets/gitstart/7.png)


### Result

저장소에 텍스트파일이 추가되었다.  
![ 8 ]({{ site.url }}/assets/gitstart/8.png)


다음은 깃허브와 IDE를 연동하여 프로젝트를 진행하는 방법이다. 
 [Github - Eclipse 연동하기]({{ site.url }}/Dev/Git-github와eclipse연동.html)
