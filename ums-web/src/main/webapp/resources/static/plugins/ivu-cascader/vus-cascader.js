Vue.component('Casitem', {
	props: {
		data: Object,
		prefixCls: String,
		tmpItem: Object
	},
	computed: {
		classes: function() {
			return [
				`${this.prefixCls}-menu-item`, {
					[`${this.prefixCls}-menu-item-active`]: (this.tmpItem ? (this.tmpItem.value == this.data.value) : false),
					[`${this.prefixCls}-menu-item-disabled`]: this.data.disabled
				}
			];
		}
	},
	methods: {
		handleClickItem: function() {
			this.$emit('handleClickItem');
		},
		handleHoverItem: function() {
			this.$emit('handleHoverItem');
		}
	},
	template: '<li :class="classes" v-on:click="handleClickItem" v-on:mouseenter="handleHoverItem">{{ data.label }}<i v-if="data.children && data.children.length>0" class="ivu-icon ivu-icon-ios-arrow-right"></i></li>'
});

Vue.component('Caspanel', {
	props: {
		data: {
			type: Object,
			default: function() {
				return [];
			}
		},
		disabled: {
			type: Boolean,
			default: false
		},
		changeOnSelect: Boolean,
		trigger: {
			type: String,
			default: 'click'
		},
		prefixCls: {
			type: String,
			default: 'ivu-cascader'
		},
		lv: {
			type: Number,
			default: function() {
				return 0;
			}
		},
		result: {
			type: Object,
			default: []
		},
	},
	data: function() {
		return {
			tmpItem: {}
		};
	},
	template: '<ul v-if="(data && data.length)" :class="[prefixCls + \'-menu\']" >' + //
		'<Casitem v-for="item in data" :prefix-cls="prefixCls" :data="item" :tmp-item="tmpItem"   @handleClickItem="handleClickItem(item)"  @handleHoverItem="handleHoverItem(item)"></Casitem>' +
		'</ul>',
	methods: {
		handleClickItem: function(item) {
			if(this.trigger != 'click' && item.children) return;
			this.handleTriggerItem(item);
		},
		handleHoverItem: function(item) {
			if(this.trigger != 'hover' || !item.children) return;
			this.handleTriggerItem(item);
		},
		handleTriggerItem: function(item, fromInit = false) {
			if(item.disabled) return;
			this.tmpItem = this.getBaseItem(item);
			this.updateResult();
			var tlv = this.lv + 1;
			if(item.children && item.children.length) {
				Vue.set(this.$parent.data, tlv, item.children);
				tlv = tlv + 1;
				this.$parent.onResultChange(false, this.changeOnSelect, fromInit);
			} else {
				this.$parent.onResultChange(true, this.changeOnSelect, fromInit);
			}
			for(; tlv < this.$parent.data.length; tlv++) {
				Vue.delete(this.$parent.data, tlv);
			}
		},
		updateResult: function() {
			this.result = this.result.slice(0, this.lv).concat([this.tmpItem]);
			this.$parent.updateResult(this.result);
		},
		getBaseItem: function(item) {
			let backItem = Object.assign({}, item);
			if(backItem.children) {
				delete backItem.children;
			}
			return backItem;
		},
		onClear: function() {
			this.tmpItem = {};
		}
	}
});
const prefixCls = 'ivu-cascader';
Vue.component('Cascader', {
	props: {
		data: {
			type: Array,
			default: []
		},
		dataBak: {
			type: Array,
			default: []
		},
		value: {
			type: Array,
			default: []
		},
		disabled: {
			type: Boolean,
			default: false
		},
		clearable: {
			type: Boolean,
			default: true
		},
		placeholder: {
			type: String,
			default: '请选择'
		},
		size: {
			type: String,
			default: 'large'
		},
		trigger: {
			type: String,
			default: 'click'
		},
		changeOnSelect: {
			type: Boolean,
			default: false
		},
		renderFormat: {
			type: Function,
			default(label) {
				return label.join(' / ');
			}
		}
	},
	data: function() {
		return {
			prefixCls: prefixCls,
			visible: false,
			selected: [],
			tmpSelected: [],
			updatingValue: false
		};
	},
	computed: {
		classes: function() {
			return [
				`${prefixCls}`, {
					[`${prefixCls}-show-clear`]: this.showCloseIcon,
					[`${prefixCls}-visible`]: this.visible,
					[`${prefixCls}-disabled`]: this.disabled
				}
			];
		},
		showCloseIcon: function() {
			return this.value && this.value.length && this.clearable;
		},
		displayRender: function() {
			let label = [];
			for(let i = 0; i < this.selected.length; i++) {
				label.push(this.selected[i].label);
			}
			return this.renderFormat(label, this.selected);
		}
	},
	template: '<div  :class="classes"  v-clickoutside="handleClose"  >' +
		'    <div :class="[prefixCls + \'-rel\']" @click="toggleOpen">' +
		'        <div class="ivu-input-wrapper ivu-input-type">	<i class="ivu-icon ivu-icon-load-c ivu-load-loop fade-transition ivu-input-icon ivu-input-icon-validate"></i>' +
		'            <input class="ivu-input" type="text" :placeholder="placeholder" readonly="" :value="displayRender" >' +
		'        </div>	<i :class="[prefixCls + \'-arrow\',\'ivu-icon\',\'ivu-icon-ios-close\']" v-show="showCloseIcon"  @click="clearSelect"></i>' +
		'        <i  :class="[prefixCls + \'-arrow\',\'ivu-icon\',\'ivu-icon-arrow-down-b\']"></i>' +
		'    </div>' +
		'    <div v-show="visible" transition="slide-up" class="ivu-select-dropdown slide-up-transition" >' +
		'        <div>' +
		'            <Caspanel v-for="(itm,index) in data" :lv="index"  :prefix-cls="prefixCls" :data="itm" :disabled="disabled" :result="tmpSelected" :trigger="trigger"></Caspanel>' +
		'        </div>' +
		'    </div>' +
		'</div>',
	methods: {
		clearSelect: function() {
			const oldVal = JSON.stringify(this.value);
			this.value = this.selected = this.tmpSelected = [];
			this.handleClose();
			this.emitValue(this.value, oldVal);
			this.$children[0].onClear();
			this.data = [this.data.length > 0 ? this.data[0] : []];
		},
		handleClose: function() {
			this.visible = false;
		},
		toggleOpen: function() {
			if(this.visible) {
				this.handleClose();
			} else {
				this.onFocus();
			}
		},
		onFocus: function() {
			this.visible = true;
			if(!this.value.length) {
				console.log("onFocus");
			}
		},
		updateResult: function(result) {
			this.tmpSelected = result;
		},
		updateSelected: function(init = false) {
			if(!this.changeOnSelect || init) {
				console.log("updateSelected");
			}
		},
		emitValue: function(val, oldVal) {
			if(JSON.stringify(val) !== oldVal) {
				this.$emit('on-change', this.value, JSON.parse(JSON.stringify(this.selected)));
				this.onFormChange(this.value, JSON.parse(JSON.stringify(this.selected)));
			}
		},
		onResultChange: function(lastValue, changeOnSelect, fromInit) {
			if(lastValue || changeOnSelect) {
				const oldVal = JSON.stringify(this.value);
				this.selected = this.tmpSelected;
				let newVal = [];
				this.selected.forEach((item) => {
					newVal.push(item.value);
				});
				if(!fromInit) {
					this.updatingValue = true;
					this.value = newVal;
					this.emitValue(this.value, oldVal);
				}
			}
			if(lastValue && !fromInit) {
				this.handleClose();
			}
		},
		onFormBlur: function() {
			return false;
		},
		onFormChange: function() {
			return false;
		}
	},
	watch: {
		visible(val) {
			if(val) {
				if(this.value.length) {
					this.updateSelected();
				}
			}
		},
		value() {
			if(this.updatingValue) {
				this.updatingValue = false;
				return;
			}
			this.updateSelected(true);
		}
	}
});