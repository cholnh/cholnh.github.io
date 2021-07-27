---
layout: post
title:  "RabbitMQ GCP 에 올리기"
categories: RabbitMQ
tags: RabbitMQ AMQP GCP Docker SpringBoot
comments: true
---

### 개요

**RabbitMQ** 란 메시지 브로커 방식의 메시지 큐 오픈소스입니다.  
메시지 큐를 왜 사용하는지, 어떻게 설치하여 사용하는지 차근차근 살펴보겠습니다.  

<div class="br"/>

아래와 같은 준비물이 필요합니다.
- RabbitMQ
- SpringBoot
- GCE (구글 클라우드 플랫폼 - Google Compute Engine)
- Docker

<br/>

### RabbitMQ 란?

AMQP(Advanced Message Queuing Protocol)은 각기 다른 벤더들이 메시징을 통해 통신할 때 사용하는 응용 계층 프로토콜 입니다.  
RabbitMQ는 얼랭(Erlang)으로 짜여진 AMQP 프로토콜의 구현체로 비동기 작업 큐에 주로 사용됩니다.

<div class="br"/>

이러한 메시지 큐에는 RabbitMQ 외에도 Kafka, ActiveMQ, ZeroMQ 등 여러가지가 있습니다.  
주로 RabbitMQ 와 Kafka 가 쓰이며, 비교적 가벼운 RabbitMQ 를 다뤄보도록 하겠습니다.  

> RabbitMQ 는 주로 복잡한 라우팅, 신속함이 요구되는 웹에 유리한 반면 Kafka 는 무거운 성능을 다루는데 유리합니다. 
> 비교적 가벼운 작업 또는 안정적인 작업이 요구된다면 RabbitMQ 를 사용하는 것이 바람직해 보입니다.

<br/>

#### 라우팅 모델

AMQP 의 라우팅 모델은 `Exchange`, `Queue`, `Binding` 으로 구성됩니다.  

- `Exchange`  
  Producer 로부터 수신한 메시지를 큐에 분배하는 라우터 역할

- `Queue`  
  메시지를 메모리/디스크에 저장했다가 Consumer 에게 전달하는 역할

- `Binding`  
  `Exchange` 와 `Queue` 의 관계를 정의한 것

<br/>

#### Exchange Type

AMQP 는 메시지를 어떻게 분배(라우팅)할지에 대해 다음과 같이 타입을 나누었습니다.

- `Direct`  
  `Exchange` 에 바인딩 된 `Queue` 중 메시지의 라우팅 키와 매핑되어 있는 `Queue` 로 메시지 전달.  
  1:1 관계로 Unicast 방식에 적합.

- `Fanout`  
  메시지의 라우팅 키를 무시하고 바인딩 된 모든 `Queue` 에 메시지를 전달.  
  1:N 관계로 브로드캐스트 하는 용도로 사용.

- `Topic`  
  메시지의 라우팅 키를 `Queue` 에 정의된 패턴과 대조하여 메시지를 전달.  
  Multicast 방식에 적합.

- `Headers`  
  라우팅 키를 사용하지 않고 메시지 헤더에 여러 속성들을 더해 속성이 매칭되는 `Queue` 에 메시지 전달.
  
<br/>

### RabbitMQ 설치 (in GCP)

#### GCE 인스턴스 생성

`GCE` 인스턴스 생성이 처음이시라면 [이곳을]({{ site.url }}/gcp/gce-위에-모놀리식-스프링부트-실행시키기.html) 참고해주세요.  
생성된 인스턴스에 방화벽 규칙을 추가하여 RabbitMQ 사용 포트(`5672`, `15672`)를 열어줍니다. 

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/rabbitmq/gcp-port.png"/>
  <div>GCP 방화벽 규칙 수정</div>
</div>

<br/>

#### Docker 설치

RabbitMQ 를 간단하게 GCE 인스턴스위에 올리기 위해 컨테이너 기술을 사용합니다.  
컨테이너 기술인 `Docker` 설명 및 설치에 관한 내용은 [이곳에]({{ site.url }}/docker/도커.html) 정리해두었습니다.

