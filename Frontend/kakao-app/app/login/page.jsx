"use client"
import { useEffect } from "react";
import * as Styled from "./page.styled";
import Image from "next/image";

const Index = () => {
    const redirectUri = `http://100.68.107.86:8084/api/v1/login/`;
    const scope = [
        'profile_nickname'
    ].join(',');

    useEffect(() => {
        if (window?.Kakao) {
            if (!window.Kakao.isInitialized()) {
                window.Kakao.init(process.env.NEXT_PUBLIC_KAKAO_JS_KEY);
                console.log('after Init: ', window.Kakao.isInitialized());
            }
        }
    }, []);


    const kakaoLoginHandler = () => {
        // 인가 코드 받기 위해서, 리다이렉트 페이지로 이동
        window.Kakao.Auth.authorize({
            redirectUri,
            scope,
        });
        console.log('Kakao Logining'); // 확인용 로그
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