import { useStoreState } from "easy-peasy";
import React from "react";
import { Route, Navigate } from "react-router-dom";

export default function SecureRoute({ children }) {
  const user = useStoreState((state) => state.user);
  if (!user.isAuthenticated) {
    return <Navigate to="/" />;
  }
  return children;
}
