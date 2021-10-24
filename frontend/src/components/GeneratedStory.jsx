import { Component } from 'react';
import TypeWriterEffect from 'react-typewriter-effect';

export default class GeneratedStory extends Component {
    render(){
    
        const myRef = document.querySelector('.scrollable-div')
        return(
            <div key={this.props.generatedStory} style={{marginTop: '20px', marginBottom: '40px' , marginLeft:'500px', marginRight:'500px' }}>
                <TypeWriterEffect
                    textStyle={{
                        fontFamily: " 'Poor Story', cursive",
                        fontWeight: 50,
                        fontSize: '1.1em',
                        textAlign: 'left'
                      }}
                    startDelay={100}
                    cursorColor="black"
                    text= {this.props.generatedStory}
                    typeSpeed={50}
                    scrollArea={myRef}
                />
              
            </div>
    )}

}
