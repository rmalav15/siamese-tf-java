import React, {Component} from 'react'
import {Menu} from 'semantic-ui-react'

export default class NavTabComponent extends Component {
    /*constructor(props) {
        super(props);
    }*/


    render() {
        const {activeItem} = this.props;

        return (
            <Menu inverted pointing vertical>
                <Menu.Item name='User List' active={activeItem === 'User List'} onClick={this.props.handleItemClick}/>
                <Menu.Item
                    name='Enroll'
                    active={activeItem === 'Enroll'}
                    onClick={this.props.handleItemClick}
                />
                <Menu.Item
                    name='Recognize'
                    active={activeItem === 'Recognize'}
                    onClick={this.props.handleItemClick}
                />
            </Menu>
        )
    }
}