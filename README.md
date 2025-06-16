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
- 초기 설정 시 FrontServer IP는 해당 장비 IP, Port는 **3000** 사용 권장  
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
| DB_MEM_LIMIT | 4096M | ⛔ | 컨테이너 메모리 제한 (선택) |
| DATABASE_PORT | 3306 | ✅ | DB 포트 |

---

### ⚙️ Backend Environments

| Key | Example | 필수 | 설명 |
|-----|---------|------|------|
| PROXY_ADDR | http://192.168.0.100:8888 | ⛔ | Proxy 설정 필요 시 사용 |
| BACKEND_PORT | 8080 | ✅ | 백엔드 서버 포트 |
| DB_URL | jdbc:mysql://192.168.0.100:3306/TEST_DATABASE_NAME | ✅ | DB 접속 URL |
| DB_USERNAME | TEST_USER_NAME | ✅ | DB 사용자명 |
| DB_PASSWORD | TEST_USER_PASSWORD | ✅ | DB 비밀번호 |
| KAKAO_OAUTH_URL | https://kauth.kakao.com/oauth/token | ✅ | 카카오 OAuth 토큰 요청 URL |
| KAKAO_GRANT_TYPE | grant_type=authorization_code | ✅ | 고정값 |
| KAKAO_USER_INFO_URL | https://kapi.kakao.com/v2/user/me | ✅ | 사용자 정보 요청 URL |
| KAKAO_CLIENT_ID | (REST API 키) | ✅ | 카카오 앱에서 발급받은 키 |
| JWT_TOKEN_SECRET | (랜덤 문자열) | ✅ | JWT 암호화 키 (충분히 복잡해야 함) |

---

### 🖥️ Frontend Environments

| Key | Example | 필수 | 설명 |
|-----|---------|------|------|
| NEXT_PUBLIC_KAKAO_REST_KEY | (REST API 키) | ✅ | 카카오 앱 REST API 키 |
| NEXT_PUBLIC_API_SERVER_URL | http://[BackendIP]:[Port] | ✅ | 백엔드 주소 |
| NEXT_PUBLIC_KAKAO_REDIRECT_URI | http://[FrontendIP]:[Port]/login?type=kakao | ✅ | 카카오에 등록한 Redirect URI |
| NEXT_PUBLIC_KAKAO_LOGIN_PROMPT_YN | 0 | ✅ | 1: 매번 계정선택 / 0: 자동 로그인 |
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
