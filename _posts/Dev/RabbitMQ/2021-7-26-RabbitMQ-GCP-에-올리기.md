---
layout: post
title:  "RabbitMQ GCP 에 올리기"
categories: RabbitMQ
tags: RabbitMQ AMQP GCP Docker SpringBoot
comments: true
---

#### 개요

RabbitMQ 란 메시지 브로커 방식의 메시지 큐 오픈소스입니다.  
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
**RabbitMQ**는 얼랭(Erlang)으로 짜여진 AMQP 프로토콜의 구현체로 비동기 작업 큐에 주로 사용됩니다.

<br/>

이러한 메시지 큐에는 RabbitMQ 외에도 Kafka, ActiveMQ, ZeroMQ 등 여러가지가 있습니다.  
주로 RabbitMQ 와 Kafka 가 쓰이며, 비교적 가벼운 RabbitMQ 를 다뤄보도록 하겠습니다.  

> RabbitMQ 는 주로 복잡한 라우팅, 신속함이 요구되는 웹에 유리한 반면 Kafka 는 무거운 성능을 다루는데 유리합니다.  
> 비교적 가벼운 작업 또는 안정적인 작업이 요구된다면 RabbitMQ 를 사용하는 것이 바람직해 보입니다.

<br/>

#### 라우팅 모델

AMQP 의 라우팅 모델은 `Exchange`, `Queue`, `Binding` 으로 구성됩니다.  

- `Exchange`  
  Producer 로부터 수신한 메시지를 큐에 분배하는 라우터 역할

<br/> 

- `Queue`  
  메시지를 메모리/디스크에 저장했다가 Consumer 에게 전달하는 역할
  
<br/>

- `Binding`  
  `Exchange` 와 `Queue` 의 관계를 정의한 것

<br/>

#### Exchange Type

AMQP 는 메시지를 어떻게 분배(라우팅)할지에 대해 다음과 같이 타입을 나누었습니다.

- `Direct`  
  `Exchange` 에 바인딩 된 `Queue` 중 메시지의 라우팅 키와 매핑되어 있는 `Queue` 로 메시지 전달.  
  1:1 관계로 Unicast 방식에 적합.
  
<br/>

- `Fanout`  
  메시지의 라우팅 키를 무시하고 바인딩 된 모든 `Queue` 에 메시지를 전달.  
  1:N 관계로 브로드캐스트 하는 용도로 사용.

<br/>

- `Topic`  
  메시지의 라우팅 키를 `Queue` 에 정의된 패턴과 대조하여 메시지를 전달.  
  Multicast 방식에 적합.
  
<br/>

- `Headers`  
  라우팅 키를 사용하지 않고 메시지 헤더에 여러 속성들을 더해 속성이 매칭되는 `Queue` 에 메시지 전달.
  
<br/>

### RabbitMQ 설치 (in GCP)

#### GCE 인스턴스 생성

`GCE` 인스턴스 생성이 처음이시라면 [이곳을]({{ site.url }}/gcp/gce-위에-모놀리식-스프링부트-실행시키기.html) 참고해주세요.  

<br/>

생성된 인스턴스에 방화벽 규칙을 추가하여 RabbitMQ 사용 포트(`5672`, `15672`)를 열어줍니다.  

<br/>

#### Docker 설치

RabbitMQ 를 간단하게 GCE 인스턴스위에 올리기 위해 컨테이너 기술을 사용합니다.  
컨테이너 기술인 `Docker` 설명 및 설치에 관한 내용은 [이곳에]({{ site.url }}/docker/도커.html) 정리해두었습니다.

<br/>

#### RabbitMQ 컨테이너 생성

도커 명령어를 통해 RabbitMQ 이미지를 받아와 컨테이너화 합니다.

`
sudo docker run -d --name rabbitmq -p 5672:5672 -p 8080:15672 --restart=unless-stopped -e RABBITMQ_DEFAULT_USER={{ USERNAME }} -e RABBITMQ_DEFAULT_PASS={{ PASSWORD }} rabbitmq:management
`

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
