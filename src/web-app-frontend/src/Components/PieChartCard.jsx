import React from "react";
import {
  LineChart,
  Line,
  Area,
  XAxis,
  YAxis,
  ResponsiveContainer,
  AreaChart,
  Tooltip,
  PieChart,
  Pie,
  Cell,
} from "recharts";

import { Row } from "antd";

import { BasicCard } from "./BasicCard";

export function PieChartCard({ size, data, dataKey, header }) {
  const COLORS = [
    "#ea5545",
    "#f46a9b",
    "#ef9b20",
    "#edbf33",
    "#ede15b",
    "#bdcf32",
    "#87bc45",
    "#27aeef",
    "#b33dc6",
  ];
  return (
    <BasicCard size={size}>
      <Row
        style={{
          alignItems: "center",
          justifyContent: "center",
          height: "10%",
        }}
      >
        <div
          style={{
            textShadow: "rgba(34,38,65,0.8) 0 0 8px",
            fontWeight: "500",
            paddingTop: "0.8vh",
            fontSize: "calc(" + size + "/" + 16 + ")",
            textAlign: "center",
            color: "#5e5873",
          }}
        >
          {header}
        </div>
      </Row>

      <Row style={{ padding: "1vh", height: "90%" }}>
        <ResponsiveContainer>
          <PieChart width={size} height={size}>
            <Tooltip />
            <Pie data={data} type="monotone" dataKey={dataKey} stroke="#8884d8">
              {data.map((entry, index) => (
                <Cell
                  key={`cell-${index}`}
                  fill={COLORS[index % COLORS.length]}
                />
              ))}
            </Pie>
          </PieChart>
        </ResponsiveContainer>
      </Row>
    </BasicCard>
  );
}
