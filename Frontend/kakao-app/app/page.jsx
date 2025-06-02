"use client"
import commonAxios from "@/lib/Axios";
import * as Styled from "./page.styled";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { IoLogOutOutline } from "react-icons/io5";
import { RiUserLine } from "react-icons/ri";

export default function Home() {
  const router = useRouter();
  const [data, setData] = useState(null);

  useEffect(() => {
    getDate();
  }, [])

  const getDate = async () => {
    try {
      const response = await commonAxios.get("/api/v1/user/me");
      if (response.status === 200) {
        setData(response.data.userName);
      }
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  }

  const logout = async () => {
    if (window.confirm("로그아웃 하시겠습니까?")) {
      await commonAxios.post("/logout");
      router.push("/login");
    };
  }

  return (
    <Styled.SLayout>
      {/* Header */}
      <Styled.SHeaderDiv>

        <Styled.SUserNameDiv>
          {data}
        </Styled.SUserNameDiv>

        <Styled.SUserIconDiv>
          <RiUserLine size={15} color="#134A8F" />
        </Styled.SUserIconDiv>

        <Styled.SLogoutDiv onClick={logout}>
          <IoLogOutOutline size={25} />
        </Styled.SLogoutDiv>
      </Styled.SHeaderDiv>

      {/* Main Contents */}
      <Styled.SContentsDiv>
        <h1 style={{fontWeight:'bold'}}>Sample Page</h1>
        <br/>
        <p>이곳은 샘플 페이지입니다.</p>
        <p>로그인 후에만 접근할 수 있습니다.</p>
        <p>로그아웃을 원하시면 우측 상단의 로그아웃 아이콘을 클릭하세요.</p>
        <p>이 페이지는 Next.js와 Kakao 로그인 연동을 예시로 보여줍니다.</p>
        <p>추가적인 기능이나 페이지를 구현할 수 있습니다.</p>
      </Styled.SContentsDiv>
    </Styled.SLayout>
  );
}
