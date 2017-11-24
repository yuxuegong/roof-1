import { stringify } from 'qs';
import request from '../utils/request';

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
  return request('/api/${projectName}/${actionName}', {
    method: 'PUT',
    body: {
      ...params
    },
  });
}




export async function removeCharacterColor(params) {
  return request(`/api/${projectName}/${actionName}?<#noparse>${stringify(params)}</#noparse>`, {
    method: 'DELETE'
  });
}

export async function loadCharacterColor(params) {
  return request(`/api/${projectName}/${actionName}/`+params['id']);
}
