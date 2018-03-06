<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link href="${request.contextPath}/resources/home/homeZhu/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<#--    <script src="http://cdn.bootcss.com/vue/2.1.8/vue.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>-->
    <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/vue.min.js"></script>
    <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/jquery-3.1.1.min.js"></script>
    <link href="${request.contextPath}/resources/home/homeZhu/bootstrap/css/icons/icomoon/styles.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/home/uiText/kendoui/style/kendo.common-material.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/home/uiText/kendoui/style/kendo.material.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/home/homeZhu/SideBar/float-button.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/home/homeZhu/SideBar/switch1.css" rel="stylesheet" />
    <script src="${request.contextPath}/resources/home/uiText/kendoui/js/kendo.all.min.js"></script>
    <script src="${request.contextPath}/resources/home/uiText/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        body {
            font-family: "Microsoft Yahei"
        }

        a {
            cursor: pointer;
        }

        ul,
        li {
            list-style-type: none;
            list-style-image: none;
            list-style-position: outside;
        }

        #sidebar-show~.sidebar {
            position: fixed;
            top: 0;
            bottom: 0;
            width: 200px;
            transition: 0.2s ease-out;
            -webkit-transition: 0.2s ease-out;
        }

        #sidebar-show~.bfx-left {
            left: -200px;
        }

        #sidebar-show:checked~.bfx-left {
            left: 0px;
        }

        #sidebar-show~.bfx-right {
            right: -200px;
        }

        #sidebar-show:checked~.bfx-right {
            right: 0;
        }

        #sidebar-show~#fx-sidebar {
            display: none;
            background-color: rgba(250, 250, 250, 0.4);
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
        }

        #sidebar-show:checked~#fx-sidebar {
            display: block;
        }

        #sidebar-show~.main {
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            transition: 0.2s ease-out;
            -webkit-transition: 0.2s ease-out;
            background-color: #fff;
        }

        #sidebar-show:checked~.mfx-left.nofx {
            position: fixed;
            top: 0;
            left: 200px;
            right: 0;
            bottom: 0;
        }

        #sidebar-show:checked~.mfx-right.nofx {
            position: fixed;
            top: 0;
            left: 0;
            right: 200px;
            bottom: 0;
        }

        #sidebar-show~.bfx-left ul.nav li>ul {
            left: 100%;
        }

        #sidebar-show~.bfx-right ul.nav li>ul {
            right: 100%;
        }

        #sidebar-show~.bfx-left ul.nav .icon-chevron-right {
            float: right;
        }

        #sidebar-show~.bfx-right ul.nav {
            text-align: right;
        }

        #sidebar-show~.bfx-right ul.nav .icon-chevron-right {
            float: left;
            line-height: 1.4;
            transform: rotate(180deg);
            -ms-transform: rotate(180deg);
            /* Internet Explorer */
            -moz-transform: rotate(180deg);
            /* Firefox */
            -webkit-transform: rotate(180deg);
            /* Safari 和 Chrome */
            -o-transform: rotate(180deg);
            /* Opera */
        }

        ul.nav li>ul {
            display: none;
            position: absolute;
            top: 0;
            width: 160px;
            padding: 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        }

        ul.nav li:hover>ul {
            display: block;
            opacity: 1;
            z-index: 100;
            visibility: visible;
        }

        .sidebar.LIGHT,
        .sidebar.LIGHT>.nav,
        .sidebar.LIGHT>.nav .nav,
        .sidebar.LIGHT>.nav a {
            color: #fff;
            background-color: #03a9f4
        }

        .sidebar.LIGHT>.nav a:hover,
        .sidebar.LIGHT>.nav a:focus {
            text-decoration: none;
            background-color: hsla(0, 0%, 100%, .3);
        }

        .main.LIGHT>.k-tabstrip-wrapper>#tabstrip {
            color: #fff;
            background-color: #03a9f4
        }

        .main.LIGHT .k-tabstrip-items .k-item {
            color: #fff;
            background-color: #03a9f4
        }

        .main.LIGHT .k-tabstrip-items .k-item.k-state-hover {
            background: #64b5f6;
            border-color: #ff4081;
        }

        .main.LIGHT .k-tabstrip-items .k-item.k-state-active {
            background: #2196f3;
            border-color: #ff4081;
        }

        .sidebar-button {
            -webkit-transition-duration: .28s;
            transition-duration: .28s;
            position: fixed;
            bottom: 10%;
            padding-left: 2px;
        }

        .sidebar-button>label.mu-float-button-mini {
            width: 48px;
            height: 48px;
        }

        .mfx-right~.sidebar-button {
            right: -46px;
        }

        .mfx-right~.sidebar-button:hover {
            right: -1px;
        }

        #sidebar-show:checked+.mfx-right~.sidebar-button {
            right: 204px;
        }

        .mfx-left~.sidebar-button {
            left: -46px;
        }

        .mfx-left~.sidebar-button:hover {
            left: -1px;
        }

        #sidebar-show:checked+.mfx-left~.sidebar-button {
            left: 204px;
        }
        .sidebar .logo {
            padding: 10px 15px 10px 15px;
        }

        .sidebar .logo>img {
            width: 100%;
        }

        .bfx-left {
            border-right: 1px solid #2196f3;
        }

        .bfx-right {
            border-left: 1px solid #2196f3;
        }
        /*--以下主题*/

        .CARBON {
            color: #fff;
            background-color: #474a4f
        }

        .TEAL {
            color: #fff;
            background-color: #009688
        }
        /**/

        .k-tabstrip:focus {
            -webkit-box-shadow: none;
            box-shadow: none
        }

        .k-tabstrip>.k-content {
            position: static;
            border-width: 0;
            margin: 0;
            padding: 0;
            border-top-width: 1px;
        }

        .k-tabstrip>.k-content iframe {
            width: 100%;
            height: 100%;
            display: block;
            border: none;
        }
        /*-----侧边栏用户信息显示------*/

        .category-content {
            position: relative;
            padding: 15px;
        }

        .media {
            margin-top: 20px;
            position: relative;
        }

        .media,
        .media-body {
            overflow: visible;
        }

        .media-left,
        .media-right,
        .media-body {
            position: relative;
        }

        .media-left,
        .media>.pull-left {
            padding-right: 20px;
        }

        .text-semibold {
            font-weight: 500;
        }

        .media-heading {
            margin-bottom: 2px;
            display: block;
        }

        .text-size-mini {
            font-size: 11px;
        }

        .sidebar .media .text-muted,
        .sidebar .media .media-annotation {
            color: rgba(255, 255, 255, 0.8);
        }

        .icons-list {
            margin: 0;
            padding: 0;
            list-style: none;
            line-height: 1;
            font-size: 0;
        }

        .icons-list>li:first-child {
            margin-left: 0;
        }

        .icons-list>li>a {
            color: inherit;
            display: block;
            opacity: 1;
            filter: alpha(opacity=100);
        }

        .icons-list>li>a>i {
            top: 0;
        }
        /*--遮罩层--*/
        .modal{
            border-radius:2px ;
            box-shadow: 0 19px 60px rgba(0,0,0,.298039),0 15px 20px rgba(0,0,0,.219608);
        }
        .modal-dialog{
            z-index: 1050;
        }

    </style>
