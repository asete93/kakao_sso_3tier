"use client"
import commonAxios from "@/lib/Axios";
import * as Styled from "./page.styled";
import UserInfo from "@/components/main/UserInfo";
import { useRouter } from "next/navigation";

export default function Home() {
  const router = useRouter();

  const logout = () => {
    commonAxios.post("/logout").then((res)=>{
      router.push("/login")
    });
  }

  return (
    <Styled.SLayout>
      <Styled.SContentsDiv>
        <Styled.SUserInfoDiv>
          {/* Welcome Message */}
          <UserInfo />
          
          <Styled.SLogoutDiv onClick={logout}>
            Logout
          </Styled.SLogoutDiv>
        </Styled.SUserInfoDiv>





      </Styled.SContentsDiv>
    </Styled.SLayout>
  );
}
