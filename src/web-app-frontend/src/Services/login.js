const fakeAuth = (name, pass) =>
  new Promise((resolve) => {
    setTimeout(() => resolve("2342f2f1d131rf12"), 1000);
  });

export async function loginUser({ name, pass }) {
  const token = await fakeAuth(name, pass);

  return {
    token,
    name,
    isAuthenticated: true,
  };
}
