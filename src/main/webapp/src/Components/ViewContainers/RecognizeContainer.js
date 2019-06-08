import React from 'react'
import axios from 'axios'
import {Button, Form, Icon, Segment, Table} from 'semantic-ui-react'
import toastr from 'toastr'
import 'toastr/build/toastr.min.css'

export class RecognizeContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            image: null,
            minConfidence: 7.0,
            topTwoMinGap: 2.0,
            isTableDisabled: true,
            result: null
        };
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onImageChange = this.onImageChange.bind(this);
        this.onConfChange = this.onConfChange.bind(this);
        this.onGapChange = this.onGapChange.bind(this);
    }

    onConfChange(e) {
        this.setState({minConfidence: e.target.value});
    }

    onGapChange(e) {
        this.setState({topTwoMinGap: e.target.value});
    }

    onImageChange(e) {
        this.setState({image: e.target.files[0]});
    }

    onFormSubmit(e) {
        e.preventDefault();// Stop form submit

        toastr.options = {
            positionClass: 'toast-top-right',
            hideDuration: 10,
            timeOut: 5000
        };

        // http://localhost:8080 explicitly added for debuggig as 'npm start' runs on port 3000
        const url = 'http://localhost:8080/recog';
        const formData = new FormData();

        formData.append('image', this.state.image);
        formData.append('minConfidence', this.state.minConfidence);
        formData.append('topTwoMinGap', this.state.topTwoMinGap);
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        };
        axios.post(url, formData, config).then(
            response => {
                this.setState({result: response.data});
                this.setState({isTableDisabled: false});
                toastr.success(`Recognition Successful`)
            }
        ).catch(
            error => {
                toastr.error(`Recognition Failed`)
            }
        )
    }

    render() {
        return (
            <Segment>
                <RecognizeForm onConfChange={this.onConfChange} onImageChange={this.onImageChange}
                               onFormSubmit={this.onFormSubmit} onGapChange={this.onGapChange}
                               minConfidence={this.state.minConfidence} topTwoMinGap={this.state.topTwoMinGap}/>
                <RecognizeSummary {...this.state}/>
            </Segment>
        )
    }

}

class RecognizeForm extends React.Component {


    render() {

        /*const gapInfo = (<Popup
            trigger={<Icon inverted name='info circle'/>}
            content="The default theme's basic popup removes the pointing arrow."
            basic
        />);*/

        return (

            <Segment inverted>
                <Form inverted>
                    <Form.Group widths='equal'>
                        <Form.Input fluid type="file" label='Image' onChange={this.props.onImageChange}/>
                        <Form.Input fluid type="text" label='Max Distance Allowed' color='#808080'
                                    value={this.props.minConfidence}
                                    onChange={this.props.onConfChange}/>
                        <Form.Input fluid type="text" label='Min Gap Between Top Two' color='#808080'
                                    value={this.props.topTwoMinGap}
                                    onChange={this.props.onGapChange}/>
                    </Form.Group>
                    {/*<Form.Checkbox label='I agree to the Terms and Conditions'/>*/}
                    <Button type='submit' onClick={this.props.onFormSubmit}> <Icon name='upload'/> Get Match</Button>
                </Form>
            </Segment>
        )
    }

}

class RecognizeSummary extends React.Component {

    render() {

        const {result} = this.props;
        let match, topName;
        if (result) {
            match = result.bestMatch;
            if (result.userList.length > 0) {
                topName = result.userList[0].key.name;
            }
        }

        const greenStyle = {
            backgroundColor: '#9AFB88'
        };

        const redStyle = {
            backgroundColor: '#E8BFBF'
        };

        return (
            <Segment inverted>
                <Table>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Distance</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>

                    <Table.Body>
                        {result && result.userList.map(
                            user => (
                                <Table.Row key={user.key.name}
                                           style={match && topName === user.key.name ? greenStyle : redStyle}>
                                    <Table.Cell>{user.key.name} </Table.Cell>
                                    <Table.Cell> {user.value}</Table.Cell>
                                </Table.Row>
                            )
                        )}
                    </Table.Body>
                </Table>
            </Segment>
        )
    }
}