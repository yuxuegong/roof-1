import { stringify } from 'qs';
import request from '../../utils/request';

export async function query${alias?cap_first}(params) {
  return request(`/api/${projectName}/${actionName}?<#noparse>${stringify(params)}`</#noparse>);
}

export async function add${alias?cap_first}(params) {
  return request('/api/${projectName}/${actionName}', {
    method: 'POST',
    body: {
      ...params
    },
  });
}

export async function update${alias?cap_first}(params) {
  return request('/api/${projectName}/${actionName}/'+params['id'], {
    method: 'PUT',
    body: {
      ...params
    },
  });
}


export async function remove${alias?cap_first}(params) {
  return request(`/api/${projectName}/${actionName}/`+params['id'], {
    method: 'DELETE'
  });
}

export async function load${alias?cap_first}(params) {
  return request(`/api/${projectName}/${actionName}/`+params['id']);
}

export async function query${alias?cap_first}Base(params) {
return request(`/api/${projectName}/${actionName}/base/`);
}
