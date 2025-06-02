import styled from "styled-components";

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