/**
 * 新易泰 刘文龙
 *2017年1月17日17:42:38 
 */
//组件区
//--搜索栏-输入框
Vue.component('sb-input', {
	props: {
		type: { /*输入框类型*/
			type: String,
			default: 'text'
		},
		label: { /*显示文字*/
			type: String,
			default: ''
		},
		value: { /*绑定值*/
			type: String,
			default: ''
		},
		col: { /*lable占用列数量*/
			type: Number,
			default: 4
		},
		placeholder: {
			type: String,
			default: ""
		},
		id: {
			type: String,
			default: ""
		},
		readonly: {
			type: Boolean,
			default: ""
		}
	},
	methods: {
		updateValue: function(value) {
			/*this.$refs.input.value = value;过滤值后赋值*/
			this.$emit('input', value); /*通过 input 事件发出数值*/
		}
	},
	template: '<div class="col-md-2"><div class="form-group ">' +
		'        <label class="control-label" v-bind:class="\'col-sm-\'+col" v-if="label" >{{label}}</label>' +
		'        <div v-bind:class="[label?\'col-sm-\'+(12-col):\'col-sm-12\']">' +
		'            <input v-bind:id="id" v-bind:placeholder="placeholder" ref="input" v-bind:type="type" class="form-control" v-bind:value="value" v-on:input="updateValue($event.target.value)" v-on:change="updateValue($event.target.value)" v-on:blur="updateValue($event.target.value)">' +
		'        </div>' +
		'</div></div>'
});

