"use client"
import * as Styled from "./LoginGoogle.styled";
import Image from "next/image";
import {toast} from "sonner";

const LoginGoogle = () => {
    const handleClick = () => {
        toast.warning("현재는 KAKAO 로그인 기능만 지원합니다.");
    }


    return(
        <Styled.SLayout onClick={handleClick}>
            <Image alt="Google Icon" src="/assets/login/google.webp" width={20} height={20} />
            Sign in with Google
        </Styled.SLayout>
    )
}

export default LoginGoogle;