import IncidentCountGraph from "./IncidentCountGraph";
import ResponseTimeGraph from "./ResponseTimeGraph";

export default function OverviewPage({ monId }) {
  return (
    <>
      <IncidentCountGraph monId={monId} />
      <ResponseTimeGraph monId={monId} />
    </>
  );
}
