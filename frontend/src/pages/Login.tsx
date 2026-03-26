import { useState } from 'react'
import { api } from '../api';
import { useNavigate } from 'react-router-dom';
import type { LoginResponse } from '../types/auth';

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const login = async () => {
    try  {
      const res = await api.post<LoginResponse>("api/auth/login", {
        email,
        password,
      });
      localStorage.setItem("token", res.data.accessToken);
      navigate("/dashboard");
    } catch (err) {
      console.log("Login failed: ", err);
    }
  };

  return (
   <div className='flex flex-col gap-4 p-10'>
    <input
    className='border p-2'
    placeholder='email'
    onChange={(e) => setEmail(e.target.value)}
    />
    <input 
    className='border p-2'
    placeholder='password'
    type='password' 
    onChange={(e) => setPassword(e.target.value)}
    />
    <button className='bg-blue-500 text-white p-2' onClick={login}>
      Login
    </button>
   </div>
  );
}

export default Login
