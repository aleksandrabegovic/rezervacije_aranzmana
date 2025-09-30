import logo from './logo.svg';
import './App.css';
import TravelShowcase from './pages/TravelShowcase';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from './pages/Login';
import Register from './pages/Register';
import NavBar from './components/NavBar';
import { AuthProvider } from './auth/AuthContext';
function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
      <NavBar></NavBar>
        <Routes>
              <Route path="/" element={<TravelShowcase></TravelShowcase>}></Route>
              <Route path="/login" element={<Login></Login>}></Route>
              <Route path="/register" element={<Register></Register>}></Route>

        </Routes>
      </BrowserRouter>
    </AuthProvider>
    
  );
}

export default App;
