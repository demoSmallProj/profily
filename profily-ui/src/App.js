import React, { Component } from 'react';
import './App.css';
import Search from "./components/Search";
import Classification from "./components/Classification"
import Transactions from "./components/Transactions"

import testData from "./testData.json"

class App extends Component {


  /*

  {
    "customerId" : 1,
    "classifications" : [ {
      "key" : "BIG_SPENDER",
      "description" : "Big Spender"
    } ],
    "dateOfBalance" : "2018-07-19T05:10:43.712+0000",
    "balance" : 42132,
    "monthDate" : "2016-06-30T14:00:00.000+0000",
    "transactionsInMonth" : [ {
      "date" : "2016-07-02T19:43:51.000+0000",
      "amountCents" : -982,
      "description" : "IPSUM ADIPISCING"
    }, {
      "date" : "2016-07-03T13:56:56.000+0000",
      "amountCents" : -515,
      "description" : "AUGUE"
    }, {
      "date" : "2016-07-04T14:46:02.000+0000",
      "amountCents" : -1857,
      "description" : "SIT"
    }, {

  */

  constructor(){
    super();
    this.state = {response : testData};
    console.log(testData);
  }
  //
  // constructor() {
  //   }
  //   this.state = {
  //     projects: []
  // }

  handleSearch(customerId, selectedMonth) {

    let url = `http://localhost:8080/customer/${customerId}?month=${selectedMonth}-01`;
    console.log(url);
    fetch(url)
      .then(resp=> resp.json())
      .then(json => this.setState({response: json}))
      .then(json => console.log(this.state.response))
      .catch(e => console.error(e));

    //
    //
    // let prjs = this.state.projects;
    // prjs.push(project);
    // this.setState({projects:prjs});
    // console.log(project);
  }

  render() {
    return (
      <div className="App">
        <h1>Customer Profiling</h1>
        <Search onSearch={this.handleSearch.bind(this)}/>
        <Classification classifications={this.state.response.classifications}
                        customerId={this.state.response.customerId}
                        dateOfBalance={this.state.response.dateOfBalance}
                        balance={this.state.response.balance}
                        monthDate={this.state.response.monthDate}/>
        <Transactions transactions={this.state.response.transactionsInMonth}/>
      </div>
    );
  }
}

export default App;
