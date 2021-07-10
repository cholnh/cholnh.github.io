---
layout: post
title:  "스프링 코드 스타일 가이드 (아키텍처 및 Directory 스타일)"
categories: Spring
tags: Java Spring Architecture Code-Style
comments: true
---

#### 개요

해당 포스팅은 상위 프로젝트([배달 서비스 플랫폼 API 서버 가이드](https://github.com/cholnh/delivery-platform-server-guide#배달-서비스-플랫폼-api-서버-가이드))
에서 다루는 내용의 예시이며, 마이크로 서비스를 구성하는 여러 서비스 중 자바로 구현된 프로젝트의 내부 아키텍처 코드 스타일에 대해 다룹니다.

<br/><br/>


#### 아키텍처 스타일

마이크로 서비스 내부 구조를 정의할 때 반드시 **내부 구조의 다양성** 을 고려해야 합니다.  
자율적인 마이크로 서비스 팀에 의한 `폴리그랏한 내부 구조`를 가질 수 있기 때문입니다.  

<br/>

마이크로 서비스 아키텍처에서 각 서비스는 각기 목표와 활용도에 따라 명확하게 분리돼야 하고,  
각 서비스 목적에 따라 적절한 개발 언어 및 저장소, 내부 아키텍처를 정의하는 것이 바람직합니다.

> 조회나 아주 간단한 기능의 경우 헥사고날이나 클린 아키텍처 방식의 구조를 고수할 필요는 없을 것입니다.  
> 그렇지만 비즈니스 규칙이 복잡할 경우 헥사고날이나 클린 아키텍처 구조 기반으로 정의하는 것이 바람직할 것입니다.

<br/>

해당 프로젝트는 복잡한 비즈니스 규칙과 다양한 조회 및 명령 기능을 포함하며,  
다양한 인터페이스(Repository, Event-message, API call proxy 등) 적용이 필요하므로  
`헥사고날 아키텍처`를 지향한 구조를 사용하여 서비스 내부 구조를 유연하고 기민하게 적용하겠습니다.  

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/spring/guide-directory/ddd-hex-arch-1.png"/>
  <div>헥사고날 아키텍처의 포트와 어댑터</div>
</div>

<br/>

헥사고날 아키텍처 구조는 다음과 같습니다.

- 고수준의 비즈니스 로직을 표현하는 내부 영역 
    + 순수한 비즈니스 로직을 표현하는 기술 독립적인 영역
    + 외부 영역과 연결되는 포트를 가지고 있음
- 인터페이스 처리를 담당하는 저수준의 외부 영역
    + 외부 요청을 처리하는 인바운드 어댑터
    + 비즈니스 로직에 의해 호출되어 외부와 연계되는 아웃바운드 어댑터

<br/>

헥사고날 아키텍처 포트의 특징은 다음과 같습니다.
- 고수준의 내부 영역이 외부의 구체 어댑터에 전혀 의존하지 않습니다. (포트에 의해)
- 인바운드 포트는 내부 영역 사용을 위해 표출된 API 입니다.
- 아웃바운드 포트는 외부를 호출하는 방법을 정의합니다.
- 아웃바운드 포트가 외부의 아웃바운드 어댑터를 호출해서 외부 시스템과 연계하는 것이 아니라  
    아웃바운드 어댑터가 아웃바운드 포트에 의존해서 구현됩니다. (DIP 원칙)

<br/>

헥사고날 아키텍처 어댑터의 특징은 다음과 같습니다.
- 인바운드 어댑터의 종류 예시
    + REST API 컨트롤러
    + 웹 페이지 스프링 MVC 컨트롤러
    + 커맨드 핸들러
    + 이벤트 메시지 구독 핸들러 등
- 아웃바운드 어댑터 종류 예시
    + DAO (Jpa, MySQL, NoSQL 등등)
    + 이벤트 메시지 발행 클래스
    + 외부 서비스 호출 프록시 등

<br/>

전체적인 내부 구조의 모양은 다음과 같습니다.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/spring/guide-directory/ddd-hex-domain-2.png"/>
  <div>마이크로 서비스의 내부 구조</div>
</div>

<br/>
<br/>



#### Directory 스타일

패키지를 구성하는 잘 알려진 방법으로는 크게 2가지 유형이 있습니다.  

- 계층형 구조  
    ```
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── example
        │   │           └── demo
        │   │               ├── DemoApplication.java
        │   │               ├── configuration
        │   │               ├── controller
        │   │               ├── domain
        │   │               ├── error
        │   │               ├── repository
        │   │               └── service
        │   └── resources
        │       └── application.yml
    ```
    
    계층형 구조는 각 Layer 를 기준으로 코드들이 구성됩니다.  
    (설정파일은 `configuration` 디렉터리에 모아두고, 컨트롤러는 `controller` 디렉터리에 모아두는 등)  
    전체적인 계층 구조가 한눈에 보여 프로젝트의 이해도가 상대적으로 낮아지는 장점이 있습니다.  
    하지만 비즈니스가 복잡해질 수록 각 디렉터리에는 클래스가 점점 많아지게 되고 복잡도가 증가하는 단점이 있습니다.

<br/>

- 도메인형 구조  
    ```
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── example
        │   │           └── demo
        │   │               ├── DemoApplication.java
        │   │               ├── coupon
        │   │               │   ├── controller
        │   │               │   ├── domain
        │   │               │   ├── exception
        │   │               │   ├── repository
        │   │               │   └── service
        │   │               ├── user
        │   │               │   ├── controller
        │   │               │   ├── domain
        │   │               │   ├── exception
        │   │               │   ├── repository
        │   │               │   └── service
        │   │               └── order
        │   │                   ├── controller
        │   │                   ├── domain
        │   │                   ├── exception
        │   │                   ├── repository
        │   │                   └── service
        │   └── resources
        │       └── application.yml
    ```
    
    각 도메인과 관련된 코드들이 응집하여 디렉터리를 구성합니다.  
    도메인 주도 개발(DDD)에서 애그리거트를 각 도메인 디렉터리로 표현했을 경우 직관적이며 도메인 이해도가 효율적입니다.  

<br/>  
    
- 커스텀 구조  
    ```
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── example
        │   │           └── demo
        │   │               ├── DemoApplication.java
        │   │               ├── domain
        │   │               │   ├── oauth
        │   │               │   │   └── service
        │   │               │   ├── todo
        │   │               │   │   ├── api
        │   │               │   │   ├── dao
        │   │               │   │   │   └── jpa
        │   │               │   │   ├── dto
        │   │               │   │   ├── exception
        │   │               │   │   ├── model
        │   │               │   │   └── service
        │   │               │   │       └── impl
        │   │               │   ├── user
        │   │               │   │   ├── dao
        │   │               │   │   │   └── jpa
        │   │               │   │   └── model
        │   │               │   └── _base
        │   │               └── global
        │   │                   ├── annotation
        │   │                   ├── configuration
        │   │                   │   ├── database
        │   │                   │   │   └── jpa
        │   │                   │   ├── health
        │   │                   │   ├── http
        │   │                   │   ├── mapper
        │   │                   │   └── security
        │   │                   │       └── jasypt
        │   │                   ├── error
        │   │                   │   └── exception
        │   │                   └── _base
        │   │                       ├── request
        │   │                       └── response
        │   └── resources
        │       └── application.yml
    ```
    
    해당 프로젝트는 도메인형을 베이스로 디렉터리를 구성하였으며 약간의 변형을 주었습니다.  
        
    - `domain` 디렉터리는 비즈니스 도메인이 담겨있는 디렉터리입니다.  
        + `oauth` 디렉터리는 Spring-OAuth-Security 사용하기 위해 구현해야 할 구현체를 다룹니다.
        + `todo` 디렉터리는 todo 라는 도메인과 관련된 코드들이 응집해 있습니다.
            + `api` : 외부 REST API 컨트롤러 클래스들이 존재합니다.  
            + `dao` : repository 와 비슷합니다. repository 로 하지 않은 이유는 조회 전용 구현체들이 많이 작성되는데  
                이러한 객체들은 DAO 라는 표현이 더 직관적이라고 생각합니다.  
                또한 Querydsl 을 사용하여 repository 를 DAO 처럼 확장하므로 DAO 디렉터리 명이 직관적입니다.   
                jpa 외에 여러 저장소 처리 어댑터를 사용하여 폴리그랏하게 구성할 수 있습니다.
            + `dto` : 계층간 데이터 교환을 위한 객체(Dto) 이지만 주로 컨트롤러에서 외부 통신을 위한 데이터 규격에 사용되며,  
                컨트롤러 계층과 서비스 계층간 이동한 뒤 변환되어 사용하나 서비스 계층과 리포지토리 계층간 이동에 사용되지는 않습니다.  
            + `exception` : 해당 도메인이 발생시키는 Exception 으로 구성됩니다.
            + `model` : 애그리거트로 나눠진 클래스(도메인 엔티티와 관련 VO 및 표준 타입) 로 구성됩니다.
            + `service` : 도메인 객체와 외부 영역을 연결해주는 파사드와 같은 역할을 주로 담당하는 클래스로 구성됩니다.  
                cqrs 패턴에 따라 command 기능 서비스와 query 기능 서비스로 분리됩니다.
        + `_base` 디렉터리는 모든 도메인에서 공통적으로 사용할 객체들로 구성됩니다.
    
    <br/>
    
    - `global` 디렉터리는 전체적인 설정(configuration)과 프로젝트 전방위적으로 사용되는 공통 기능이 담겨있는 디렉터리입니다.
        + `annotation` : 커스텀 어노테이션들로 구성됩니다.
        + `configuration` : 스프링 각종 설정들로 구성됩니다.
        + `error` : 예외 및 예외 핸들링을 담당하는 클래스들로 구성됩니다.
        + `_base` 디렉터리는 global 내부에서 사용될 공통 객체들로 구성됩니다.

<br/>
<br/>



#### 참고

- [cheese10yun/spring-guide](https://github.com/cheese10yun/spring-guide) 참고
- 도메인 주도 설계로 시작하는 마이크로서비스 개발 (한정헌, 유해식, 최은정, 이주영 저)
- 테스트 주도 개발로 배우는 객체 지향 설계와 실천 (Steve Freeman, Nat Pryce 저)
- Clean Code (Robert C. Martin 저)
- Mastering Spring 5.0 (Ranga Rao Karanam 저)
