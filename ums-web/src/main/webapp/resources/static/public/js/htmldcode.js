 function htmldecode(s) { //读取解密用
    var div = document.createElement('div');
    div.innerHTML = s;
    return div.innerText || div.textContent;
}
