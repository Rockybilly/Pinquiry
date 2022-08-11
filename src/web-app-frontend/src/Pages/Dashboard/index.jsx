import MonitorApp from "../MonitorApp";

import { Col, Divider, Row, Spin } from "antd";
import React, { useEffect, useState } from "react";
import { Paper } from "@mui/material";
import {
  getUserIncidentCount,
  getUserMonitorCount,
} from "../../Services/backendComm";
import { BasicLineCard } from "../../Components/BasicLineCard";
import { BasicTextCard } from "../../Components/BasicTextCard";

const data2 = [
  {
    name: "Page A",
    response_time_ms: 8098,
  },
  {
    name: "Page B",
    response_time_ms: 7771,
  },
  {
    name: "Page C",
    response_time_ms: 9021,
  },
  {
    name: "Page D",
    response_time_ms: 8211,
  },
  {
    name: "Page E",
    response_time_ms: 7599,
  },
  {
    name: "Page F",
    response_time_ms: 7869,
  },
  {
    name: "Page G",
    response_time_ms: 8001,
  },
];

function Dashboard() {
  const [contentLoading, setContentLoading] = useState(true);
  const [monitorCount, setMonitorCount] = useState(0);
  const [incidentCount, setIncidentCount] = useState(0);

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      setMonitorCount(await getUserMonitorCount("ata"));
      setIncidentCount(await getUserIncidentCount("ata"));
      setContentLoading(false);
    }
    doWork();
  }, []);

  if (contentLoading) {
    return (
      <Spin>
        <div style={{ width: "80vw", height: "95vh" }}></div>
      </Spin>
    );
  }

  return (
    <>
      <Row style={{ width: "100%", height: "10vh" }} />
      <Row style={{ width: "100%", height: "35vh" }}>
        <Col
          style={{
            justifyContent: "center",
            alignItems: "center",
            display: "flex",
          }}
          span={12}
        >
          <BasicTextCard
            backgroundColor={"#ffffff"}
            textColor={"#00273b"}
            header="Monitor Count"
            text={monitorCount}
            footer="Monitors"
            size="35vh"
          />
        </Col>

        <Col
          style={{
            justifyContent: "center",
            alignItems: "center",
            display: "flex",
          }}
          span={12}
        >
          {/*#ea5455*/}

          <BasicTextCard
            backgroundColor={incidentCount > 0 ? "#ea5455" : "#28c76f"}
            textColor={"#00273b"}
            header="Incident Count"
            text={incidentCount}
            footer="incidents"
            size="35vh"
          />
        </Col>
      </Row>
      <Row style={{ width: "100%", height: "10vh" }} />
      <Row style={{ width: "100%", height: "35vh" }}>
        <Col
          style={{
            justifyContent: "center",
            alignItems: "center",
            display: "flex",
          }}
          span={12}
        >
          <BasicLineCard
            backgroundColor={"#28c76f"}
            textColor={"#00273b"}
            width="15vh"
            height="15vh"
            data={data2}
            dataKey="response_time_ms"
          />
        </Col>
        <Col
          style={{
            justifyContent: "center",
            alignItems: "center",
            display: "flex",
          }}
          span={12}
        >
          <BasicTextCard
            backgroundColor={"#28c76f"}
            textColor={"#00273b"}
            header="Incident Count"
            text="0"
            footer="incidents"
            size="35vh"
          />
        </Col>
      </Row>
      <Row style={{ width: "100%", height: "10vh" }} />
    </>
  );
}

export default Dashboard;
