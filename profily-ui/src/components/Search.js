import React, { Component } from 'react';
// import Button from 'antd/lib/button';

import { Form, Icon, Input, Button } from 'antd';
import { DatePicker } from 'antd';


const FormItem = Form.Item;
const { MonthPicker} = DatePicker;




function hasErrors(fieldsError) {
  return Object.keys(fieldsError).some(field => fieldsError[field]);
}

class HorizontalLoginForm extends React.Component {
  componentDidMount() {
    // To disabled submit button at the beginning.
    this.props.form.validateFields();
  }




/*
  handleSubmit(e) {
    if(this.refs.title.value === '') {
      alert('title is required');
    } else {
      this.setState({
        newProject:{
          id:uuidv4(),
          title:this.refs.title.value,
          category:this.refs.category.value
        }
      }, function() {
        this.props.addProject(this.state.newProject)
        // console.log(this.state);
      });
    }
    e.preventDefault();
    console.log(this.refs.title.value);
  }*/



  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.setState({customerId: values.customerId}, function() {
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
    const { getFieldDecorator, getFieldsError, getFieldError, isFieldTouched } = this.props.form;

    // Only show error after a field is touched.
    const customerIdError = isFieldTouched('customerId') && getFieldError('customerId');
    // const passwordError = isFieldTouched('password') && getFieldError('password');
    const monthError = isFieldTouched('month') && getFieldError('month');
    return (
      <Form layout="inline" onSubmit={this.handleSubmit} className="SearchForm">
        <FormItem
          validateStatus={customerIdError ? 'error' : ''}
          help={customerIdError || ''}
        >
          {getFieldDecorator('customerId', {
            rules: [{ required: true, message: 'Please input Customer Id!' }],
          })(
            <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Customer Id" />
          )}
        </FormItem>
{/*        <FormItem
          validateStatus={passwordError ? 'error' : ''}
          help={passwordError || ''}
        >
          {getFieldDecorator('password', {
            rules: [{ required: true, message: 'Please input your Password!' }],
          })(
            <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
          )}
        </FormItem>*/}
        <FormItem
          validateStatus={monthError ? 'error' : ''}
          help={monthError || ''}
        >
          {getFieldDecorator('month', {
            rules: [{ required: true, message: 'Please select month!' }],
          })(
            <MonthPicker onChange={this.onMonthChange.bind(this)} placeholder="Select month" />
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
