import React, {Component} from 'react';
import {Tag} from 'antd';
import Moment from 'react-moment';
import 'moment-timezone';

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
        <div><strong>Customer Id:</strong> {this.props.customerId}</div>
        <div><strong>Request time:</strong> <Moment format="YYYY-MM-DD HH:mm:ss">{this.props.dateOfBalance}</Moment></div>
        <div><strong>Current Balance:</strong> { this.props.balance ? parseFloat( this.props.balance / 100).toFixed(2) : undefined }</div>
        <br/>
      </div>
    );
  }
}

export default Classification;
