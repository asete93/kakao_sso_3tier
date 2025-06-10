<!-- 
 ** Backend (JAVA) Remote VSCode + Debugging 진행하기
    1. build.gradle jvmArgs = ['-Xdebug','-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005'] 옵션 때문에, 자동으로 5005번 포트가 디버깅 포트로 동작함.

    2.Extension Pack for Java [Plugin 설치]

    3. F5 눌러서 디버깅 모드로 실행 , Port 충돌나는 경우, application.properties에 server.port = 원하는 포트로 변경
-->

## Backend-SERVER
- Java 17
- Gradle
- Maven

## Build & Run
1. CLI : ./gradlew bootRun

