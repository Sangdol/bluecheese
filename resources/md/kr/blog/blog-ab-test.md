+++
draft = false
slug = "blog-ab-test"
description = "구글, 유투브, 페이스북, 인스타그램, 넷플릭스와 같은 서비스에 들어가는 순간 우리는 특정 테스트의 실험군 혹은 대조군에 들어가게 된다."
images = []
date = "2019-08-26T00:20:23+02:00"
title = "블로그 A/B 테스트 하기 "

+++

구글, 유투브, 페이스북, 인스타그램, 넷플릭스와 같은 서비스에 들어가는 순간 우리는 특정 테스트의 실험군 혹은 대조군에 들어가게 된다. [넷플릭스가 거의 모든 것을 A/B 테스트한다](https://www.quora.com/What-types-of-things-does-Netflix-A-B-test-aside-from-member-sign-up)는 것은 유명한 사실이며 [구글은 2009년 약 12,000개의 실험을 수행하였다](https://searchengineland.com/whos-afraid-of-the-big-bold-test-134569). 그들은 언제나 동시에 여러가지의 테스트를 진행 중이다.

우리 팀에서도 마케팅 퍼포먼스나 웹 UI를 개선하기 위해 A/B 테스트를 사용하고 있다. 머신러닝을 통한 마케팅 최적화 작업을 진행한 적이 있는데 마찬가지로 A/B 테스트를 통해 결과를 확인하였다.

A/B 테스트에 대한 이해는 가지고 있지만 팀에서 일할 때에는 데이터 사이언티스트나 애널리스트가 있어 A/B 테스트 결과를 직접 분석하진 않았다. A/B 테스트에 대한 좀 더 직접적인 경험을 가져야겠다는 생각에 블로그에 A/B 테스트를 진행해 보기로 하였다.

약간의 조사를 통해 [구글 옵티마이즈](https://optimize.google.com)를 발견하였는데 사용법을 익히고 세팅을 거치고 나면 A/B 테스트를 실행하는데 10분도 걸리지 않으면서 다양한 기능을 제공하는 만족스러운 툴이었다.

{{< img src="/img/google-optimize.png" title="구글옵티마이즈 UI" >}}

내가 경험한 구글 옵티마이즈의 장점은 다음과 같다.

* 위지위그(WYSIWYG) 웹 UI를 통해 코딩 없이도 A/B 테스트 설정이 가능하다.
* 웹 UI를 통해 실험군과 대조군을 쉽게 확인 할 수 있다.
* 구글 애널리틱스와 연동된다.
* 테스트에 대한 알림을 이메일로 받아 볼 수 있다.
* 테스트 결과분석을 해준다.

결과분석은 직접해보고 싶었던 부분이었는데 다 알아서 결과를 보여주니 오히려 아쉽기도 했다. 현재 페이지 하단에는 이전 글목록이 들어가 있다.

**첫 번째 테스트: 이전 글 목록**

세팅 후 세 가지 테스트를 실행하였는데 그 중 첫 번째는 글 하단의 "이전 글"이다. 가설은 각 글의 하단에 이전 글 목록을 추가하면 방문자들이 글을 읽고 바로 떠나지 않고 다른 글을 읽을 가능성이 높아지니 페이지뷰 수가 증가하고 이탈수(bounces)가 감소할 것이라는 것이었다. 따라서 구독자 수도 증가하지 않을까하는 기대도 있었다. 여기에서 이전 글 목록의 유무가 _대안_(variant)이며  페이지뷰 수, 이탈수, 구독수는 목표가 된다.

기본으로 선택할 수 있는 목표는 이탈수, 페이지뷰 수, 세션의 길이이다. 구독수는 구글 애널릭티스에서 목표로 설정했던 것이었는데 추가적인 설정 없이 구글 옵티마이즈에서 선택이 가능했다. 무료버전에서는 3개까지의 목표 설정이 가능하므로 세션의 길이를 제외한 세 가지를 목표로 설정하였다.

결과는 예상하였던대로 이전 글 목록을 추가하였을 때 구독자 수와 페이지뷰가 증가하였고 이탈수가 감소하였다.

**두 번째 테스트: 프로필 사진**

두 번째 테스트는 상단 프로필 사진 유무에 따른 사용자 행동 차이였다. 블로그를 리디자인하며 선택했던 템플릿의 상단에 프로필 사진을 넣는 영역이 있어 사진을 넣어 놨는데 그것이 방문자에게 어떤 영향을 미치는지는 알 수 없었다.

결과는 방문자들이 나의 사진을 좋아하지 않는 것으로 나타났다. 테스트 기간 동안 총 12명의 새 구독자가 생겼는데 그 중 10명이 사진이 없을때 구독을 하였다.

사진을 넣어 신뢰를 줄 수 있지 않을까 생각하였는데 오히려 전문성이 없어 보이게하는 역효과가 난것이 아닐까 하고 추측해본다. 지나서 생각해보니 글 옆에 있는 사진을 보고 신뢰를 더 얻었던 경우보다는 왜인지 더 신뢰가 가지 않았던 경우가 더 많았던 것 같다.

A/B 테스트를 진행 후에는 결과가 우연으로 발생했을 가능성을 측정하기 위한 통계적 유의성(statistical significance)을 계산하는데 샘플의 수가 많거나 두 변수간 결과의 차이가 클수록 높은 값을 얻을 수 있다. 예를 들어 A에서 5명이 구독하고 B에서 6명이 구독했을 경우에는 우연으로 B가 높게 나왔을 가능성이 높지만 A에서 500명이 구독하고 B에서 600명이 구독했다면 같은 비율차이이더라도 더 높은 통계적 유의성을 얻을 수 있다. 같은 샘플 수의 경우 5:6일 때보다는 1:10과 같이 두 결과 값의 차이가 클수록 통계적으로 유의미한 결과를 얻을 수 있다 (수학적 설명이 궁금하다면 [위키피디아의 허용 오차 페이지](https://ko.wikipedia.org/wiki/허용_오차)를 살펴보는 것도 좋다.)

실험 결과로 나온 구독자 수 10 대 2의 경우 97%의 통계적 유의성을 얻을 수 있었으며 이것은 더 많은 사용자들이 사진을 좋아하지 않는다는 결론이 우연에 의해 내려졌을 가능성이 3%라는 것을 뜻한다. 이때 발생하는 오류를 1종 오류(Type I error)라고 하며 이것은 긍정오류(false positive)이기도 하다. (항상 False positive라는 용어만 써오다 글을 쓰기 위해 긍정오류라는 단어를 검색을 통해 처음 보았다. 실제 커뮤니케이션에서 긍정오류라는 말이 잘 쓰이는지는 모르겠다.)

실험 결과를 낼 때 p값이라는 것을 이용하는데 보통은 0.05(5%)로 설정한다. 이 말은 95% 이상의 통계적 유의성이 나올 경우 통계적으로 유의하다는 결론을 내린다는 뜻이며 실험이 20번 중 1번의 확률로 우연히 유의미한 결과가 나올 수 있다는 뜻이다.

만약 실험 전 내가 p 값을 0.01로 잡았다면 이 결과를 유의미하게 받아들이지 않았을 것이다. 하지만 실제로는 더 많은 방문자들이 내 사진을 좋아하지 않는다면 이것은 잘 못된 결론을 내리게 되는 것이고 이것을 2종 오류 (Type II error) 또는 부정오류(false negative)라고 한다.

**세 번째 테스트: "이상현 in 베를린" vs. "이상현 블로그"**

[블로그를 리디자인하며](https://iamsang.com/blog/2017/05/22/blog-rebranding-and-from-tumblr-to-github-page-and-hugo/) 블로그 이름을 "이상현 in 베를린"으로 변경하였다. 해외 취업이나 베를린과 관련한 내용을 다룰 계획이 있었고 블로그명만으로 더 많은 정보를 제공할 수 있다고 생각하였기 때문에 괜찮은 이름이라고 생각하였지만 실제로 방문자에게 어떤 영향을 주는지는 알 수 없었다.

결과는 "이상현 in 베를린"일 경우 페이지뷰가 더 높았다. 블로그에 글의 많지 않기 때문에 지속적인 방문자는 적어 보통 새 글을 쓸 경우 방문자가 발생하는데 해당 실험 중 새 글을 쓰지 않아 통계적으로 유의미한 값을 얻지는 못하였다.

단순히 샘플의 수가 많다고 결과를 신뢰할 수 있는 것은 아니다. 미국에 사는 친구가 트럼프 당선 후 이런 이야기를 한 적이 있다. "주변에서 트럼프 뽑는다는 사람은 한명도 못 봤는데…" 주변에서 볼 수 있는 사람의 수가 적은 것도 문제이겠지만 이 경우에는 샘플의 대표성이 부족한 것이 예상 밖의 결과를 보게 된 이유이다. 내 주변에는 나와 비슷한 생각을 가진 사람이 많을 가능성이 높으므로 주변 사람들의 의견은 전체를 대표하기 어렵다.

블로그 A/B 테스트 같은 경우에는 특정 기간동안 블로그에 방문하는 사람들이 샘플이 되는데 그들 중 대부분이 특정 주제의 글 한 두 개를 보고 들어왔다면 과연 전체 방문자를 대표할 수 있는지 의심해볼 필요가 있다.

**유용한 자료들**

[유다시티에 구글이 만든 무료 A/B 테스트 강의](https://eu.udacity.com/course/ab-testing--ud257)가 있다. Lesson 1부터 통계학 지식을 요구하므로 기본적인 통계학 지식이 있거나 통계학을 다른 자료를 통해 공부하면서 볼 생각이라면 볼만하다. (영문)

통계적 유의도를 얻기 위해 얼마나 많은 샘플 사이즈가 필요한지 쉽게 계산하고 싶다면 [Sample Size Calculator](https://www.abtasty.com/sample-size-calculator/) 페이지에서 가능하다. 기본적인 용어에 대한 설명도 제공한다. (영문)

A/B 테스트에 대한 전반적인 지식을 얻고 싶다면 "A/B 테스트를 통한 웹사이트 전환율 최적화"를 추천한다. 전반적인 개념과 방법론을 설명하기 때문에 통계학적 지식없이도 인사이트를 얻기에 좋은 책이다.