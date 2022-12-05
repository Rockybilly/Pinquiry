import { ResponsiveContainer } from "recharts";
import { Col, Row } from "antd";
import { Outlet } from "react-router-dom";

//#5e5873
//Montserrat

export function RectangleCard({ children, backgroundColor, width, height }) {
  return (
    <div
      style={{
        boxShadow: "0 0 5px #000000",
        backgroundColor: backgroundColor,
        height: height,
        width: width,
        display: "inline-block",
      }}
    >
      {children}
    </div>
  );
}