<br/>

#### RabbitMQ 컨테이너 생성

도커 명령어를 통해 RabbitMQ 이미지를 받아와 컨테이너화 합니다.

```
sudo docker run -d --name rabbitmq -p 5672:5672 -p 8080:15672 --restart=unless-stopped -e RABBITMQ_DEFAULT_USER={{ USERNAME }} -e RABBITMQ_DEFAULT_PASS={{ PASSWORD }} rabbitmq:management
```

- `USERNAME`  
  RabbitMQ 관리 모듈에 사용되는 아이디 설정
  
- `PASSWORD`  
  RabbitMQ 관리 모듈에 사용되는 패스워드 설정

<br/>

`{외부 접속 IP}:8080` 으로 접속하면 관리 페이지를 확인할 수 있습니다.  

<div class="nzzi-image-box">
  <img src="{{ site.url }}/assets/rabbitmq/rabbitmq-intro.png"/>
  <div>RabbitMQ 관리 페이지</div>
</div>
     
<br/>

### RabbitMQ 스프링 연동

간단한 스프링 부트 프로젝트를 생성합니다.  
전체 코드는 [이곳을](https://github.com/cholnh/mqtest) 확인해주세요.

<div class="br"/>

스프링 AMQP 사용을 위한 디펜던시를 추가해줍니다.  
(데이터 바인딩을 위한 jackson 도 추가해줍니다)

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-amqp'
    implementation 'com.fasterxml.jackson.core:jackson-databind'
}
```

<div class="br"/>

`application.yml` 파일을 열어 rabbitmq 설정을 적어줍니다.

```yaml
spring:
  rabbitmq:
    host: {{ GCP 외부 IP }}
    port: 5672
    username: {{ 위에서 설정한 접속 아이디 }}
    password: {{ 위에서 설정한 접속 비밀번호 }}
    # virtual-host: virtual host를 사용하는 경우 virtual host 이름
```

<div class="br"/>

#### RabbitTemplate (Publisher)

`RabbitTemplate` 은 메시지 큐로 이벤트를 생산하는 Publisher 입니다.  
아래와 같이 `RabbitTemplate` 빈을 설정합니다.  

```java
@Bean
public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setChannelTransacted(true);
    rabbitTemplate.setReplyTimeout(60000);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
}
```

- `setConnectionFactory()`  
  주입 받은 connectionFactory 를 설정합니다.

- `setChannelTransacted()`  
  위 설정이 true 라면, @Transaction 이 붙은 것처럼 처리됩니다.
  
- `setReplyTimeout()`  
  요청에 대한 타임아웃 시간을 설정합니다.

- `setMessageConverter()`  
  받아온 JSON 형태의 메시지를 객체로 바인딩하는 컨버터와 연결시켜줍니다.
  
<br/>
  
#### SimpleRabbitListenerContainerFactory (Consumer)

구독한 메시지 큐를 통해 받아온 이벤트를 처리하는 Consumer 입니다.  
아래와 같이 `SimpleRabbitListenerContainerFactory` 빈을 설정합니다.

```java
@Bean
public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
        ConnectionFactory connectionFactory) {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setDefaultRequeueRejected(false);
    factory.setMessageConverter(messageConverter());
    factory.setChannelTransacted(true);
    factory.setAdviceChain(RetryInterceptorBuilder
            .stateless()
            .maxAttempts(MAX_TRY_COUNT)
            .recoverer(new RabbitMqExceptionHandler())
            .backOffOptions(INITIAL_INTERVAL, MULTIPLIER, MAX_INTERVAL)
            .build());
    return factory;
}
```

- `setDefaultRequeueRejected()`  
  true 시, 리스너에서 예외 발생하면 다시 큐로 돌아가 쌓이게 됩니다.  
  (예외 상황을 해제하기 전까지 무한 반복될 듯 합니다)

- `setMessageConverter()`  
  받아온 JSON 형태의 메시지를 객체로 바인딩하는 컨버터와 연결시켜줍니다.
  
- `setChannelTransacted()`  
  위 설정이 true 라면, @Transaction 이 붙은 것처럼 처리됩니다.
  
- `maxAttempts()`  
  예외가 발생했을 경우 몇 번을 더 재시도할 지를 정합니다.
  
- `recoverer()`  
  예외 핸들러를 추가합니다.
  
- `backOffOptions()`  
  재시도 횟수에 대한 옵션을 지정합니다.  
  ex) 3000, 3, 10000 으로 지정한 경우: 3초 간격으로 3으로 곱해서 최대 10초 까지 재시도
  
<br/>

#### 이벤트 이름 정의

각 이벤트 이름들에 대해 interface 형태로 관리합니다.  
이곳에 정의된 이벤트 이름으로 라우팅하게 됩니다. 

```java
public interface RabbitMqEvent {
    String PRODUCT_MESSAGE_EVENT = "product.message";
    String PRODUCT_SAVE_EVENT = "product.save";
}
```

<br/>

#### Queue 빈 등록

정의한 이벤트를 받아올 Queue 를 등록합니다.  
각 Queue 는 Bean 으로 등록되고 서버에 자동으로 등록됩니다.  

```java
@Bean
public Queue productMessageEvent() {
    return QueueBuilder
            .durable(RabbitMqEvent.PRODUCT_MESSAGE_EVENT)
            .build();
}

