"use client"
import * as Styled from "./LoginKakao.styled";
import Image from "next/image";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/navigation";
import commonAxios from "@/lib/Axios";


const LoginKakao = () => {
    const param = useSearchParams();
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        const code = param.get("code");
        const type = param.get("type");

        if (code && type && type == "kakao") {
            setIsLoading(true);


            // 카카오 로그인 성공 후, 백엔드로 코드 전송
            commonAxios.post(`/login/kakao`, { code: code }).then((response) => {
                if (response.status === 200) {
                    // 로그인 성공 후, 메인 페이지로 이동

                    if (response?.data?.needSignup) {
                        setTimeout(() => {
                            setIsLoading(false);
                            router.push("/signup");
                        }, 2000);
                        return
                    }

                    setTimeout(() => {
                        setIsLoading(false);
                        router.push("/");
                    }, 2000);
                }
            })
        }
    }, []);


    const handleClick = async () => {
        const REDIRECT_URI = `http://100.68.107.86:3002/login?type=kakao`;
        const KAKAO_REST_API_KEY = process.env.NEXT_PUBLIC_KAKAO_REST_KEY;
        // const kakaoRedirectUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_REST_API_KEY}&redirect_uri=${REDIRECT_URI}&prompt=login`;
        const kakaoRedirectUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_REST_API_KEY}&redirect_uri=${REDIRECT_URI}`;
        window.location.href = kakaoRedirectUrl;
    }

    return (
        <>
            {isLoading && <Styled.SLoadingDiv><Styled.SLoadingIcon /></Styled.SLoadingDiv>}
            <Styled.SLayout onClick={handleClick}>
                <Image alt="Kakao Icon" src="/assets/login/kakao.webp" width={20} height={20} />
                Sign in with Kakao
            </Styled.SLayout>
        </>
    )
}

export default LoginKakao;