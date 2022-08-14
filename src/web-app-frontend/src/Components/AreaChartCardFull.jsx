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

const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length) {
    return (
      <div className="custom-tooltip" style={{ borderStyle: "none" }}>
        <p style={{ fontSize: "1.3rem", borderStyle: "none" }}>
          {payload[0].value}ms
        </p>
      </div>
    );
  }
};

export function AreaChartCardFull({ width, height, data, dataKey }) {
  return (
    <RectangleCard width={width} height={height}>
      <Row style={{ padding: "1vh", height: "100%" }}>
        <ResponsiveContainer>
          <AreaChart width={width} height={height} data={data}>
            <defs>
              <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8} />
                <stop offset="95%" stopColor="#8884d8" stopOpacity={0.2} />
              </linearGradient>
            </defs>
            <Tooltip content={<CustomTooltip />} />
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
