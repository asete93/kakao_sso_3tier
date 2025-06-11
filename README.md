# Introduce
**본 프로젝트에서는 하위 3 Tier 구조로 KakaoLogin 인증 기능을 구성합니다.**

- Frontend - React (Next.js 14) - node version 20.19.0
- Backend - JAVA (STS 3.0.1) - Java 17
- Database - MySQL (9.2)
  
  
  

---
  


#### 1. 카카오 어플리케이션 등록
&emsp;**https://developers.kakao.com/console/app 접속 후, 아래 순서 진행**  
  
    
      

#### &emsp;1-1. 어플리케이션 추가
![카카오_어플리케이션_등록_1](https://github.com/user-attachments/assets/50f42f8a-8e6b-4cef-927a-76fe0fbf7bed)

#### &emsp;1-2. 카카오 로그인 연동 설정
&emsp;&emsp;- _6번 항목의 Redirect URI는 반드시, **"http://[FrontServerIP]:[FrontServerPort]/login?type=kakao"** 형태로 작성._  
&emsp;&emsp;_Front ServerIP와 Port를 모를 경우, 우선 서버를 띄울 장비의 IP address와 Port는 3000으로 입력. 이후의 .env 파일 내용과 일치화 필요요_  


![카카오_어플리케이션_등록_2](https://github.com/user-attachments/assets/1df49f13-4cfd-4389-9779-7fa49ee1c960)  


#### &emsp;1-3. 카카오 로그인 연동 데이터 설정
&emsp;&emsp;- **_8번 항목의 닉네임 부분은 반드시 필수 동의 바랍니다.(현재 프로젝트에서 활용되는 부분이기 때문입니다.)_**

![카카오_어플리케이션_등록_3](https://github.com/user-attachments/assets/45c3081c-7b93-4778-be99-fcc56651ce53)
  

#### &emsp;1-4. 카카오 어플리케이션 REST API 키 발급
![카카오_어플리케이션_등록_4](https://github.com/user-attachments/assets/3fc4f659-f7f5-4343-8b8e-4ae3d2073bfe)

---
  
  
#### 2. .env 파일 설정
  &emsp;**_[Database Environments]&emsp;_** Backend Environments와 맞춰줘야하는 부분이 있으므로, 주의

| Key | Example | 필수여부 | Description |
| ------ | ------ | ------ | ------ |
| MYSQL_USER | TEST_USER_NAME | 필수 | 데이터베이스 사용자 명 |
| MYSQL_ROOT_PASSWORD | TEST_ROOT_PASSWORD | 필수 | 데이터베이스 관리자 비밀번호 |
| MYSQL_PASSWORD | TEST_USER_PASSWORD | 필수 | 데이터베이스 사용자 비밀번호 |
| MYSQL_DATABASE | TEST_DATABASE_NAME | 필수 | 데이터베이스 이름 |
| DB_MEM_LIMIT | 4096M |  | 데이터베이스 컨테이너 메모리 사용량 제한 |
| DATABASE_PORT | 3306 | 필수 | 데이터베이스 사용 Port |
---

  &emsp;**_[Backend Environments]&emsp;_** Database Environments와 맞춰줘야하는 부분이 있고,    
  &emsp;Docker로 구동할 경우, DNS 설정 문제로 간혹 Proxy 설정이 필요한 경우가 존재하는데, 이때 PROXY_ADDR 항목이 활용되니 주의.

| Key | Example | 필수여부 | Description |
| ------ | ------ | ------ | ------ |
| PROXY_ADDR | http://192.168.0.100:8888 |  | 프록시 서버정보보, Docker와 같이 컨테이너를 활용하는 경우 필요할 수 있음. docker-compose-tinyproxy.tml을 통해 tinyproxy를 쓴다면, 기본 8888 포트로 되어 있음. |
| BACKEND_PORT | 8080 | 필수 | Backend Server Port |
| DB_URL | jdbc:mysql://192.168.0.100:3306/TEST_DATABASE_NAME | 필수 | Database URL, 위 Database 환경설정 값을 기반으로 작성 |
| DB_USERNAME | TEST_USER_NAME | 필수 | Database 사용자명, 위 Database 환경설정 값을 기반으로 작성 |
| DB_PASSWORD | TEST_USER_PASSWORD | 필수 | Database 사용자 비밀번호, 위 Database 환경설정 값을 기반으로 작성 |
| KAKAO_OAUTH_URL | https://kauth.kakao.com/oauth/token | 필수 | 카카오 SSO에 활용되는 URL로 변경은 필요없음. |
| KAKAO_GRANT_TYPE | grant_type=authorization_code | 필수 | 카카오 SSO과정에서 권한 획득을 위한 값으로, 현재 프로젝트 예시에서는 변경이 필요없으나, 기능상 변경이 필요하다면 변경해서 진행. |
| KAKAO_USER_INFO_URL | https://kapi.kakao.com/v2/user/me | 필수 | 카카오 로그인된 계정의 정보를 획득하는 카카오측 API 엔드포인트 |
| KAKAO_CLIENT_ID | [KAKAO REST API 키] | 필수 | 위에서 어플리케이션을 등록한 시점에 [앱키] 항목에서 REST API 키 항목 값을 입력. |
| JWT_TOKEN_SECRET | Hd76fb2_yii9s0nm(sd++sXYms7u0=as_asd7IO8(DFhj1234876asd00--) | 필수 | JWT 토큰을 암호화할때 쓰이는 값으로, 아무값이나 입력할 수 있지만, 너무 짧은 문자열로는 불가능. |



  &emsp;**_[Frontend] Environments]&emsp;_** Backend Environments와 맞춰줘야하는 부분이 있고,    
  &emsp;Docker로 구동할 경우, DNS 설정 문제로 간혹 Proxy 설정이 필요한 경우가 존재하는데, 이때 PROXY_ADDR 항목이 활용되니 주의.

| Key | Example | 필수여부 | Description |
| ------ | ------ | ------ | ------ |
| NEXT_PUBLIC_KAKAO_REST_KEY | [KAKAO REST API 키] | 필수 | 위에서 어플리케이션을 등록한 시점에 [앱키] 항목에서 REST API 키 항목 값을 입력. Backend의 KAKAO_CLIENT_ID값과 동일한 값 |
| NEXT_PUBLIC_API_SERVER_URL | http://[Backend ip address]:[Backend Port] | 필수 | Backend Server 정보, 위 Backend 환경설정 값을 기반으로 작성 |
| NEXT_PUBLIC_KAKAO_REDIRECT_URI | http://[Frontend ip address]:[Frontend Port]/login?type=kakao | 필수 | 위 어플리케이션에서 등록한 Redirect URI와 동일한 값을 넣어야하며, Frontend ip와 Port는 이 설정의 PORT값과, 이 Frontend를 띄울 서버의 IP값을 입력한다. |
| NEXT_PUBLIC_KAKAO_LOGIN_PROMPT_YN | 0 | 필수 | 매 카카오로그인마다 계정 정보를 물을것인지를 정한다, 1인 경우 매번 묻고, 0인 경우 가장 최근에 로그인한 계정으로 자동 로그인한다. |
| PORT | 8080 | 필수 | Frontend Server Port |

---
  
  
#### 3. Build & Deploy
#### &emsp;3-1. Docker 환경으로 시작하기

- Proxy 환경이 필요하지 않은경우.    
`docker compose up -d --build`

- Proxy 환경이 필요한 경우.    
`docker compose -f docker-compose-tinyproxy.yml up -d --build`  
`docker compose -f docker-compose-withproxy.yml up -d --build`  


#### &emsp;3-2. Local 환경으로 시작하기
- Database Setting.  
`docker compose up mysql -d --build`  

- Backend Setting.  
`cd Backend && ./gradlew bootrun`  

- Frontend Setting.  
`cd Frontend/kakao-app && npm i && npm run dev`  


---

#### 4. 화면
#### 4-1. Login 화면,  KAKAO 버튼만 동작함.
![image](https://github.com/user-attachments/assets/444b25dc-8234-49d9-a8d8-6da5c4753bc8)
