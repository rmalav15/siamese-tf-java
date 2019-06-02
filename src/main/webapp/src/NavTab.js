import React from 'react';
import {Button, Card, CardText, CardTitle, Col, Nav, NavItem, NavLink, Row, TabContent, TabPane} from 'reactstrap';
import {UserList} from "./UserContainer";
import {EnrollForm} from "./EnrollementContainer";
import classnames from 'classnames';

export class NavTab extends React.Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            activeTab: 'user'
        };
    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }

    render() {
        return (
            <div>
                <Nav tabs>

                    <NavItem>
                        <NavLink
                            className={classnames({active: this.state.activeTab === 'user'})}
                            onClick={() => {
                                this.toggle('user');
                            }}
                        >
                            User List
                        </NavLink>
                    </NavItem>

                    <NavItem>
                        <NavLink
                            className={classnames({active: this.state.activeTab === 'enroll'})}
                            onClick={() => {
                                this.toggle('enroll');
                            }}
                        >
                            Enroll
                        </NavLink>
                    </NavItem>

                    <NavItem>
                        <NavLink
                            className={classnames({active: this.state.activeTab === 'recognize'})}
                            onClick={() => {
                                this.toggle('recognize');
                            }}
                        >
                            Recognize
                        </NavLink>
                    </NavItem>
                </Nav>


                <TabContent activeTab={this.state.activeTab}>
                    <TabPane tabId="user">
                        <UserList/>
                    </TabPane>

                    <TabPane tabId="enroll">
                        <EnrollForm/>
                    </TabPane>

                    <TabPane tabId="recognize">
                        <Row>
                            <Col sm="6">
                                <Card body>
                                    <CardTitle>Special Title Treatment</CardTitle>
                                    <CardText>With supporting text below as a natural lead-in to additional
                                        content.</CardText>
                                    <Button>Go somewhere</Button>
                                </Card>
                            </Col>
                            <Col sm="6">
                                <Card body>
                                    <CardTitle>Special Title Treatment</CardTitle>
                                    <CardText>With supporting text below as a natural lead-in to additional
                                        content.</CardText>
                                    <Button>Go somewhere</Button>
                                </Card>
                            </Col>
                        </Row>
                    </TabPane>

                </TabContent>
            </div>
        );
    }
}