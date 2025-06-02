import styled from "styled-components";

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