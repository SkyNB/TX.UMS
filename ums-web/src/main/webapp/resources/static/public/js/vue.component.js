//Vue 模板
//遮罩层输入框
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
		placeholder: {
			type: String,
			default: ""
		},
		id: {
			type: String,
			default: ""
		},
		msg: {
			type: String,
			default: ""
		},
		msgClass: {
			type: String,
			default: ""
		},
		readonly:{
			type:Boolean,
			default:false
		},
		disabled:{
			type:Boolean,
			default:false
		}
	},
	methods: {
		updateValue: function (value) {
			/*this.$refs.input.value = value;过滤值后赋值*/
			this.$emit('input', value); /*通过 input 事件发出数值*/
		}
	},
	template: '<div class="form-group form-group--float" v-bind:class="{\'form-group--active\':value.length>0}">' +
	'    <input class="form-control input-sm" :id="id" :readonly="readonly" :disabled="disabled" :placeholder="placeholder" :type="type"   :value="value" @input="updateValue($event.target.value)" @change="updateValue($event.target.value)" @blur="updateValue($event.target.value)">' +
	'    <label>{{label}}<small :class="msgClass" v-html="msg"></small></label>	<i class="form-group__bar"></i>' +
	'</div>'
});
//复选框
Vue.component('sb-checkbox', {
	props: {
		label: { /*显示文字*/
			type: String,
			default: ''
		},
		value: { /*绑定值*/
			type: String,
			default: ''
		},
		id: {
			type: String,
			default: ""
		},
		readonly: {
			type: String,
			default: ""
		}
	},
	methods: {
		updateValue: function(value) {
			this.$emit('input', value); /*通过 input 事件发出数值*/
		}
	},
	template: '<div class="checkbox">'+
	'    <label><input type="checkbox" :id="id" :value="value" @input="updateValue($event.target.value)" @change="updateValue($event.target.value)" @blur="updateValue($event.target.value)"><i class="input-helper"></i>{{label}}</label>'+
	'</div>'
});