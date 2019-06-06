import React from 'react';
import HeaderComponent from './Components/HeaderComponent'
import NavTabComponent from './Components/NavTabComponent';
import './App.css';
import ViewComponent from "./Components/ViewComponent";
import {Segment} from 'semantic-ui-react'


class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // users: [],
            activeState: 'User List'
        };
        this.handleItemClick = this.handleItemClick.bind(this);
    }

    handleItemClick = (e, {name}) => this.setState({activeState: name});

    render() {

        const {activeState} = this.state;
        const w20style = {
            width: '20%'
        };

        const w80style = {
            width: '80%'
        };

        return (
            <div className='App'>
                <HeaderComponent/>
                <Segment.Group horizontal>
                    <Segment style={w20style}><NavTabComponent activeItem={activeState}
                                                               handleItemClick={this.handleItemClick}/></Segment>
                    <Segment style={w80style}><ViewComponent activeState={activeState}/></Segment>
                </Segment.Group>
            </div>

        )
    }
}


export default App;
