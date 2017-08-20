---
layout: post
title:  "Java MySQL DB 연결"
categories: Java
tags: Java MySQL DB 
comments: true
---
<br/>
### Intro
<br/>
Java WEB개발을 위해 필요한    
**MySQL community server 5.7** 연결 방법

<br/>
### How to
<br/>
우선 **[MySQL community server 5.7](https://dev.mysql.com/downloads/mysql/)을 설치**한다.  
<br/>
![ 1 ]({{ site.url }}/assets/mysql/1.png){: width="100%" height="100%"}  
<br/>

Developer Default 선택 - 설치 진행  
<br/>
![ 2 ]({{ site.url }}/assets/mysql/2.png){: width="100%" height="100%"}  
![ 3 ]({{ site.url }}/assets/mysql/3.png){: width="100%" height="100%"}  
![ 4 ]({{ site.url }}/assets/mysql/4.png){: width="100%" height="100%"}  
<br/>

설치 완료 후 MySQL Command Line Client 실행  
<br/>
![ 5 ]({{ site.url }}/assets/mysql/5.png){: width="100%" height="100%"}  
<br/>

Java와 연동을 하기 위한 **[Connector J 설치](https://dev.mysql.com/downloads/connector/j/)**  
<br/>
![ 6 ]({{ site.url }}/assets/mysql/6.png){: width="100%" height="100%"}  
<br/>

`.TAR`  또는 `.ZIP` 파일 내 `mysql-connector-java-5.1.40-bin.jar` 를  
**프로젝트 -  WebContent - WEB-INF - lib 에 복사**한다.  
<br/>
![ 7 ]({{ site.url }}/assets/mysql/7.png){: width="100%" height="100%"}  
<br/>

**Window - Show View - Data source explorer 선택**  
- 없을 경우 : **Window - Show View - other - Data Management 내 Data source explorer 선택**)  
- 이것도 없을 경우 : **Help - Install New Software 이동**  
<br/>
![ 8 ]({{ site.url }}/assets/mysql/8.png){: width="100%" height="100%"}  
<br/>

**Add… 선택**  
<br/>
![ 9 ]({{ site.url }}/assets/mysql/9.png){: width="100%" height="100%"}  
<br/>

Location 에 입력 :  `url + 버전` (아래 예시는 neon 버전)  
Ex) `http://download.eclipse.org/releases/neon`  
<br/>
![ 10 ]({{ site.url }}/assets/mysql/10.png){: width="100%" height="100%"}  
<br/>

**Database Development** 선택 후 Next 눌러 설치  
<br/>
![ 11 ]({{ site.url }}/assets/mysql/11.png){: width="100%" height="100%"}  
<br/>
**Database Connections - 오른쪽 클릭 - New - MySQL 선택**  
<br/>
![ 12 ]({{ site.url }}/assets/mysql/12.png){: width="100%" height="100%"}  
![ 13 ]({{ site.url }}/assets/mysql/13.png){: width="100%" height="100%"}  
<br/>
Test Connection 눌러서 **Ping Test**  
<br/>
![ 14 ]({{ site.url }}/assets/mysql/14.png){: width="100%" height="100%"}  
<br/>
   
**연동 완료**

