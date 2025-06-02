/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [
            {
                source: "/login/kakao",
                destination: `${process.env.NEXT_PUBLIC_API_SERVER_URL}/api/v1/login/kakao`,
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

};



export default nextConfig;
