import styled from "styled-components";

export const SLayout = styled.div`
    width: 100%;
    margin-top: 20px;
    display: flex;
    flex-direction: column;
`

export const SInputBox = styled.input`
    background-color: #f6f4f2;
    padding: 10px 10px 10px 13px;
    border-radius: 4px;
`

export const SSingupButton = styled.button`
    margin-top: 10px;
    background-color: #474645;
    border-radius: 4px;
    padding: 7px;
    color: white;
    cursor: pointer;

    &:hover {
        background-color: #3a3a39;
    }
`