"use client"
import * as Styled from "./Login.styled";
import {toast} from "sonner";

const Login = () => {

    const handleClick = () => {
        toast.warning("현재는 KAKAO 로그인 기능만 지원합니다.");
    }

    return(
    <Styled.SLayout>
        <Styled.SInputBox type="text" placeholder="Enter Your Email..." />
        <Styled.SSingupButton onClick={handleClick} >Sign in</Styled.SSingupButton>
    </Styled.SLayout>
    )
}

export default Login;