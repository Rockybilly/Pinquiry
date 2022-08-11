const fakeRequest = (value) =>
  new Promise((resolve) => {
    setTimeout(() => resolve(value), 500);
  });

export async function getUserIncidentCount() {
  return await fakeRequest(0);
}

export async function getUserMonitorCount() {
  return await fakeRequest(1233);
}

export async function getMonitorServices() {
  return await fakeRequest([
    {
      serviceId: "abc",
      serviceIp: "127.0.0.1",
      countryName: "United States",
      countryCode: "US",
    },
    {
      serviceId: "abc2",
      serviceIp: "127.0.0.2",
      countryName: "United Kingdom",
      countryCode: "GB",
    },
  ]);
}
