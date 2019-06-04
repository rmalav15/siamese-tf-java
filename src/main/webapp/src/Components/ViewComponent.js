import React, {Component} from 'react'
import EnrollmentContainer from './ViewContainers/EnrollmentContainer'


export default class ViewComponent extends Component {
    /*constructor(props) {
        super(props);
    }*/

    render() {

        return (
            <div>
                {/*if (this.props.activeState === 'enroll') {*/}

                <EnrollmentContainer/>
            </div>
        )
    }
}