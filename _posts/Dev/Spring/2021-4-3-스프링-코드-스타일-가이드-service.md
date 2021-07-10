---
layout: post
title:  "스프링 코드 스타일 가이드 (Service 스타일)"
categories: Spring
tags: Java Spring Architecture Code-Style
comments: true
---

#### 개요

해당 포스팅은 상위 프로젝트([배달 서비스 플랫폼 API 서버 가이드](https://github.com/cholnh/delivery-platform-server-guide#배달-서비스-플랫폼-api-서버-가이드))
에서 다루는 내용의 예시이며, 마이크로 서비스를 구성하는 여러 서비스 중 자바로 구현된 프로젝트의 내부 아키텍처 코드 스타일에 대해 다룹니다.

<br/><br/>



#### 기본 구조
<br/>

레이어드 아키텍처 또는 헥사고날 아키텍처에서 계층(영역) 간 호출은 인터페이스를 통해 호출하는 것이 바람직합니다.  

<br/>

추상 인터페이스를 통한 통신이 상-하위 계층간 의존성을 분리시켜주고,  
하위 계층에서는 추상적 인터페이스를 만족하는 다양한 방식의 구현체를 선택적으로 적용할 수 있게 됩니다.  

<br/>

마이크로 서비스 아키텍처 구조를 다시 살펴보면,  

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/spring/guide-directory/ddd-hex-domain-2.png"/>
  <div>마이크로 서비스의 내부 구조</div>
</div>

외부 영역과 내부 영역 사이에 추상 인터페이스(Service I/F, Proxy I/F, Event I/F, Repository I/F 등)가 존재합니다.  
이러한 인터페이스를 통한 기술(저수준의 외부 영역)과 비즈니스(고수준의 내부 영역) 분리는 응집성을 높이고 의존도를 낮추게 됩니다.  

<br/>

서비스 인터페이스는 외부 영역이 내부 영역에 대해 너무 많이 알지 못하게 가리는 역할을 합니다.  
또한 인터페이스를 통해 외부/내부 서비스의 구현 및 테스트를 독자적으로 진행할 수 있도록 합니다.  
(인터페이스가 없이 서비스 구현체를 직접 호출한다면 서비스의 구현이 끝난 후에 다음 작업이 진행될 수 밖에 없습니다)  

<br/>

서비스 인터페이스를 구현하는 **구현체** 에서는 도메인을 활용해 시스템 흐름 처리를 수행합니다.  
(비즈니스 개념 표현 및 비즈니스 행위는 서비스 구현체가 아닌 도메인 모델 객체 스스로가 제어합니다)  

<br/>

또한 자바 스프링 프레임워크를 사용하는 프로젝트에서의 서비스 구현체는 데이터베이스에 대한 트랜젝션을 관리하게 됩니다.  
도메인 객체 내부에 비즈니스 행위가 정의 되어 있지만 그것을 영속화 시켜야 하기 때문에 별도로 서비스 구현체에서 트랜젝션을 관리합니다.  
   
<br/>

즉 서비스 영역은 인프라스트럭처 영역(데이터베이스와 같은)과 도메인 영역을 연결해주는 매개체 역할이고,  
비즈니스 행위는 도메인 모델에서 제어하게 됩니다.

<br/><br/>



#### 행위 기반의 서비스

`Todo` 를 다루는 도메인의 서비스를 정의할 때 `TodoService` 라는 네이밍을 사용하는 것이 일반적입니다.  
하지만 `Todo` 와 관련된 모든 비즈니스 로직이 일정한 기준없이 모이게 되고, 구현체 또한 복잡한 코드로 넘칠 것 입니다.  

<br/>

로버트 C. 마틴이 쓴 클린코드에서는 이런 말이 나옵니다.  

> 도구 상자를 어떻게 관리하고 싶은가?  
> 작은 서랍을 많이 두고 기능과 이름이 명확한 컴포넌트를 나눠 담고 싶은가?  
> 아니면 큰 서랍 몇 개를 두고 모두를 던져 넣고 싶은가?

<br/>

큰 클래스 보다는 작은 여러 클래스로 나누어진 시스템이 더 바람직하며,  
작은 클래스는 단일 책임 원칙(SRP; Single Responsibility Principle)을 준수해야 한다는 이야기 입니다.

<br/>

그렇다면 `TodoService` 보다는 목적(행위)에 맞게 네이밍을 하면 어떨까요.  
조회 목적을 갖는 서비스는 `TodoFindService` 라는 네이밍을 통해 조회의 책임을 갖는 객체라는 것을 알릴 수 있습니다.  
객체를 행위 기반으로 바라보고 네이밍을 하여 자연스럽게 책임을 부여하는 것이 바람직합니다.  

<br/>

```java
public interface TodoQueryService {
    TodoResponse findTodo(Long id);
    Page<TodoResponse> findTodos(Pageable pageable);
    Page<TodoResponse> searchByContents(TodoPredicate predicate, Pageable pageable);
}
```

```java
public interface TodoCommandService {
    TodoResponse create(TodoRequest request);
    TodoResponse update(Long id, TodoRequest request);
    TodoResponse delete(Long id);
}

```

<br/>
<br/>



#### 참고

- [cheese10yun/spring-guide](https://github.com/cheese10yun/spring-guide) 참고
- 도메인 주도 설계로 시작하는 마이크로서비스 개발 (한정헌, 유해식, 최은정, 이주영 저)
- 테스트 주도 개발로 배우는 객체 지향 설계와 실천 (Steve Freeman, Nat Pryce 저)
- Clean Code (Robert C. Martin 저)
- Mastering Spring 5.0 (Ranga Rao Karanam 저)
