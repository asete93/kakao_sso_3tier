import styled from "styled-components";

export const SLayout = styled.div`
  display: flex;
  flex-direction: column;
`

export const SHeaderDiv = styled.div`
  height: 40px;
  display: flex;
  background-color: #134A8F;
  justify-content: end;
  align-items: center;
`

export const SUserNameDiv = styled.div`
  margin-right: 10px;
  color: white;
`

export const SUserIconDiv = styled.div`
  color: #ffffff;
  height: 60%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 9pt;
  font-weight: 600;
  background-color: #ffffff;
  padding: 5px;
  border-radius: 20px;
`

export const SLogoutDiv = styled.div`
  margin-left: 20px;
  color: #ffffff;
  height: 100%;
  display: flex;
  align-items: center;
  margin-right: 20px;
  cursor: pointer;
  transition: 0.1s;

  &:hover {
    color: #e4e2fd;
  }
`

export const SContentsDiv = styled.div`
  width: 100%;
  min-height: calc(100vh - 40px);
  padding: 30px;
`


export const SUserInfoDiv = styled.div`
  display: flex;
`