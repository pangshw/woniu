export function tableScrollHeight() {
  let dom = document.getElementsByClassName("page-content")[0];
  if (!dom) {
    return 0;
  }
  let h = dom.clientHeight
    - parseInt(getStyle(dom, 'marginTop')) - parseInt(getStyle(dom, 'marginBottom'))
    - parseInt(getStyle(dom, 'paddingTop')) - parseInt(getStyle(dom, 'paddingBottom'))
    - document.getElementsByClassName("ant-table-thead")[0].clientHeight;

  dom.childNodes.forEach((item: any, index) => {
    if (!item.getAttribute('class') || item.getAttribute('class').indexOf('ant-table-wrapper') === -1) {
      h -= item.clientHeight;
    }
  });
  return h;
}

const getStyle = (obj: any, attr: any) => {
  if (obj.currentStyle) {
    return obj.currentStyle[attr];
  } else {
    return document.defaultView.getComputedStyle(obj, null)[attr];
  }
}
