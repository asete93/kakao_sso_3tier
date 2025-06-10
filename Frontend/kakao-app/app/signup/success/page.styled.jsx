import styled,  { keyframes } from "styled-components";
import { AiOutlineLoading3Quarters } from "react-icons/ai";

export const SLayout = styled.div`
    display: flex;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    user-select: none;
    align-items: center;
`

export const SContentsDiv = styled.div`
    padding: 30px;
    margin-top: 2rem;
    width: 400px;
    display: flex;
    flex-direction: column;
    border-radius: 7px;
    border: 1px solid #d8d6d4;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
`

export const STitle = styled.h1`
    font-size: 1.7rem;
    font-weight: bold;
    text-align: center;
`

export const SIconDiv = styled.div`
    display: flex;
    justify-content: center;
    margin: 2rem 0 1rem 0;
`

export const STextDiv = styled.div`
    display: flex;
    justify-content: center;
    margin-bottom: 1rem;
    font-size: 0.8rem;
`

export const SSignupButton = styled.div`
    background-color: #081274;
    padding: 10px;
    border-radius: 7px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-weight: bold;
    margin-top: 20px;
    cursor: pointer;
    transition: 0.2s;
    
    &:hover {
        background-color: #343987;
    }

    &:active {
        background-color: #081274;
    }
    
`

const spin = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

export const SLoadingIcon = styled(AiOutlineLoading3Quarters)`
  font-size: 48px; // 아이콘 크기
  animation: ${spin} 1s linear infinite; // 1초마다 한 바퀴 회전
`;


export const SLoadingDiv = styled.div`
    z-index: 1000;
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(3px);
    pointer-events: all;
`