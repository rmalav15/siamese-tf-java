import React from 'react'
import axios from 'axios';

export default class EnrollmentContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            file: null,

        };
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.fileUpload = this.fileUpload.bind(this);
    }

    onFormSubmit(e) {
        e.preventDefault();// Stop form submit
        this.fileUpload(this.state.file).then((response) => {
            console.log(response.data);
        })
    }

    onChange(e) {
        this.setState({file: e.target.files});
    }

    fileUpload(file) {
        const url = 'http://localhost:8080/enroll/new';
        const formData = new FormData();
        if(file) {
            for (const image of file) {
                formData.append('files', image);
            }
        }
        formData.append('name', 'name');
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        };
        return axios.post(url, formData, config)
    }

    render() {
        return (
            <form onSubmit={this.onFormSubmit}>
                <h1>File Upload</h1>
                <input multiple type="file" onChange={this.onChange}/>
                <button type="submit">Upload</button>
            </form>
        )
    }

}

/*
class EnrollmentForm extends React.Component {

    render() {
        return (

            <Segment inverted>
                <Form inverted>
                    <Form.Group widths='equal'>
                        <Form.Input fluid type="text" label='Name' placeholder='Name'/>
                        <Form.Input fluid type="file" onChange={this.props.onChange}/>
                    </Form.Group>
                    {/!*<Form.Checkbox label='I agree to the Terms and Conditions'/>*!/}
                    <Button type='submit'>Submit</Button>
                </Form>
            </Segment>
        )
    }

}

class EnrollmentSummary extends React.Component {

    constructor(props) {
        super(props);
        // this.state = {isDisabled: true};
    }

    render() {
        const {activeItem} = this.state;

        return (
            <Segment inverted>
                <Table
                    inverted
                    disabled={this.props.isTableDisabled}
                >
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Images#</Table.HeaderCell>
                            <Table.HeaderCell>Embeddings</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        <Table.Row>
                            <Table.Cell>John</Table.Cell>
                            <Table.Cell>Approved</Table.Cell>
                            <Table.Cell>None</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </Segment>
        )
    }
}*/
