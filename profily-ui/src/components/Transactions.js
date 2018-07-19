import React, { Component } from 'react';
import { Table, Icon, Divider } from 'antd';
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