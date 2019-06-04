import React, { Component } from 'react'
import { Menu } from 'semantic-ui-react'

export default class NavTabComponent extends Component {
    state = { activeItem: 'user' }

    handleItemClick = (e, { name }) => this.setState({ activeItem: name })

    render() {
        const { activeItem } = this.state

        return (
            <Menu inverted pointing vertical>
                <Menu.Item name='User List' active={activeItem === 'user'} onClick={this.handleItemClick} />
                <Menu.Item
                    name='Enroll'
                    active={activeItem === 'enroll'}
                    onClick={this.handleItemClick}
                />
                <Menu.Item
                    name='Recognize'
                    active={activeItem === 'recog'}
                    onClick={this.handleItemClick}
                />
            </Menu>
        )
    }
}