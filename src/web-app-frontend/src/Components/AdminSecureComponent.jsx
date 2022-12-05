import { useStoreState } from "easy-peasy";

export default function AdminSecureComponent({ children }) {
  const user = useStoreState((state) => state.user);

  if (user.isAdmin) {
    return children;
  }

  return null;
}
