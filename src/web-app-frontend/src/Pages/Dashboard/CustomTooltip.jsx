export default function CustomTooltip({ active, payload, label }) {
  if (active && payload && payload.length) {
    console.log(payload);
    return (
      <>
        <p>Monitor name: {payload[0].key}</p>
        <p>Incident Count: {payload[0].value}</p>
      </>
    );
  }

  return <></>;
}
