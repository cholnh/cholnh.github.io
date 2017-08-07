---
layout: page
permalink: /note/
title: Note
---

{% for post in paginator.posts %}
  # {{ post.url }}
  # {{ post.title }}
	{{ post.date | date_to_string }}
{% endfor %}
# test
