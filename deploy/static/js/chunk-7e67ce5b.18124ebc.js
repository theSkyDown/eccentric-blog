(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7e67ce5b"],{3528:function(e,t,n){"use strict";n.d(t,"e",(function(){return l})),n.d(t,"f",(function(){return a})),n.d(t,"d",(function(){return o})),n.d(t,"g",(function(){return s})),n.d(t,"c",(function(){return i})),n.d(t,"a",(function(){return u})),n.d(t,"b",(function(){return c}));var r=n("b775");function l(){return Object(r["a"])({url:"/role/listAllRole",method:"get"})}function a(e){return Object(r["a"])({url:"/role/list",method:"get",params:e})}function o(e){return Object(r["a"])({url:"/role/"+e,method:"get"})}function s(e){return Object(r["a"])({url:"/role/update",method:"put",data:e})}function i(e){return Object(r["a"])({url:"/role/delete/"+e,method:"delete"})}function u(e){return Object(r["a"])({url:"/role/add",method:"post",data:e})}function c(e,t){var n={id:e,status:t};return Object(r["a"])({url:"/role/changeStatus",method:"put",data:n})}},"70eb":function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("el-form",{ref:"queryForm",attrs:{model:e.queryParams,inline:!0}},[n("el-form-item",{attrs:{label:"角色名称",prop:"roleName"}},[n("el-input",{staticStyle:{width:"240px"},attrs:{placeholder:"请输入角色名称",clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.roleName,callback:function(t){e.$set(e.queryParams,"roleName",t)},expression:"queryParams.roleName"}})],1),n("el-form-item",{attrs:{label:"状态",prop:"status"}},[n("el-select",{staticStyle:{width:"240px"},attrs:{placeholder:"角色状态",clearable:"",size:"small"},model:{value:e.queryParams.status,callback:function(t){e.$set(e.queryParams,"status",t)},expression:"queryParams.status"}},[n("el-option",{key:0,attrs:{label:"正常",value:0}}),n("el-option",{key:1,attrs:{label:"停用",value:1}})],1)],1),n("el-form-item",[n("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")])],1)],1),n("el-row",{staticClass:"mb8",attrs:{gutter:10}},[n("el-col",{attrs:{span:1.5}},[n("el-button",{attrs:{type:"primary",plain:"",icon:"el-icon-plus",size:"mini"},on:{click:e.handleAdd}},[e._v("新增")])],1),n("el-col",{attrs:{span:1.5}},[n("el-button",{attrs:{type:"danger",plain:"",icon:"el-icon-delete",size:"mini",disabled:e.multiple},on:{click:e.handleDelete}},[e._v("删除")])],1)],1),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.roleList},on:{"selection-change":e.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),n("el-table-column",{attrs:{label:"角色编号",prop:"id",width:"120"}}),n("el-table-column",{attrs:{label:"角色名称",prop:"roleName","show-overflow-tooltip":!0,width:"150"}}),n("el-table-column",{attrs:{label:"权限字符",prop:"roleKey","show-overflow-tooltip":!0,width:"150"}}),n("el-table-column",{attrs:{label:"显示顺序",prop:"roleSort",width:"100"}}),n("el-table-column",{attrs:{label:"状态",align:"center",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-switch",{attrs:{"active-value":"0","inactive-value":"1"},on:{change:function(n){return e.handleStatusChange(t.row)}},model:{value:t.row.status,callback:function(n){e.$set(t.row,"status",n)},expression:"scope.row.status"}})]}}])}),n("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("span",[e._v(e._s(e.parseTime(t.row.createTime)))])]}}])}),n("el-table-column",{attrs:{label:"操作",align:"center","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return 1!==t.row.id&&2!==t.row.id?[n("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-edit"},on:{click:function(n){return e.handleUpdate(t.row)}}},[e._v("修改")]),n("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-delete"},on:{click:function(n){return e.handleDelete(t.row)}}},[e._v("删除")])]:void 0}}],null,!0)})],1),n("el-pagination",{attrs:{"page-size":e.queryParams.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total,"page-sizes":[10,20,30,40],"current-page":e.queryParams.pageNum},on:{"update:pageSize":function(t){return e.$set(e.queryParams,"pageSize",t)},"update:page-size":function(t){return e.$set(e.queryParams,"pageSize",t)},"update:currentPage":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:current-page":function(t){return e.$set(e.queryParams,"pageNum",t)},"current-change":e.getList,"size-change":e.getList}}),n("el-dialog",{attrs:{title:e.title,visible:e.open,width:"500px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[n("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"100px"}},[n("el-form-item",{attrs:{label:"角色名称",prop:"roleName"}},[n("el-input",{attrs:{disabled:e.roleNameDisabled,placeholder:"请输入角色名称"},model:{value:e.form.roleName,callback:function(t){e.$set(e.form,"roleName",t)},expression:"form.roleName"}})],1),n("el-form-item",{attrs:{prop:"roleKey"}},[n("span",{attrs:{slot:"label"},slot:"label"},[n("el-tooltip",{attrs:{content:"控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)",placement:"top"}},[n("i",{staticClass:"el-icon-question"})]),e._v(" 权限字符 ")],1),n("el-input",{attrs:{placeholder:"请输入权限字符"},model:{value:e.form.roleKey,callback:function(t){e.$set(e.form,"roleKey",t)},expression:"form.roleKey"}})],1),n("el-form-item",{attrs:{label:"角色顺序",prop:"roleSort"}},[n("el-input-number",{attrs:{"controls-position":"right",min:1},model:{value:e.form.roleSort,callback:function(t){e.$set(e.form,"roleSort",t)},expression:"form.roleSort"}})],1),n("el-form-item",{attrs:{label:"状态"}},[n("el-radio-group",{model:{value:e.form.status,callback:function(t){e.$set(e.form,"status",t)},expression:"form.status"}},[n("el-radio",{key:"0",attrs:{label:"0"}},[e._v("正常")]),n("el-radio",{key:"1",attrs:{label:"1"}},[e._v("停用")])],1)],1),n("el-form-item",{attrs:{label:"菜单权限"}},[n("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeExpand(t)}},model:{value:e.menuExpand,callback:function(t){e.menuExpand=t},expression:"menuExpand"}},[e._v("展开/折叠")]),n("el-checkbox",{on:{change:function(t){return e.handleCheckedTreeNodeAll(t)}},model:{value:e.menuNodeAll,callback:function(t){e.menuNodeAll=t},expression:"menuNodeAll"}},[e._v("全选/全不选")]),n("el-tree",{ref:"menu",staticClass:"tree-border",attrs:{data:e.menuOptions,"show-checkbox":"","node-key":"id","check-strictly":!1,"empty-text":"加载中，请稍候",props:e.defaultProps}})],1),n("el-form-item",{attrs:{label:"备注"}},[n("el-input",{attrs:{type:"textarea",placeholder:"请输入内容"},model:{value:e.form.remark,callback:function(t){e.$set(e.form,"remark",t)},expression:"form.remark"}})],1)],1),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),n("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},l=[],a=(n("d81d"),n("3528")),o=n("1f27"),s={name:"Role",data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,total:0,roleList:[],title:"",open:!1,menuOptions:[],menuExpand:!1,menuNodeAll:!1,queryParams:{pageNum:1,pageSize:10,roleName:void 0,roleKey:void 0,status:void 0},form:{},defaultProps:{children:"children",label:"label"},rules:{roleName:[{required:!0,message:"角色名称不能为空",trigger:"blur"}],roleKey:[{required:!0,message:"权限字符不能为空",trigger:"blur"}],roleSort:[{required:!0,message:"角色顺序不能为空",trigger:"blur"}]},roleNameDisabled:!1}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.loading=!0,Object(a["f"])(this.queryParams).then((function(t){e.roleList=t.rows,e.total=t.total,e.loading=!1}))},getMenuTreeselect:function(){var e=this;Object(o["g"])().then((function(t){e.menuOptions=t}))},getMenuAllCheckedKeys:function(){var e=this.$refs.menu.getCheckedKeys(),t=this.$refs.menu.getHalfCheckedKeys();return e.unshift.apply(e,t),e},getRoleMenuTreeselect:function(e){var t=this;return Object(o["f"])(e).then((function(e){return t.menuOptions=e.menus,e}))},handleStatusChange:function(e){var t=this,n="0"===e.status?"启用":"停用";this.$modal.confirm('确认要"'+n+'""'+e.roleName+'"角色吗？').then((function(){return Object(a["b"])(e.id,e.status)})).then((function(){t.$modal.msgSuccess(n+"成功")})).catch((function(){e.status="0"===e.status?"1":"0"}))},cancel:function(){this.open=!1,this.reset()},reset:function(){void 0!==this.$refs.menu&&this.$refs.menu.setCheckedKeys([]),this.roleNameDisabled=!1,this.menuNodeAll=!1,this.menuExpand=!1,this.form={id:void 0,roleName:void 0,roleKey:void 0,roleSort:0,status:"0",menuIds:[],remark:void 0},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!==e.length,this.multiple=!e.length},handleCheckedTreeExpand:function(e){for(var t=this.menuOptions,n=0;n<t.length;n++)this.$refs.menu.store.nodesMap[t[n].id].expanded=e},handleCheckedTreeNodeAll:function(e){this.$refs.menu.setCheckedNodes(e?this.menuOptions:[])},handleAdd:function(){this.reset(),this.getMenuTreeselect(),this.open=!0,this.title="添加角色"},handleUpdate:function(e){var t=this;this.reset();var n=e.id||this.ids;this.getMenuTreeselect(n),Object(a["d"])(n).then((function(e){t.form=e,t.open=!0,t.roleNameDisabled=!0,t.$nextTick((function(){t.$refs.menu.setCheckedKeys(e.menuIds)})),t.title="修改角色"}))},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(void 0!==e.form.id?(e.form.menuIds=e.getMenuAllCheckedKeys(),Object(a["g"])(e.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.open=!1,e.getList()}))):(e.form.menuIds=e.getMenuAllCheckedKeys(),Object(a["a"])(e.form).then((function(t){e.$modal.msgSuccess("新增成功"),e.open=!1,e.getList()}))))}))},handleDelete:function(e){var t=this,n=e.id||this.ids;this.$modal.confirm('是否确认删除角色编号为"'+n+'"的数据项？').then((function(){return Object(a["c"])(n)})).then((function(){t.getList(),t.$modal.msgSuccess("删除成功")})).catch((function(){}))}}},i=s,u=n("2877"),c=Object(u["a"])(i,r,l,!1,null,null,null);t["default"]=c.exports}}]);