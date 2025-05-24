"use client"
import commonAxios from "../lib/Axios";
import * as Styled from "./page.styled";
import { useEffect, useState } from "react";

const Index = () => {
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
        <Styled.SLayoutDiv>
            <Styled.SContentsDiv>
                {data}
            </Styled.SContentsDiv>
        </Styled.SLayoutDiv>
    )
}

export default Index;