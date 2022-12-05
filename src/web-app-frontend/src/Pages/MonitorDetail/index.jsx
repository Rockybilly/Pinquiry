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

export default function MonitorDetail() {
  const [currentPage, setCurrentPage] = useState("1");
  const { monId } = useParams();
  console.log(monId);
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

      {currentPage === "1" ? <OverviewPage monId={monId} /> : null}
      {/*{currentPage === "2" ? <IncidentDetail MonId={MonId} /> : null}*/}
      {currentPage === "3" ? <DangerZone monId={monId} /> : null}
    </>
  );
}
