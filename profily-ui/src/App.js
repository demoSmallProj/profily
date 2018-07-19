import React, {Component} from 'react';
import './App.css';
import Search from "./components/Search";
import Classification from "./components/Classification"
import Transactions from "./components/Transactions"

// import testData from "./testData.json"
class App extends Component {
  constructor() {
    super();
    // this.state = {response: testData};
    this.state = {response: {}};
  }

  handleSearch(customerId, selectedMonth) {

    let url = `http://localhost:8080/customer/${customerId}?month=${selectedMonth}-01`;
    console.log(url);
    fetch(url)
      .then(resp => resp.json())
      .then(json => this.setState({response: json}))
      .then(json => console.log(this.state.response))
      .catch(e => console.error(e));
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
