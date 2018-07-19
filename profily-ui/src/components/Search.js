import React from 'react';

import {Button, DatePicker, Form, Icon, Input} from 'antd';
const FormItem = Form.Item;
const {MonthPicker} = DatePicker;


function hasErrors(fieldsError) {
  return Object.keys(fieldsError).some(field => fieldsError[field]);
}

class HorizontalLoginForm extends React.Component {
  componentDidMount() {
    this.props.form.validateFields();
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.setState({customerId: values.customerId}, function () {
          this.props.onSearch(this.state.customerId, this.state.selectedMonth)
        })
        console.log('Received values of form: ', values);
      }
    });
  }

  onMonthChange(date, dateString) {
    console.log(date);
    console.log(dateString);
    this.setState({selectedMonth: dateString})
  }

  render() {
    const {getFieldDecorator, getFieldsError, getFieldError, isFieldTouched} = this.props.form;
    const customerIdError = isFieldTouched('customerId') && getFieldError('customerId');
    const monthError = isFieldTouched('month') && getFieldError('month');
    return (
      <Form layout="inline" onSubmit={this.handleSubmit} className="SearchForm">
        <FormItem
          validateStatus={customerIdError ? 'error' : ''}
          help={customerIdError || ''}
        >
          {getFieldDecorator('customerId', {
            rules: [{required: true, message: 'Please input Customer Id!'}],
          })(
            <Input prefix={<Icon type="user" style={{color: 'rgba(0,0,0,.25)'}}/>} placeholder="Customer Id"/>
          )}
        </FormItem>
        <FormItem
          validateStatus={monthError ? 'error' : ''}
          help={monthError || ''}
        >
          {getFieldDecorator('month', {
            rules: [{required: true, message: 'Please select month!'}],
          })(
            <MonthPicker onChange={this.onMonthChange.bind(this)} placeholder="Select month"/>
          )}

        </FormItem>
        <FormItem>
          <Button
            type="primary"
            htmlType="submit"
            disabled={hasErrors(getFieldsError())}
          >
            Search
          </Button>
        </FormItem>
        <br/>
      </Form>
    );
  }
}

const Search = Form.create()(HorizontalLoginForm);

export default Search;
