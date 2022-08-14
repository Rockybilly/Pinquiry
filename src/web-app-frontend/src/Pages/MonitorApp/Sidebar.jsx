import {
  AppstoreOutlined,
  DesktopOutlined,
  HomeOutlined,
  LogoutOutlined,
  PlusSquareOutlined,
  SettingOutlined,
  TableOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Menu } from "antd";
import React, { useEffect, useState } from "react";
import "antd/dist/antd.css";
import { Link, useLocation } from "react-router-dom";
import AdminSecureComponent from "../../Components/AdminSecureComponent";
import { useStoreState } from "easy-peasy";

export function Sidebar() {
  const [current, setCurrent] = useState("/dashboard");
  const user = useStoreState((state) => state.user);
  let location = useLocation();

  useEffect(() => {
    setCurrent(location.pathname);
  }, [location]);

  return (
    <>
      <Menu
        defaultOpenKeys={["/dashboard"]}
        selectedKeys={[current]}
        style={{ height: "100%" }}
        mode="inline"
      >
        <Menu.Item key="/dashboard" icon={<HomeOutlined />}>
          <Link to="/dashboard">Dashboard</Link>
        </Menu.Item>

        <Menu.SubMenu
          key="5"
          title="Monitor Operations"
          icon={<AppstoreOutlined />}
        >
          <Menu.Item key="/monitors" icon={<DesktopOutlined />}>
            <Link to="/monitors">Monitors</Link>
          </Menu.Item>
          <Menu.Item key="/add-monitor" icon={<PlusSquareOutlined />}>
            <Link to="/add-monitor">Add Monitor</Link>
          </Menu.Item>
        </Menu.SubMenu>

        <Menu.Item key="/edit-profile" icon={<SettingOutlined />}>
          <Link to="/edit-profile">Edit Profile</Link>
        </Menu.Item>

        {user.isAdmin && (
          <>
            <Menu.Divider />
            <Menu.Divider />
            <Menu.Item key="/user-operations" icon={<UserOutlined />}>
              <Link to="/user-operations">User Operations</Link>
            </Menu.Item>
            <Menu.Item key="/monitor-services" icon={<TableOutlined />}>
              <Link to="/monitor-services">Monitor Service Workers</Link>
            </Menu.Item>
          </>
        )}
      </Menu>
    </>
  );
}
