+++
date = "2013-12-02T17:10:42+09:00"
draft = false
title = "개발일정 산정하기"
slug = 'software-estimation'

+++

> Hofstadter's Law: It always takes longer than you expect, even when you take into account Hofstadter's Law.

얼마 전 [AngularJS 1.2.0이 릴리즈 되었다](http://blog.angularjs.org/2013/11/angularjs-120-timely-delivery.html)는 반가운 소식을 들었다. 버전 1.2.0의 코드명은 timely-delivery. 과연 AngularJS 1.2.0의 출시는 timely 했을까.

AngularJS 팀은 2012년 6월 1.0.0 버전을 출시하고 7월 [1.2.0으로의 로드맵을 발표](http://blog.angularjs.org/2012/07/angularjs-10-12-roadmap.html)했다. 당시 예정된 1.2.0의 릴리즈 시점은 2012년 9월 초·중순. 개발기간을 3개월 정도로 잡았다. 실제 출시일은 2013년 11월로 약 17개월 정도 소요되었으니, 처음 예상했던 기간의 6배 정도가 걸린 셈이다. 코드명 timely-delivery는 이런 상황을 역설적으로 표현한 게 아닐까 생각된다. 부끄러움을 능청스러움으로 극복했달까.

AngularJS 개발자들의 예측이 이렇게 심각하게 빗나간 이유는 뭘까. 분명 다들 개발을 해볼 만큼 해봤을 베테랑 개발자들일 텐데 말이다. 늦어진 정확한 이유야 알 수 없지만, 개발일정 산정의 어려움을 보여주는 좋은 예라고 볼 수 있겠다. (며칠 전 [AngularJS 블로그에 1.2 버전이 늦어진데에 대한 회고](http://blog.angularjs.org/2013/11/on-launching-angular-12-what-we-learned.html)가 올라왔다.)

개발일정 산정은 왜 이렇게 어려운 것일까. Quora에 소프트웨어 개발은 왜 항상 예상보다 2~3배 이상의 시간이 걸리는가에 대한 질문이 있는데, 해당 스레드에는 [압도적으로 많은 수의 추천을 받은 답변](http://www.quora.com/Engineering-Management/Why-are-software-development-task-estimations-regularly-off-by-a-factor-of-2-3/answer/Michael-Wolfe?srid=24b&share=1)이 있다. 해당 글에서는 프로젝트 진행을 도보 여행에 비유하여 설명하는데, 전국지도와 같이 축적이 작은 지도를 보고 "단순거리 / 하루 걸을 수 있는 양"으로 계산한 예상 일정과 실제 걸으면서 만나는 예상치 못한 상황을 지나면서 걸리는 일정은 상당히 차이가 날 수밖에 없다는 것이 핵심 내용이다.

[Reddit Programming에도 Quora의 해당 글이 올라](http://www.reddit.com/r/programming/comments/1i1vlc/an_absolutely_brilliant_analogy_as_to_why/) 큰 호응을 얻었었는데, 여기에 달린 댓글들을 보면 일정산정의 어려움에 대한 개발자들의 수많은 개드립을 감상할 수 있다.

Programmers Stack Exchange에는 ["개발일정에 대해 질문받았을 때에 어떻게 답변해야 하는가?"](http://programmers.stackexchange.com/questions/648/how-to-respond-when-you-are-asked-for-an-estimate)에 대한 질답이 있다. 그에 대해 가장 많은 추천을 받은 답변은 실용주의 프로그래머의 추정(Estimating) 챕터를 그대로 요약한 글이다. 해당 답변을 번역하여 옮겨본다면 아래와 같다.

<hr>

> **일정산정 요청을 받으면 뭐라고 답해야 하나**
>
> "나중에 다시 알려 드리겠습니다."이라고 말하라.
>
> 일정산정 과정을 느리게 하고 이 장에서 이야기한 일정산정 단계들에 많은 시간을 들일수록 더 정확한 결과를 얻을 수 있다. 커피 자판기 앞에서 산정된 일정은 (커피처럼) 너를 따라와 괴롭힐 것이다.

이 장에서 저자는 다음의 과정들을 추천한다:

* 필요한 정확도를 정하라. 기간에 따라 다른 정확도로 일정산정이 가능하다. "5~6개월"이라고 말하는 것과 "150일"이라고 말하는 것은 다르다. 일정이 6개월을 약간 넘기더라도 추정은 여전히 꽤 정확한 것이다. 하지만 180일이나 210일 정도가 걸렸다면 그다지 정확하지 않은 것이다.
* 무엇을 질문 받은 것인지 확실히 하라. 문제의 범위를 정하라.
* 시스템을 모델링하라. 모델은 멘탈 모델, 다이어그램 또는 존재하는 데이터가 될 수 있다. 이 모델들을 분해하여 구성요소들로부터 추정하라. 값과 오차범위(+/-)를 할당하라.
* 당신의 추정을 추적하라. 추정하고 있는 문제, 추정 및 실제적이 값들에 대한 정보를 기록하라.
* 그 외 추적할 것들에는 요구사항, 위험 및 유효성검사가 있다.
