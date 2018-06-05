$(function() {

    $("select").select2({
        minimumResultsForSearch: Infinity
    });
    searchList();
    $("#adminUserForm").validation({icon: true});
    //添加验证
    $(document).on('click',"#addSysUser", function (event) {

        if ($("#adminUserForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    //修改验证
    $(document).on('click',"#updateSysUser", function (event) {
        if ($("#adminUserForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    });
});

function checkForm(){
    var mobile = $('#mobile').val();
    var pat = /^1\d{10}$/;
    var pat2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/; //8位以上数字或字母，至少一个数字和字母
    if (!pat.test(mobile)) {
        $.scojs_message('手机号码格式不正确', $.scojs_message.TYPE_ERROR);
        $("#phone").focus();
        return false;
    }
    if($("#password").val()){
        if (!pat2.test($("#password").val())) {
            $.scojs_message('密码至少为8位,且包含至少一个数字和字母', $.scojs_message.TYPE_ERROR);
            $("#password").focus();
            return false;
        }
        if($("#password").val() == $("#phone").val()){
            $.scojs_message('密码不能和用户名相同', $.scojs_message.TYPE_ERROR);
            $("#password").focus();
            return false;
        }
    }
    return true;
}

function doAdd() {
    $("#addSysUser").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addSysUser").removeAttr("disabled");
        return false;
    }

    var mobile = $('#mobile').val();
    var realName = $('#realName').val();
    var password = $('#').val();
    var roles = $roleSel.val();
    var status = $(":radio[name='status']:checked").val();
    var dataObj = JSON.stringify({mobile:mobile,realName:realName,password:password,status:status,roles:roles});

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/user/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                searchList();
                $.scojs_message('用户账号添加成功', $.scojs_message.TYPE_OK);
                $('#adminUserModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#addSysUser").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('用户账号添加失败', $.scojs_message.TYPE_ERROR);
            $("#addSysUser").removeAttr("disabled");
        }
    });
}

function doUpdate(){
    $("#updateSysUser").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addSysUser").removeAttr("disabled");
        return false;
    }

    var userId = $('#userId').val();
    var mobile = $('#mobile').val();
    var realName = $('#realName').val();
    var roles = $roleSel.val();
    var status = $(":radio[name='status']:checked").val();
    var dataObj = JSON.stringify({id:userId,mobile:mobile,realName:realName,status:status,roles:roles});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/user/update",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                searchList();
                $.scojs_message('用户账号修改成功', $.scojs_message.TYPE_OK);
                $('#adminUserModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#updateSysUser").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('用户账号修改失败', $.scojs_message.TYPE_ERROR);
            $("#updateSysUser").removeAttr("disabled");
        }
    });
}

function deleteUser(id){
    bootbox.confirm({
        title: "删除",
        message: "是否删除用户账号",
        buttons: {
            confirm: {
                label: '是',
                className: 'btn-info'
            },
            cancel: {
                label: '否',
                className: 'btn-default'
            }
        },
        callback: function (result) {
            if(result){
                $.ajax({
                    processData: false,
                    type: 'GET',
                    url: "/sys/user/delete/"+id,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        if(data.success){
                            searchList();
                            $.scojs_message('删除用户账号成功', $.scojs_message.TYPE_OK);
                        } else {
                            $.scojs_message('删除用户账号失败', $.scojs_message.TYPE_ERROR);
                        }
                    },
                    error: function (data) {
                        $.scojs_message('删除用户账号失败', $.scojs_message.TYPE_ERROR);
                    }
                });
            }
        }
    });
}

function searchList() {
    var searchVal = $('#searchVal').val();
    var param = {searchVal:searchVal};
    $("#adminUserList").datagrid({
        url: "/sys/user/list",
        data:param
    });
}

function formtemplate(data){
    var templateoutput;
    if(data == 0) {
        templateoutput = template('tpl-userform',{add:true});
        $("#form-modal").html("").html(templateoutput);
    } else{
        templateoutput = template('tpl-userform',data);
        $("#form-modal").html("").html(templateoutput);
    }
    $("#adminUserForm").validation({icon: false});
    $roleSel = $("#roleSel").select2({language: 'zh-CN'});
}

/**
 * 点击添加弹出添加框
 */
function initAddSysUser() {
    $("#addSysUser").removeAttr("disabled");
    formtemplate(0);
    $("#adminUserForm").validation({icon: false});
    $("#status").prop("checked",true);
    $roleSel.val(null);
    $roles = "";
    $('#adminUserModal').modal("show");
}

function initEditSysUser(id) {
    $("#updateSysUser").removeAttr("disabled");
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/user/info/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                formtemplate(data.module);
                $roles = data.module.roles;
                $roleSel.val(data.module.roles).trigger("change");
                $('#adminUserModal').modal("show");
            } else {
                $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}




