"use client"
import { useEffect, useState } from "react";
import * as Styled from "./page.styled";
import {toast} from "sonner";
import commonAxios from "@/lib/Axios";
import { useRouter } from "next/navigation";

const Index = () => {
    const router = useRouter();

    useEffect(() => {
        handleAllChange();
    }, []);

    const [checks, setChecks] = useState({
        all: false,
        age: false,
        terms: false,
        privacy: false,
        marketing: false,
    });

    const handleAllChange = () => {
        const newValue = !checks.all;
        setChecks({
            all: newValue,
            age: newValue,
            terms: newValue,
            privacy: newValue,
            marketing: newValue,
        });
    };

    const handleCheckChange = (key) => {
        const newChecks = { ...checks, [key]: !checks[key] };
        const { age, terms, privacy, marketing } = newChecks;
        newChecks.all = age && terms && privacy && marketing;
        setChecks(newChecks);
    };

    const signup = () => {
        commonAxios.post("/api/v1/user/signup").then(() => {
            router.push("/signup/success");
        }).catch((error) => {
            console.error("회원가입 오류:", error);
            toast.error("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
        });
    }

    return (
        <Styled.SLayout>
            <Styled.SContentsDiv>
                <Styled.STitle>회원가입</Styled.STitle>
                <Styled.SDescription>
                    회원가입 샘플페이지입니다. 원하는 내용으로 변경하여 사용해주세요. 예시를 위한 화면입니다.
                </Styled.SDescription>

                <Styled.SSubContentsDiv>
                    <Styled.SSubContentsTitleDiv>
                        <Styled.SSubContentsTitle>모두 동의합니다</Styled.SSubContentsTitle>
                        <Styled.SSubContentsTitleCheckbox
                            type="checkbox"
                            checked={checks.all}
                            onChange={handleAllChange}
                        />
                    </Styled.SSubContentsTitleDiv>
                    <Styled.SSubContentsText>
                        {"만14세 이상 가입자(선택) 이용약관, 개인정보 수집 및 이용 안내, 마케팅 수신(선택)에 모두 동의합니다."}
                    </Styled.SSubContentsText>
                </Styled.SSubContentsDiv>

                <Styled.SDivider />

                <Styled.SSubContentsDiv>
                    <Styled.SSubContentsTitleDiv>
                        <Styled.SSubContentsTitle>만 14세 이상 가입자입니다</Styled.SSubContentsTitle>
                        <Styled.SSubContentsTitleCheckbox
                            type="checkbox"
                            checked={checks.age}
                            onChange={() => handleCheckChange("age")}
                        />
                    </Styled.SSubContentsTitleDiv>
                    <Styled.SSubContentsText>
                        {"만 14세 미만 가입자의 경우 회원가입시 보호자 동의가 필요합니다."}
                    </Styled.SSubContentsText>
                </Styled.SSubContentsDiv>

                <Styled.SSubContentsDiv>
                    <Styled.SSubContentsTitleDiv>
                        <Styled.SSubContentsTitle>이용약관 동의</Styled.SSubContentsTitle>
                        <Styled.SSubContentsTitleCheckbox
                            type="checkbox"
                            checked={checks.terms}
                            onChange={() => handleCheckChange("terms")}
                        />
                    </Styled.SSubContentsTitleDiv>
                    <Styled.SSubContentsDescription>
                        {`제1장 총칙
제1조 (목적)
아래 내용을 채워주세요`}
                    </Styled.SSubContentsDescription>
                </Styled.SSubContentsDiv>

                <Styled.SSubContentsDiv>
                    <Styled.SSubContentsTitleDiv>
                        <Styled.SSubContentsTitle>개인정보 수집 및 이용 안내 동의</Styled.SSubContentsTitle>
                        <Styled.SSubContentsTitleCheckbox
                            type="checkbox"
                            checked={checks.privacy}
                            onChange={() => handleCheckChange("privacy")}
                        />
                    </Styled.SSubContentsTitleDiv>
                    <Styled.SSubContentsDescription>
                        {`개인정보 수집 관련 내용을 채워주세요
개인정보 수집 관련 내용을 채워주세요
개인정보 수집 관련 내용을 채워주세요`}
                    </Styled.SSubContentsDescription>
                </Styled.SSubContentsDiv>

                <Styled.SSubContentsDiv>
                    <Styled.SSubContentsTitleDiv>
                        <Styled.SSubContentsTitle>마케팅 수신 동의(선택)</Styled.SSubContentsTitle>
                        <Styled.SSubContentsTitleCheckbox
                            type="checkbox"
                            checked={checks.marketing}
                            onChange={() => handleCheckChange("marketing")}
                        />
                    </Styled.SSubContentsTitleDiv>
                </Styled.SSubContentsDiv>

                <Styled.SSignupButton onClick={signup}>
                    회원가입
                </Styled.SSignupButton>
            </Styled.SContentsDiv>
        </Styled.SLayout>
    );
};

export default Index;
