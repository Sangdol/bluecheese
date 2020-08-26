+++
date = "2014-11-22T17:10:42+09:00"
draft = false
title = "ack: 개발자용 grep"
slug = 'ack'

+++

[ack](http://beyondgrep.com/)는 beyond grep이라는 부재를 가지고 있는 툴로 grep의 발전된 버전이다. 패키지 매니저를 통해 [다양한 OS에서 쉽게 설치 가능한데](http://beyondgrep.com/install/), OSX에서는 brew를 이용해 설치 가능하다.

<pre class="prettyprint">
$ brew install ack
</pre>

grep 대신 ack를 사용하는 이유는 여러가지가 있는데, 먼저 간편한 사용법이 있다. 프로젝트에서 grep을 이용해 'tooltip'이라는 단어를 검색하려면 아래와 같이 옵션과 디렉토리를 인자가 필요한데

<pre class="prettyprint">
$ grep -rn tooltip .
</pre>

ack를 이용하면 아래와 같이 간단히 할 수 있다.

<pre class="prettyprint">
$ ack tooltip
</pre>

여기서 ack는 기본적으로 `.git`, `.svn`과 같은 디렉토리 등을 무시하기 때문에 해당 폴더가 있는 경우에도 따로 처리해 줄 필요가 없다.

ack도 다양한 옵션을 제공하고 있어 필요에 맞게 쓰려면 옵션 값을 넘겨줘야한다. 하지만 ack는 [rc 파일](http://stackoverflow.com/questions/11030552/what-does-rc-mean-in-dot-files)을 통한 환경변수 설정이 가능해 상시 쓰는 옵션들을 넣어둘 수 있다. 예를 들어 결과 내용에 색을 넣어주는 `--color` 옵션, 결과를 페이징해서 볼 수 있게 해주는 `--pager` 옵션, 특정 디렉토리를 무시하는 `--ignore-dir` 옵션 등을 넣어둘 수 있다. rc 파일은 `~/.ackrc` 위치에 두면 된다. rc 파일 설정이 귀찮다면 일단 [남의 ackrc 파일](https://github.com/Sangdol/prezto/blob/master/runcoms/ackrc)을 복붙하고 약간 손봐서 사용하는 것도 좋은 방법이다.

ack를 사용하면 검색 결과를 한눈에 볼 수 있어 개발 중 가장 많이 사용하는 명령어 중 하나인데, `ABC` 옵션을 사용하면 좀 더 유연한 활용이 가능하다. `A`는 after, `B`는 before는 `C`는 after+before로, 검색된 단어의 위아래 줄들을 함께 보고 싶을 때 사용할 수 있다. 예를 들어 TODO가 어떤 코드에 붙어 있는지 보고 싶다면 아래와 같이 하면 된다.

<pre class="prettyprint">
$ ack -A3 TODO
</pre>

![bootstrap 프로젝트에서 TODO 검색](https://31.media.tumblr.com/168894b87329005cb3f354a5facefa68/tumblr_inline_nfhwolJm0n1qimauz.png)

명령어와 옵션 사용에 익숙해지면 더 다양한 활용이 가능하다. 예를 들어 'console'이라는 문자열을 'TODO'를 포함한 파일들 중에서만 검색해보고 싶다면 아래와 같이 할 수 있다.

<pre class="prettyprint">
$ ack console $(ack -l TODO)
</pre>