</head>

<body>
<div id="app">
    <input id="sidebar-show" type="checkbox" checked="checked" hidden="hidden" style="display: none;" />
    <div class="main LIGHT" v-bind:class="{'mfx-left':sidebar.isLeft,'mfx-right':!sidebar.isLeft,nofx: !sidebar.isFx }">
        <div id="tabstrip">
            <ul class="">
                <li class="k-state-active ">
                    <i class="icon-home" style="font-size: 12px;"></i>&nbsp;主页
                </li>
            </ul>
            <div>
                <iframe src="${request.contextPath}/ums/home"></iframe>
                <#--<iframe src="../错误页面/404.html"></iframe>-->
            </div>
        </div>
    </div>
    <label v-if="sidebar.isFx==true" for="sidebar-show" id="fx-sidebar"></label>
    <!---->
    <div class="sidebar-button">
        <label for="sidebar-show" tabindex="0" class="mu-float-button mu-float-button-mini" style="-webkit-user-select: none; outline: none; cursor: pointer; -webkit-appearance: none;">
            <div>
                <div class="mu-float-button-wrapper">
                    <i class="icon-paragraph-justify3"></i>
                </div>
            </div>
        </label>
    </div>
    <!---->
    <div class="sidebar LIGHT" v-bind:class="{'bfx-left':sidebar.isLeft,'bfx-right':!sidebar.isLeft}">
        <div class="logo">
            <img src="${request.contextPath}/resources/home/homeZhu/SideBar/logo2.png" />
        </div>
        <div class="sidebar-user">
            <div class="category-content">
                <div class="media">
                    <div class="media-body">
                        <span class="media-heading text-semibold">
                            <#if ErpUser>
                                <div style="position: absolute; bottom: 0;left: 0;right: 0;padding: 12px 15px;text-align: center;">${ErpUser.userName}</div>
                            </#if>
                        </span>
                        <div class="text-size-mini text-muted">
                            <i class="icon-pin text-size-small">
                           </i> &nbsp;
                        <#if ErpUser>
                            ${ErpUser.realName}
                        </#if>

                        </div>
                    </div>

                    <div class="media-right media-middle">
                        <ul class="icons-list">
                            <li>
                                <a href="javascript:void(0)" v-on:click="settingShow=true"><i class="icon-cog3"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <ul class="nav">
            <li style="margin-top: 6px;margin-bottom: 10px;">
                <span style="padding: 12px 15px;min-height: 44px;font-weight: 500;color: #eee;">菜单</span>
            </li>
            <li v-for="(itm,index) in menuBar">
                <a href=""><span>{{itm.menuname}}</span><i class="icon-chevron-right"></i></a>
                <ul class="nav">
                    <li class="nav" v-for="(itm2,index2) in itm.chindren" v-if="itm2&&itm2.chindren">
                        <a href="#" class="has-ul">{{itm2.menuname}}<i class="icon-chevron-right"></i></a>
                        <dropdown-menu v-bind:chindren="itm2.chindren"></dropdown-menu>
                    </li>
                    <li v-else>
                        <a onclick="addTab(this)" v-bind:data-id="itm2.menuid" v-bind:data-href="itm2.menuurl">{{itm2.menuname}}</a>
                    </li>
                </ul>
            </li>
        </ul>
    <#if ErpUser>
        <div style="position: absolute; bottom: 0;left: 0;right: 0;padding: 12px 15px;text-align: center;">
                <i class="icon-exit3"></i>&nbsp;退出登录
        </div>
    </#if>
    </div>
    <!-- Modal -->
    <div class="modal fade" v-bind:class="{'in':settingShow}" v-bind:style="{'display':(settingShow?'block':'none')}">
        <div class="modal-backdrop fade in" v-on:click="settingShow=false"></div>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header" style="border-bottom: 1px solid #f3f3f3;">
                    <h4 class="modal-title" id="myModalLabel">设置</h4>
                </div>
                <div class="modal-body" style="text-align: center;border-bottom: 1px solid #f3f3f3;">
                    <h4>布局设置</h4>
                    <br />
                    <!---->
                    <label class="mu-switch">
                        <input type="checkbox" hidden="hidden"  v-model="sidebar.isFx">
                        <div class="mu-switch-wrapper"><!---->
                            <div class="mu-switch-container">
                                <div class="mu-switch-track"></div>
                                <div class="mu-switch-thumb"> </div>
                            </div>
                            <div class="mu-switch-label">主页面固定显示({{sidebar.isFx?'是':'否'}})</div>
                        </div>
                    </label>
                    <br />
                    <label class="mu-switch">
                        <input type="checkbox" hidden="hidden"  v-model="sidebar.isLeft">
                        <div class="mu-switch-wrapper"><!---->
                            <div class="mu-switch-container">
                                <div class="mu-switch-track"></div>
                                <div class="mu-switch-thumb"> </div>
                            </div>
                            <div class="mu-switch-label">菜单居左边显示({{sidebar.isLeft?'是':'否'}})</div>
                        </div>
                    </label>

                    <!---->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" v-on:click="settingShow=false">关闭</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    Vue.component('dropdown-menu', {
        props: ['chindren'],
        methods: {
            increment: function(i, index) {
                main.addNewTab(i, index);
            }
        },
        template: '<ul class="nav">' +
        '    <li  v-for="(itm3,index3) in chindren" v-if="itm3&&itm3.chindren"><a href="#">{{itm3.menuname}}<i class="icon-chevron-right"></i></a>' +
        '        <dropdown-menu v-bind:chindren="itm3.chindren"></dropdown-menu>' +
        '    </li>' +
        '    <li v-else><a onclick="addTab(this)" v-bind:data-id="itm3.menuid" v-bind:data-href="itm3.menuurl">{{itm3.menuname}}</a>' +
        '    </li>' +
        '</ul>'
    });

    function transData(a, idStr, pidStr, chindrenStr) {
        var r = [],
                hash = {},
                id = idStr,
                pid = pidStr,
                children = chindrenStr,
                i = 0,
                j = 0,
                len = a.length;
        for(; i < len; i++) {
            hash[a[i][id]] = a[i];
        }
        for(; j < len; j++) {
            var aVal = a[j],
                    hashVP = hash[aVal[pid]];
            if(hashVP) {
                !hashVP[children] && (hashVP[children] = []);
                hashVP[children].push(aVal);
            } else {
                r.push(aVal);
            }
        }
        return r;
    }
    function str2JSON(str){
        if(typeof(str)=="string"){
            str=JSON.parse(str);
        }
        return str;

    }
    var app = new Vue({
        el: "#app",
        data: {
            menuBar: transData(${userData}, "menuid", "parentid", "chindren"),
            sidebar: {
                isFx: false, //是否固定侧边栏
                isLeft: true //固定左边显示
            },
            settingShow:false
        },
    });
