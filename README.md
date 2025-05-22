<!-- 
 ** Backend (JAVA) Remote VSCode + Debugging 진행하기
    1. build.gradle jvmArgs = ['-Xdebug','-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005'] 옵션 때문에, 자동으로 5005번 포트가 디버깅 포트로 동작함.

    2.Extension Pack for Java [Plugin 설치]

    3. F5 눌러서 디버깅 모드로 실행 , Port 충돌나는 경우, application.properties에 server.port = 원하는 포트로 변경
-->


# KakaoLogin
1. Ubuntu 22.04 기준으로 작성되었음.
본 프로젝트에서는 하위 3 Tier 구조로 KakaoLogin 인증 기능을 구성합니다.

Frontend - React (Next.js 14)
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











