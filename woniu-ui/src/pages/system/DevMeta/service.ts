import {request} from 'umi';

const TAG = "service";

export async function listMetadata(params: any) {
  return request('http://47.93.127.115:8100/api/metadata/list', {
    method: 'post',
    data: params,
    requestType: 'form',
  });
}

export async function saveMetadata(params: any) {
  return request('http://47.93.127.115:8100/api/metadata/save', {
    method: 'post',
    data: params,
  });
}

export async function getMetadata(id: number) {
  return request('http://47.93.127.115:8100/api/metadata/get', {
    method: 'post',
    data: {
      id,
    },
    requestType: 'form',
  });
}

export async function deleteMetadata(id: number) {
  return request('http://47.93.127.115:8100/api/metadata/delete', {
    method: 'post',
    data: {
      ids: Array.of(id)
    },
    requestType: 'form',
  });
}

export async function genPreview(id: number) {
  return request('http://47.93.127.115:8100/api/metadata/gen/preview', {
    method: 'post',
    data: {
      id: id
    },
    requestType: 'form',
  });
}

export async function genDownload(ids: any) {
  return request('http://47.93.127.115:8100/api/metadata/gen/download', {
    method: 'post',
    data: {
      ids: ids
    },
    requestType: 'form',
    responseType : 'blob',
  });
}

export async function genLocal(ids: any) {
  return request('http://47.93.127.115:8100/api/metadata/gen/local', {
    method: 'post',
    data: {
      ids: ids
    },
    requestType: 'form',
  });
}
