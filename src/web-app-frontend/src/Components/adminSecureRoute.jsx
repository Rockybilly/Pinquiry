import { useStoreState } from "easy-peasy";
import React from "react";
import { Route, Navigate } from "react-router-dom";

export default function AdminSecureRoute({ children }) {
  const user = useStoreState((state) => state.user);

  if (!user.isAdmin) {
    return <Navigate to="/dashboard" />;
  }
  return children;
}
