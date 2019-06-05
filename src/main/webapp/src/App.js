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

        return (
            <div className='App'>
                <HeaderComponent/>
                <Segment.Group horizontal>
                    <Segment><NavTabComponent activeItem={activeState}
                                                         handleItemClick={this.handleItemClick}/></Segment>
                    <Segment><ViewComponent activeState={activeState}/></Segment>
                </Segment.Group>
            </div>

        )
    }
}


export default App;
