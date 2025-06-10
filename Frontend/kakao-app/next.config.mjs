import path from 'path';
import { fileURLToPath } from 'url';
import dotenv from 'dotenv';

// __dirname 대체 코드
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// 상위 폴더의 .env 로드
dotenv.config({ path: path.resolve(__dirname, '../../.env') });


/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [
            {
                source: "/login/kakao",
                destination: `${process.env.NEXT_PUBLIC_API_SERVER_URL}/api/v1/kakao/login`,
            },
            {
                source: "/refresh/token",
                destination: `${process.env.NEXT_PUBLIC_API_SERVER_URL}/api/v1/token/refresh`,
            },
            {
                source: "/api/v1/user/me",
                destination: `${process.env.NEXT_PUBLIC_API_SERVER_URL}/api/v1/user/me`,
            },
            {
                source: "/logout",
                destination: `${process.env.NEXT_PUBLIC_API_SERVER_URL}/api/v1/user/logout`,
            },
            {
                source: "/api/v1/user/signup",
                destination: `${process.env.NEXT_PUBLIC_API_SERVER_URL}/api/v1/user/signup`,
            },
        ];
    },
    // Styled 렌더링 이슈 해소하기 위함.
    compiler: {
        styledComponents: true,
    },

    reactStrictMode: false,

    env: {
        NEXT_PUBLIC_KAKAO_REST_KEY: process.env.NEXT_PUBLIC_KAKAO_REST_KEY,
        NEXT_PUBLIC_API_SERVER_URL: process.env.NEXT_PUBLIC_API_SERVER_URL,
        NEXT_PUBLIC_KAKAO_REDIRECT_URI: process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI,
        NEXT_PUBLIC_KAKAO_LOGIN_PROMPT_YN: process.env.NEXT_PUBLIC_KAKAO_LOGIN_PROMPT_YN,
    },
};



export default nextConfig;
