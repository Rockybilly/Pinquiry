import {
  AppstoreOutlined,
  HomeOutlined,
  SettingOutlined,
  UserOutlined,
  LogoutOutlined,
} from "@ant-design/icons";
import { Col, Layout, Menu } from "antd";
import React, { useState } from "react";
import "antd/dist/antd.css";
import { Link, useNavigate } from "react-router-dom";
import { useStoreActions, useStoreState } from "easy-peasy";
import logo from "../../static/PinquiryLogoBlue.png";
import logoRzgi from "../../static/PinquiryLogoRzgi.png";
export function Topbar() {
  const navigate = useNavigate();
  const [current, setCurrent] = useState("1");
  const user = useStoreState((state) => state.user);
  const setUser = useStoreActions((actions) => actions.setUser);

  const onLogOutClick = (e) => {
    console.log("click ", "LoggedOut");
    setUser("");
    navigate("/");
  };

  return (
    <>
      <Menu
        mode="horizontal"
        theme="dark"
        style={{ height: "100%", width: "100%" }}
      >
        <img
          style={{
            height: "100%",
            width: "undefined",
            aspectRatio: "795/277",
            marginLeft: 8,
          }}
          src={logo}
          alt="Logo"
        />

        <img
          style={{
            height: "100%",
            width: "undefined",
            aspectRatio: "795/277",
            marginLeft: 8,
          }}
          src={logoRzgi}
          alt="Logo"
        />
        <Col flex={1} />
        <Menu.Item
          key="1"
          style={{ height: "100%", backgroundColor: "transparent" }}
        >
          <UserOutlined /> Welcome, {user.name}
        </Menu.Item>

        <Menu.Item
          onClick={onLogOutClick}
          style={{ height: "100%" }}
          key="2"
          icon={<LogoutOutlined />}
        >
          Log Out
        </Menu.Item>
      </Menu>
    </>
  );
}
