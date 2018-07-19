import React, { Component } from 'react';
import { Table, Icon, Divider } from 'antd';

// ReactDOM.render(<Table columns={columns} dataSource={data} />, mountNode);
/*
"transactionsInMonth" : [ {
  "date" : "2016-07-02T19:43:51.000+0000",
  "amountCents" : -982,
  "description" : "IPSUM ADIPISCING"
}, {
  "date" : "2016-07-03T13:56:56.000+0000",
  "amountCents" : -515,
  "description" : "AUGUE"
},
  transactions*/


class Transactions extends Component {



  render() {

    let transactions = [];
    if(this.props.transactions) {
      transactions = this.props.transactions;
    }

    //
    // sorter: (a, b) => a.age - b.age,
    //   sortOrder: sortedInfo.columnKey === 'age' && sortedInfo.order,

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
      },
    ];




    const columns = [{
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      render: text => <a href="javascript:;">{text}</a>,
    }, {
      title: 'Age',
      dataIndex: 'age',
      key: 'age',
    }, {
      title: 'Address',
      dataIndex: 'address',
      key: 'address',
    }, {
      title: 'Action',
      key: 'action',
      render: (text, record) => (
        <span>
      <a href="javascript:;">Action 一 {record.name}</a>
      <Divider type="vertical" />
      <a href="javascript:;">Delete</a>
      <Divider type="vertical" />
      <a href="javascript:;" className="ant-dropdown-link">
        More actions <Icon type="down" />
      </a>
    </span>
      ),
    }];

    const data = [{
      key: '1',
      name: 'John Brown',
      age: 32,
      address: 'New York No. 1 Lake Park',
    }, {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park',
    }, {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sidney No. 1 Lake Park',
    }];



    return (
      <div className="Transactions">
        <h2>Transactions</h2>
        {/*<Table columns={columns} dataSource={data} />*/}
        <Table columns={tblColumns} dataSource={transactions} />
      </div>
    );
  }
}

export default Transactions;