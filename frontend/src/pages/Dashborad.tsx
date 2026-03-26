import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="p-10">
      <h1 className="text-2xl font-bold mb-4">Welcome, logged in user</h1>
      <button className="bg-red-500 text-white px-4" onClick={logout}>
        Logout
      </button>
    </div>
  );
}
