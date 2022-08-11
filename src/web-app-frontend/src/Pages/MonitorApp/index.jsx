import React, { useState } from "react";
import "../../App.css";

import { AppBar, Button, Typography } from "@mui/material";
import Grid from "@mui/material/Grid";
import TextField, { textFieldClasses } from "@mui/material/TextField";
import { Link, Outlet } from "react-router-dom";

import { Sidebar } from "./Sidebar";
import { Col, Divider, Layout, Menu, Row } from "antd";
import { Topbar } from "./Topbar";

function MonitorApp() {
  return (
    <Col
      style={{ width: "100%", height: "100%", margin: "0px", padding: "0px" }}
    >
      <Row
        style={{
          width: "100%",
          height: "4%",
          minHeight: "50px",
          margin: "0px",
          padding: "0px",
        }}
      >
        <Topbar />
      </Row>
      <Row
        style={{ width: "100%", height: "96%", margin: "0px", padding: "0px" }}
      >
        <Col span={3}>
          <Sidebar />
        </Col>
        <Col span={21}>
          <div
            style={{
              backgroundColor: "#f1f1f1",
              width: "80vw",
              minHeight: "95vh",
              padding: "5vw",
              margin: "2%",
              boxShadow: "-4px -4px 15px gray",
            }}
          >
            <Outlet />
          </div>
        </Col>
      </Row>
    </Col>
  );
}

export default MonitorApp;
