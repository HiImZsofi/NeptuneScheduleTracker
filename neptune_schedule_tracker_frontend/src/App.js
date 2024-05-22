import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Registration from "./registration/Registration";
import Login from "./login/Login";

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/Registration" element={<Registration />} />
          <Route path="/Login" element={<Login />} />
        </Routes>
      </Router>
  );
}

export default App;