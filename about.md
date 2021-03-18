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

## Professional Highlights
> 前) ~ 2021 스타트업 포만감, CTO
  
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
> 2020 예약배송 플랫폼 포만감 2.0 REACT WEB 개발 및 배포  
> 2019 예약배송 플랫폼 포만감 2.0 Flutter APP 개발  
> 2019 미스터포터, REST API Server 개발 및 배포, 3억원 규모 거래 트렌젝션 처리
> 2018 미스터포터, 관리용 WEB 개발 및 배포  
> 2018 미스터포터, 고객용 WEB 개발 및 배포, 구글 애널리틱스 기준 MAU 7천여명, 1일 최대 9백여명 접속
> 2018 영상스트리밍 그룹웨어 관리시스템/CMS WEB 개발  
> 2018 해외 영상스트리밍 APP 개발  
> 2018 영상스트리밍 관리 서버 개발  
> 2018 졸업 프로젝트, 영상 스트리밍 APP 및 관리용 CMS WEB 개발  
> 2017 (주)Douzone, 그룹웨어 매뉴얼 WEB 개발  
> 2017 경기대학교, 어셈블리 PC 시뮬레이터 개발  

## Programming Languages
> Java / Dart / JavaScript / TypeScript / Kotlin / Python  
  
## Skills & Knowledge
> Spring5+ / Spring Boot2+  
> MSA / Modular Monolith / AOP / RESTful API  
> Spring Security / Spring OAuth2.0 / CORS / Bcrypt  
> Ehcache / Redis  
> Apache Tomcat8+ / Netty / NginX / HTTPS/2 / Grafana / Prometheus /  NCP / Fcm  
> JPA(ORM) / MySQL5.7  
> TDD / JUnit5+ / Jmeter /  NGROK / Postman  
> Git / BitBucket / GitHub / SourceTree  
> Flutter / React.js / Vue.js / Jsp / Bootstrap / JQuery / ES6+  
> SMTP / IMAPS / POP3S / hMailServer / ThunderBird  
  
