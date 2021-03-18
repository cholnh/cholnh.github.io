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

<br><br>
<div style="overflow: hidden">
  <img src="/images/guam.png" style="width:100%; height:100%;">
<div>
<br><br>

