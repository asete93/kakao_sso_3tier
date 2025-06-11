"use client"
import * as Styled from "./LoginPassKey.styled";
import Image from "next/image";
import {toast} from "sonner";

const LoginPassKey = () => {
    const handleClick = () => {
        toast.warning("현재는 KAKAO 로그인 기능만 지원합니다.");
    }

    return(
        <Styled.SLayout onClick={handleClick}>
            <Image alt="PassKey Icon" src="/assets/login/passkey.webp" width={20} height={20} />
            Sign in with PassKey
        </Styled.SLayout>
    )
}

export default LoginPassKey;