@Bean
public Queue productSaveEvent() {
    return QueueBuilder
            .durable(RabbitMqEvent.PRODUCT_SAVE_EVENT)
            .build();
}
```

<br/>

#### 이벤트 정의

이벤트 데이터 모델을 정의합니다.

```java
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@ToString
public class ProductEvent {
    private String name;
    private int price;
}
```

- 기본 생성자의 접근지정자를 private 로 설정할 경우 오류가 발생합니다.  
- jackson 데이터 바인딩 시 내부 필드를 읽을 수 있도록 fieldVisibility 를 열어줍니다.

<br/>

#### 이벤트 핸들러 클래스 정의

수신받은 이벤트를 처리할 핸들러를 정의합니다.

```java
@Component
@AllArgsConstructor
public class ProductListener {

    @RabbitListener(queues = RabbitMqEvent.PRODUCT_MESSAGE_EVENT)
    public void productMessageHandler(final Message message) {
        System.out.println("productMessageHandler");
        System.out.println(message);
    }

    @RabbitListener(queues = RabbitMqEvent.PRODUCT_SAVE_EVENT)
    public void productSaveHandler(final ProductEvent event) {
        System.out.println("productSaveHandler");
        System.out.println(event);
    }
}
```

<div class="br"/>

이제 스프링 프로젝트를 시작하면 수신부가 돌아가게 됩니다.  
다음은 간단한 이벤트 메시지를 발신하는 발신부 코드입니다.  

<br/>

#### Sender 프로젝트

이 역시 스프링 프로젝트로 생성 후 윗 작업과 같은 반복을 해줍니다.
- spring amqp 디펜던시 추가
- application.yml 에 rabbitMQ 설정 추가

<div class="br"/>

`RabbitTemplate` 빈을 등록해줍니다.

```java
@Configuration
public class RabbitConfiguration {

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
```

<br/>

#### 메시지 발송

`CommandLineRunner` 를 통해 간단한 메시지를 발송합니다.

```java
@Component
public class ProductSender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public ProductSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) {
        ProductEvent event = ProductEvent.builder()
                .name("초코파이")
                .price(3600)
                .build();

        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMqEvent.PRODUCT_MESSAGE_EVENT, "all message!!");

        System.out.println("Sending my custom message");
        rabbitTemplate.convertAndSend(RabbitMqEvent.PRODUCT_SAVE_EVENT, event);
    }
}
```

<br/>

### 참고 사이트
- https://cheese10yun.github.io/spring-rabbitmq/
