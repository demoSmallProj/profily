import React, { Component } from 'react';
import { Table} from 'antd';
import Moment from 'react-moment';
import 'moment-timezone';

class Transactions extends Component {

  render() {
    let transactions = [];
    if(this.props.transactions) {
      transactions = this.props.transactions;
    }
    const tblColumns = [
      {
        title: 'Date',
        dataIndex: 'date',
        key: 'date',
        render: date => <Moment format="YYYY-MM-DD HH:mm:ss">{date}</Moment>,
      },
      {
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
      },
      {
        title: 'Amount',
        dataIndex: 'amountCents',
        key: 'amountCents',
        render: cents => parseFloat( cents / 100).toFixed(2),
      },
    ];
    return (
      <div className="Transactions">
        <h2>Transactions</h2>
        <Table columns={tblColumns} dataSource={transactions} />
      </div>
    );
  }
}

export default Transactions;