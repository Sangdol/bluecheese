+++
draft = false
slug = "redis-cache"
date = "2021-10-24T20:15:00+02:00"
title = "(웹툰) Redis 캐시"
images = ["img/redis-cache.png"]

+++

<img src="/img/redis-cache.png" alt="(웹툰) 레디스 캐시" />

### 스크립트

Redis는 뛰어난 성능과 쉬운 사용법으로 가장 사랑받는 key-value 저장소로 캐시 저장소로 주로 사용된다.

Redis가 빠른 이유는 in-memory 데이터 저장소를 통해 데이터를 제공하기 때문이며 스택오버플로의 Redis는 하루 37.5억 쿼리를 처리하며 단 1%의 CPU만을 이용한다.

"각 서버의 메모리에 데이터를 캐시해 놓으면 더 빠른데 왜 Redis를 쓰나요?"

Good point.

대략 서버 재시작 시 어떻게 캐시를 로드할지, 각 서버 캐시의 데이터 불일치를 어떻게 해결할지, 캐시 사이즈가 커지면 어떻게 감당할지 정도 문제만 해결한다면 괜찮은 생각이지.
