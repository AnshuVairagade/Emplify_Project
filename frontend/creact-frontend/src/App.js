
import './App.css';
import ListEmployeeComponent from './components/ListEmployeeComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';

import {BrowserRouter as Router, Switch, Route, Link, Routes} from "react-router-dom";
import AddEmployeeComponent from './components/AddEmployeeComponent';

function App() {
  return (
    <div className="App">
      <Router>
      <HeaderComponent/>
      <Routes>
        <Route exact path='/' Component={ListEmployeeComponent}></Route>
        <Route exact path='/employees' Component={ListEmployeeComponent}></Route>
        <Route exact path='/add-employee' Component={AddEmployeeComponent}></Route>
        <Route exact path='/edit-employee/:id' Component={AddEmployeeComponent}></Route>
      </Routes>
      <FooterComponent/>
      </Router>
    </div>
  );
}

export default App;
