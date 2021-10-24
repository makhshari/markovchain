import { Component } from 'react';
import FormControl from 'react-bootstrap/FormControl';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

export default class  Story extends Component  {
    handleChange = (event) => {
        const params={};
        params["param"]=event.target.id
        params["value"]=event.target.value
        this.props.parentUpdate(params);
    }
    submitStory = () => {
        this.props.parentGenerate();
      }
    render(){
    return(
        <div className="Story">
            <InputGroup className="mb-3">
            <InputGroup.Text>Name your story:</InputGroup.Text>
                <FormControl aria-label="story-title" id="title" />
            </InputGroup>
            <InputGroup>
            <InputGroup.Text>Write your story:</InputGroup.Text>
            <FormControl as="textarea" rows={10} aria-label="story" onChange={this.handleChange.bind(this)} id="story" />
            </InputGroup>
            <div style={{marginTop: '20px', marginBottom: '20px' }}>
                <div className="params" style={{marginTop: '10px', marginBottom: '10px' }}>
                    <Form className='d-flex'>
                        <Form.Group style={{padding: '20px'}} className="mb-6" controlId="prefix">
                            <Form.Label>Prefix</Form.Label>
                            <Form.Control size="sm"  type="number" placeholder="Number of words..." onChange={this.handleChange.bind(this)}   />
                        </Form.Group>
                        <Form.Group style={{padding: '20px'}}   className="mb-6" controlId="postfix">
                            <Form.Label>Postfix</Form.Label>
                            <Form.Control size="sm" type="number" placeholder="Number of words..." onChange={this.handleChange.bind(this)}   />
                        </Form.Group>
                        <Form.Group style={{padding: '20px'}}   className="mb-6" controlId="output">
                            <Form.Label>Output</Form.Label>
                            <Form.Control size="sm" type="number" placeholder="Number of words..." onChange={this.handleChange.bind(this)}   />
                        </Form.Group>
                    </Form>
                </div>
            <Button variant="info" onClick={this.submitStory} size="lg" >Hey Markov! Re-write my story!..</Button>
            </div>
        </div>
    )}
}