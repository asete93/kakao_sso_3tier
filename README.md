
# KakaoLogin
1. Ubuntu 22.04 기준으로 작성되었음.
본 프로젝트에서는 하위 3 Tier 구조로 KakaoLogin 인증 기능을 구성합니다.

Frontend - React (Next.js 14) - node version 20.19.0
Backend - JAVA (STS 3.0.1) - openjdk:17-jdk-slim
Database - MySQL (9.2)

## Ubuntu 22.04에 기본 툴 설치하기.
### Install List : Git, Docker, NVM, Pyenv, SDKMAN
$ ./install_default_tool/ubuntu_Default_set.sh$

## STS 구동 환경 구축 (Docker만 구동할 경우 이 단계 스킵해도 됨.)
sdk install java 17.0.14-jbr
sdk use java 17.0.14-jbr
sdk default java 17.0.14-jbr
apt install maven -y





# 설명
1. Front
Middleware 에서 refresh_token이라는 값이 없으면, login 페이지로 튕기게 되어있음.
login시, kakao auth URL을 다녀와서 code값을 가져오게되고,
해당 code값으로 Backend에서 실제 Kakao 인증 여부를 판단한다.
인증이 완료되었다면, 백엔드에서 자체적으로 access_token과 refresh_token을 주게되고,
jwtAuthFilter를 통해 패턴에 일치하는 API는 모두 token 검증을 진행한다.
때문에 token을 프론트에서 강제 생성할수는 없다.
access_token은 현재 30분 만료기간을 갖고있고,
refresh_token은 1일 만료기간을 갖고있다.

jwtAuthFilter에 의해 access_token 인증을 실패한 경우.
401에러를 뱉는데,
API 요청당 1번 front commonAxios에서 401이 발생한 경우, 
refresh_token을 가지고 access_token을 갱신하고, 원래 요청을 다시 한번 진행하도록 설계되어있다.


todo - 회원정보가 없는 경우. 회원가입하도록 페이지를 유도해야함.

Dockerizing, session 관리 기능 추가해야함.






<!-- 
React Extension Pack

 -->




