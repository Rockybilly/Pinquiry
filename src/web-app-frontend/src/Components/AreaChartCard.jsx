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
} from "recharts";

import { Row } from "antd";

import { RectangleCard } from "./RectangleCard";

export function AreaChartCard({ width, height, data, dataKey, value, desc }) {
  return (
    <RectangleCard width={width} height={height}>
      <Row style={{ height: "20%" }}>
        <span
          style={{
            textShadow: "rgba(94,88,115,0.8) 0 0 8px",
            fontWeight: "600",
            paddingLeft: "calc(" + width + "/" + 20 + ")",
            paddingTop: "calc(" + height + "/" + 20 + ")",
            fontSize: "calc(" + height + "/" + 7 + ")",
            color: "#5e5873",
          }}
        >
          {value}
        </span>
      </Row>
      <Row style={{ height: "20%" }}>
        {" "}
        <span
          style={{
            textShadow: "rgba(94,88,115,0.6) 0 0 8px",
            fontWeight: "600",
            paddingLeft: "calc(" + width + "/" + 20 + ")",
            paddingTop: "calc(" + height + "/" + 30 + ")",
            fontSize: "calc(" + height + "/" + 20 + ")",
            color: "#5e5873",
          }}
        >
          {desc}
        </span>
      </Row>
      <Row style={{ padding: "1vh", height: "60%" }}>
        <ResponsiveContainer>
          <AreaChart width={width} height={height} data={data}>
            <defs>
              <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8} />
                <stop offset="95%" stopColor="#8884d8" stopOpacity={0.2} />
              </linearGradient>
            </defs>
            <Tooltip />
            <Area
              type="monotone"
              dataKey={dataKey}
              stroke="#8884d8"
              strokeWidth={1}
              fill="url(#colorUv)"
            />
          </AreaChart>
        </ResponsiveContainer>
      </Row>
    </RectangleCard>
  );
}
