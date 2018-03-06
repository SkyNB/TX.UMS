/* * 
 * 追加节点函数
 * 新易泰 刘文龙 2017年1月13日22:09:22
 * */
function cr_node(i, l = "body") { //创建节点并添加
	t = i.split(".").reverse()[0];
	var n = null;
	n = document.createElement({
		"js": "script",
		"css": "link"
	}[t]);
	if(t == "js") {
		n.setAttribute("type", "text/javascript");
		n.setAttribute("src", i);
	} else if(t == "css") {
		n.setAttribute("rel", "stylesheet");
		n.setAttribute("href", i);
	}
	if(n != null) {
		n.setAttribute("defer", "defer");
		if(l == "body") {
			document.body.appendChild(n);
		} else if(l == "head") {
			document.head.appendChild(n);
		} else {
			l.appendChild(n);
		}
	}
	return n;
}

function addNode(o, c = undefined) { //单个添加
	var n = null;
	if(typeof(o) == "object") {
		n = cr_node(o.src, (o.parent ? o.parent : "body"));
		if(typeof(o.load) == "function") {
			n.onload = o.load; //绑定加载事件
		}
		if(typeof(o.err) == "function") {
			n.onerror = o.err; //绑定错误事件
		}
	} else if(typeof(o) == "string") {
		n = cr_node(o); //直接添加节点
		if(typeof(c) == "function") {
			n.onerror = n.onload = c; //绑定加载事件
		}
	}
}

function addNodes(o, index = 0) { //多个添加
	if(o.src.length > index && typeof(o.src) == "object" && o.src.length > 0) {
		var n = cr_node(o.src[index], o.parent);
		if(o.src.length - 1 == index && typeof(o.load) == "function") {
			n.onerror = n.onload = o.load;
		} else {
			n.onerror = n.onload = function() {
				addNodes(o, index + 1);
			}
		}
	}
}