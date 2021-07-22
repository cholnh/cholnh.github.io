---
layout: page
---
<script src="public/js/jquery-1.11.1.min.js"></script>
<script src="public/js/tagcloud.jquery.min.js"></script>
<style>
	#tagcloud {
    display: none;
		resize: none;
		border: none;
		outline: none;
		text-decoration: none;
		padding: auto;
		margin: auto;
		list-style-type: none;
	}
</style>
<script type="text/javascript">
  function isMobile(){
    var UserAgent = navigator.userAgent;
    if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null           || UserAgent.match(/LG|SAMSUNG|Samsung/) != null) {
      return true;
    } else {
      return false;
    }
  }
</script>
<script type="text/javascript">
    var settings = {
    //height of sphere container
    height: 350,
    //width of sphere container
    width: 350,
    //radius of sphere
    radius: 100,
    //rotation speed
    speed: 0.5,
    //sphere rotations slower
    slower: 0.1,
    //delay between update position
    timer: 5,
    //dependence of a font size on axis Z
    fontMultiplier: 25,
    //tag css stylies on mouse over
    hoverStyle: {
        border: 'bold',
        color: '#0b2e6f'
    },
    //tag css stylies on mouse out
    mouseOutStyle: {
        border: '',
        color: ''
    }
    };
    $(document).ready(function(){
        if(isMobile()) {
            $('#tagcloud').hide();
        } else {
            $('#tagcloud').show();
            $('#tagcloud').tagoSphere(settings);
        }
    });
</script>

<div id="tagcloud">
	<ul>
	{% for tag in site.tags %}
		<li><a href="/tags/#{{ tag | first | slugize }}">{{ tag | first }}</a></li>
	{% endfor %}
	</ul>
</div>
<!--
<br><br>
<div style="overflow: hidden">
  <img src="/images/guam.png" style="width:100%; height:100%;">
<div>
<br><br>
-->

## Introduce
**3**년간 스타트업을 직접 운영하며 **서버/웹/앱** 서비스를 **기획/개발/배포**하였습니다. 기획자와 디자이너와 함께 일하며 MAU 7천여명 접속자와 3억원 규모 거래 트렌젝션이 발행되는 작은  비즈니스를 만들어왔습니다.

<br/>

개발자는 근본적으로 **문제를 해결하는 직업**이며 그 도구로 프로그래밍 언어를 사용한다고 생각합니다. 문제 해결을 위해 **책임감**을 갖고 임하는 것이 무엇보다 중요하다고 믿습니다. 
반복되는 비효율적인 업무의 **자동화를** 좋아합니다. 개발 뿐만 아니라 다른 포지션 팀원의 단순 반복 업무를 자동화하여 팀의 업무 효율에 기여한 바 있습니다. 

<br/>

요리하는 것을 좋아하며, 건강에 관심이 많아 7년째 덤벨을 들고있는 헬스인입니다.

## Programming Languages
> Java / Dart / JavaScript / TypeScript  
  
## Skills & Knowledge

### Back-End

- Spring Boot/Security/OAuth/EHCache
- ORM (JPA, QueryDSL)
- RDBMS (MySQL)
- Junit5/Mockito

### DevOps

- NCP (classic server)
- GCP (GCE, GCR)
- Docker
- Github Actions
- Tomcat/NginX

### Front-End

- Flutter
- Html5/CSS/JS(ES6)
- React.js 
- Vue.js

## Professional Highlights
> 前) 2017 ~ 2021 스타트업 포만감
  
## Service & Activities
> 2020 경기도경제과학진흥원 공유기업 선정  
> 2019 특허 출원 (다중 주문 실시간 배송 처리 장치 및 이를 이용한 다중 주문 실시간 배송 처리 방법, 10-2019-0022262)  
> 2019 베트남 TECHFEST 연수  
> 2019 미래로 고양 창업경진대회 2위 수상  
> 2018 한국과학창의재단 청년혁신가 인큐베이팅 과정 선발  
> 2018 한국항공대학교 항공경영학장배 창업팀 선발  
> 2018 광운대 도시樂 창업경진대회 1위 수상  
> 2018 경기도경제과학진흥원 주관 베이징 중관촌 해외연수  
> 2018 고양지식정보산업진흥원 고-로켓 창업 아이디어 공모전 수상  
> 2018 고양지식정보산업진흥원 고-로켓 창업 경진대회 2위 수상  
> 2018 경기도경제과학진흥원 경기북부 대학생 창업캠프 은상 수상  
> 2018 도전 K-스타트업 통합 본선 진출  
> 2018 대학 창업 U-300팀 최종 60팀 선발  
> 2018 성신여대 스마트창작터 시장검증 지원 선발  
> 2018 한국항공대학교 기술창업경진대회 총장상 수상  
> 2018 고양시 사회적기업 공모전 지원금사업 최종 선발  
  
## Projects
> 2021 포만감, 고객용 리뉴얼 REACT WEB 개발 및 배포  
> 2021 포만감, 업주용 Flutter APP 개발 및 배포  
> 2020 포만감, 소개용 REACT WEB 개발 및 배포  
> 2019 포만감, 고객용 2.0 Flutter APP 개발  
> 2019 미스터포터, API Server 개발 및 배포
> 2018 미스터포터, 관리용 WEB 개발 및 배포  
> 2018 미스터포터, 고객용 WEB 개발 및 배포
> 2018 영상 스트리밍 그룹웨어 CMS WEB 개발  
> 2017 (주)Douzone, 그룹웨어 CMS WEB 개발  
