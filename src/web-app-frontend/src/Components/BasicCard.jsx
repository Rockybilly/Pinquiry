import { ResponsiveContainer } from "recharts";
import { Col, Row } from "antd";
import { Outlet } from "react-router-dom";

//#5e5873
//Montserrat

export function BasicCard({ children, backgroundColor, size }) {
  return (
    <div
      style={{
        boxShadow: "0 0 10px #000000",
        backgroundColor: backgroundColor,
        height: size,
        width: size,
        aspectRatio: "1",
        display: "inline-block",
      }}
    >
      {children}
    </div>
  );
}
