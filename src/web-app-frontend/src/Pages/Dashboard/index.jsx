import MonitorApp from "../MonitorApp";

import { Col, Divider, Row, Spin } from "antd";
import React, { useEffect, useState } from "react";
import { getDashboardStatistics } from "../../Services/backendComm";
import { PieChartCard } from "../../Components/PieChartCard";
import { BasicTextCard } from "../../Components/BasicTextCard";
import CustomTooltip from "./CustomTooltip";

function Dashboard() {
  const [contentLoading, setContentLoading] = useState(true);
  const [monitorIncidentList, setMonitorIncidentList] = useState([]);
  const [statistics, setStatistics] = useState({});

  useEffect(() => {
    async function doWork() {
      setContentLoading(true);
      let local_statistics = {};
      setStatistics(await getDashboardStatistics());
      setContentLoading(false);

      let list = [];
      if (statistics.topTenIncidents) {
        Object.keys(statistics.topTenIncidents).forEach((key) => {
          //console.log(key);
          list.push({ name: key, value: statistics.topTenIncidents[key] });
        });
      }
      setMonitorIncidentList(list);
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
            text={statistics.monCount}
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
            backgroundColor={
              statistics.incidentCountLastHour > 0 ? "#ea5455" : "#28c76f"
            }
            textColor={"#00273b"}
            header="Incident Count"
            text={statistics.incidentCountLastHour}
            footer="in the last hour"
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
          <PieChartCard
            backgroundColor={"#28c76f"}
            textColor={"#00273b"}
            size="35vh"
            data={monitorIncidentList}
            dataKey="value"
            header={
              "Incident counts of " +
              monitorIncidentList.length +
              " monitors (last hour)"
            }
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
            backgroundColor={"#28c7ff"}
            textColor={"#00273b"}
            header="Requests"
            text={statistics.requestPerMinute}
            footer="per minute"
            size="35vh"
          />
        </Col>
      </Row>
      <Row style={{ width: "100%", height: "10vh" }} />
    </>
  );
}

export default Dashboard;
