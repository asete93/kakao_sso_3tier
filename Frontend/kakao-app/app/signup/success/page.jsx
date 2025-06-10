"use client"
import * as Styled from "./page.styled";
import { CiCircleCheck } from "react-icons/ci";
import { useRouter } from "next/navigation";
import { useState } from "react";

const Index = () => {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);

    const ok = async () => {
        setIsLoading(true);
        router.push("/");
    }

    return (
        <>
            {isLoading && <Styled.SLoadingDiv><Styled.SLoadingIcon /></Styled.SLoadingDiv>}
            <Styled.SLayout>
                <Styled.SContentsDiv>
                    <Styled.STitle>환영합니다</Styled.STitle>

                    <Styled.SIconDiv>
                        <CiCircleCheck size={100} color={"#6fa3cb"} />
                    </Styled.SIconDiv>

                    <Styled.STextDiv>
                        회원가입이 완료되었습니다.
                    </Styled.STextDiv>

                    <Styled.SSignupButton onClick={ok}>
                        확인
                    </Styled.SSignupButton>
                </Styled.SContentsDiv>
            </Styled.SLayout>
        </>
    );
};

export default Index;
