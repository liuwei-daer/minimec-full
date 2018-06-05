
$(function() {

    $("#objInfoForm").validation({icon: true});
    //添加验证
    $("#add").on('click', function (event) {
        //console.info("add is in");
        if ($("#objInfoForm").valid(this) == false) {
            //console.info("校验不通过");
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
            /**************数据处理*************/
            doUpdate();
        }
    })
    searchList();
});

function doAdd() {
    console.info("验证通过。");
    var frm = $("#objInfoForm");
        var url = "/sys/param/add?v="+Math.random();
    $.post(
        url,
        frm.serialize(),
        function(data){
            if(data.success){
                searchList();
                $.scojs_message('添加成功', $.scojs_message.TYPE_OK);
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
            }
            $('#objInfoModal').modal("hide");
        }
    )

}

function doUpdate(){
	//var id=$('#id').val();
	$("#objInfoForm").validation({icon: false});
    //console.info("id ->"+id);
    $("#paramName").attr("disabled", false);
    $("#paramKey").attr("disabled", false);
	var frm = $("#objInfoForm");
    var url = "/sys/param/update?v="+Math.random();
    $.post(
            url,
            frm.serialize(),
            function(data){
                if(data.success){
                    searchList();
                    $.scojs_message('更新成功', $.scojs_message.TYPE_OK);
                } else {
                    $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                }
                $("#paramName").attr("disabled", true);
                $("#paramKey").attr("disabled", true);
               $('#objInfoModal').modal("hide");
            }
    )
    
}



function searchList() {
    var searchVal = $('#searchVal').val();
    var param = {searchVal:searchVal};
    $("#objInfoList").datagrid({
        url:'/sys/param/list',
        data:param
    });
}

function edit(id) {
    $("#objInfoForm").validation({icon: false});
    console.info("id ->"+id);
    
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/param/item/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(rs){
        	var item=rs.module;
            //alert("同步提示信息缓存操作完成:"+item.code);
            //设定值为空
        	$('#id').val(id);
            $('#paramName').val(item.paramName);
            $('#paramKey').val(item.paramKey);
            $('#paramValue').val(item.paramValue);
            $('#memo').val(item.memo);
            $("#paramName").attr("disabled", true);
            $("#paramKey").attr("disabled", true);
        }
    })
    
   
    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("修改");
    $("#add").hide();
    $("#update").show();
}

function synCache(){
	$.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/param/syncache",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            $.scojs_message('同步缓存操作完成', $.scojs_message.TYPE_OK);
        }
    })
}

/**
 * 点击添加弹出添加框
 */
function add() {
    $("#objInfoForm").validation({icon: false});
    //设定值为空
    $('#paramName').val("");
    $('#paramKey').val("");
    $('#paramValue').val("");
    $('#memo').val("");
    $("#paramName").attr("disabled", false);
    $("#paramKey").attr("disabled", false);
    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
    $("#update").hide();
}




