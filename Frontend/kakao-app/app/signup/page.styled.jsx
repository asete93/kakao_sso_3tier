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
    max-width: 400px;
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
export const SDescription = styled.div`
    margin: 2rem 0 0.5rem 0;
    font-size: 0.72rem;
    padding: 15px;
    border-radius: 4px;
    background-color: #e1e1e1;
`

export const SSubContentsDiv = styled.div`
    display: flex;
    flex-direction: column;
    margin-top: 1rem;
`

export const SSubContentsTitleDiv = styled.div`
    display: flex;
    align-items: center;
`

export const SSubContentsTitle = styled.div`
    font-size: 0.85rem;
`

export const SSubContentsTitleCheckbox = styled.input`
    margin-left: auto;
    width: 15px;
    height: 15px;
    accent-color: #020749;
    cursor: pointer;
`

export const SSubContentsText = styled.div`
    font-size: 0.75rem;
    margin-top: 0.5rem;
    color: gray;
`

export const SDivider = styled.div`
    margin: 1rem 0 1rem 0;
    height: 1px;
    display: flex;
    border-bottom: 1px solid #e6e6e6;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
`

export const SSubContentsDescription = styled.div`
    white-space: pre;
    margin: 1rem 0 0.5rem 0;
    font-size: 0.8rem;
    padding: 10px;
    border-radius: 4px;
    border: 1px solid #e9e9e9;
    background-color: #f4f4f4;
    height: 50px;
    overflow-y: auto;
`

export const SSignupButton = styled.div`
    background-color: #020749;
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
        background-color: #020749;
    }
    
`