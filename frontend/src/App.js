import React, { setState } from 'react';
import { Component } from 'react';
import Container from 'react-bootstrap/Container';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import Story from './components/Story';
import GeneratedStory from './components/GeneratedStory';
import axios from 'axios'
import globals from './globals.js'

export default class App extends React.Component{

  constructor(props) {
    super(props)
    this.updateStory = this.updateStory.bind(this)
  }

  state = { 
    story: "u",
    prefix: 1,
    postfix: 1,
    output: 1,
    generatedStory : "..."
  }


  updateStory (params) {
    this.setState({
      [params.param] : [params.value]
  }, () => {
    // next action
  });  
  }

  async generateStory () {

    var filteredInput=this.state.story[0].replace(/[^a-z0-9 ,.;!?]/gi,'');
    let url=globals.SERVER_BASE_URL+"/generate"+
                    "?postfixSize="+this.state.postfix[0]+
                    "&prefixSize="+this.state.prefix[0]+
                    "&outputSize="+this.state.output[0]+
                    "&input="+filteredInput;

      const res= await axios.get(url)
      console.log(res)
      this.setState({
        generatedStory: res.data
        }, () => {
      this.forceUpdate()
     });  
  }


  render(){
      return (
        <div className="App">
          <header className="App-header">
              <h1>Ask Markov to re-write your story :)</h1>
              <Story parentUpdate={this.updateStory.bind(this)} parentGenerate={this.generateStory.bind(this)} />
              <GeneratedStory generatedStory={this.state.generatedStory} />
          </header>
        </div>
      )
    }
}

