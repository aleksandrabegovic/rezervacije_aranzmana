import './App.css';
import TravelShowcase from './pages/TravelShowcase';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from './pages/Login';
import Register from './pages/Register';
import NavBar from './components/NavBar';
import { AuthProvider } from './auth/AuthContext';
import AgentDashboard from './pages/AgentDashboard';
import RequireAuth from './auth/RequireAuth';
import RequireRole from './auth/RequireRole';


function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
      <NavBar></NavBar>
        <Routes>
              <Route path="/" element={<TravelShowcase></TravelShowcase>}></Route>
              <Route path="/login" element={<Login></Login>}></Route>
              <Route path="/register" element={<Register></Register>}></Route>
               <Route
                  path="/agent"
                  element={
                    <RequireAuth>
                      <RequireRole role="AGENT">
                        <AgentDashboard />
                      </RequireRole>
                    </RequireAuth>
                  }
                />

        </Routes>
      </BrowserRouter>
    </AuthProvider>
    
  );
}

export default App;