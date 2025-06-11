"use client"
import * as Styled from "./LoginMicrosoft.styled";
import Image from "next/image";
import {toast} from "sonner";

const LoginMicrosoft = () => {
    const handleClick = () => {
        toast.warning("현재는 KAKAO 로그인 기능만 지원합니다.");
    }

    return(
        <Styled.SLayout onClick={handleClick}>
            <Image alt="Microsoft Icon" src="/assets/login/microsoft.webp" width={20} height={20} />
            Sign in with Microsoft
        </Styled.SLayout>
    )
}

export default LoginMicrosoft;