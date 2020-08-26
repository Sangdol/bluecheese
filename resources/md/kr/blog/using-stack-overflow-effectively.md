+++
date = "2012-05-30T17:10:42+09:00"
draft = false
title = "스택오버플로 활용하기"
slug = 'using-stack-overflow-effectively'

+++

> Done is better than perfect

저의 첫 포스트는 제가 좋아하는 사이트 중 하나인 스택오버플로(Stack Overflow)에 대한 소개로 시작하려 합니다.

스택오버플로는 개발자라면 누구나 잘 알고 있는 사이트이지만, 언어의 장벽 때문에 국내 개발자에게는 주로 읽기 활동만 하게 되는 사이트입니다. 저도 오랫동안 읽기만 하는 소극적 사용자였지만 [한 줄짜리 질문](http://stackoverflow.com/questions/6184869/what-is-difference-between-memoization-and-dynamic-programming)을 시작으로 자신감을 얻고 조금씩 활동하기 시작하여 지금은 어설픈 영어로 가끔 답변도 달고 질문도 올리며 어느정도 활용할 수 있게 되었습니다.

이번 글에서는 적극적 사용자로서 스택오버플로를 사용하며 느꼈던 시스템의 훌륭함과 다양한 기능을 소개해 보겠습니다.

![스택오버플로의 에러 페이지](http://farm8.staticflickr.com/7100/7234478862_665a8f85b4_c.jpg "스택오버플로의 에러 페이지")

### 들어가기

스택오버플로는 유명한 책 조엘 온 소프트웨어의 저자인 Joel Spolsky와 [지금은 스택오버플로를 떠난](http://www.codinghorror.com/blog/2012/02/farewell-stack-exchange.html) Jeff Atwood가 2008년 공동창업하여 만든 사이트입니다.

여기서 잠깐 짚고 넘어가야 할 부분이 있습니다. 회사를 시작하던 당시에는 스택오버플로라는 이름으로 시작했지만 [2011년 3월 회사 이름을 스택익스체인지(Stack Exchange)로 바꾸고](http://blog.stackoverflow.com/2011/03/a-new-name-for-stack-overflow-with-surprise-ending/) 카테고리별로 [수많은 사이트](http://stackexchange.com/sites)을 만들어 지금은 수십 개 이상의 크고 작은 스택익스체인지 사이트가 있습니다. 이 글에서는 스택익스체인지 사이트 중 가장 규모가 크며 국내 사용자에게 많이 알려진 스택오버플로를 기준으로 소개하도록 하겠습니다.

![스택익스체인지의 수많은 사이트](http://media.tumblr.com/tumblr_m4dn5z3Ob41qimauz.png "스택익스체인지의 수많은 사이트")

### 점수 시스템

스택오버플로의 [점수 시스템](http://stackoverflow.com/faq#reputation)은 다음과 같습니다.

<table><tbody><tr><td>답변이 추천받았을 때 </td>
      <td> +10</td>
      <td>
      </td>
    </tr><tr><td>질문이 추천받았을 때</td>
      <td> +5</td>
      <td>
      </td>
    </tr><tr><td>답변이 채택되었을 때</td>
      <td> +15</td>
      <td> (채택자에게 +2)</td>
    </tr><tr><td>질문이 반대되었을 때</td>
      <td> -2</td>
      <td>
      </td>
    </tr><tr><td>답변이 반대되었을 때</td>
      <td> -2</td>
      <td> (반대자에게 -1)</td>
    </tr></tbody></table>

단순하면서도 훌륭한 시스템이라고 생각됩니다. 무분별한 반대를 줄이기 위해 반대를 한 사람에게도 작은 페널티를 줍니다. 1점밖에 되지 않는 페널티이지만 [손실회피](http://en.wikipedia.org/wiki/Loss_aversion) 경향을 생각해 본다면 큰 영향력을 미치는 부분으로 보입니다.

### 동기부여

스택오버플로는 사용자의 활발한 활동을 유도하기 위해 다양한 방법으로 동기부여를 합니다. 그 중 대표적인 두 가지를 소개해 보겠습니다.

먼저 [배지 시스템](http://stackoverflow.com/badges)입니다. 다양한 배지를 이용하여 점수로 보상되지 않는 부분을 매워 점수와 직결되지 않는 활동도 적극 참여하도록 유도합니다. 특히 처음 활동을 시작할 때는 글을 쓰거나 추천하는 등의 간단한 활동만 해도 Student, Teacher, Supporter와 같은 이름으로 다양한 배지들을 부여하여 흥미를 유발합니다.

다음으로 점수에 따른 권한 부여입니다. 처음 가입 시 시작하는 1점 상태에서는 질문/답변을 쓰는 것 외에는 아무런 활동도 할 수 없습니다. 사이트의 [FAQ](http://stackoverflow.com/faq#reputation)에 "점수(reputation)란 커뮤니티가 얼마나 당신을 신뢰하고 있는 것인가에 대한 측정이다(how much the community trusts you)"라는 말이 있듯이 점수로써 신뢰를 얻기 전까지는 모든 활동이 제한됩니다. 15점이 되어서야 비로소 추천을 할 수 있고, 50점이 되어야 다른 사람의 글에 댓글을 달 수 있게 됩니다. 125점이 되기 전에는 반대를 할 수 없습니다. 1,000점이 되면 해당 글의 추천/반대의 수를 나누어서 볼 수 있게 되는 등 점수가 높아질 수록 강력한 권한을 얻게 됩니다.

![1,000점 이상이 되면 확인할 수 있는 추천/반대의 수](http://media.tumblr.com/tumblr_m4fgyaOYwg1qimauz.png "1,000점 이상이 되면 확인할 수 있는 추천/반대의 수")

### 자정작용

앞서 말한 배지나 점수 시스템은 사이트의 자정작용에도 큰 영향을 미칩니다. 스택오버플로를 사용하며 가장 훌륭하다고 생각했던 부분이 바로 놀라운 자정작용이었습니다.

일정 이상 점수가 되지 않은 사용자에게는 권한을 제한하여 스팸을 막습니다. 질문이나 답변에 문법적인 오류나 잘못된 내용이 있을 때는 위키피디아와 같이 [다른 사람이 수정할 수 있습니다](http://stackoverflow.com/faq#editing). 수정한 내용은 다른 사람의 리뷰를 통해 확정되고 확정되었을 때 수정한 사람에게 2점이 부여됩니다. 점수뿐만 아니라 Editor, Archaeologist, Copy Editor 등과 같은 다양한 배지로도 수정을 유도하여 글의 퀄리티를 유지합니다.

스택오버플로의 [엄격한 규칙](http://stackoverflow.com/faq#dontask)도 글의 자정작용에 한몫하고 있습니다. 사이트에서는 오로지 실용적이며, 실제적인 문제를 기반으로 한 답변 가능한 문제(practical, answerable questions based on actual problems that you face)만 질문하라고 하고 있습니다. 질문이 사이트의 성격과 맞지 않다고 생각되면 해당 질문은 여러 사람의 동의를 통해 가차 없이 [close](http://stackoverflow.com/faq#close)됩니다. 이와 같은 엄격한 규칙 때문에 아래의 질문과 같이 실제적으로는 유용하고 좋은 정보를 주며 인기도 있는 질문이 닫히는 경우도 종종 있습니다.

* [List of freely available programming books](http://stackoverflow.com/questions/194812/list-of-freely-available-programming-books)
* [What is the single most influential book every programmer should read?](http://stackoverflow.com/questions/1711/what-is-the-single-most-influential-book-every-programmer-should-read)
* [Strangest language feature](http://stackoverflow.com/questions/1995113/strangest-language-feature)
* [How can you program if you’re blind?](http://stackoverflow.com/questions/118984/how-can-you-program-if-youre-blind)

이와 같은 엄격한 규칙을 가진 이유에 대해서는 스택익스체인지의 블로그 글인 [Where We Hate Fun](http://blog.stackoverflow.com/2010/01/stack-overflow-where-we-hate-fun/)이라는 글에서 잘 설명되어 있습니다.

### 퀄리티 유지

앞서 설명한 사용자에 의해 이루어지는 자정작용으로도 글의 퀄리티가 높게 유지되지만 기능적으로도 글의 퀄리티나 신뢰도를 높이고 있습니다.

스택오버플로에서는 본인이 작성한 [질문이나](http://meta.stackoverflow.com/questions/25088/how-can-i-delete-my-post-on-stack-overflow) [답변도](http://meta.stackoverflow.com/questions/73765/how-can-i-delete-my-answer) 자유롭게 삭제하지 못합니다. 작성한 [댓글은 5분 이내에만 수정이 가능](http://meta.stackoverflow.com/questions/459/should-we-be-allowed-to-edit-comments)합니다. 추천/반대 후 15분이 지나면 해당 글이 수정되기 전까지는 취소할 수 없습니다. 이와 같은 기능들은 사용자에게 불편을 줄 수도 있는 부분이지만 사용자로 하여금 신중한 글쓰기와 선택을 유도하는 데에는 탁월한 방법입니다.

본인 질문에 본인이 답변 다는 것이 가능하지만, 채택은 질문을 올린 지 이틀이 지나야 가능합니다. 본인이 답변하고 채택했을 때에는 채택점수는 받을 수 없습니다.

![이틀 이내에는 본인 글을 채택할 수 없는 시스템](http://farm8.staticflickr.com/7228/7286605860_358e0d99cc_z.jpg "이틀 이내에는 본인 글을 채택할 수 없는 시스템")

### 그 외 스택익스체인지 사이트

다음은 스택오버플로 외에 종종 활용하는 스택익스체인지 사이트입니다.

* [Programmers](http://programmers.stackexchange.com)
* [English](http://english.stackexchange.com)
* [UX](http://ux.stackexchange.com/)
* [Apple](http://apple.stackexchange.com/)

Programmers Stack Exchange는 개발자를 위한 곳이라는 점에서는 스택오버플로와 같지만, 그 용도에서 [스택오버플로와는 다른 점](http://blog.stackoverflow.com/2010/12/introducing-programmers-stackexchange-com/)을 가집니다. 그 외의 것들은 개발과 직결되어 있진 않지만, 개발자로서 종종 활용하게 되는 곳들입니다.

### 적극적으로 스택오버플로를 활용해야 하는 이유

스택오버플로를 활용하기 시작한 것을 매우 잘한 일이었다고 생각합니다. 왜 조금 더 일찍 시작하지 못했나 하는 아쉬움이 들 정도입니다. 스택오버플로를 잘 활용하면 다음과 같은 세 가지 장점을 얻을 수 있습니다.

먼저 영어학습 효과입니다. 개발과 관련된 정보 대부분이 영어로 되어 있는 인터넷에서 [영어를 잘 못한다는 것은 개발을 잘 못한다는 것과 직결된다고 할 수 있을 정도로 개발자에게 영어는 중요합니다](http://stackoverflow.com/questions/461356/does-english-hinder-improvement-of-your-programming-skills). 프로그래밍 언어도 읽기만 하기보다는 쓰기도 함께해야 실력이 빨리 느는 것처럼 실제 언어도 읽기와 쓰기를 병행해야 빠른 실력향상을 기대할 수 있다고 생각합니다. 스택오버플로는 전공 분야의 영어 쓰기를 연습해볼 수 있는 좋은 장소입니다. 잘못 사용된 단어나 문법들은 다른 사용자들이 교정해주기도 하므로 무료로 첨삭지도까지 받는 효과도 누릴 수 있습니다.

다음으로 문제 해결 능력 향상입니다. 질문자로서 활동할 때 독특한 케이스의 문제가 아닐 때에는 대부분 답변이 잘 달리는 편이라 막혀있던 문제를 해결하는데 도움을 받을 수 있습니다. 정답이 올라오지 않더라도 단서가 될만한 내용을 알려주는 경우도 많으므로 대부분 기대 이상의 효과를 얻을 수 있습니다. 답변자로 활동할 때는 여러 사람이 겪는 다양한 문제를 살펴볼 수 있고 답변하기 위해 정보를 찾다가 새로운 내용을 알게 되는 때도 많습니다. 이것 역시 문제 해결 능력에 도움을 줍니다.

마지막으로 이력입니다. 질문/답변을 잘하여 좋은 점수를 가지게 되면 개인의 이력에 스택오버플로 활동 이력이 추가됩니다. 혹시 국외로 진출을 목표로 하고 있다면 더없이 좋은 이력 쌓기 방법이라고 볼 수 있습니다. 스택오버플로에서는 자체적으로 [이력서 관리 시스템](http://careers.stackoverflow.com/)도 제공하고 있습니다([GitHub의 프로젝트도 추가할 수 있습니다](http://blog.stackoverflow.com/2011/03/careers-2-0-now-does-github/)). 국내 IT 기업에서는 이것을 얼마나 고려할지 알 수 없지만, 개인의 영어실력과 개발실력을 입증할 수 있는 좋은 방법이라는 점에는 의심의 여지가 없습니다. 앞선 기업이라면 지원자를 판단하는데 GitHub이나 스택오버플로 같은 곳에서의 활동 이력을 고려할 것이며 앞으로 그와 같은 기업은 더욱 많아질 것으로 생각합니다.
