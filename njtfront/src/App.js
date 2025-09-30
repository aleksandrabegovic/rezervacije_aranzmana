import logo from './logo.svg';
import './App.css';
import TravelShowcase from './pages/TravelShowcase';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from './pages/Login';
import Register from './pages/Register';
import NavBar from './components/NavBar';
function App() {
  return (
    <BrowserRouter>
    <NavBar></NavBar>
      <Routes>
            <Route path="/" element={<TravelShowcase></TravelShowcase>}></Route>
            <Route path="/login" element={<Login></Login>}></Route>
            <Route path="/register" element={<Register></Register>}></Route>

      </Routes>
    </BrowserRouter>
    
  );
}

export default App;
