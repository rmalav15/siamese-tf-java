import React from 'react';
import {Button, Icon, Segment, Table} from "semantic-ui-react";
import axios from 'axios'

export class UserContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: null,
            isLoaded: false
        };
        this.removeUser = this.removeUser.bind(this);
        this.getUsers = this.getUsers.bind(this);
    }

    componentDidMount() {
        this.getUsers();
    }

    async getUsers() {
        // http://localhost:8080 explicitly added for debuggig as 'npm start' runs on port 3000
        const url = 'http://localhost:8080/user/get/all';
        await axios.get(url).then(
            response => {
                this.setState({users: response.data});
                this.setState({isLoaded: true});
            }
        ).catch(
            error => {
                console.log(error) //TODO: add tostr
            }
        )
    }

    removeUser(user) {
        // http://localhost:8080 explicitly added for debuggig as 'npm start' runs on port 3000
        axios.get('http://localhost:8080/user/delete/' + user.id)
            .then(this.getUsers)
            .catch(function (error) {
                console.log(error);
            })
    }

    render() {
        const users = this.state.users;
        return (
            <Segment inverted>
                <Table inverted>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Images#</Table.HeaderCell>
                            <Table.HeaderCell>Embeddings</Table.HeaderCell>
                            <Table.HeaderCell>Remove User</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        {users && users.map(
                            user => (
                                <Table.Row key={user.name}>
                                    <Table.Cell>{user.name} </Table.Cell>
                                    <Table.Cell> {user.numImages}</Table.Cell>
                                    <Table.Cell>{user.embedding.substring(1, 60) + '...'}</Table.Cell>
                                    <Table.Cell><Button type='submit' onClick={() => this.removeUser(user)}>
                                        <Icon name='remove user'/></Button></Table.Cell>
                                </Table.Row>
                            )
                        )}
                    </Table.Body>
                </Table>
            </Segment>
        )
    }
}