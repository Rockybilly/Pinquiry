import React from "react";
import { LineChart, Line } from "recharts";
import { ResponsiveContainer } from "recharts";
import { BasicCard } from "./BasicCard";

export function BasicLineCard({ width, height, data, dataKey }) {
  return (
    <BasicCard>
      <ResponsiveContainer {...console.log(width, height, dataKey)}>
        <LineChart width={width} height={height} data={data}>
          <Line
            type="monotone"
            dataKey={dataKey}
            stroke="#8884d8"
            strokeWidth={2}
            dot={false}
          />
        </LineChart>
      </ResponsiveContainer>
    </BasicCard>
  );
}
