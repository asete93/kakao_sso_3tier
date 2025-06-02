import styled from "styled-components";


export const SLayoutDiv = styled.div`
    display: flex;
    height: 100vh;
    justify-content: center;
`;

export const SContentsDiv = styled.div`
    padding: 30px;
    background-color: white;
    border-radius: 5px;
    min-width: 400px;
    margin-top: 30px;
    margin-bottom: 100px;
`

export const STitleDiv = styled.div`
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: center;
    font-size: 20pt;
    font-weight: bold;
    gap: 10px;
    user-select: none;
`

export const SButtonWrapperDiv = styled.div`
    cursor: pointer;
`

export const SOrLabel = styled.div`
    margin: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 10pt;
    color: gray;
`
