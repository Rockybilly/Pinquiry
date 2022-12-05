import { useStoreState } from "easy-peasy";
import React from "react";
import { Route, Navigate } from "react-router-dom";
import { Spin } from "antd";

export default function SecureRoute({ children }) {
  const user = useStoreState((state) => state.user);
  const loginLoading = useStoreState((state) => state.loginLoading);

  if (loginLoading) {
    return (
      <Spin spinning={true}>
        <div style={{ height: "100vh", width: "100vw" }} />
      </Spin>
    );
  }

  if (!loginLoading && !user.isAuthenticated) {
    return <Navigate to="/" />;
  }
  return children;
}
