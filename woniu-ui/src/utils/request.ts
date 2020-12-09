import { request as umiRequest } from 'umi';

const API_HOST = "http://api.woniu365.net";

export const request = (url: any, options: any) => {
  let target = API_HOST + url;
  return umiRequest(target, options);
};
