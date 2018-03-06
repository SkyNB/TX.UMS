/*
 * vertical_layout
 * 新易泰 刘文龙
 * 2017年1月16日10:49:38
 */
///dropdown menu
Vue.component('dropdown-menu', {
	props: ['chindren'],
	methods: {
		increment: function(i, index) {
			main.addNewTab(i, index);
		}
	},
	template: '<ul class="dropdown-menu">' +
		'    <li class="dropdown-submenu" v-for="(itm3,index3) in chindren" v-if="itm3&&itm3.chindren"><a href="#">{{itm3.menuname}}</a>' +
		'        <dropdown-menu v-bind:chindren="itm3.chindren"></dropdown-menu>' +
		'    </li>' +
		'    <li v-else><a v-bind:data-id="itm3.menuid" v-bind:data-href="itm3.menuurl" onclick="addTab(this)">{{itm3.menuname}}</a>' +
		'    </li>' +
		'</ul>'
});
 
var layout;
var tabStrip;
var tmpTimeout = null;
$(function() {
	layout = new Vue({
		el: "#main",
		data: {
			menuBar: []
		},
		methods: {
			loadMenu: function(url, data) {
				if(data==undefined){
					data= {};
				}
				$.ajax({
					type: "GET",
					url: url,
					data: data,
					dataType: "json",
					success: function(data) {
						layout.menuBar = transData(data, "menuId", "parentId", "chindren");
					}
				});
			},
			loadSetting: function() {
				this.menuBar = store.get('menubar');
			}
		},
		watch: {
			menuBar: {
				handler: function(val, oldVal) {
					store.set('menubar', val);
				},
				deep: true
			}
		}
	});

	layout.loadSetting();
	// //---加载菜单
	// $.getScript("./public/js/to_treeJson.js", function() {
	// 	layout.loadMenu("menu_data.json");
	// });
	//-----选项卡
	tabStrip = $("#tabstrip").kendoTabStrip({ //创建
		activate: function() {
			tmpTimeout = setTimeout(function() {
				$(".k-content").height(getMainH());
			}, 10);
		}
	}).data("kendoTabStrip");

	$(".removeItem").click(function(e) { //删除
		var tab = tabStrip.select(),
			otherTab = tab.next();
		otherTab = otherTab.length ? otherTab : tab.prev();
		tabStrip.remove(tab);
		tabStrip.select(otherTab);
	});
	//---自适应--
	$(window).resize(function() {
		$(".k-content").height(getMainH());
	});
	$(document).ready(function() {
		$(".k-content").height(getMainH());
	});
	//------
	if($("#main_loading").length){
		$("#main_loading").fadeOut(300);
		store.set('layout');
	}
});

function configureCloseTab() { //创建关闭
	var tabsElements = $('#tabstrip li[role="tab"]');
	$.each(tabsElements, function(i, o) {
		if($(o).find('[data-id]').length > 0 && $(o).find('[data-type="remove"]').length <= 0) {
			$(o).append('<span data-type="remove" onclick="rmTab(event)" class="k-link"><span class="k-icon k-font-icon k-i-x"></span></span>');
		}
	});
};

function addTab(dom) { //添加
	var domDid = $(dom).attr("data-id");
	if(domDid == undefined) {
		return;
	}
	var tabstripItems = $(".k-tabstrip-items li.k-item");
	for(var i = 0; i < tabstripItems.length; i++) {
		var tmpId = $(tabstripItems[i]).find('[data-id]');
		if(tmpId != undefined) {
			tmpId = tmpId.attr("data-id");
			if(tmpId == domDid) {
				$(tabstripItems[i])[0].click();
				return;
			}
		}
	}
	tabStrip.append({
		encoded: false, //false允许html代码
		text: '<span data-id="' + domDid + '">' + $(dom).text() + '</span>',
		content: "<iframe src='" +$(dom).attr("data-href") + "'></iframe>"
			//						contentUrl:'http://erp.ym.com/'+$(dom).attr("data-href")
	});
	configureCloseTab();
	$(".k-tabstrip-items li.k-item")[$(".k-tabstrip-items li.k-item").length - 1].click();
}


function rmTab(e) { //关闭事件
	e.preventDefault();
	e.stopPropagation();
	var item = $(e.target).closest(".k-item");
	tabStrip.remove(item.index());
	if(item.hasClass('k-state-active')) {
		if(tabStrip.items().length > 1) {
			tabStrip.select(tabStrip.items().length - 1);
		} else if(tabStrip.items().length > 0) {
			tabStrip.select(0);
		}
	}
}

function getMainH() { //获取内容主体高度
	return window.innerHeight - $("#navbar-second").outerHeight() - $("#tabstrip ul.k-tabstrip-items").outerHeight() - 2;
}