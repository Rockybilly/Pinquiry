import IncidentCountGraph from "./IncidentCountGraph";
import ResponseTimeGraph from "./ResponseTimeGraph";

export default function OverviewPage() {
  return (
    <>
      <IncidentCountGraph />
      <ResponseTimeGraph />
    </>
  );
}
