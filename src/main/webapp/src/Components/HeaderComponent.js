import React from 'react'
import {Header, Image, Segment} from 'semantic-ui-react'

const HeaderComponent = () => (
    <Segment inverted>
        <Header
            as='h1'
            textAlign='center'
        >
            <Image circular size='tiny' src='https://upload.wikimedia.org/wikipedia/commons/2/2d/Tensorflow_logo.svg'/>
            <Image circular size='tiny'
                   src='http://assets.stickpng.com/thumbs/58480979cef1014c0b5e4901.png'/>
            TF-Java: Signature Recognition App
        </Header>
    </Segment>
);

export default HeaderComponent