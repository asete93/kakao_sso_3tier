"use client"
import { useEffect, useState } from "react";
import * as Styled from "./UserInfo.styled";
import commonAxios from "@/lib/Axios";

const UserInfo = () => {
    const [data, setData] = useState(null);

    useEffect(()=>{
        getDate();
    },[])

    const getDate = async () => {
        try {
            const response = await commonAxios.get("/api/v1/user/me");
            if (response.status === 200) {
                setData(response.data.userName+"님 환영합니다.");
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }

    return (
        <Styled.SLayout>
            {data}
        </Styled.SLayout>
    )
}

export default UserInfo;