//全局变量区
var app;
var datepicker;
var tbTmp;
$(document).ready(function() {
	//以下是Vue区
	app = new Vue({
		el: "#app",
		data: {
			searchBar: { /*------搜索栏*/
				show: false, //显示隐藏搜索栏
				data: { //搜索栏绑定数据
					text: "111",
					date: null,
					dpd: null,
					dpd2: ""
				},
				tag: { //附加数据
					testList: [{ //比如这是 下拉列的数据
						text: "Black",
						value: "1"
					}, {
						text: "Orange",
						value: "2"
					}, {
						text: "Grey",
						value: "3"
					}]
				}
			},
			tableOption: { /*<-----表格配置*/
				//					editable: true,
				dataSource: {
					schema: {
						model: {
							id: "userid",
							fields: {userid: { editable: false, nullable: true }}
						},
//						data:"用户数据.txt"
					},

					data: [], //实际数据
					pageSize: 20 //分页大小
				},
				height: getMainH(), //高度
				groupable: true, //分组
				//					scrollable: {
				//                          virtual: true//横向滑块
				//                      },
				columnMenu: true, //列头菜单
				filterable: true, //筛选
				sortable: true, //排序
				resizable: true, //可拖拽改变列宽
				pageable: {
					//						 numeric: false,//是否显示页码
					//                          previousNext: false,//是否显示下一页
					refresh: true, //是否刷新按钮
					pageSizes: true, //是否分页按钮
					buttonCount: 5, //是否显示最大的分页页码数量
					//						 messages: {
					//                              display: "Showing {2} data items"
					//                          }//自定义消息
				},
				//					toolbar: "aaaaa",//工具栏
				selectable: "multiple", //多选----row cell;multiple, row ;multiple cell
				columns: [{ //列头数据
					field: "userId", //绑定的ID
					title: "userId", //绑定的文字
					hidden: true
				}, {
					columnMenu: false, //是否允许列头菜单 
					filterable: false, //是否允许筛选
					field: "userName",
					title: "userName",
					width: 240 //宽度
				}, {
					field: "realName",
					title: "realName",
					width: 150
				}, {
					field: "userSex",
					title: "userSex",
					width: 150
				}, {
					title: "操作",
					template: '<a>删除</a>' //自定义模板
				}],
				//				change: onChange, //选中改变事件
				//				//					dataBound: onDataBound,//
				//				//					dataBinding: onDataBinding,//
				//				sort: onSorting,
				//				filter: onFiltering,
				//				group: onGrouping,
				//				page: onPaging,
			},
			selectIndex: 0,
			toast: { /*<----弹出消息配置*/
				show: false,
				text: "",
				time: 2000
			}
		},
		methods: {
			getTableData: function(url, data = {}) {
				$.ajax({
					type: "GET",
					url: url,
					data: data,
					dataType: "json",
					success: function(data) {
//						app.tableOption.dataSource.schema.data=data;
						
						app.tableOption.dataSource.data = data;
					}
				});
			},
			resetTable:function(){//重置表格
				var tbp = objCopy(app.tableOption);
				tbp.change = onChange;
				tbTmp = $("#grid").kendoGrid(tbp);
			},
			//-----Toast
			showToast: function() {
				this.toast.show = true
				if(this.toastTimer) clearTimeout(this.toastTimer);
				this.toastTimer = setTimeout(() => {
					app.toast.show = false;
				}, this.toast.time);
			},
			hideToast: function() {
				this.toast.show = false
				if(this.toastTimer) clearTimeout(this.toastTimer);
			},
			//---搜索栏
			toggleSearchBar: function() {
				this.searchBar.show = !this.searchBar.show;
				setTimeout(function() {
					$("#grid").height(getMainH());
					$("#grid").data('kendoGrid').refresh();
				}, 310);
			}
		},
		watch: { //观察者模式
			tableOption: {
				handler: function(val, oldVal) {
					this.resetTable();
					//								$("#grid").data('kendoGrid').dataSource.read(); //重新读取
					//								$("#grid").data('kendoGrid').refresh(); //刷新
				},
				deep: true
			},
			pageSize: function(val, oldVal) {
					$("#grid").data('kendoGrid').dataSource.pageSize(val); //分页
				}
				//					alertMsgBox:{
				//						handler:function(val, oldVal){
				//							if(val.show){
				//								setTimeout(function(){
				//									app.alertMsgBox.show=false; 
				//								},val.time);
				//							}
				//						},
				//						deep: true
				//					}
		}
	});

	function tbEventInit() {

	}
	app.getTableData("用户数据.txt");
	//下拉列选项初始化
	$("#size,#dropd").kendoDropDownList();
	//	$("#color").kendoDropDownList({
	//		dataTextField: "text",
	//		dataValueField: "value",
	//		dataSource: [{
	//			text: "Black",
	//			value: "1"
	//		}, {
	//			text: "Orange",
	//			value: "2"
	//		}, {
	//			text: "Grey",
	//			value: "3"
	//		}],
	//		index: 0,
	//		change: function(e){
	//			console.log(e.sender._old);
	//			app.searchBar.data.dpd2 = e.sender._old; //变化时绑定
	//		}
	//	});
	$("#fabric").kendoComboBox({
		dataTextField: "text",
		dataValueField: "value",
		dataSource: [{
			text: "Cotton",
			value: "1"
		}, {
			text: "Polyester",
			value: "2"
		}, {
			text: "Cotton/Polyester",
			value: "3"
		}, {
			text: "Rib Knit",
			value: "4"
		}],
		filter: "contains",
		suggest: true,
		//		index: 3,
		change: function(e) {
			console.log(e.sender._old);
			app.searchBar.data.dpd = e.sender._old; //变化时绑定
		}
	});
	//日期选择器
	kendo.culture("zh-CN"); //国际化 
	datepicker = $("#datepicker").kendoDatePicker({
		format: "yyyy-MM-dd"
			//		,value: new Date()
	});

	$("#datetimepicker").kendoDateTimePicker({
		value: new Date()
	});
	//表格
	//				$("#grid").kendoGrid(app.tableOption);
	//---自适应--
	$(window).resize(function() {
		$("#grid").height(getMainH());
		$("#grid").data('kendoGrid').refresh();
	});
	$("#grid").height(getMainH());
	//---
	if($("#main_loading").length) {
		$("#main_loading").fadeOut(300);
	}
});
//全局事件-----
//---表格
var debugData = null;
var debugData2 = null;
function onChange(arg) {
	debugData2 =arg;
	console.log(arg);
	var selected = $.map(this.select(), function(item) {
		debugData = item;
		console.log($(item));
		return $(item).text();
	});

	console.log("Selected: " + selected.length + " item(s), [" + selected[0] + "]");
}

function onDataBound(arg) {
	console.log("Grid data bound");
}

function onDataBinding(arg) {
	console.log("Grid data binding");
}

function onSorting(arg) {
	console.log("Sorting on field: " + arg.sort.field + ", direction:" + (arg.sort.dir || "none"));
}

function onFiltering(arg) {
	console.log("Filter on " + kendo.stringify(arg.filter));
}

function onPaging(arg) {
	console.log("Paging to page index:" + arg.page);
}

function onGrouping(arg) {
	console.log("Group on " + kendo.stringify(arg.groups));
}
//--自适应
function getMainH() { //获取内容主体高度
	var nBar = document.getElementsByClassName("nav-bar");
	var outerHeight = 0;
	$(".nav-bar").each(function(i, v) {
		outerHeight += $(v).outerHeight();
	});
	return window.innerHeight - outerHeight - 1;
}