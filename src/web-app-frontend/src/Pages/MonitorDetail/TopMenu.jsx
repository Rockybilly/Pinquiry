import React, { useState } from "react";
import { Col, Menu } from "antd";
import { LogoutOutlined } from "@ant-design/icons";

export default function TopMenu({ setCurrentPage }) {
  const [current, setCurrent] = useState("1");

  const onClick = (e) => {
    setCurrentPage(e.key);
    setCurrent(e.key);
  };

  return (
    <Menu
      style={{
        fontSize: "1.5rem",
        backgroundColor: "#f1f1f1",
        justifyContent: "center",
        color: "#707070",
        textShadow: "0 0 10px rgba(122, 112, 112, 0.7)",
        borderStyle: "groove",
        borderWidth: "medium",
      }}
      onClick={onClick}
      selectedKeys={[current]}
      mode="horizontal"
    >
      <Menu.Item style={{ height: "100%" }} key="1">
        Overview
      </Menu.Item>

      <Menu.Item style={{ height: "100%" }} key="2">
        Incident Detail
      </Menu.Item>
      <Menu.Item style={{ height: "100%", width: "50%" }} key="4">
        <Col flex={1} />
      </Menu.Item>

      <Menu.Item style={{ height: "100%", color: "#e06666" }} key="3">
        Danger Zone
      </Menu.Item>
    </Menu>
  );
}
