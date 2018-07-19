import React, {Component} from 'react';
import {Tag} from 'antd';

/*
{/!*<Classification*!/}
                {/!*classifications={this.state.classifications}*!/}
                {/!*customerId={this.state.customerId}*!/}
                {/!*dateOfBalance={this.state.dateOfBalance}*!/}
                {/!*balance={this.state.balance}*!/}
                {/!*monthDate={this.state.monthDate}/>*!/}
{/!*{this.props.classifications}*!/}


let projectItems;
if (this.props.projects) {
  projectItems = this.props.projects.map(project => {
    return (
      <ProjectItem key={project.title} project={project} onDelete={this.deleteProject.bind(this)}/>
    );
  })
}
*/


class Classification extends Component {
  render() {
    let classifications;
    if (this.props.classifications) {
      classifications = this.props.classifications.map(classification => <Tag color="geekblue">{classification.description}</Tag>)
    }
    return (
      <div className="Classification">
        <h2>Customer Information</h2>
        <div>
          <strong>Classifications</strong>: {classifications}
        </div>
        <div><strong>Customer ID:</strong> {this.props.customerId}</div>
        <div><strong>Request time:</strong> {this.props.dateOfBalance}</div>
        <div><strong>Current Balance CENTS:</strong> {this.props.balance}</div>
        <br/>
      </div>
    );
  }
}

export default Classification;
