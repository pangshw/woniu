import { useAliveController } from 'umi';

/**
 * 开始下载
 */
export const startDownload = (res: any, fileName: string) => {
  let blob = new Blob([res], { type: 'application/octet-stream' });
  if (window.navigator.msSaveOrOpenBlob) {
    console.log('msSaveOrOpenBlob');
    navigator.msSaveBlob(blob, fileName)
  } else {
    let downloadElement = document.createElement('a');
    let href = window.URL.createObjectURL(blob); // 创建下载的链接
    downloadElement.href = href;
    downloadElement.download = fileName; // 下载后文件名
    console.log(downloadElement);
    document.body.appendChild(downloadElement);
    downloadElement.click(); // 点击下载
    document.body.removeChild(downloadElement); // 下载完成移除元素
    window.URL.revokeObjectURL(href); // 释放掉blob对象
  }
}

export const apiServer = () => {
  return 'http://47.93.127.115';
}
