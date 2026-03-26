import { useState } from 'react'
import { api } from './api';

function App() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const login = async () => {
    try  {
      const res = await api.post("api/auth/login", {
        email,
        password,
      });
      console.log(res.data);
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

export default App
