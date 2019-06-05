import React from 'react'
import axios from 'axios'
import {Button, Form, Icon, Segment, Table} from 'semantic-ui-react'

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
                this.setState({user: response});
                this.setState({isTableDisabled: false});
            }
        ).catch(
            error => {
                console.log(error)
            }
        )
    }

    render() {
        return (
            <Segment>
                <EnrollmentForm onNameChange={this.onNameChange} onImageChange={this.onImageChange}
                                onFormSubmit={this.onFormSubmit}/>
                <EnrollmentSummary isTableDisabled={this.state.isTableDisabled}/>
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
                            <Table.Cell>John</Table.Cell>
                            <Table.Cell>Approved</Table.Cell>
                            <Table.Cell>None</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </Segment>
        )
    }
}
