import styled,  { keyframes } from "styled-components";
import { AiOutlineLoading3Quarters } from "react-icons/ai";

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

export const SLayout = styled.div`
    margin-top: 10px;
    display: flex;
    border-radius: 4px;
    border: 1px solid #d8d6d4;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    padding: 10px;
    justify-content: center;
    align-items: center;
    font-size: 0.9rem;
    font-weight: 500;
    color: #242424;
    gap: 10px;
    cursor: pointer;
    transition: 0.1s;

    &:hover {
        border: 1px solid #aaa8a6;
    }

    &:active {
        border-color: #daedff;
        background-color: #daedff;
    }
`

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