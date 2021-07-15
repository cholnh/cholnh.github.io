---
layout: post
title:  "RESTful API 설계"
categories: REST
tags: API Server REST
comments: true
---

#### 개요

마이크로 서비스 팀은 서비스가 제공하는 기능에 대한 프론트엔드/백엔드 구현을 모두 책임지며, 두 엔지니어가 하나의 팀에서 긴밀하게 협업해야 합니다.  
이러한 협력을 위해서는 프론트/백엔드 연계를 위한 계약이 필요한데, 이것이 API 설계입니다.  
(API는 백엔드 서버에 존재하지만 프론트엔드 요구사항을 충족하도록 정의해야 합니다)

<br/>

최근의 추세는 HTTP 프로토콜과 JSON 포맷을 사용하는 REST API 가 표준처럼 사용되고 있습니다.  
이러한 REST API 방식에 대해 살펴보도록 하겠습니다.

<br/>

### REST API 배경 및 개념
REST(Representational State Transfer) API 는 HTTP 프로토콜을 사용하는 네트워크 기반의 아키텍처 스타일 입니다.  
미국 컴퓨터 과학자인 Roy Fielding(이하 “로이 필딩”) 박사에 의해 쓰여진 논문에서 REST 가 처음 언급됩니다.  

> REST 를 개발하게 된 동기는 웹이 어떻게 동작해야 하는지에 대한 구조적 모델을 만들어서  
> 웹 프로토콜 표준을 위한 guiding framework 역할을 하기 위함이었다.

웹이 쓰이는 어플리케이션은 무수하며 그 사양이나 인터페이스는 천차만별입니다.  
REST 는 이러한 다양성에 대해 일관성을 위한 가이드를 제시합니다.  

<br/>

구성요소는 다음과 같습니다.
- 자원 (resource)
- 행위 (verb)
- 표현 (representations)

<br/>

예를 들어, '양념치킨 이라는 제품을 생성한다' 라는 API 가 필요하다면  
- 자원 : 제품 (products)
- 행위 : 생성한다 (post)
- 표현 : 양념치킨

<br/>

이를 REST API 로 표현하면 다음과 같습니다.

```metadata json
HTTP POST http://api.domain.com/products/
{
  "products": {
    "name": "양념치킨"
  }
}
```

- 자원은 `http://api.domain.com/products/` 라는 URL 로 표현됩니다.
- 생성을 위한 HTTP 메서드인 `POST` 가 사용됩니다.
- `JSON` 문서의 형태로 구체적인 `products` 로 표시(전달)되었습니다.
- 개별 항목을 조회할 때는 뒤에 ID 를 붙여 조회합니다.
    + `HTTP GET http://api.domain.com/products/1`

<br/>

아래처럼 REST API 구성요소만 보고 API 내용을 직관적으로 이해할 수 있으므로 최소한의 문서로써 소통이 가능합니다.

|HTTP 메서드|URI|요청|
|-|-|-|
|POST|`https://api.domain.com/v1/products`|새로운 제품정보 생성|
|GET|`https://api.domain.com/v1/products`|제품정보 목록 조회|
|GET|`https://api.domain.com/v1/products/1`|1번 제품정보 조회|
|PUT|`https://api.domain.com/v1/products/1`|1번 제품정보 수정|
|DELETE|`https://api.domain.com/v1/products/1`|1번 제품정보 삭제|

<br/><br/>

REST API 특징은 다음과 같습니다.
- 애플리케이션 분리 및 통합
- 다양한 기기와의 통신 (다양한 모바일, 다양한 브라우저 등)

<br/><br/>

RESTful 하다는 것은 다음을 충족해야 합니다.

- Server-Client  
    자원이 있는 쪽이 Server, 자원을 요청하는 쪽이 Client 가 됩니다.  
    서로간 의존성이 적습니다.
    
<br/>

- Stateless (무상태)  
    Http 프로토콜은 Stateless 프로토콜 이므로, REST 역시 Stateless 성격을 갖습니다.  
    (Stateless 란 Client 상태(State)를 Server 측에 저장/관리 하지 않음을 뜻합니다)
    - 세션, 쿠키와 같은 context 정보를 신경쓰지 않아도 되므로 구현이 단순해집니다.
        
<br/>

- Cacheable  
    (웹 Http 인프라를 그대로 사용할 수 있어서) Http 캐시 활용이 가능합니다.
    - HTTP 프로토콜 표준에서 사용하는 `Last-Modified` 태그나 `E-Tag`를 이용하면 캐싱 구현이 가능합니다.

