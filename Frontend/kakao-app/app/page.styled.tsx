import styled from "styled-components";

export const SLayout = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  overflow: auto;
`

export const SContentsDiv = styled.div`
  min-width: 90vw;
  min-height: 300px;
  background-color: #e4e4fa;
  padding: 30px;
`


export const SUserInfoDiv = styled.div`
  display: flex;
`

export const SLogoutDiv = styled.div`
  margin-left: auto;
  flex-flow: 1;
  display: flex;
  align-items:center;
  color: #461cee;
  transition: 0.1s;
  cursor: pointer;

  &:hover {
    text-decoration: underline;
    color: #7657f1;
  }
`

