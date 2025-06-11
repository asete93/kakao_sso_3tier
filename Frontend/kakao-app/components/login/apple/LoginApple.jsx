"use client"
import * as Styled from "./LoginApple.styled";
import Image from "next/image";
import {toast} from "sonner";

const LoginApple = () => {
    const handleClick = () => {
        toast.warning("현재는 KAKAO 로그인 기능만 지원합니다.");
    }

    return(
        <Styled.SLayout onClick={handleClick}>
            <Image alt="Apple Icon" src="/assets/login/apple.webp" width={20} height={20} />
            Sign in with Apple
        </Styled.SLayout>
    )
}

export default LoginApple;