//    app.getMenuInfo();
    var tabStrip = $("#tabstrip").kendoTabStrip().data("kendoTabStrip");
    $(".removeItem").click(function(e) {
        var tab = tabStrip.select(),
                otherTab = tab.next();
        otherTab = otherTab.length ? otherTab : tab.prev();

        tabStrip.remove(tab);
        tabStrip.select(otherTab);
    });

    $(".appendItem").click(function(e) {
        if(e.type != "keypress" || kendo.keys.ENTER == e.keyCode)
            tabStrip.append({
                text: $("#appendText").val(),
                content: "<br>"
            });
    });

    //-----选项卡
    var tabStrip = $("#tabstrip").kendoTabStrip({ //创建
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
            content: "<iframe src='" + 'http://erp.ym.com/' + $(dom).attr("data-href") + "'></iframe>"
            //						contentUrl:'http://erp.ym.com/'+$(dom).attr("data-href")
        });
        configureCloseTab();
        $(".k-tabstrip-items li.k-item")[$(".k-tabstrip-items li.k-item").length - 1].click();

    }
    var tmpTimeout = null;

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
        return window.innerHeight - $("#tabstrip ul.k-tabstrip-items").outerHeight() - 2;
    }
    $(window).resize(function() {
        $(".k-content").height(getMainH());
    });
    $(document).ready(function() {
        $(".k-content").height(getMainH());
    });
</script>
</body>

</html>