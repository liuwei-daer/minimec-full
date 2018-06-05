$(function () {


    //日期
    $("#startTime").val(moment().subtract(30, 'days').format('YYYY-MM-DD'));
    $("#endTime").val(moment().format('YYYY-MM-DD'));
    $("#timePicker").val(moment().subtract(30, 'days').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'));
    $("#timePicker").datetimeInit(
        {
            'singleDatePicker':false,
            'maxDate':moment().format('YYYY-MM-DD')
        },
        $("#startTime"),$("#endTime")
    );
    $('#appId').select2({language: 'zh-CN',minimumResultsForSearch: Infinity});

    searchList();



});

function searchList() {
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var userName = $('#userName').val();
    var userMobile = $('#userMobile').val();
    var ipAddr = $('#ipAddr').val();
    var module = $('#module').val();
    var mean = $('#mean').val();
    var fnction = $('#function').val();
    var param = {
        startTime:startTime,
        endTime:endTime,
        userName:userName,
        userMobile:userMobile,
        ipAddr:ipAddr,
        module:module,
        mean:mean,
        function:fnction
    }
    $("#logManageList").datagrid({
        url:'/sys/operatLog/list',
        data:param
    });
}




