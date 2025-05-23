/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [];
    },
    // Styled 렌더링 이슈 해소하기 위함.
    compiler: {
        styledComponents: true,
    },
};



export default nextConfig;
