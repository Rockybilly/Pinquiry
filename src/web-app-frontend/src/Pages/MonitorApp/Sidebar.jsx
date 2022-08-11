import {
  AppstoreOutlined,
  DesktopOutlined,
  HomeOutlined,
  LogoutOutlined,
  PlusSquareOutlined,
  SettingOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Menu, Switch } from "antd";
import React, { useState } from "react";
import "antd/dist/antd.css";
import { Link, Outlet, useNavigate } from "react-router-dom";

export function Sidebar() {
  const navigate = useNavigate();
  const [current, setCurrent] = useState("1");

  const onClick = (e) => {
    console.log("click ", e.label);
    setCurrent(e.key);
  };

  return (
    <>
      {" "}
      <Menu
        onClick={onClick}
        defaultOpenKeys={["1"]}
        selectedKeys={[current]}
        style={{ height: "100%" }}
        mode="inline"
      >
        <Menu.Item key="1" icon={<HomeOutlined />}>
          <Link to="/dashboard">Dashboard</Link>
        </Menu.Item>

        <Menu.SubMenu
          key="5"
          title="Monitor Operations"
          icon={<AppstoreOutlined />}
        >
          <Menu.Item key="2" icon={<DesktopOutlined />}>
            <Link to="monitors">Monitors</Link>
          </Menu.Item>
          <Menu.Item key="3" icon={<PlusSquareOutlined />}>
            <Link to="add-monitor">Add Monitor</Link>
          </Menu.Item>
          <Menu.Item key="4">
            <Link to="/dashboard">Haha</Link>
          </Menu.Item>
        </Menu.SubMenu>
      </Menu>
    </>
  );
}
