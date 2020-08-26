+++
date = "2017-09-08T04:53:40+02:00"
draft = false
title = "카페 네트워크 트러블슈팅"
slug = "network troubleshooting in a cafe"
images = ["img/network-dns-settings.png"]
description = "집 근처 카페에서 와이파이 연결 후 인터넷 접속을 하려는데 안 된다. 책이나 읽어야겠다고 생각하고 잠시 책을 보다가 지루해져서 왜 안 되는지 알아보기로 했다."

+++

집 근처 카페에서 와이파이 연결 후 인터넷 접속을 하려는데 안 된다. 책이나 읽어야겠다고 생각하고 잠시 책을 보다가 지루해져서 왜 안 되는지 알아보기로 했다.

아이피는 잘 할당되었나?

```shell
en0: flags=8863<UP,BROADCAST,SMART,RUNNING,SIMPLEX,MULTICAST> mtu 1500
  inet6 fe80::c88:d8dc:9818:3458%en0 prefixlen 64 secured scopeid 0x4
  inet 192.168.179.43 netmask 0xffffff00 broadcast 192.168.179.255
  nd6 options=201<PERFORMNUD,DAD>
  media: autoselect
  status: active
```

아이피가 잘 할당된 것을 보니 공유기가 동작은 하는 듯하다.

어디에서 막히는지를 볼까.

```shell
$ traceroute google.com
traceroute: unknown host google.com
```

호스트를 못찾는다. DNS 검색 시도.

```shell
$ dig google.com

; <<>> DiG 9.8.3-P1 <<>> google.com
;; global options: +cmd
;; connection timed out; no servers could be reached
```

DNS 서버로 접근 자체를 못하고 있다.

[구글 퍼블릭 DNS 서버](https://ko.wikipedia.org/wiki/%EA%B5%AC%EA%B8%80_%ED%8D%BC%EB%B8%94%EB%A6%AD_DNS)로 연결이 잘 되는지 확인.

```shell
$ ping 8.8.8.8
PING 8.8.8.8 (8.8.8.8): 56 data bytes
64 bytes from 8.8.8.8: icmp_seq=0 ttl=46 time=36.443 ms
64 bytes from 8.8.8.8: icmp_seq=1 ttl=46 time=34.619 ms
64 bytes from 8.8.8.8: icmp_seq=2 ttl=46 time=34.496 ms
```

DNS 서버로 연결은 되고 있다. 외부 인터넷으로 연결은 되고있다는 뜻이다. 문제는 DNS 검색. 네트워크 DNS 설정에 가보니 이전에 설정해놓은 구글 DNS 서버 IP들이 있다.

![네트워크 DNS 세팅](/img/network-dns-settings.png)

해당 목록을 제거하고 설정을 적용한 후 다시 인터넷에 접속하니 잘 된다.

### 왜 DNS 서버를 수동으로 설정했을땐 안 되었을까?

일부 네트워크에서는 특정 도메인으로 접근을 막는 등의 통제를 위해 또는 [DNS 스푸핑](https://ko.wikipedia.org/wiki/DNS_%EC%8A%A4%ED%91%B8%ED%95%91)과 같은 공격으로부터 보호하기 위해 외부 DNS 서버로의 접근을 막는 경우가 있다.

### 그렇다면 왜 특정(구글) DNS 서버를 사용하나?

[구글 DNS 서버는 빠르고 안전하며 쿼리에 대한 정확한 결과를 내어준다](https://blog.dnsimple.com/2015/03/why-and-how-to-use-googles-public-dns/).

2014년 터키 시위 당시 정부에서 시민들의 트위터로의 접근을 막기 위해 ISP의 DNS를 조작하였는데, 시민들이 구글 DNS를 이용하여 접속을 하기도 하였다. 정부는 결국 구글 DNS로의 접근도 막았다.

{{< img src="/img/dns-giraffiti.jpg" title="구글 DNS 아이피를 적은 그라피티" >}}

DNS 서버를 수동으로 설정해 놓는 것은 좋은 생각일 수 있다. 하지만 일부 네트워크에서는 문제가 될 수 있으니 주의해야한다.
