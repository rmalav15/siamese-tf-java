import React from 'react'
import axios from 'axios'
import {Button, Form, Icon, Segment, Table} from 'semantic-ui-react'
import toastr from 'toastr'
import 'toastr/build/toastr.min.css'

export default class EnrollmentContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: null,
            images: null,
            user: null,
            isTableDisabled: true
        };
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onImageChange = this.onImageChange.bind(this);
        this.onNameChange = this.onNameChange.bind(this);
    }

    onNameChange(e) {
        this.setState({name: e.target.value});
    }

    onImageChange(e) {
        this.setState({images: e.target.files});
    }

    onFormSubmit(e) {
        e.preventDefault();// Stop form submit

        toastr.options = {
            positionClass : 'toast-top-right',
            hideDuration: 10,
            timeOut: 5000
        };

        // http://localhost:8080 explicitly added for debuggig as 'npm start' runs on port 3000
        const url = 'http://localhost:8080/enroll/new';
        const formData = new FormData();

        if (this.state.images) {
            for (const image of this.state.images) {
                formData.append('files', image);
            }
        }
        formData.append('name', this.state.name);
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        };
        axios.post(url, formData, config).then(
            response => {
                this.setState({user: response.data});
                this.setState({isTableDisabled: false});
                toastr.success(`User ${this.state.name} enrolled`)
            }
        ).catch(
            error => {
                toastr.error(`Unable to enroll.`)
            }
        )
    }

    render() {
        return (
            <Segment>
                <EnrollmentForm onNameChange={this.onNameChange} onImageChange={this.onImageChange}
                                onFormSubmit={this.onFormSubmit}/>
                <EnrollmentSummary user={this.state.user} isTableDisabled={this.state.isTableDisabled}/>
            </Segment>
        )
    }

}

class EnrollmentForm extends React.Component {
    render() {
        return (

            <Segment inverted>
                <Form inverted>
                    <Form.Group widths='equal'>
                        <Form.Input fluid type="text" label='Name' placeholder='Name'
                                    onChange={this.props.onNameChange}/>
                        <Form.Input fluid multiple type="file" label='Images' onChange={this.props.onImageChange}/>
                    </Form.Group>
                    {/*<Form.Checkbox label='I agree to the Terms and Conditions'/>*/}
                    <Button type='submit' onClick={this.props.onFormSubmit}> <Icon name='upload'/> Submit</Button>
                </Form>
            </Segment>
        )
    }

}

class EnrollmentSummary extends React.Component {

    render() {

        const {user} = this.props;
        /*const wrapStyle = {
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            whiteSpace: 'nowrap',
            color: 'red'
        };*/
        return (
            <Segment inverted>
                <Table inverted>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Images#</Table.HeaderCell>
                            <Table.HeaderCell>Embeddings</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        <Table.Row disabled={this.props.isTableDisabled}>
                            <Table.Cell>{user ? user.name : "Enroll User" }</Table.Cell>
                            <Table.Cell> {user? user.numImages: ""} </Table.Cell>
                            <Table.Cell>{user ? user.embedding.substring(1, 35) + '...': ""}</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </Segment>
        )
    }
}
