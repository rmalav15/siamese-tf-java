import React from 'react';
import {NavTab} from './NavTab';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
    return (
        <div className="App">
            <h3 className="text-center">TF-Java: Signature Recognition App</h3>
            <NavTab className="NavTab shadow"/>
        </div>
    );
}

export default App;
