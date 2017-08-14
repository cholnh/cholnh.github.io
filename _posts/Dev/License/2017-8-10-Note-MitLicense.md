---
layout: post
title:  "MIT License란?"
categories: License
tags: MIT-License Copyright
comments: true
---

### Intro

**[MIT License](https://ko.wikipedia.org/wiki/MIT_%ED%97%88%EA%B0%80%EC%84%9C)** 란?

MIT License를 갖는 소프트웨어를 많이 접해 봤을 것이다.  
MIT(미국의 메사추세츠 공대)학생이 만든 소프트웨어가 이렇게나 많나...? 하고 생각해봤을 법 한데, 해당 라이센스는 MIT대학의 소프트웨어 공학도들을 돕기 위해 개발한 라이선스이다.

즉 MIT에서 개발된 소프트웨어를 나타내는 것이 아니고 단지 라이센스 이름이 MIT License인 것이다.

해당 라이센스는 다음과 같은 특징을 갖는다.
- 누구라도 **무상으로 제한없이 취급**할 수 있다.
- 하지만 **저작권 표시 및 이 허가 표시**를 소프트웨어의 모든 복제물 또는 중요한 부분에 **기재**해야 한다.
- 저자 또는 저작권자는 소프트웨어에 관해서 아무런 **책임을 지지 않는다**.

**"무료로 사용할라면 하던가, 근데 사용하다가 뭔 일이 터져도 내 책임은 아님.  
아 그리고 사용할 때 내이름은 이쁜곳에 적어주셈."** 이란 뜻이다.


### Usage

다음은 MIT License의 형식이다.

>Copyright (c) <연도> <저작권 소유자>

>Permission is hereby granted, free of charge, to any person
>obtaining a copy of this software and associated documentation
>files (the "Software"), to deal in the Software without
>restriction, including without limitation the rights to use,
>copy, modify, merge, publish, distribute, sublicense, and/or sell
>copies of the Software, and to permit persons to whom the
>Software is furnished to do so, subject to the following
>conditions:

>The above copyright notice and this permission notice shall be
>included in all copies or substantial portions of the Software.

>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
>EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
>OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
>NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
>HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
>WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
>FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
>OTHER DEALINGS IN THE SOFTWARE.
  
- 이 내용을 프로젝트 root디렉토리에 **[license.txt]({{ site.url }}/assets/license/license.txt)형태로 추가**시켜 놓아야 한다.
- **연도**는 해당 프로젝트의 마지막 배포 년도를 기입해야 한다.
- 마지막으로 프로젝트의 **모든 소스**에 아래와 같은 코멘트를 상단에 달아놓아야 한다.  

>Copyright (c) <연도> <저작권 소유자>
>See the file license.txt for copying permission.

위 코멘트는 **license.txt**와 같은 셈이다
