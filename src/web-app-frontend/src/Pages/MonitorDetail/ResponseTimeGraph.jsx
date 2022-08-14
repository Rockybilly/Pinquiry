import {
  CartesianGrid,
  Legend,
  Line,
  Tooltip,
  XAxis,
  YAxis,
  LineChart,
  ResponsiveContainer,
} from "recharts";
import { Row } from "antd";

const fake_data = [
  { name: "ata", value: 1 },
  { name: "beta", value: 2 },
  { name: "zeta", value: 3 },
];

export default function ResponseTimeGraph() {
  return (
    <>
      <Row>
        {" "}
        <ResponsiveContainer>
          <LineChart
            data={fake_data}
            margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
          >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis dataKey="value" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="value" stroke="#8884d8" />
          </LineChart>
        </ResponsiveContainer>
      </Row>
    </>
  );
}
