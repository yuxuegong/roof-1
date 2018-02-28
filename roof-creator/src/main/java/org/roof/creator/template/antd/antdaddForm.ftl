import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { routerRedux, Link } from 'dva/router';
import {
  Form, Input, DatePicker, Select, Button, Card, InputNumber, Radio, Icon, Tooltip,
} from 'antd';
import PageHeaderLayout from '../../../layouts/PageHeaderLayout';
import styles from '../Formstyle.less';

const FormItem = Form.Item;
const { Option } = Select;
const { RangePicker } = DatePicker;
const { TextArea } = Input;
<#assign key = primaryKeyFields[0] />

@connect(state => ({
  ${actionName}: state.${actionName},
}))
@Form.create()
export default class BasicForms extends PureComponent {

  state = {
  };

  componentDidMount() {
    const { dispatch } = this.props;

  }



  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        this.props.dispatch({
          type: '${actionName}/add',
          payload: values,
          callback: () => {
            this.props.dispatch(routerRedux.push('/${actionName}'));
          },
        });
      }
    });
  }
  render() {
    const { ${actionName}: { regularFormSubmitting:submitting } } = this.props;
    const { getFieldDecorator, getFieldValue } = this.props.form;

    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 },
        md: { span: 10 },
      },
    };

    const submitFormLayout = {
      wrapperCol: {
        xs: { span: 24, offset: 0 },
        sm: { span: 10, offset: 7 },
      },
    };

    return (
      <PageHeaderLayout title="" content="">
        <Card bordered={false}>
          <Form
            onSubmit={this.handleSubmit}
            hideRequiredMark
            style={{ marginTop: 8 }}
          >
          <#list fields as field>
              <#if (field.fieldName == key)>
                    {getFieldDecorator('${field.fieldName}', {

                    })(
                    <Input type="hidden"/>
                    )}
            <#else >
                <FormItem
                        {...formItemLayout}
                        label="${field.fieldDisplay}"
                >
                    {getFieldDecorator('${field.fieldName}', {
                    rules: [{
                      required: true, message: '请输入${field.fieldDisplay}',
                    }],
                    })(
                    <Input placeholder="" />
                    )}
                </FormItem>
            </#if>
          </#list>
            
            <FormItem {...submitFormLayout} style={{ marginTop: 32 }}>
              <Button type="primary" htmlType="submit" loading={submitting}>
                提交
              </Button>
                <Link to={'/${actionName}'}><Button style={{ marginLeft: 8 }}>取消</Button></Link>
            </FormItem>
          </Form>
        </Card>
      </PageHeaderLayout>
    );
  }
}
