# 🔐 KakaoLogin 인증 프로젝트 가이드

본 프로젝트는 **3-Tier 구조**로 구성된 **Kakao 로그인 인증 시스템**입니다.

- **Frontend**: React (Next.js 14), Node.js 20.19.0  
- **Backend**: Java 17 (STS 3.0.1)  
- **Database**: MySQL 9.2  

---

## 1️⃣ 카카오 어플리케이션 등록

👉 [카카오 개발자 센터 바로가기](https://developers.kakao.com/console/app)

### 1-1. 어플리케이션 추가
![카카오 앱 등록 1](https://github.com/user-attachments/assets/50f42f8a-8e6b-4cef-927a-76fe0fbf7bed)

### 1-2. 로그인 연동 설정
- **Redirect URI**는 반드시 아래 형태로 입력:  
  `http://[FrontServerIP]:[FrontServerPort]/login?type=kakao`
- Front ServerIP와 Port를 모를 경우, 우선 서버를 띄울 장비의 IP address와 Port는 **3000**으로 입력.
  → 이후 `.env`와 동기화 필요

![카카오 앱 등록 2](https://github.com/user-attachments/assets/1df49f13-4cfd-4389-9779-7fa49ee1c960)

### 1-3. 로그인 동의 항목 설정
- **닉네임 항목은 필수 동의로 설정해야 합니다.**  
  (현재 프로젝트에서 해당 값 사용 중)

![카카오 앱 등록 3](https://github.com/user-attachments/assets/45c3081c-7b93-4778-be99-fcc56651ce53)

### 1-4. REST API 키 발급
![카카오 앱 등록 4](https://github.com/user-attachments/assets/3fc4f659-f7f5-4343-8b8e-4ae3d2073bfe)

---

## 2️⃣ 환경 변수 설정 (`.env`)

### 🛢️ Database Environments

| Key | Example | 필수 | 설명 |
|-----|---------|------|------|
| MYSQL_USER | TEST_USER_NAME | ✅ | DB 사용자명 |
| MYSQL_ROOT_PASSWORD | TEST_ROOT_PASSWORD | ✅ | DB 루트 비밀번호 |
| MYSQL_PASSWORD | TEST_USER_PASSWORD | ✅ | DB 사용자 비밀번호 |
| MYSQL_DATABASE | TEST_DATABASE_NAME | ✅ | DB 이름 |
| DB_MEM_LIMIT | 4096M |  | 컨테이너 메모리 제한 (선택) |
| DATABASE_PORT | 3306 | ✅ | DB 포트 |

---

### ⚙️ Backend Environments

| Key | Example | 필수 | 설명 |
|-----|---------|------|------|
| PROXY_ADDR | http://192.168.0.100:8888 |  | 프록시 서버정보, Docker와 같이 컨테이너를 활용하는 경우 필요할 수 있음. docker-compose-tinyproxy.tml을 통해 tinyproxy를 쓴다면, 기본 8888 포트로 되어 있음. |
| BACKEND_PORT | 8080 | ✅ | 백엔드 서버 포트 |
| DB_URL | jdbc:mysql://192.168.0.100:3306/TEST_DATABASE_NAME | ✅ | DB URL, 위 DB 환경설정 값을 기반으로 작성 |
| DB_USERNAME | TEST_USER_NAME | ✅ | DB 사용자명, 위 DB 환경설정 값을 기반으로 작성 |
| DB_PASSWORD | TEST_USER_PASSWORD | ✅ | DB 사용자 비밀번호, 위 DB 환경설정 값을 기반으로 작성 |
| KAKAO_OAUTH_URL | https://kauth.kakao.com/oauth/token | ✅ | 카카오 SSO에 활용되는 URL로 변경은 필요없음. |
| KAKAO_GRANT_TYPE | grant_type=authorization_code | ✅ | 카카오 SSO과정에서 권한 획득을 위한 값으로, 현재 프로젝트 예시에서는 변경이 필요없으나, 기능상 변경이 필요하다면 변경해서 진행. |
| KAKAO_USER_INFO_URL | https://kapi.kakao.com/v2/user/me | ✅ | 사용자 정보 요청 URL |
| KAKAO_CLIENT_ID | (REST API 키) | ✅ | 위에서 어플리케이션을 등록한 시점에 [앱키] 항목에서 REST API 키 항목 값을 입력. |
| JWT_TOKEN_SECRET | (랜덤 문자열) | ✅ | JWT 토큰을 암호화할때 쓰이는 값으로, 아무값이나 입력할 수 있지만, 너무 짧은 문자열로는 불가능. |

---

### 🖥️ Frontend Environments

| Key | Example | 필수 | 설명 |
|-----|---------|------|------|
| NEXT_PUBLIC_KAKAO_REST_KEY | (REST API 키) | ✅ | 위에서 어플리케이션을 등록한 시점에 [앱키] 항목에서 REST API 키 항목 값을 입력. Backend의 KAKAO_CLIENT_ID값과 동일한 값 |
| NEXT_PUBLIC_API_SERVER_URL | http://[BackendIP]:[Port] | ✅ | Backend Server 정보, 위 Backend 환경설정 값을 기반으로 작성 |
| NEXT_PUBLIC_KAKAO_REDIRECT_URI | http://[FrontendIP]:[Port]/login?type=kakao | ✅ | 위 어플리케이션에서 등록한 Redirect URI와 동일한 값을 넣어야하며, Frontend ip와 Port는 이 설정의 PORT값과, 이 Frontend를 띄울 서버의 IP값을 입력한다. |
| NEXT_PUBLIC_KAKAO_LOGIN_PROMPT_YN | 0 | ✅ | 매 카카오로그인마다 계정 정보를 물을것인지를 정한다, 1인 경우 매번 묻고, 0인 경우 가장 최근에 로그인한 계정으로 자동 로그인한다. |
| PORT | 8080 | ✅ | 프론트엔드 실행 포트 |

---

## 3️⃣ Build & Deploy

### 🚀 Docker 환경

- **Proxy 없음**
```bash
docker compose up -d --build
```

- **Proxy 사용**
```bash
docker compose -f docker-compose-tinyproxy.yml up -d --build
docker compose -f docker-compose-withproxy.yml up -d --build
```

---

### 💻 Local 환경

```bash
# 1. DB 실행
docker compose up mysql -d --build

# 2. Backend 실행
cd Backend
./gradlew bootrun

# 3. Frontend 실행
cd Frontend/kakao-app
npm install
npm run dev
```

---

## 4️⃣ 화면 예시

### 🔐 로그인 화면  
- **KAKAO 버튼만 동작함**

![로그인 화면](https://github.com/user-attachments/assets/444b25dc-8234-49d9-a8d8-6da5c4753bc8)
