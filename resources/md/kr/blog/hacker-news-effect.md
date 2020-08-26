+++
date = "2013-04-02T17:10:42+09:00"
draft = false
title = "해커뉴스 이펙트"
slug = 'hacker-news-effect'

+++

> 여러분이 무엇을 진정으로 사랑하지 않는다면 그것을 정말로 잘해낼 수 없다. 마찬가지로 해킹을 정말로 좋아한다면 뭔가 자기 자신의 프로젝트를 수행하지 않고는 견딜 수가 없는 것이다. - 폴 그레이엄

해커뉴스는 사용자가 게시물을 올리는 <a href="http://en.wikipedia.org/wiki/Social_news">소셜 뉴스</a> 웹사이트로 폴 그레이엄이 운영하는 사이트이다. 폴 그레이엄은 2009년에 자신이 작성한 글 <a href="http://www.paulgraham.com/hackernews.html">What I've learned from hacker news</a>에서, 해커뉴스가 2007년 <a href="http://en.wikipedia.org/wiki/Arc_(programming_language)">Arc</a> 실력을 향상하기 위한 사이드 프로젝트로 시작한 사이트라고 소개한다. 개인적으로는 최신기술이나 뉴스를 쉽게 찾아볼 수 있고, 그에 대한 사람들의 의견을 볼 수 있어 자주 찾아보는 사이트이다.

해커뉴스 이펙트라는 용어가 있다. 해커뉴스에 올린 블로그 글이나 오픈소스 프로젝트 등이 상위권에 머물게 되면서 얻는 효과를 말한다. "Hacker news effect"로 <a href="https://www.google.com/search?q=hacker+news+effect&aq=f&oq=hacker+news+&aqs=chrome.0.59j57j65j59j60j59.3321&sourceid=chrome&ie=UTF-8">구글링</a>을 해보면, 다양한 경험담을 볼 수 있다.

대표적인 것으로 <a href="https://medium.com/open-source/7d4fa461a9">List.js</a>가 있다. 해커뉴스에 다섯 시간 동안 1위에 머물러 있었을 뿐이었는데도 45시간 이내에 아래와 같은 인기를 얻었다.

* 27,500명의 방문자
* 269개의 트윗
* GitHub에서 439명의 Watcher와 21개의 fork

해커뉴스가 없었다면 이렇게 빠른 속도록 인기를 얻지는 못했을 것이다.

이렇게 강력한 힘을 가지고 있는 해커뉴스지만, 생각보다 대단치 않은 콘텐츠들이 올라오는 경우도 종종 있다. <a href="https://news.ycombinator.com/item?id=1781013">해커뉴스의 알고리즘</a>을 보면, 게시물의 점수는 시간에 따라 급격히 낮아지고 하루가 지나고 나면 아무리 추천을 많이 받았더라도 낮은 점수를 가지기 때문에 게시물이 빠르게 순환되는데, 이것이 그 이유가 될 수 있다. 게시물의 시간에 따른 점수의 변화는 <a href="http://www.wolframalpha.com/input/?i=plot%28+++++%2830+-+1%29+%2F+%28t+%2B+2%29%5E1.8%2C++++++%2860+-+1%29+%2F+%28t+%2B+2%29%5E1.8%2C+++++%28200+-+1%29+%2F+%28t+%2B+2%29%5E1.8+%29+where+t%3D0..24">Wolfram Alpha</a>에서 그래프로 확인할 수 있다(여기서 점수는 해커뉴스 사이트에 나타나는 점수가 아닌 내부적으로 계산되는 점수를 말한다).

![시간에 따른 게시물의 점수 변화](http://media.tumblr.com/7fafef383f8da00374e8767b7613cb21/tumblr_inline_mktwcayTmc1qz4rgp.png "시간에 따른 게시물의 점수 변화")


번역 API가 필요한 사이드 프로젝트를 만들어 보려는데, 구글 번역 API가 유료라는 사실을 알게 되었다. 재미로 만드는 사이드 프로젝트를 위해 돈을 내고 싶지는 않았다. 다른 방법이 없나 찾던 중 구글 스프레드시트 API를 이용한 꼼수를 사용하면 구글 번역 API를 만들 수 있다는 사실을 알게 되었고, 루비로 간단히 구현해 <a href="https://github.com/Sangdol/free-and-slow-google-translate-api">GitHub에 올리고</a>, <a href="http://google-translate-api.herokuapp.com/translate?from=en&to=ko&text%5B%5D=hi,%20how%20are%20you?&text%5B%5D=i'm%20fine,%20thank%20you&callback=test">Heroku에 띄웠다</a>.

내 GitHub 계정에 방문하는 사람은 아무도 없었기 때문에, 해당 프로젝트에 관심 가지는 사람 역시 없었다. 그러던 중 해커뉴스에나 한번 올려볼까 하는 생각이 들어 Readme를 조금 다듬은 뒤 올려보았다. 그냥 묻혀버리지 않을까 생각했는데 결과는 의외였다. 한 시간쯤 지나니 4 points라는 낮은 점수로 첫 화면에 진입했고 2시간쯤 되었을 때에는 15위까지 올라갔다. 실시간으로 계속 보진 않았지만 아마 15위가 최고였던 듯하고, 그렇게 조금씩 오르락내리락하다 약 8시간쯤 후에는 첫 페이지에서 완전히 밀려났다.

![해커뉴스에서의 순위](http://media.tumblr.com/9f174d2ec96961621478586dcabdbdd2/tumblr_inline_mktwy6S5em1qz4rgp.png "해커뉴스에서의 순위")

그 결과 하루 동안 GitHub에서 20여 개의 Star를 받고 1개의 Full request를 받았다. 해커뉴스 이펙트라는 이름을 붙이기에는 초라한 결과지만, 오픈소스 프로젝트에 참여한 적도 없고, 별다른 인지도도 가지고 있지 않은 나에게는 만족스러운 성과이다.

이 경험을 통해 두 가지 생각이 들었는데,

1. 영어는 필요조건이며,
2. 실력만 있다면 세상에 인정받을 수 있다라는 것.

만든 건 보잘것없는 간단한 프로젝트지만, 가능성을 볼 수 있게 해준 좋은 경험이었다. 흥미로운 토이 프로젝트를 만들었는데 아무도 관심가져주지 않는다면 해커뉴스에 한번 올려보는 것도 괜찮겠다.
