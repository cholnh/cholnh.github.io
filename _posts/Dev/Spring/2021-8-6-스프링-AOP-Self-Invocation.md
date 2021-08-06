---
layout: post
title:  "스프링 AOP Self-Invocation 발생 해결"
categories: Spring
tags: Java Spring Dependency AOP Self-Invocation CGLIB
comments: true
---

## Self-Invocation 이란?

스프링 AOP 는 **프록시 기반의 AOP** 를 제공하기 위해 `CGLIB` 과 `JDK Dynamic Proxy` 를 사용한다.
`JDK Dynamic Proxy` 의 경우 인터페이스를 구현받아 프록시를 생성하게 되는데, 인터페이스가 구현되어있지 않을 경우 `CGLIB` 을 통해 프록시가 생성이 된다.
자세한 내용은 [ **[스프링 AOP]({{ site.url }}/spring/스프링-AOP.html)** ]에 포스팅하였습니다.

<br/>

프로그램이 시작되면 IoC 컨테이너는 DI 작업을 위한 빈 등록을 하게된다.
우선 빈 후처리기를 통해 AOP 에 등록할 빈을 검수(?)하게 된다. (어드바이스의 포인트컷을 조회하여 빈을 뽑아옴)
선별된 빈은 프록시 생성기를 통해 프록시 객체로 감싸진 다음 IoC 컨테이너에 (빈을 대신하여)등록되어 진다.

<br/>

빈 내부에 있는 메서드가 Invocation(실행)될 때, 주입 받은 프록시 객체가 대신 참조되게 된다.
하지만 자기 자신의 클래스 내에 있는 메서드를 실행할 경우, 프록시 객체를 참조하지 않고 자신을 통해 Invocation(실행) 되게 되는데,
이것이 바로 Self-Invocation 이다.

<br/>

그렇다면 왜 Self-Invocation 이 문제가 될까?
아래와 같은 코드를 통해 확인해 보자.

```java
@Service
public class ServiceImpl {

    @Cacheable(cacheNames = "testCache")
    public Response findOne(Long id) {
        ...
    }

    @Cacheable(cacheNames = "testCache")
    public Set<Response> findMulti(Set<Long> ids) {
        for (Long id : ids)
            findOne(id);
        ...
    }
}
```

`findMulti()` 메서드가 자신의 클래스 내부에 있는 `findOne()` 을 호출하고 있다.
결과는 어떻게 될까?
피호출자인 `findOne()` 메서드의 `@Cacheable` 은 단 한번도 실행되지 않게 된다.

<br/>

## Self-Invocation 원인

Self-Invocation 은 프록시를 사용하기에 생기는 문제이다.
위 `findMulti()` 메서드가 실행되는 것을 그림으로 확인해보자.

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/spring/aop-self-invocation-1.jpg"/>
  <div>AOP 동작 원리</div>
</div>

- `findMulti()` 메서드가 Invocation 되면 프록시의 메서드가 실행된다.
  `$proxy.findMulti();`
  해당 프록시에 주입된 Aspect 와 위빙된 본래 메서드가 실행된다.

- `findMulti` 메서드 내부에 `findOne()` 메서드가 실행된다.
  `this.findOne();`
  자기 자신의 클래스 메서드를 실행한다. (물론 Aspect 실행이 되지 않는다)

<br/>

그림과 같이 self-invocation 은 aspect 로 감싸진 proxy 를 호출하는 것이 아니라,
자기 자신(`this`)을 바로 호출하여 aspect 가 실행되지 않게 된다.

<br/>

## Self-Invocation 해결

해결방식은 대부분 비슷하다. this 를 사용한 메서드 대신 프록시 객체를 참조하게 하는 것이다.

- `AopContext` 사용
  ```java
  @Cacheable(cacheNames = "testCache")
  public Set<Response> findMulti(Set<Long> ids) {
    for (Long id : ids)
        ((ServiceImpl) AopContext.currentProxy()).findOne(id);
    ...
  }
  ```
  + 현재 호출된 프록시 객체를 재사용하는 방법이다.

- IoC 컨테이너 Bean 주입
  `@Resource` 어노테이션을 통해 자기 자신의 빈을 주입받아 프록시 객체를 참조하게 한다.

  ```java
  @Service
  public class ServiceImpl {

      @Resource(name="serviceImpl")
      ServiceImpl self;

      @Cacheable(cacheNames = "testCache")
      public Response findOne(Long id) {
          ...
      }

      @Cacheable(cacheNames = "testCache")
      public Set<Response> findMulti(Set<Long> ids) {
          for (Long id : ids)
              self.findOne(id);
          ...
      }
  }
  ```
  + 위와 같이 `this.findOne()` 호출이 아닌 `self.findOne()` 호출을 통해 프록시 객체를 호출한다.
