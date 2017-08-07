---
layout: page
title: Note
---

{% for post in site.posts %}
{% capture currentyear %}{{ post.date | date: "%Y. %m" }}{% endcapture %}
{% if currentyear != year %}

  <h3>{{ currentyear }}</h3>
    &nbsp;&nbsp;{% capture year %}{{ currentyear }}{% endcapture %}
  {% endif %}

  <li>{{ post.date | date: "%m. %d" }} — <a href="{{ post.url }}">{{ post.title }}</a></li>
{% endfor %}
