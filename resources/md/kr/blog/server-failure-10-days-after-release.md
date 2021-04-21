+++
date = "2017-05-27T21:03:08+02:00"
draft = false
title = "프로젝트 출시 10일 후 새벽 4시에 발생한 장애의 원인"
slug = "server failure 10 days after release"
description = "약 3년 반 전 2013년 겨울, 맡고 있던 모바일웹 프로젝트 출시 후 문제없이 돌아가던 서버에서 10일이 지난 새벽 4시 2분에 장애가 발생했다. 별다른 로그는 없었고 단서는 문제가 발생했을 때 스크린 캡처한 404 페이지 화면밖에 없었다."

+++

2013년 겨울, 맡고 있던 모바일웹 프로젝트 출시 후 10일이 지난 새벽 4시 2분에 장애가 발생했다. 출근했을 때에는 이미 서버를 재시작하여 복구된 상태였다. 별다른 로그는 없었고 단서는 문제가 발생했을 때 스크린캡처한 `404 Not Found` 페이지 화면 밖에 없었다. 원인을 밝혀내지 않으면 언제 다시 문제가 발생할지 모르는 상황이었다.

서버 구성은 다음과 같았다.

* 센트OS 서버 운영체제
* 엔진엑스 웹서버
* 스프링부트+제티 스탠드얼론 웹애플리케이션 서버 (이하 WAS)

당시까지만 해도 스프링부트(Spring Boot)가 베타 버전이었기 때문에 많이 사용되고 있지 않았고 스탠드얼론(Standalone) WAS 서버도 흔치 않았다. 나에게는 톰캣이 익숙했지만, 당시 그래들(Gradle)에는 스탠드얼론 서버를 운용할 수 있는 임베디드 톰캣 플러그인이 없었기 때문에 제티를 선택했다.

사내에서는 처음 시도된 방식이었고 서버구조나 운용방법도 새로웠다. 설정파일 관리, 배포방식, 스태이징(Staging) 서버 관리 등 기존 구조가 가지고 있던 많은 불편한 부분들을 해소할 수 있도록 설계하였지만 그만큼 위험도 있었다.

유일한 단서인 404 페이지를 유심히 보았다. 레이아웃이 깨어져 있었다. CSS와 이미지 파일 로딩이 안 된 것이다. 404 페이지의 HTML은 엔진엑스에서 제공되고 있었고 CSS와 이미지 파일은 WAS와 함께 있었다. 서버 에러가 생긴 것이 아니고 파일이 사라진 것이다.

파일이 갑자기 왜 사라졌을까. 지금 WAS 관련 파일들이 어디에 저장되고 있지? 조사 후 [명시적으로 WAR(Web application ARchive) 파일 압축을 풀 디렉터리를 설정하지 않으면 `/tmp/jetty` 디렉토리가 선택된다는 사실을 알게되었다](https://stackoverflow.com/questions/7011940/how-to-configure-a-webapps-deployment-directory-in-jetty).

그럼 센트OS에서 `/tmp` 디렉토리는 어떻게 관리되지?

[센트OS 6에서 `/tmp` 디렉토리의 10일 이상 된 파일들은 `tmpwatch` 명령어에 의해 삭제된다](https://unix.stackexchange.com/a/118794/8610). [`tmpwatch`](https://linux.die.net/man/8/tmpwatch)는 `cron.daily`의 일부로 들어가 있고, [`/etc/crontab` 파일을 살펴보면](https://www.centos.org/docs/5/html/5.2/Deployment_Guide/s2-autotasks-cron-configuring.html) 다음과 같다.

```bash
01 * * * * root run-parts /etc/cron.hourly
02 4 * * * root run-parts /etc/cron.daily
22 4 * * 0 root run-parts /etc/cron.weekly
42 4 1 * * root run-parts /etc/cron.monthly
```
