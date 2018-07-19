import React, {Component} from 'react';
import {Tag} from 'antd';

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
