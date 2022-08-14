import {
  Bar,
  BarChart,
  CartesianGrid,
  Legend,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

const fake_data = [
  { name: "ata", value: 1 },
  { name: "beta", value: 2 },
  { name: "zeta", value: 3 },
];

export default function IncidentCountGraph() {
  return (
    <>
      <BarChart width={730} height={250} data={fake_data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis dataKey="value" />
        <Tooltip />
        <Legend />
        <Bar dataKey="value" fill="#8884d8" />
      </BarChart>
    </>
  );
}
