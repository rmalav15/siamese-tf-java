import React, {Component} from 'react'
import {UserContainer} from "./ViewContainers/UserContainer";
import {RecognizeContainer} from "./ViewContainers/RecognizeContainer";
import EnrollmentContainer from './ViewContainers/EnrollmentContainer'


export default class ViewComponent extends Component {


    render() {

        if (this.props.activeState === 'Enroll') {
            return (
                <EnrollmentContainer/>
            )
        }
        else if(this.props.activeState === 'User List') {
            return (
                <UserContainer/>
            )
        }
        else if(this.props.activeState === 'Recognize'){
            return (
                <RecognizeContainer/>
            )
        }
    }
}