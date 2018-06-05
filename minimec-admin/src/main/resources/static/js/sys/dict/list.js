$(function () {

    searchList();

    $("select").select2();

    $(this).find("form").validation({icon: true});

    $("#objInfoModal").on('hidden.bs.modal',function(){
        $(this).find("form")[0].reset();
        $(this).find("form").validation();
    });

    $("#add").on('click', function (event) {
        //console.info("add is in");
        if ($("#objInfoForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    })
    //修改验证
    $("#update").on('click', function (event) {
        if ($("#objInfoForm").valid(this) == false) {
            return false;
        } else {
            doUpdate();
        }
    })

});

function searchList() {
    var searchVal = $('#searchVal').val();
    var param = {searchVal:searchVal};
    $("#objInfoList").datagrid({
        url:'/sys/dict/list',
        data:param
    });
}

/**
 * 点击添加弹出添加框
 */
function add() {
    $("#objInfoForm").validation({icon: false});
    //设定值为空
    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
    $("#update").hide();
}

function edit(id) {
    $("#objInfoForm").validation({icon: false});
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/tip/item/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(rs){
            var item=rs.module;
            //设定值为空
            $('#id').val(id);
            $("#roleId").val(data.module.id);//角色id
            $("#roleName").val(data.module.roleName);
            $("#roleType").val(data.module.roleType);
            $("#roleForm").validation({icon: false});

            if(data.module.status==1){
                $("#status").prop("checked",true);
            } else {
                $("#unstatus").prop("checked",true);
            }
        }
    })

    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("修改");
    $("#add").hide();
    $("#update").show();
}

function doUpdate(){
    $("#objInfoForm").validation({icon: false});
    var frm = $("#objInfoForm");
    var url = "/sys/tip/update?v="+Math.random();
    $.post(
        url,
        frm.serialize(),
        function(data){
            if(data.success){
                $.scojs_message('保存成功', $.scojs_message.TYPE_OK);
                $("#objInfoModal").modal("hide");
                searchList();
            }else{
                $.scojs_message(data.module.errorMsg, $.scojs_message.TYPE_ERROR);
            }
        }
    )

}

function doAdd() {
    var frm = $("#objInfoForm");
    var url = "/sys/tip/add?v="+Math.random();
    $.post(
        url,
        frm.serialize(),
        function(data){
            if(data.success){
                $.scojs_message('保存成功', $.scojs_message.TYPE_OK);
                $("#objInfoModal").modal("hide");
                searchList();
            }else{
                $.scojs_message(data.module.errorMsg, $.scojs_message.TYPE_ERROR);
            }
        }
    )
}

function synCache(){
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/tip/syncache",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            bootbox.alert("同步提示信息缓存操作完成");
        }
    })
}




