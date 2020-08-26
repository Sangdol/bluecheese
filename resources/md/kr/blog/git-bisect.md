+++
date = "2014-03-02T17:10:42+09:00"
draft = false
title = "Git bisect를 이용하여 버그발생시점 찾아내기"
slug = 'git-bisect'

+++

버그가 발생했는데 도저히 무엇이 원인인지, 언제 어떤 부분에서 이루어진 수정으로 인해 문제가 발생했는지 감을 잡을 수 없을 때가 있다.

이런 상황에서 활용할 수 있는 것이 <a href="http://git-scm.com/book/en/Git-Tools-Debugging-with-Git#Binary-Search">git bisect</a>이다. `git bisect`는 이진검색을 이용해 버그 발생 시점을 찾아내는 방법이다. 원인을  예상할 수 없는 문제가 발생했을 때, 의심되는 파일의 코드를 반씩 잘라내가며 수동 이진검색 디버깅을 했던 적이 있는데, `git bisect`를 잘 활용하면 이런 수고를 덜 수 있다.

사용법은 간단하다. 아래와 같은 커밋이 있다고 하자. 문제 발생 시점은 커밋 4이고, 현재 위치는 커밋 6이다.

```
.. - 0 - 1 - 2 - 3 - 4* - 5 - (6)
```

`git bisect`를 이용해 문제 위치를 찾아보자. 먼저 문제가 없던 지점을 대략적으로 찾는다. 커밋 0에서는 문제가 없다고 확인되었을 때 다음과 같이 명령을 날린다.

<pre class="prettyprint">
$ git bisect start
$ git bisect bad
$ git bisect good 0
Bisecting: 2 revisions left to test after this (roughly 2 steps)
[3] <comment></comment></pre>

```
.. - 0 - 1 - 2 - (3) - 4* - 5 - HEAD
```

`git bisect` 명령을 통해 현재 시점과 커밋 0의 중간 지점인 커밋 3 시점으로 이동되었다. 커밋 3에서는 문제가 재현되지 않을테니 다음과 같이 good 명령을 날린다.

<pre class="prettyprint">
$ git bisect good
Bisecting: 0 revisions left to test after this (roughly 1 step)
[5] <comment></comment></pre>

```
.. - 0 - 1 - 2 - 3 - 4* - (5) - HEAD
```

이번에는 커밋 5로 이동되었다. 여기서는 문제가 재현될테니 bad를 날린다.

<pre class="prettyprint">
$ git bisect bad
Bisecting: 0 revisions left to test after this (roughly 0 steps)
[4] <comment></comment></pre>

```
.. - 0 - 1 - 2 - 3 - (4*) - 5 - HEAD
```

여전히 문제가 재현되니 다시 bad.

<pre class="prettyprint">
$ git bisect bad
[4] is the first bad commit
</pre>

자, 이제 문제 발생 시점을 찾아냈다.

작업이 완료된 후에는 다음과 같이 reset 명령어를 통해 HEAD가 원래 위치로 돌아갈 수 있게 해준다.

<pre class="prettyprint">
$ git bisect reset
</pre>

찾아야하는 커밋의 범위가 넓을 수록 해당 기능의 힘을 제대로 느낄 수 있다. N개의 커밋이 있을 때, <code>1 + log<sub>2</sub>N</code> 번 이하의 테스트만으로 문제 발생 커밋을 찾아내는 것이 가능하다.

이 방법을 50개 정도의 커밋들 사이에서 여섯 스탭 정도만에 원인을 찾아낼 수 있었다. 쉘 스크립트를 활용하면 문제 발생 시점 찾는 것을 자동화하는 것도 가능하다.
