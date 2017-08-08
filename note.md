---
layout: page
title: Note
---

{% for post in site.categories.Note %}
  {% if post.title != null %}
    {% capture currentyear %}{{ post.date | date: "%Y. %m" }}{% endcapture %}
    {% if currentyear != year %}
      <h3>{{ currentyear }}</h3>
      {% capture year %}{{ currentyear }}{% endcapture %}
    {% endif %}

    <li>
      <div>{{ post.date | date: "%b %d, %Y" }}» </div> 
      <div><a class="post-link" href="{{ post.url | prepend: site.baseurl }}">{{ post.title }}</a><div>
    </li>  
  {% endif %}
{% endfor %}
