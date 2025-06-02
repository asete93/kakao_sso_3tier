import axios from 'axios';

const commonAxios = axios.create({
  withCredentials: true, // ✅ 쿠키 전송 필수!
});


let isRefreshing = false;
let refreshSubscribers: ((token: string) => void)[] = [];

function onAccessTokenFetched(token: string) {
  refreshSubscribers.forEach((callback) => callback(token));
  refreshSubscribers = [];
}

function addSubscriber(callback: (token: string) => void) {
  refreshSubscribers.push(callback);
}

commonAxios.interceptors.response.use(
  (response) => response, // 성공한 응답은 그대로
  async (error) => {
    const originalRequest = error.config;

    // 401 오류가 발생했을 때만 처리
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      // 토큰 갱신 중이면 기다리기
      if (isRefreshing) {
        return new Promise((resolve) => {
          addSubscriber(() => {
            resolve(commonAxios(originalRequest)); // 재시도
          });
        });
      }

      isRefreshing = true;

      try {
        await axios.post(
          '/refresh/token',
          {},
          { withCredentials: true }
        );

        isRefreshing = false;
        onAccessTokenFetched(''); // access_token은 서버 쿠키로 관리 중이므로 생략

        return commonAxios(originalRequest); // 재요청
      } catch (err) {
        await axios.post("/logout");
        isRefreshing = false;
        location.href = "/login";
        return Promise.reject(err);
      }
    }

    return Promise.reject(error);
  }
);

export default commonAxios;