+++
draft = false
slug = "corona-project"
date = "2021-08-25T22:20:00+02:00"
title = "유럽 코로나 확진자 수 프로젝트 개발/실패기"
images = ["img/corona-project-stat.png"]

+++

작년 7월 유럽의 COVID-19 상황을 모바일 폰에서 한눈에 보고 싶어 [Europe COVID-19 Cases](https://europecorona.com/) 사이트를 출시하였다.

"이런 사이트가 있으면 좋겠다. 나에게 필요하다면 다른 사람에게도 필요하지 않을까"라는 생각에 만들게 되었다. 지금도 개인적으로는 잘 사용하고 있지만 현재 사이트 방문자는 하루 5명 이내이다.

Europe COVID-19 Cases 사이트를 개발하며 데이터는 어디서 받을 수 있었는지 홍보는 어떻게 하였는지,  왜 실패인지, 어떤 것을 배울 수 있었는지에 대해 공유해보려고 한다.

<img src="/img/corona-project-stat.png" alt="유럽 코로나 확진자 수 프로젝트 방문자 수" />

개발을 위해 사용한 기술과 플랫폼은 다음과 같다.

* Python (Jupyter, Pandas): 데이터 분석
* Clojure: 데이터 처리, 사이트 생성
* [Tabulator](http://tabulator.info/): 테이블 UI
* [Netlify](https://www.netlify.com/): 정적 사이트 호스팅
* [NameSilo](https://www.namesilo.com/): 도메인 호스팅
* 라즈베리 파이: 크론잡 통해 정적 서버 생성 및 배포

서버 유지비용은 도메인 비용인 약 연 $9 정도이다. 닷컴 도메인이 아니었다면 $2~3 정도의 싼 도메인도 가능했는데 혹시 많은 사람들이 방문하는데 도움이 되지 않을까라는 헛된 꿈을 꾸고 닷컴 도메인을 구매하였다. 한달에 500원정도 더 내는 건 감당할만하다고 생각하였다.

프로젝트를 시작하며 데이터를 찾는데 많은 시간이 걸렸다. 공개된 API들이 있었지만 안정적으로 운용되는 곳은 없었다. [존스 홉킨스 대학에서 제공하는 데이터](https://github.com/CSSEGISandData/COVID-19)도 있었지만 상업적으로는 사용할 수 없다는 조건이 있었다. 프로젝트를 상업화할 가능성은 낮다고 생각했지만 처음부터 그 가능성을 없애고 싶진 않았다. 이후 [Our World in Data](https://ourworldindata.org/)라는 서비스를 찾을 수 있었다. 출처 표기만 있으면 데이터를 원하는 용도로 사용할 수 있었다. (알고보니 해당 서비스는 해커뉴스를 만든 폴그레이엄이 운영하는 투자기업 Y Combinator의 지원을 받는 스타트업이었다. 코로나와 관련된 데이터 뿐 아니라 건강, 인구, 식품 등 다양한 분야에 대한 데이터를 제공한다.)

<img src="/img/corona-project-architecture.png" alt="유럽 코로나 확진자 수 프로젝트 아키텍처" />

개발 후 홍보를 위해 처음 이용한 웹사이트는 [인디해커](https://www.indiehackers.com/)였다. 인디해커는 수익성이 될만한 사이드 프로젝트를 개발하는 사람들이 모인 커뮤니티이다. 한 개발자가 시작한 사이트인데 이후 Stripe에 인수되었다. 서로에 대해 지지적인 회원들로 이루어져 있는 것으로 알려져있다.

기본적인 기능으로 출시 한 뒤 반응을 보고 싶었기 때문에 인디해커에 글을 올렸을 때는 지금보다 적은 기능을 가지고 있었다. 한 사람이 정성스러운 피드백을 남겨주었고 덕분에 사이트를 개선할 수 있었지만 별다른 호응은 없었다.

다음으로 홍보를 시도한 곳은 레딧이었다. [Germany 서브레딧](https://reddit.com/r/germany)에 올린 글은 자동으로 삭제되었다. 몇몇 곳은 Karma의 부족으로 글 자체를 올릴 수가 없었다. [Europe 서브레딧](https://reddit.com/r/europe)과 [Webdev 서브레딧](https://www.reddit.com/r/webdev)에 올렸을 때는 몇몇 친절한 사람들이 지지적인 댓글을 달아주었다. 하지만 큰 호응은 없었다.

[해커뉴스](https://news.ycombinator.com/)의 Show HN 코너에도 올려보았다. 별 것 아니지만 꽤 긴장되는 경험이었다. 글은 결국 한 명의 추천을 받았다. 혹시 타이밍이 안 좋았던게 아닐까 하고 얼마 후 글 제목을 조금 바꾸어 다시 올려보았다. 해커뉴스에 글을 쓸 때 타이밍과 제목에 따라 같은 링크라도 추천 수가 크게 차이가 난다는 경험적인 글들이 있으며 [머신러닝을 이용해 제목을 A/B 테스트 해주는 사이트](https://www.hacker-ai.com/)도 있다. 제목을 더 짧게하여 다른 시간대에 다시 한번 올려보았을 때는 추천율이 100% 올라 두 명의 추천을 받을 수 있었다.

MVP (Minimum Viable Product)를 만든 후 반응이 좋으면 기능을 늘려나가고 사용자가 많아지면 수익화도 가능하지 않을까라는 생각이었는데 이쯤되니 그냥 좋은 추억으로 남겨야겠다는 생각이 들었다. 덕분에 Clojure를 실용적으로 사용해 볼 수 있었고 그 경험을 통해 [커스텀 정적사이트를 개발하여 블로그를 개편](https://iamsang.com/blog/2020/10/10/custom-static-site-generator/)할 수 있었다. 사용자를 확보할 수 있는 제품을 개발하는 것이 얼마나 어려운 것인지도 느꼈다.

Clojure를 이용해 만든 첫 프로젝트라 코드는 엉망이지만 [깃헙](https://github.com/Sangdol/corona-project)에 올려놓았다.