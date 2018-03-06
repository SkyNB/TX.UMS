<#import "/WEB-INF/layouts/iframe_layout.ftl" as layout>
<#assign bodyEnd>
<script src="${request.contextPath}/resources/pages/site/index.js"></script>
</#assign>

<@layout.iframe_layout bodyEnd=bodyEnd>
<div id="app">
    <div class="nav-bar">
        <div class="btn-group">
            <button type="button" data-target="#modal_theme_primary" class="btn btn-default" @click="page('create')"><i class="icon-plus2"></i>新增</button>
            <button type="button" class="btn btn-default" @click='onDelete'><i class="icon-cross2"></i>删除</button>
        </div>
        <div class="input-group" style="float: right;">
            <input type="text" class="form-control" placeholder="站点名称" v-model="search.name" @keyup.enter="onSearch()">
            <div class="input-group-btn">
                <button type="button" class="btn btn-default" aria-label="Help" @click="search.name='';onSearch()"><span class="icon-rotate-cw3"></span></button>
                <button type="button" class="btn btn-default" @click="onSearch"><i class="icon-search4"></i>搜索</button>
            </div>
        </div>
    </div>

    <div id="main-content">
        <div class="grid-wrapper">
            <div id="grid" class="dynamic-height"></div>
        </div>
    </div>
</div>
</@layout.iframe_layout>