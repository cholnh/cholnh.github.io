---
layout: post
title:  "Java MySQL DB 연결"
categories: Java
tags: Java MySQL DB 
comments: true
---

### Intro
Java WEB개발을 위해 필요한    
**MySQL community server 5.7** 연결 방법


### How to

우선 **[MySQL community server 5.7](https://dev.mysql.com/downloads/mysql/)을 설치**한다.


![ 1 ]({{ site.url }}/assets/mysql/1.png)


Developer Default 선택 - 설치 진행


![ 2 ]({{ site.url }}/assets/mysql/2.png)


![ 3 ]({{ site.url }}/assets/mysql/3.png)


![ 4 ]({{ site.url }}/assets/mysql/4.png)


설치 완료 후 MySQL Command Line Client 실행


![ 5 ]({{ site.url }}/assets/mysql/5.png)


Java와 연동을 하기 위한 **[Connector J 설치](https://dev.mysql.com/downloads/connector/j/)**


![ 6 ]({{ site.url }}/assets/mysql/6.png)


`.TAR`  또는 `.ZIP` 파일 내 `mysql-connector-java-5.1.40-bin.jar` 를  
**프로젝트 -  WebContent - WEB-INF - lib 에 복사**한다.

![ 7 ]({{ site.url }}/assets/mysql/7.png)


**Window - Show View - Data source explorer 선택**  
- 없을 경우 : **Window - Show View - other - Data Management 내 Data source explorer 선택**)  
- 이것도 없을 경우 : **Help - Install New Software 이동**

![ 8 ]({{ site.url }}/assets/mysql/8.png)


**Add… 선택**

![ 9 ]({{ site.url }}/assets/mysql/9.png)


Location 에 입력 :  `url + 버전` (아래 예시는 neon 버전)  
Ex) `http://download.eclipse.org/releases/neon`

![ 10 ]({{ site.url }}/assets/mysql/10.png)


**Database Development** 선택 후 Next 눌러 설치 

![ 11 ]({{ site.url }}/assets/mysql/11.png)

**Database Connections - 오른쪽 클릭 - New - MySQL 선택**

![ 12 ]({{ site.url }}/assets/mysql/12.png)

![ 13 ]({{ site.url }}/assets/mysql/13.png)

Test Connection 눌러서 **Ping Test**

![ 14 ]({{ site.url }}/assets/mysql/14.png)

   
**연동 완료**

