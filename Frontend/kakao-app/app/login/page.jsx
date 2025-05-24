"use client"
import { useEffect } from "react";
import * as Styled from "./page.styled";
import Image from "next/image";
import { useSearchParams } from "next/navigation";
import axios from "axios";
import { useRouter } from "next/navigation";

const Index = () => {
    const param = useSearchParams();
    const router = useRouter();

    useEffect(() => {
        const code = param.get("code");
        if (code) {
            // 카카오 로그인 성공 후, 백엔드로 코드 전송
            axios.post(`/login/kakao`,{code:code}).then((response) => {
                if(response.status === 200) {
                    // 로그인 성공 후, 메인 페이지로 이동
                    router.push("/main");
                }
            })
        }
    }, []);


    const kakaoLoginHandler = () => {
        const REDIRECT_URI = `http://100.68.107.86:3002/login`;
        const KAKAO_REST_API_KEY = process.env.NEXT_PUBLIC_KAKAO_REST_KEY;
        const kakaoRedirectUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_REST_API_KEY}&redirect_uri=${REDIRECT_URI}`;
        window.location.href = kakaoRedirectUrl;
    };


    return (
        <Styled.SLayoutDiv>
            <Styled.SContentsDiv>
                <Styled.SButtonWrapperDiv>
                    <Image src="/assets/kakao_login_medium_narrow.png" width={200} height={50} onClick={kakaoLoginHandler}/>
                </Styled.SButtonWrapperDiv>
            </Styled.SContentsDiv>
        </Styled.SLayoutDiv>
    )
}

export default Index;