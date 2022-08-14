import { useParams } from "react-router-dom";
import { Row } from "antd";
import {
  Bar,
  BarChart,
  CartesianGrid,
  Legend,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import TopMenu from "./TopMenu";
import { useState } from "react";
import OverviewPage from "./OverviewPage";
import IncidentDetail from "./IncidentDetail";
import DangerZone from "./DangerZone";

const data = [
  {
    timestamp: 1,
    uv: 4000,
    pv: 2400,
  },
  {
    timestamp: 2,
    uv: 3000,
    pv: 1398,
  },
  {
    timestamp: 3,
    uv: 2000,
    pv: 9800,
  },
  {
    timestamp: 4,
    uv: 2780,
    pv: 3908,
  },
  {
    timestamp: 5,
    uv: 1890,
    pv: 4800,
  },
  {
    timestamp: 6,
    uv: 2390,
    pv: 3800,
  },
  {
    timestamp: 7,
    uv: 3490,
    pv: 4300,
  },
];

export default function MonitorDetail() {
  const [currentPage, setCurrentPage] = useState("1");
  const { id } = useParams();

  return (
    <>
      <Row
        style={{
          textAlign: "center",
          alignItems: "center",
          justifyContent: "center",
          height: "10vh",
          fontSize: "2rem",
          fontWeight: "500",
          paddingBottom: "15vh",
          marginTop: "-10vh",
        }}
      >
        Monitor Detail Page
      </Row>
      <TopMenu setCurrentPage={setCurrentPage} />

      {currentPage === "1" ? <OverviewPage /> : null}
      {currentPage === "2" ? <IncidentDetail /> : null}
      {currentPage === "3" ? <DangerZone /> : null}
    </>
  );
}