<br/>

- Layered System  
    REST Server 는 다중 계층으로 구성될 수 있습니다.
    - API Server 는 순수 비즈니스 로직을 수행하고 그 앞단에 보안, 로드밸런싱, 암호화, 사용자 인증 등을 추가하여 구조상의 유연성을 줄 수 있습니다.
    - 또한 로드밸런싱, 공유 캐시 등을 통해 확장성과 보안성을 향상시킬 수 있습니다.
    - PROXY, 게이트웨이 같은 네트워크 기반의 중간 매체를 사용할 수 있습니다.
        
<br/>

- Code-On-Demand (Optional)  
    Server 로 부터 스크립트를 받아서 Client 에서 실행합니다.  
    (반드시 충족할 필요는 없습니다)
        
<br/>

- Uniform Interface  
    URI 로 지정한 Resource 에 대한 조작을 통일되고 한정적인 인터페이스로 수행합니다.  
    이러한 특성에 의해 HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용이 가능합니다.
        
<br/><br/>

### 바람직한 REST API
다음은 바람직한 REST API 설계를 위한 리처드슨이 정의한 REST API 성숙도 모델입니다.  
아래 모델을 참고해서 RESTful 한 API 설계를 할 수 있습니다.  

<br/>

- 레벨 0  
    REST API 메커니즘을 전혀 사용하지 않고 전통적인 원격 프로시저 호출(RPC) 방식으로 HTTP 프로토콜만 사용한 것.  
    예로 들면 `/ProductService?Flag=create` 처럼 하나의 URI 주소에 GET 방식의 매개변수 `Flag` 를 통해 CRUD (조회/입력/수정/삭제)를 처리하는 방식입니다.  
    (개발자가 백엔드 내부에서 비즈니스 로직을 통해 어떤 결정을 하는지 사용자는 API 를 보고는 알 수 없기에) API 사용을 위한 명세가 필요합니다.

<br/>

- 레벨 1  
    URI 에 개별적인 자원을 표현하는 것.  
    여러 기능을 사용하기 위해 하나의 URI 에 요청하지 않고 요청이 필요한 대상을 특정합니다.  
    예로 들면 `/products/chicken`, 사용자는 특정 리소스가 어떠한 정보를 제공하는지 인지할 수 있습니다.
    
<br/>

- 레벨 2  
    서비스의 기능을 처리하기 위해 약속된 HTTP 메서드들을 사용하는 것입니다.  
    (물론 레벨 0,1 에서도 메서드를 사용했지만 개발자마다 사용법이 달랐습니다)  
    가능한 한 약속된 HTTP 사용법에 가깝게 메서드를 사용합니다.  
    조회, 입력, 수정, 삭제 기능을 HTTP 메서드인 `GET`, `POST`, `PUT`, `DELETE` 로 각각 처리합니다.  
    API 사용자는 어떠한 메서드를 사용했을 때 어떠한 행위가 발생할지 인지할 수 있습니다.
    
<br/>

- 레벨 3  
    HATEOAS(Hypertext As The Engine Of Application State) 방식으로 정의됩니다.  
    이 방식은 특정 요청을 하게 되면 반환값 결과에 덧붙여 추가로 사용자가 그다음에 무엇을 할 수 있는지와 그것을 실행하기 위한 URI 값을 보내줍니다.  
    사용자에게 좀 더 리소스를 탐색해서 활용할 수 있는 가능성을 제공합니다.

<br/>    

REST API 사용을 암묵적으로 가능하게 하는 레벨 2 정도를 지향하는 것이 좋습니다.  

<br/>

### API 설계 문서화
애자일 모델링 방식을 추구할 때 가급적이면 불필요한 설계물을 남기지 않는 것이 좋습니다.  
산출물의 필요성은 항상 공유/협업 측면에서 고려되어야 합니다.  
API 설계는 프론트엔드 엔지니어 - 백엔드 엔지니어의 협업 차원에서 중요합니다.  
(특히 다른 설계 요소에 비해 문서화 공유가 중요합니다)  

<br/>

협업 시스템이 있다면 간단히 위키(Wiki) 형태의 문서로 작성합니다.  
다음은 API 문서화에 필요한 최소 항목입니다.
- 서비스명, API 명, 리소스(URI)
- 요청 매개변수, 요청 샘플
- 응답 매개변수, 응답 샘플
