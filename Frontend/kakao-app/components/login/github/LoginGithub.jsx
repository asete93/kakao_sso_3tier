"use client"
import * as Styled from "./LoginGithub.styled";
import Image from "next/image";
import {toast} from "sonner";

const LoginGithub = () => {
    const handleClick = () => {
        toast.warning("현재는 KAKAO 로그인 기능만 지원합니다.");
    }

    return(
        <Styled.SLayout onClick={handleClick}>
            <Image alt="Github Icon" src="/assets/login/github.webp" width={20} height={20} />
            Sign in with Github
        </Styled.SLayout>
    )
}

export default LoginGithub;