"use client"
import { useEffect } from "react";
import * as Styled from "./page.styled";
import Login from "@/components/login/Login";
import LoginKakao from "@/components/login/kakao/LoginKakao";
import LoginApple from "@/components/login/apple/LoginApple";
import LoginGithub from "@/components/login/github/LoginGithub";
import LoginGoogle from "@/components/login/google/LoginGoogle";
import LoginMS from "@/components/login/microsoft/LoginMicrosoft";
import LoginPassKey from "@/components/login/passkey/LoginPassKey";
import {toast} from 'sonner';

const Index = () => {
    useEffect(() => {
        toast.warning("KAKAO 로그인 기능만 지원합니다.");
    },[]);

    return (
        <Styled.SLayoutDiv>
            <Styled.SContentsDiv>
                <Styled.STitleDiv>
                    LOGIN SAMPLE
                </Styled.STitleDiv>

                {/* Normal Login */}
                <Login />

                <Styled.SOrLabel>OR</Styled.SOrLabel>

                {/* Kakao Login Button */}
                <LoginKakao />
                {/* Apple Login Button */}
                <LoginApple />
                {/* GitHub Login Button */}
                <LoginGithub />
                {/* Google Login Button */}
                <LoginGoogle />
                {/* Microsoft Login Button */}
                <LoginMS />
                {/* PassKey Login Button */}
                <LoginPassKey />

            </Styled.SContentsDiv>
        </Styled.SLayoutDiv>
    )
}

export default Index;