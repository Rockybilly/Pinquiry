const fakeRequest = (value) =>
  new Promise((resolve) => {
    setTimeout(() => resolve(value), 500);
  });

export async function getUserIncidentCount(user) {
  return await fakeRequest(0);
}

export async function getUserMonitorCount(user) {
  return await fakeRequest(1233);
}
